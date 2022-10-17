package com.flutterwave.nibsseasypay.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 04/06/2021   - 04 - 11:13$
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse extends PaymentApiResponse {
  private AuthResponseData response;
}
