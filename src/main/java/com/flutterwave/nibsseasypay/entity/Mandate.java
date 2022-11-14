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
 * Email aminu@flutterwavego.com Date 11/9/22   - 09 - 7:11 AM$
 */


@Entity
@Table(name = "mandate")
public class Mandate {

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

  @Basic
  @Column(name = "reference")
  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  @Basic
  @Column(name = "account_number")
  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  @Basic
  @Column(name = "product_id")
  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  @Basic
  @Column(name = "bank_code")
  public String getBankCode() {
    return bankCode;
  }

  public void setBankCode(String bankCode) {
    this.bankCode = bankCode;
  }

  @Basic
  @Column(name = "payer_name")
  public String getPayerName() {
    return payerName;
  }

  public void setPayerName(String payerName) {
    this.payerName = payerName;
  }

  @Basic
  @Column(name = "payer_address")
  public String getPayerAddress() {
    return payerAddress;
  }

  public void setPayerAddress(String payerAddress) {
    this.payerAddress = payerAddress;
  }

  @Basic
  @Column(name = "account_name")
  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
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
  @Column(name = "payee_name")
  public String getPayeeName() {
    return payeeName;
  }

  public void setPayeeName(String payeeName) {
    this.payeeName = payeeName;
  }

  @Basic
  @Column(name = "narration")
  public String getNarration() {
    return narration;
  }

  public void setNarration(String narration) {
    this.narration = narration;
  }

  @Basic
  @Column(name = "payee_address")
  public String getPayeeAddress() {
    return payeeAddress;
  }

  public void setPayeeAddress(String payeeAddress) {
    this.payeeAddress = payeeAddress;
  }

  @Basic
  @Column(name = "phone_number")
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Basic
  @Column(name = "email_address")
  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  @Basic
  @Column(name = "subscriber_code")
  public String getSubscriberCode() {
    return subscriberCode;
  }

  public void setSubscriberCode(String subscriberCode) {
    this.subscriberCode = subscriberCode;
  }

  @Basic
  @Column(name = "start_date")
  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  @Basic
  @Column(name = "end_date")
  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  @Basic
  @Column(name = "file_extension")
  public String getFileExtension() {
    return fileExtension;
  }

  public void setFileExtension(String fileExtension) {
    this.fileExtension = fileExtension;
  }

  @Basic
  @Column(name = "file_base64_encoded_string")
  public String getFileBase64EncodedString() {
    return fileBase64EncodedString;
  }

  public void setFileBase64EncodedString(String fileBase64EncodedString) {
    this.fileBase64EncodedString = fileBase64EncodedString;
  }


  @Basic
  @Column(name = "app_user")
  public String getAppUser() {
    return appUser;
  }

  public void setAppUser(String appUser) {
    this.appUser = appUser;
  }

  @Basic
  @Column(name = "biller_id")
  public String getBillerId() {
    return billerId;
  }

  public void setBillerId(String billerId) {
    this.billerId = billerId;
  }


  private String reference;
  private String accountNumber;
  private String productId;
  private String bankCode;
  private String payerName;
  private String payerAddress;
  private String accountName;
  private String amount;
  private String payeeName;
  private String narration;
  private String payeeAddress;
  private String phoneNumber;
  private String emailAddress;
  private String subscriberCode;
  private String startDate;
  private String endDate;
  private String fileExtension;
  private String fileBase64EncodedString;
  private String appUser;
  private String billerId;

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
