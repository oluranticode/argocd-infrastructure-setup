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
 * Email aminu@flutterwavego.com Date 11/7/22   - 07 - 10:51 AM$
 */

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MandateResponse {
  private String responseCode;
  private String mandateCode;
  private String subscriberCode;
  private String phoneNumber;
  private String responseDescription;
}
