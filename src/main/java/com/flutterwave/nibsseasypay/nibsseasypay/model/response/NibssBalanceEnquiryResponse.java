package com.flutterwave.nibsseasypay.nibsseasypay.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 10/17/22   - 17 - 8:48 AM$
 */

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NibssBalanceEnquiryResponse {
  public String responseCode;
  public String sessionID;
  public String transactionId;
  public int channelCode;
  public String destinationInstitutionCode;
  public String authorizationCode;
  public String targetAccountName;
  public String targetBankVerificationNumber;
  public String targetAccountNumber;
  public double availableBalance;
  public String timestamp;
  public String code;
  public String message;
}
