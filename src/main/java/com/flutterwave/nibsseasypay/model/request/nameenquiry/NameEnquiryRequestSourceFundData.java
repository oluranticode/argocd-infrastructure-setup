package com.flutterwave.nibsseasypay.model.request.nameenquiry;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 10/4/22   - 04 - 4:43 PM$
 */

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class NameEnquiryRequestSourceFundData {


  @Valid
  private NameEnquiryRequestAccountData account;
}
