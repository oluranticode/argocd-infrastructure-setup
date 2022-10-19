package com.flutterwave.nibsseasypay.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@NoArgsConstructor@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentOrderResponseData {

  private String amount;
  private String currency;
  private String country;
  private String dcc;
}
