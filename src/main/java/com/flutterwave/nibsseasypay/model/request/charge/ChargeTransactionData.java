package com.flutterwave.nibsseasypay.model.request.charge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 10/7/22   - 07 - 7:06 AM$
 */

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ChargeTransactionData {
  private String reference;
  private String externalreference;
  private String narration;
  private ChargeSourceoffunds sourceoffunds;
}
