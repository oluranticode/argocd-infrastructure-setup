package com.flutterwave.nibsseasypay.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.Expose;
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
  @Expose
  private String reference;
  @NotEmpty
  @NotNull
  @SafeHtml

  @NotEmpty
  @NotNull
  @SafeHtml
  @Expose
  private String accountNumber;
  private String productId;
  @NotEmpty
  @NotNull
  @SafeHtml
  @Expose
  private String bankCode;
  @NotEmpty
  @NotNull
  @SafeHtml
  @Expose
  private String payerName;
  @NotEmpty
  @NotNull
  @SafeHtml
  @Expose
  private String payerAddress;
  @NotEmpty
  @NotNull
  @SafeHtml
  @Expose
  private String accountName;
  @NotEmpty
  @NotNull
  @SafeHtml
  @Expose
  private String amount;
  @NotEmpty
  @NotNull
  @SafeHtml
  @Expose
  private String payeeName;
  @NotEmpty
  @NotNull
  @SafeHtml
  @Expose
  private String narration;
  @NotEmpty
  @NotNull
  @SafeHtml
  @Expose
  private String payeeAddress;
  @NotEmpty
  @NotNull
  @SafeHtml
  @Expose
  private String phoneNumber;
  @NotEmpty
  @NotNull
  @SafeHtml
  @Expose
  private String emailAddress;
  @NotEmpty
  @NotNull
  @SafeHtml
  @Expose
  private String subscriberCode;
  @NotEmpty
  @NotNull
  @SafeHtml
  @Expose
  private String startDate;
  @NotEmpty
  @NotNull
  @SafeHtml
  @Expose
  private String endDate;
  @NotEmpty
  @NotNull
  @SafeHtml
  @Expose
  private String fileExtension;
  @NotEmpty
  @NotNull
  @SafeHtml
  @Expose
  private String fileBase64EncodedString;
}
