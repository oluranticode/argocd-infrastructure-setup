package com.flutterwave.nibsseasypay.nibsseasypay.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 10/11/22   - 11 - 6:40 PM$
 */

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class NibssTransactionQueryRequest {
  private String transactionId;
}
