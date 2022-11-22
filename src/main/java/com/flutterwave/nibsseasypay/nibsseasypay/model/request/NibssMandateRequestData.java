package com.flutterwave.nibsseasypay.nibsseasypay.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 11/9/22   - 09 - 7:21 AM$
 */

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NibssMandateRequestData {
  private String accountNumber;
  private String productId;
  private String bankCode;
  private String payerName;
  private String payerAddress;
  private String accountName;
  private String amount;
  private String payeeName;
  private String narration;
  private String payeeAddress;
  private String phoneNumber;
  private String emailAddress;
  private String subscriberCode;
  private String startDate;
  private String endDate;
  private String fileExtension;
  private String fileBase64EncodedString;

}
