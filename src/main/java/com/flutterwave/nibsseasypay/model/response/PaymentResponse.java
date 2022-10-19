package com.flutterwave.nibsseasypay.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.flutterwave.nibsseasypay.entity.Payment;
import com.flutterwave.nibsseasypay.model.constant.CustomResponse;
import com.flutterwave.nibsseasypay.model.constant.ResponseCodeAndMessages;
import com.flutterwave.nibsseasypay.model.request.charge.ChargeRequest;
import com.flutterwave.nibsseasypay.model.request.nameenquiry.NameEnquiryRequest;
import com.flutterwave.nibsseasypay.nibsseastpay.model.response.NibssBalanceEnquiryResponse;
import com.flutterwave.nibsseasypay.nibsseastpay.model.response.NibssNameEnquiryResponse;
import com.flutterwave.nibsseasypay.nibsseastpay.model.response.NibssTransactionQueryResponse;
import com.flutterwave.nibsseasypay.nibsseastpay.model.response.NibssTransactionResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentResponse extends PaymentApiResponse {

  private Response response;
  private String status;
  private String statusMessage;
  private TransactionResponseData transaction;
  private PaymentOrderResponseData order;
  private List<ChargePaymentResponse> transactions;


  public static PaymentResponse buildNameEnquiryResponse(NibssNameEnquiryResponse response, NameEnquiryRequest nameEnquiryRequest, String reference) {

    if(response.getResponseCode() != null && response.getResponseCode().equals(ResponseCodeAndMessages.SUCCESSFUL.code())) {
      return PaymentResponse.builder()
          .transaction(TransactionResponseData.builder()
              .reference(nameEnquiryRequest.getTransaction().getReference())
              .linkingReference(reference)
              .providerReference(response.getSessionId())
              .build())
          .response(Response.builder()
              .code(ResponseCodeAndMessages.SUCCESSFUL.code())
              .message(ResponseCodeAndMessages.SUCCESSFUL.message())
              .build())
          .build();
    } else if(response.getCode() != null) {
      return PaymentResponse.builder()
          .transaction(TransactionResponseData.builder()
              .reference(nameEnquiryRequest.getTransaction().getReference())
              .linkingReference(reference)
              .build())
          .response(Response.builder()
              .code(response.getCode())
              .message(response.getMessage())
              .build())
          .build();
    }
    else {
      return PaymentResponse.builder()
          .transaction(TransactionResponseData.builder()
              .reference(nameEnquiryRequest.getTransaction().getReference())
              .linkingReference(reference)
              .build())
          .response(Response.builder()
              .code(ResponseCodeAndMessages.TIMEOUT.code())
              .message(ResponseCodeAndMessages.TIMEOUT.message())
              .build())
          .build();
    }
  }


  public static PaymentResponse buildPaymentResponse(NibssTransactionResponse response, ChargeRequest chargeRequest, String linkingReference) {

    if(response.getResponseCode() != null && response.getResponseCode().equals(ResponseCodeAndMessages.SUCCESSFUL.code())) {
      return PaymentResponse.builder()
          .transaction(TransactionResponseData.builder()
              .reference(chargeRequest.getTransaction().getReference())
              .linkingReference(linkingReference)
              .providerReference(response.getSessionId())
              .build())
          .order(PaymentOrderResponseData.builder()
              .amount(chargeRequest.getOrder().getAmount()).build())
          .response(Response.builder()
              .code(ResponseCodeAndMessages.SUCCESSFUL.code())
              .message(ResponseCodeAndMessages.SUCCESSFUL.message())
              .build())
          .build();
    } else if(response.getCode() != null) {
      return PaymentResponse.builder()
          .transaction(TransactionResponseData.builder()
              .reference(chargeRequest.getTransaction().getReference())
              .linkingReference(linkingReference)
              .build())
          .order(PaymentOrderResponseData.builder()
              .amount(chargeRequest.getOrder().getAmount()).build())
          .response(Response.builder()
              .code(response.getCode())
              .message(response.getMessage())
              .build())
          .build();
    }
    else {
      return PaymentResponse.builder()
          .transaction(TransactionResponseData.builder()
              .reference(chargeRequest.getTransaction().getReference())
              .linkingReference(linkingReference)
              .build())
          .order(PaymentOrderResponseData.builder()
              .amount(chargeRequest.getOrder().getAmount()).build())
          .response(Response.builder()
              .code(ResponseCodeAndMessages.TIMEOUT.code())
              .message(ResponseCodeAndMessages.TIMEOUT.message())
              .build())
          .build();
    }
  }

  public static NibssBalanceEnquiryResponse buildBalanceEnquiryResponse(NibssBalanceEnquiryResponse balanceEnquiryResponse) {
    return balanceEnquiryResponse;
  }


  public static PaymentResponse buildTransactionStatusResponse(
      NibssTransactionQueryResponse response, Payment payment, String reference) {

    if(response.getResponseCode() != null && response.getResponseCode().equals(ResponseCodeAndMessages.SUCCESSFUL.code())) {
      return PaymentResponse.builder()
          .transaction(TransactionResponseData.builder()
              .reference(reference)
              .linkingReference(reference)
              .providerReference(response.getSessionId())
              .build())
          .order(PaymentOrderResponseData.builder()
              .amount(payment.getAmount()).build())
          .response(Response.builder()
              .code(ResponseCodeAndMessages.SUCCESSFUL.code())
              .message(ResponseCodeAndMessages.SUCCESSFUL.message())
              .build())
          .build();
    } else if(response.getCode() != null) {
      return PaymentResponse.builder()
          .transaction(TransactionResponseData.builder()
              .reference(reference)
              .linkingReference(reference)
              .build())
          .order(PaymentOrderResponseData.builder()
              .amount(payment.getAmount()).build())
          .response(Response.builder()
              .code(response.getCode())
              .message(response.getMessage())
              .build())
          .build();
    }
    else {
      return PaymentResponse.builder()
          .transaction(TransactionResponseData.builder()
              .reference(reference)
              .linkingReference(reference)
              .build())
          .order(PaymentOrderResponseData.builder()
              .amount(payment.getAmount()).build())
          .response(Response.builder()
              .code(ResponseCodeAndMessages.TIMEOUT.code())
              .message(ResponseCodeAndMessages.TIMEOUT.message())
              .build())
          .build();
    }
  }
  public static PaymentResponse buildSuccessfulPaymentResponse (Payment payment) {
    return  PaymentResponse.builder()
        .transaction(TransactionResponseData.builder()
            .reference(payment.getTransactionId())
            .linkingReference(payment.getLinkingReference())
            .providerReference(payment.getProviderReference())
            .build())
        .order(PaymentOrderResponseData.builder()
            .amount(payment.getAmount())
            .build())
        .response(Response.builder()
            .code(ResponseCodeAndMessages.SUCCESSFUL.code())
            .message(ResponseCodeAndMessages.SUCCESSFUL.message())
            .build())
        .build();
  }


}
