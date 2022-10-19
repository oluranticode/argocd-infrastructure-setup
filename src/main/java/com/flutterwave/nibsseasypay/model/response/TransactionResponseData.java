package com.flutterwave.nibsseasypay.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Oluwafemi
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponseData {

  private String type;
  private String ip;
  private String deviceid;
  private String reference;
  private String externalreference;
  private String narration;
  private String document;
  private TransactionSourceFundResponseData sourceoffunds;
  private CallbackResponseData urls;
  @JsonProperty("linkingreference")
  private String linkingReference;
  private String providerReference;

  private String balance;

  private String hash;
  private String merchantCaptureCode;
  private String merchantRefundCode;
  private String refundId;
  @JsonProperty("secretquestion")
  private String secretQuestion;
  @JsonProperty("secretanswer")
  private String secretAnswer;
  @JsonProperty("startdate")
  private String startDate;
  @JsonProperty("enddate")
  private String endDate;

}
