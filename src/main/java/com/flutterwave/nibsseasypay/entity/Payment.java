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
import javax.persistence.PreUpdate;
import javax.persistence.Table;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 31/05/2021   - 31 - 12:54$
 */
@Entity
@Table(name = "payment")
public class Payment {
  private Integer id;



  @Basic
  @Column(name = "source_institution_code")
  public String getSourceInstitutionCode() {
    return sourceInstitutionCode;
  }

  public void setSourceInstitutionCode(String sourceInstitutionCode) {
    this.sourceInstitutionCode = sourceInstitutionCode;
  }

  @Basic
  @Column(name = "amount")
  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  @Basic
  @Column(name = "beneficiary_account_name")
  public String getBeneficiaryAccountName() {
    return beneficiaryAccountName;
  }

  public void setBeneficiaryAccountName(String beneficiaryAccountName) {
    this.beneficiaryAccountName = beneficiaryAccountName;
  }

  @Basic
  @Column(name = "beneficiary_account_number")
  public String getBeneficiaryAccountNumber() {
    return beneficiaryAccountNumber;
  }

  public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
    this.beneficiaryAccountNumber = beneficiaryAccountNumber;
  }

  @Basic
  @Column(name = "beneficiary_bank_verification_number")
  public String getBeneficiaryBankVerificationNumber() {
    return beneficiaryBankVerificationNumber;
  }

  public void setBeneficiaryBankVerificationNumber(String beneficiaryBankVerificationNumber) {
    this.beneficiaryBankVerificationNumber = beneficiaryBankVerificationNumber;
  }

  @Basic
  @Column(name = "beneficiary_kyc_level")
  public String getBeneficiaryKYCLevel() {
    return beneficiaryKYCLevel;
  }

  public void setBeneficiaryKYCLevel(String beneficiaryKYCLevel) {
    this.beneficiaryKYCLevel = beneficiaryKYCLevel;
  }

  @Basic
  @Column(name = "channel_code")
  public String getChannelCode() {
    return channelCode;
  }

  public void setChannelCode(String channelCode) {
    this.channelCode = channelCode;
  }

  @Basic
  @Column(name = "response_code")
  public String getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }

  @Basic
  @Column(name = "response_message")
  public String getResponseMessage() {
    return responseMessage;
  }

  public void setResponseMessage(String responseMessage) {
    this.responseMessage = responseMessage;
  }

  @Basic
  @Column(name = "originator_account_name")
  public String getOriginatorAccountName() {
    return originatorAccountName;
  }

  public void setOriginatorAccountName(String originatorAccountName) {
    this.originatorAccountName = originatorAccountName;
  }

  @Basic
  @Column(name = "originator_account_number")
  public String getOriginatorAccountNumber() {
    return originatorAccountNumber;
  }

  public void setOriginatorAccountNumber(String originatorAccountNumber) {
    this.originatorAccountNumber = originatorAccountNumber;
  }

  @Basic
  @Column(name = "originator_bank_verification_number")
  public String getOriginatorBankVerificationNumber() {
    return originatorBankVerificationNumber;
  }

  public void setOriginatorBankVerificationNumber(String originatorBankVerificationNumber) {
    this.originatorBankVerificationNumber = originatorBankVerificationNumber;
  }

  @Basic
  @Column(name = "originator_kyc_level")
  public String getOriginatorKYCLevel() {
    return originatorKYCLevel;
  }

  public void setOriginatorKYCLevel(String originatorKYCLevel) {
    this.originatorKYCLevel = originatorKYCLevel;
  }

  @Basic
  @Column(name = "destination_institution_code")
  public String getDestinationInstitutionCode() {
    return destinationInstitutionCode;
  }

  public void setDestinationInstitutionCode(String destinationInstitutionCode) {
    this.destinationInstitutionCode = destinationInstitutionCode;
  }

  @Basic
  @Column(name = "mandateReference_number")
  public String getMandateReferenceNumber() {
    return mandateReferenceNumber;
  }

  public void setMandateReferenceNumber(String mandateReferenceNumber) {
    this.mandateReferenceNumber = mandateReferenceNumber;
  }

  @Basic
  @Column(name = "name_enquiry_ref")
  public String getNameEnquiryRef() {
    return nameEnquiryRef;
  }

  public void setNameEnquiryRef(String nameEnquiryRef) {
    this.nameEnquiryRef = nameEnquiryRef;
  }

  @Basic
  @Column(name = "originator_narration")
  public String getOriginatorNarration() {
    return originatorNarration;
  }

  public void setOriginatorNarration(String originatorNarration) {
    this.originatorNarration = originatorNarration;
  }

  @Basic
  @Column(name = "payment_reference")
  public String getPaymentReference() {
    return paymentReference;
  }

  public void setPaymentReference(String paymentReference) {
    this.paymentReference = paymentReference;
  }

  @Basic
  @Column(name = "transaction_id")
  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  @Basic
  @Column(name = "transaction_location")
  public String getTransactionLocation() {
    return transactionLocation;
  }

  public void setTransactionLocation(String transactionLocation) {
    this.transactionLocation = transactionLocation;
  }

  @Basic
  @Column(name = "beneficiary_narration")
  public String getBeneficiaryNarration() {
    return beneficiaryNarration;
  }

  public void setBeneficiaryNarration(String beneficiaryNarration) {
    this.beneficiaryNarration = beneficiaryNarration;
  }

  @Basic
  @Column(name = "biller_id")
  public String getBillerId() {
    return billerId;
  }

  public void setBillerId(String billerId) {
    this.billerId = billerId;
  }

  @Basic
  @Column(name = "nibss_response_code")
  public String getNibssResponseCode() {
    return nibssResponseCode;
  }

  public void setNibssResponseCode(String nibssResponseCode) {
    this.nibssResponseCode = nibssResponseCode;
  }

  @Basic
  @Column(name = "session_id")
  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  @Basic
  @Column(name = "nibss_error_timestamp")
  public String getNibssErrorTimestamp() {
    return nibssErrorTimestamp;
  }

  public void setNibssErrorTimestamp(String nibssErrorTimestamp) {
    this.nibssErrorTimestamp = nibssErrorTimestamp;
  }

  @Basic
  @Column(name = "nibss_error_code")
  public String getNibssErrorCode() {
    return nibssErrorCode;
  }

  public void setNibssErrorCode(String nibssErrorCode) {
    this.nibssErrorCode = nibssErrorCode;
  }

  @Basic
  @Column(name = "nibss_error_message")
  public String getNibssErrorMessage() {
    return nibssErrorMessage;
  }

  public void setNibssErrorMessage(String nibssErrorMessage) {
    this.nibssErrorMessage = nibssErrorMessage;
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

  private String sourceInstitutionCode;
  private String amount;
  private String beneficiaryAccountName;
  private String beneficiaryAccountNumber;
  private String beneficiaryBankVerificationNumber;
  private String beneficiaryKYCLevel;
  private String channelCode;
  private String responseCode;
  private String responseMessage;
  private String originatorAccountName;
  private String originatorAccountNumber;
  private String originatorBankVerificationNumber;
  private String originatorKYCLevel;
  private String destinationInstitutionCode;
  private String mandateReferenceNumber;
  private String nameEnquiryRef;
  private String originatorNarration;
  private String paymentReference;
  private String transactionId;
  private String linkingReference;
  private String providerReference;
  private String transactionLocation;
  private String beneficiaryNarration;
  private String billerId;
  private String nibssResponseCode;
  private String sessionId;
  private String nibssErrorTimestamp;
  private String nibssErrorCode;
  private String nibssErrorMessage;
  private Timestamp createdAt;
  private Timestamp updatedAt;

  public String getLinkingReference() {
    return linkingReference;
  }

  public void setLinkingReference(String linkingReference) {
    this.linkingReference = linkingReference;
  }

  public String getProviderReference() {
    return providerReference;
  }

  public void setProviderReference(String providerReference) {
    this.providerReference = providerReference;
  }

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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
