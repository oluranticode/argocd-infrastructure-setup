package com.flutterwave.nibsseasypay.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.flutterwave.nibsseasypay.model.constant.CustomResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Aminu Cincin Kabunu.
 *
 * Email aminu@flutterwavego.com
 * Date 27/04/2021   - 27 - 07:51$
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
  private String code;
  private String message;

  public Response build(String code){
    return Response.builder().code(code).message(CustomResponse.buildMessage(code)).build();
  }
}
