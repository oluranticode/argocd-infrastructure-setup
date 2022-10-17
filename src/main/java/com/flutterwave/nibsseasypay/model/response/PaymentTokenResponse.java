package com.flutterwave.nibsseasypay.model.response;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 3/15/22   - 15 - 8:14 AM$
 */


@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class PaymentTokenResponse extends PaymentApiResponse {

  @Expose
  private String token;
  @Expose
  private String refreshToken;
  private String refreshTokenExpires;
}
