package com.flutterwave.nibsseasypay.entity;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

/**
 * @author Oluwafemi
 */
@Entity
public class Token {

  private Integer id;
  private String token;
  private Integer configurationId;
  private String refreshToken;
  private String tokenExpiry;
  private Timestamp createdAt;
  private Timestamp updatedAt;


  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Basic
  @Column(name = "configuration_id")
  public Integer getConfigurationId() {
    return configurationId;
  }

  public void setConfigurationId(Integer configurationId) {
    this.configurationId = configurationId;
  }

  @Basic
  @Column(name = "token")
  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Basic
  @Column(name = "refresh_token")
  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  @Basic
  @Column(name = "token_expiry")
  public String getTokenExpiry() {
    return tokenExpiry;
  }

  public void setTokenExpiry(String tokenExpiry) {
    this.tokenExpiry = tokenExpiry;
  }

  @Basic
  @Column(name = "created_at")
  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  @Basic
  @Column(name = "updated_at")
  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

  @PrePersist
  public void beforeSave() {
    this.createdAt = new Timestamp(new Date().getTime());
  }


}
