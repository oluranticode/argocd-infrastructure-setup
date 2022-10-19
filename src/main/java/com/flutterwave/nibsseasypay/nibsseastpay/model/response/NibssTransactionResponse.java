package com.flutterwave.nibsseasypay.nibsseastpay.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 10/7/22   - 07 - 9:33 AM$
 */

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NibssTransactionResponse {
  private String responseCode;
  @JsonProperty("sessionID")
  @SerializedName("sessionID")
  private String sessionId;
  private String transactionId;
  private String channelCode;
  private String nameEnquiryRef;
  private String destinationInstitutionCode;
  private String beneficiaryAccountName;
  private String beneficiaryAccountNumber;
  private String beneficiaryKYCLevel;
  private String beneficiaryBankVerificationNumber;
  private String originatorAccountName;
  private String originatorAccountNumber;
  private String originatorBankVerificationNumber;
  private String originatorKYCLevel;
  private String transactionLocation;
  private String narration;
  private String paymentReference;
  private String amount;
  private String timestamp;
  private String code;
  private String message;
}
