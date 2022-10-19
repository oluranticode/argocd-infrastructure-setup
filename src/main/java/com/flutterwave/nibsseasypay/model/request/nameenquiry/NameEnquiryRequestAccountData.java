package com.flutterwave.nibsseasypay.model.request.nameenquiry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 10/4/22   - 04 - 4:43 PM$
 */

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class NameEnquiryRequestAccountData {

  @Valid
  private NameEnquiryRequestToData to;
}
