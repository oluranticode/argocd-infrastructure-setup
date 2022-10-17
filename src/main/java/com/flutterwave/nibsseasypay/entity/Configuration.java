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
import org.springframework.stereotype.Component;

/**
 * @author Abdussamad
 */
@Component
@Entity
@Table(name =  "configuration")
public class Configuration {

  private Integer id;
  private String baseUrl;
  private String grantType;
  private String clientSecret;
  private String clientId;
  private String mandateReferenceNumber;
  private String sourceAccountName;
  private String sourceAccountNumber;
  private String sourceBvn;
  private String sourceKycLevel;


  @Basic
  @Column(name = "authorization_code")
  public String getAuthorizationCode() {
    return authorizationCode;
  }

  public void setAuthorizationCode(String authorizationCode) {
    this.authorizationCode = authorizationCode;
  }

  private String authorizationCode;

  @Basic
  @Column(name = "source_account_name")
  public String getSourceAccountName() {
    return sourceAccountName;
  }

  public void setSourceAccountName(String sourceAccountName) {
    this.sourceAccountName = sourceAccountName;
  }

  @Basic
  @Column(name = "source_account_number")
  public String getSourceAccountNumber() {
    return sourceAccountNumber;
  }

  public void setSourceAccountNumber(String sourceAccountNumber) {
    this.sourceAccountNumber = sourceAccountNumber;
  }

  @Basic
  @Column(name = "source_bvn")
  public String getSourceBvn() {
    return sourceBvn;
  }

  public void setSourceBvn(String sourceBvn) {
    this.sourceBvn = sourceBvn;
  }

  @Basic
  @Column(name = "source_kyc_level")
  public String getSourceKycLevel() {
    return sourceKycLevel;
  }

  public void setSourceKycLevel(String sourceKycLevel) {
    this.sourceKycLevel = sourceKycLevel;
  }

  @Basic
  @Column(name = "source_institution_code")
  public String getSourceInstitutionCode() {
    return sourceInstitutionCode;
  }

  public void setSourceInstitutionCode(String sourceInstitutionCode) {
    this.sourceInstitutionCode = sourceInstitutionCode;
  }

  private String sourceInstitutionCode;

  @Basic
  @Column(name = "mandate_reference_number")
  public String getMandateReferenceNumber() {
    return mandateReferenceNumber;
  }

  public void setMandateReferenceNumber(String mandateReferenceNumber) {
    this.mandateReferenceNumber = mandateReferenceNumber;
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

  @Basic
  @Column(name = "institution_code")
  public String getInstitutionCode() {
    return institutionCode;
  }

  public void setInstitutionCode(String institutionCode) {
    this.institutionCode = institutionCode;
  }

  @Basic
  @Column(name = "biller_id")
  public String getBillerId() {
    return BillerId;
  }

  public void setBillerId(String billerId) {
    BillerId = billerId;
  }

  private String scope;
  private String institutionCode;
  private String BillerId;
  private String proxyUsername;
  private String proxyPassword;
  private String proxyUrl;
  private Integer proxyPort;
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
  @Column(name = "base_url")
  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  @Basic
  @Column(name = "proxy_username")
  public String getProxyUsername() {
    return proxyUsername;
  }

  public void setProxyUsername(String proxyUsername) {
    this.proxyUsername = proxyUsername;
  }

  @Basic
  @Column(name = "proxy_password")
  @Convert(converter = SensitiveDataConverter.class)
  public String getProxyPassword() {
    return proxyPassword;
  }

  public void setProxyPassword(String proxyPassword) {
    this.proxyPassword = proxyPassword;
  }

  @Basic
  @Column(name = "proxy_url")
  public String getProxyUrl() {
    return proxyUrl;
  }

  public void setProxyUrl(String proxyUrl) {
    this.proxyUrl = proxyUrl;
  }

  @Basic
  @Column(name = "proxy_port")
  public Integer getProxyPort() {
    return proxyPort;
  }

  public void setProxyPort(Integer proxyPort) {
    this.proxyPort = proxyPort;
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
