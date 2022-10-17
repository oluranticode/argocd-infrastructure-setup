package com.flutterwave.nibsseasypay.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flutterwave.nibsseasypay.entity.Configuration;
import com.flutterwave.nibsseasypay.entity.Payment;
import com.flutterwave.nibsseasypay.exception.BadRequestException;
import com.flutterwave.nibsseasypay.exception.ConflictException;
import com.flutterwave.nibsseasypay.model.constant.ResponseCodeAndMessages;
import com.flutterwave.nibsseasypay.model.request.charge.ChargeRequest;
import com.flutterwave.nibsseasypay.model.request.nameenquiry.NameEnquiryRequest;
import com.flutterwave.nibsseasypay.model.response.GetTokenResponse;
import com.flutterwave.nibsseasypay.model.response.PaymentOrderResponseData;
import com.flutterwave.nibsseasypay.model.response.PaymentResponse;
import com.flutterwave.nibsseasypay.model.response.Response;
import com.flutterwave.nibsseasypay.model.response.TransactionResponseData;
import com.flutterwave.nibsseasypay.nibsseastpay.constant.LogType;
import com.flutterwave.nibsseasypay.nibsseastpay.model.request.NibssGetBalanceRequest;
import com.flutterwave.nibsseasypay.nibsseastpay.model.request.NibssNameEquiryRequest;
import com.flutterwave.nibsseasypay.nibsseastpay.model.request.NibssTransactionQueryRequest;
import com.flutterwave.nibsseasypay.nibsseastpay.model.request.NibssTransactionRequest;
import com.flutterwave.nibsseasypay.nibsseastpay.model.response.NibssAuthResponse;
import com.flutterwave.nibsseasypay.nibsseastpay.model.response.NibssBalanceEnquiryResponse;
import com.flutterwave.nibsseasypay.nibsseastpay.model.response.NibssNameEnquiryResponse;
import com.flutterwave.nibsseasypay.nibsseastpay.model.response.NibssTransactionQueryResponse;
import com.flutterwave.nibsseasypay.nibsseastpay.model.response.NibssTransactionResponse;
import com.flutterwave.nibsseasypay.repository.BankRepository;
import com.flutterwave.nibsseasypay.repository.ConfigurationRepository;
import com.flutterwave.nibsseasypay.repository.LogRepository;
import com.flutterwave.nibsseasypay.repository.PaymentRepository;
import com.flutterwave.nibsseasypay.repository.TokenRepository;
import com.flutterwave.nibsseasypay.util.TimeUtil;
import com.flutterwave.nibsseasypay.util.UUIDUtil;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 3/15/22   - 15 - 8:10 AM$
 */

@Service
@Slf4j
public class PaymentService {

  private static final String MEDIA_TYPE = "application/json";
  private final Configuration configuration;
  private final Gson gson;
  private final PaymentRepository paymentRepository;
  private final SaveLogService saveLogService;
  private final TokenRepository tokenRepository;
  private final BankRepository bankRepository;
  private final RestClientProxy restClientProxy;
  private final ObjectMapper objectMapper;

  @Autowired
  private RestTemplate restTemplate;


  @Autowired
  public PaymentService(Gson gson,
      PaymentRepository paymentRepository,
      LogRepository logRepository,
      TokenRepository tokenRepository,
      BankRepository bankRepository, RestClientProxy restClientProxy,
      ConfigurationRepository configurationRepository,
      SaveLogService saveLogService,
      ObjectMapper objectMapper) {
    this.bankRepository = bankRepository;
    this.configuration = configurationRepository.findOneById(1);
    this.gson = gson;
    this.paymentRepository = paymentRepository;
    this.saveLogService = saveLogService;
    this.tokenRepository = tokenRepository;
    this.restClientProxy = restClientProxy;
    this.objectMapper = objectMapper;
  }


  public PaymentResponse charge(ChargeRequest chargeRequest) {
    String linkingReference =
        configuration.getInstitutionCode() + TimeUtil.getCurrentDateTime() + UUIDUtil
            .RandGeneratedStr();
    NibssTransactionResponse response = null;
    PaymentResponse paymentResponse = new PaymentResponse();
//    try {

      checkTransactionExist(chargeRequest.getTransaction().getReference());

      Payment payment =  ChargeRequest.buildPayment(chargeRequest, configuration, linkingReference);
      payment = paymentRepository.save(payment);

      saveLogService.saveLog(chargeRequest.getTransaction().getReference(),
          LogType.TRANSACTION_CHARGE.name(), gson.toJson(chargeRequest), "", "", "");

      NibssNameEnquiryResponse nameEnquiryResponse = nameEnquiryInternal(chargeRequest);

      if(nameEnquiryResponse.getCode() != null) {
        throw new BadRequestException(nameEnquiryResponse.getMessage());
      }
      payment.setBeneficiaryBankVerificationNumber(nameEnquiryResponse.getBankVerificationNumber());
      payment.setBeneficiaryKYCLevel(String.valueOf(nameEnquiryResponse.getKycLevel()));
      payment.setBeneficiaryAccountName(nameEnquiryResponse.getAccountName());
      payment.setNameEnquiryRef(nameEnquiryResponse.getTransactionId());

      payment = paymentRepository.save(payment);

      String url = configuration.getBaseUrl() + "/nipservice/v1/nip/fundstransfer";
      NibssTransactionRequest nibssTransactionRequest = ChargeRequest.chargeRequest(chargeRequest, linkingReference, nameEnquiryResponse, configuration);

       response = this.restClientProxy.sendRequestProxy(url,
          nibssTransactionRequest, NibssTransactionResponse.class, header(),
          "POST", "application/json", configuration,
          chargeRequest.getTransaction().getReference(), LogType.TRANSACTION_CHARGE.name());

      System.out.println(gson.toJson(response));

      if(response.getResponseCode() != null && response.getResponseCode().equals(ResponseCodeAndMessages.SUCCESSFUL.code())) {
        payment.setSessionId(response.getSessionId());
        payment.setProviderReference(response.getSessionId());
        payment.setNibssResponseCode(response.getResponseCode());
        payment.setPaymentReference(response.getPaymentReference());
        payment.setResponseMessage(response.getNarration());
        paymentResponse =  PaymentResponse.buildPaymentResponse(response, chargeRequest, linkingReference);
        payment.setResponseCode(paymentResponse.getResponse().getCode());
        payment.setResponseMessage(paymentResponse.getResponse().getMessage());
        paymentRepository.save(payment);
      } else {
        payment.setNibssErrorCode(response.getCode());
        payment.setNibssErrorTimestamp(response.getTimestamp());
        payment.setNibssErrorMessage(response.getMessage());
        paymentResponse =  PaymentResponse.buildPaymentResponse(response, chargeRequest, linkingReference);
        payment.setResponseCode(paymentResponse.getResponse().getCode());
        payment.setResponseMessage(paymentResponse.getResponse().getMessage());
        paymentRepository.save(payment);
      }
//    } catch (Exception e) {
//      e.printStackTrace();
//    }


    return paymentResponse;
  }


  public NibssNameEnquiryResponse nameEnquiryInternal(ChargeRequest nameEnquiryRequest) {
    saveLogService.saveLog(nameEnquiryRequest.getTransaction().getReference(),
        LogType.NAME_ENQUIRY_INTERNAL.name(), gson.toJson(nameEnquiryRequest), "", "", "");
    String referecne = configuration.getInstitutionCode() + TimeUtil.getCurrentDateTime() + UUIDUtil.RandGeneratedStr();
    String url = configuration.getBaseUrl() + "/nipservice/v1/nip/nameenquiry";
    NibssNameEquiryRequest nibssNameEquiryRequest = NameEnquiryRequest.nameChargeEnquiryRequest(nameEnquiryRequest, referecne);

    NibssNameEnquiryResponse response = this.restClientProxy.sendRequestProxy(url,
        nibssNameEquiryRequest, NibssNameEnquiryResponse.class, header(),
        "POST", "application/json", configuration,
        nameEnquiryRequest.getTransaction().getReference(), LogType.NAME_ENQUIRY_INTERNAL.name());
    saveLogService.saveLog(nameEnquiryRequest.getTransaction().getReference(),
        LogType.NAME_ENQUIRY_INTERNAL.name(),"" , "gson.toJson(response)", "", "");
      return response;
  }

  public PaymentResponse nameEnquiry(NameEnquiryRequest nameEnquiryRequest) {
    saveLogService.saveLog(nameEnquiryRequest.getTransaction().getReference(),
        LogType.NAME_ENQUIRY.name(), gson.toJson(nameEnquiryRequest), "", "", "");
    String referecne = configuration.getInstitutionCode() + TimeUtil.getCurrentDateTime() + UUIDUtil.RandGeneratedStr();
    String url = configuration.getBaseUrl() + "/nipservice/v1/nip/nameenquiry";
    NibssNameEquiryRequest nibssNameEquiryRequest = NameEnquiryRequest.nameEnquiryRequest(nameEnquiryRequest, referecne);

    NibssNameEnquiryResponse response = this.restClientProxy.sendRequestProxy(url,
        nibssNameEquiryRequest, NibssNameEnquiryResponse.class, header(),
        "POST", "application/json", configuration,
        nameEnquiryRequest.getTransaction().getReference(), LogType.NAME_ENQUIRY.name());

    return PaymentResponse.buildNameEnquiryResponse(response, nameEnquiryRequest, referecne);
  }

  public PaymentResponse getTransactionStatus(String transactionId) {

    saveLogService.saveLog(transactionId,
        LogType.TRANSACTION_QUERY.name(), "", "", "", "");
    String url = configuration.getBaseUrl() + "/nipservice/v1/nip/tsq";
    PaymentResponse paymentResponse = new PaymentResponse();
    Payment payment = verifyTransactionExist(transactionId);
    if(payment.getResponseCode() != null && payment.getResponseCode().equals(ResponseCodeAndMessages.SUCCESSFUL.code())) {

       return PaymentResponse.buildSuccessfulPaymentResponse(payment);
    }

    NibssTransactionQueryRequest queryRequest = NibssTransactionQueryRequest.builder()
        .transactionId(payment.getLinkingReference())
        .build();

    NibssTransactionQueryResponse response = this.restClientProxy.sendRequestProxy(url,
        queryRequest, NibssTransactionQueryResponse.class, header(),
        "POST", "application/json", configuration,transactionId
        , LogType.TRANSACTION_QUERY.name());
    if(response.getResponseCode() != null && response.getResponseCode().equals(ResponseCodeAndMessages.SUCCESSFUL.code())) {
      payment.setNibssResponseCode(response.getResponseCode());
      payment.setSessionId(response.getSessionId());
      paymentResponse = PaymentResponse.buildTransactionStatusResponse(response, payment, transactionId);
      payment.setResponseCode(paymentResponse.getResponse().getCode());
      payment.setResponseMessage(paymentResponse.getResponse().getMessage());
      paymentRepository.save(payment);
    }else {
      payment.setNibssErrorCode(response.getCode());
      payment.setNibssErrorTimestamp(response.getTimestamp());
      payment.setNibssErrorMessage(response.getMessage());
      paymentResponse = PaymentResponse.buildTransactionStatusResponse(response, payment, transactionId);
      payment.setResponseCode(paymentResponse.getResponse().getCode());
      payment.setResponseMessage(paymentResponse.getResponse().getMessage());
      paymentRepository.save(payment);
    }
    return  paymentResponse;
  }


  public NibssBalanceEnquiryResponse getBalance() {
    saveLogService.saveLog("",
        LogType.GET_BALANCE.name(), "", "", "", "");
    String url = configuration.getBaseUrl() + "/nipservice/v1/nip/balanceenquiry";
    String reference = configuration.getInstitutionCode() + TimeUtil.getCurrentDateTime() + UUIDUtil.RandGeneratedStr();
    NibssGetBalanceRequest queryRequest = NibssGetBalanceRequest.builder()
        .authorizationCode(configuration.getAuthorizationCode())
        .billerId(configuration.getBillerId())
        .channelCode("1")
        .destinationInstitutionCode(configuration.getSourceInstitutionCode())
        .targetAccountName(configuration.getSourceAccountName())
        .targetAccountNumber(configuration.getSourceAccountNumber())
        .targetBankVerificationNumber(configuration.getSourceBvn())
        .transactionId(reference)
        .build();

    NibssBalanceEnquiryResponse response = this.restClientProxy.sendRequestProxy(url,
        queryRequest, NibssBalanceEnquiryResponse.class, header(),
        "POST", "application/json", configuration, reference
        , LogType.NAME_ENQUIRY.name());
    return PaymentResponse.buildBalanceEnquiryResponse(response);
  }

  private void checkTransactionExist(String transactionId) {
    boolean savedPayment = paymentRepository.findOneByTransactionId(transactionId).isPresent();
    if (savedPayment) {
      throw new ConflictException("Transaction with reference (" + transactionId + ") already exists");
    }
  }



  public GetTokenResponse getToken() {
    String url = configuration.getBaseUrl() + "/reset";
    NibssAuthResponse nibssAuthResponse = this.restClientProxy.getTokenRequestProxy(url, configuration, NibssAuthResponse.class);
    return  GetTokenResponse.builder()
        .token(nibssAuthResponse.getAccessToken())
        .tokenType(nibssAuthResponse.getTokenType())
        .expiresIn(nibssAuthResponse.getExpiresIn())
        .expires(nibssAuthResponse.getExpiresIn())
        .build();
  }

  private Payment verifyTransactionExist(String reference) {
    return paymentRepository.findOneByTransactionId(reference).orElseThrow(() ->
        new ConflictException("Transaction with reference (" + reference + ") does not exists")
    );
  }

  private Map<String, String> header() {
    GetTokenResponse  response = getToken();
    Map<String, String> headers = new HashMap<>(1);
    headers.put("Authorization", "Bearer " + response.getToken());
    return headers;
  }

}
