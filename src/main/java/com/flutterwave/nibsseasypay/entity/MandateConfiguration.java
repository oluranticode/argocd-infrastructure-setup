package com.flutterwave.nibsseasypay.entity;

import com.flutterwave.nibsseasypay.converter.SensitiveDataConverter;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 11/9/22   - 09 - 7:11 AM$
 */


@Entity
@Table(name = "mandate_configuration")
public class MandateConfiguration {

  private Integer id;

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer getId() {
    return id;
  }
  private void setId(Integer id) {
    this.id = id;
  }

  private String username;



  @Basic
  @Column(name = "username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  private String appUser;

  @Basic
  @Column(name = "app_user")
  public String getAppUser() {
    return appUser;
  }

  public void setAppUser(String appUser) {
    this.appUser = appUser;
  }

  private String billerId;
  @Basic
  @Column(name = "biller_id")
  public String getBillerId() {
    return billerId;
  }

  public void setBillerId(String billerId) {
    this.billerId = billerId;
  }


  @Basic
  @Column(name = "password")
  @Convert(converter = SensitiveDataConverter.class)
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  @Basic
  @Column(name = "api_key")
  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  @Basic
  @Column(name = "grant_type")
  public String getGrantType() {
    return grantType;
  }


  public void setGrantType(String grantType) {
    this.grantType = grantType;
  }

  @Basic
  @Column(name = "client_secret")
  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  @Basic
  @Column(name = "client_id")
  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  @Basic
  @Column(name = "scope")
  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  private String grantType;
  private String clientSecret;
  private String clientId;
  private String scope;


  private String password;
  private String apiKey;

  private Timestamp createdAt;
  private Timestamp updatedAt;

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
  @PreUpdate
  private void beforeUpdate() {
    this.updatedAt = new Timestamp(new Date().getTime());
  }
}
