package com.flutterwave.nibsseasypay.nibsseasypay.model.response;

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
 * Email aminu@flutterwavego.com Date 10/4/22   - 04 - 5:41 PM$
 */

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NibssNameEnquiryResponse {
  private String responseCode;
  @JsonProperty("sessionID")
  @SerializedName("sessionID")
  private String sessionId;
  private String transactionId;
  private int channelCode;
  private String destinationInstitutionCode;
  private String accountNumber;
  private String accountName;
  private String bankVerificationNumber;
  private int kycLevel;
  private String timestamp;
  private String code;
  private String message;
}
