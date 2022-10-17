package com.flutterwave.nibsseasypay.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Abdussamad
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfigurationResponse extends PaymentApiResponse {

  private boolean status;
  private String message;
}
