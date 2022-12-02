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
  private String startDate;
  private String endDate;
  private String accountNumber;
  private NIbssMandateFileRequest mandateFile;
  private String payerAddress;
  private int frequency;
  private String emailAddress;
  private String narration;
  private String bankCode;
  private String payerName;
  private String accountName;
  private String amount;
  private String phoneNumber;
  private String subscriberCode;
  private String productId;

}
