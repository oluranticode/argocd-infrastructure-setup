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
 * Email aminu@flutterwavego.com Date 10/12/22   - 12 - 5:47 AM$
 */

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NibssTransactionQueryResponse {
  private String responseCode;

  @JsonProperty("sessionID")
  @SerializedName("sessionID")
  private String sessionId;
  private String transactionId;
  private String channelCode;
  private String sourceInstitutionCode;
  private String timestamp;
  private String code;
  private String message;
}
