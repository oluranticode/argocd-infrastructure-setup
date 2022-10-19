package com.flutterwave.nibsseasypay.model.response;


import com.flutterwave.nibsseasypay.model.constant.ErrorCode;
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
public class ErrorResponse extends PaymentApiResponse {

  private boolean status;
  private ErrorCode error;
  private String message;
  private ResponseData response;
}
