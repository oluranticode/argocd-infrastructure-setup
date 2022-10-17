package com.flutterwave.nibsseasypay.model.request.charge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 10/7/22   - 07 - 7:13 AM$
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ChargeBankData {
  private String code;
}
