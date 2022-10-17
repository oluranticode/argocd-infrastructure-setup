package com.flutterwave.nibsseasypay.nibsseastpay.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 10/12/22   - 12 - 2:05 PM$
 */

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NibssGetBalanceRequest {
  private String channelCode;
  private String targetAccountName;
  private String targetAccountNumber;
  private String targetBankVerificationNumber;
  private String authorizationCode;
  private String destinationInstitutionCode;
  private String billerId;
  private String transactionId;
}
