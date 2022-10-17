package com.flutterwave.nibsseasypay.nibsseastpay.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 10/7/22   - 07 - 7:21 AM$
 */

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class NibssTransactionRequest {
  private Integer SourceInstitutionCode;
  private String amount;
  private String beneficiaryAccountName;
  private String beneficiaryAccountNumber;
  private String beneficiaryBankVerificationNumber;
  private String beneficiaryKYCLevel;
  private String channelCode;
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
  private String transactionLocation;
  private String beneficiaryNarration;
  private String billerId;
}
