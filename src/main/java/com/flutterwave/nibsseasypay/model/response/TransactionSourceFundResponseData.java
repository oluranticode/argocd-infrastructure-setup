package com.flutterwave.nibsseasypay.model.response;

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
public class TransactionSourceFundResponseData {

  private TransactionAccountResponseData account;
}
