package com.flutterwave.nibsseasypay.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.SafeHtml;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 11/7/22   - 07 - 11:06 AM$
 */

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MandateRequestData {
  @NotEmpty
  @NotNull
  @SafeHtml
  private String accountNumber;
  private String productId;
  @NotEmpty
  @NotNull
  @SafeHtml
  private String bankCode;
  @NotEmpty
  @NotNull
  @SafeHtml
  private String payerName;
  @NotEmpty
  @NotNull
  @SafeHtml
  private String payerAddress;
  @NotEmpty
  @NotNull
  @SafeHtml
  private String accountName;
  @NotEmpty
  @NotNull
  @SafeHtml
  private String amount;
  @NotEmpty
  @NotNull
  @SafeHtml
  private String payeeName;
  @NotEmpty
  @NotNull
  @SafeHtml
  private String narration;
  @NotEmpty
  @NotNull
  @SafeHtml
  private String payeeAddress;
  @NotEmpty
  @NotNull
  @SafeHtml
  private String phoneNumber;
  @NotEmpty
  @NotNull
  @SafeHtml
  private String emailAddress;
  @NotEmpty
  @NotNull
  @SafeHtml
  private String subscriberCode;
  @NotEmpty
  @NotNull
  @SafeHtml
  private String startDate;
  @NotEmpty
  @NotNull
  @SafeHtml
  private String endDate;
  @NotEmpty
  @NotNull
  @SafeHtml
  private String fileExtension;
  @NotEmpty
  @NotNull
  @SafeHtml
  private String fileBase64EncodedString;
}
