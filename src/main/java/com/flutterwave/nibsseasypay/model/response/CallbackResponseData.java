package com.flutterwave.nibsseasypay.model.response;

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
public class CallbackResponseData {

  @JsonProperty("callback")
  private String callBack;
  private String response;
}
