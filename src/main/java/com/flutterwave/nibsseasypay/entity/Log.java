package com.flutterwave.nibsseasypay.entity;

import com.flutterwave.nibsseasypay.converter.MaskLogSensitiveData;
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
 * Email aminu@flutterwavego.com Date 08/06/2021   - 08 - 12:13$
 */
@Entity
@Table(name = "log")
public class Log {
  private Integer id;
  private String reference;
  private String operationType;
  private String requestBody;
  private String responseBody;
  private String responseCode;
  private String status;
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

  private String appUser;
  @Basic
  @Column(name = "app_user", nullable = false)
  public String getAppUser() {
    return appUser;
  }

  public void setAppUser(String appUser) {
    this.appUser = appUser;
  }


  @Basic
  @Column(name = "reference")
  public String getReference() {
    return reference;
  }
  public void setReference(String reference) {
    this.reference = reference;
  }
  @Basic
  @Column(name = "operation_type", length = 1024)
  public String getOperationType() {
    return operationType;
  }
  public void setOperationType(String operationType) {
    this.operationType = operationType;
  }
  @Basic
  @Column(name = "request_body", length = 10240)

  @Convert(converter = MaskLogSensitiveData.class)
  public String getRequestBody() {
    return requestBody;
  }
  public void setRequestBody(String requestBody) {
    this.requestBody = requestBody;
  }
  @Basic
  @Column(name = "response_body", length = 10240)
  @Convert(converter = MaskLogSensitiveData.class)
  public String getResponseBody() {
    return responseBody;
  }
  public void setResponseBody(String responseBody) {
    this.responseBody = responseBody;
  }
  @Basic
  @Column(name = "response_code", length = 100)
  public String getResponseCode() {
    return responseCode;
  }
  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }
  @Basic
  @Column(name = "status", length = 16)
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
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
  @PreUpdate
  private void beforeUpdate() {
    this.updatedAt = new Timestamp(new Date().getTime());
  }
}