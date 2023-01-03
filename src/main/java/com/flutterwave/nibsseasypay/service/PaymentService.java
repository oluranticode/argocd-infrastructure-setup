package com.flutterwave.nibsseasypay.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flutterwave.nibsseasypay.entity.Auth;
import com.flutterwave.nibsseasypay.entity.Configuration;
import com.flutterwave.nibsseasypay.entity.Mandate;
import com.flutterwave.nibsseasypay.entity.MandateConfiguration;
import com.flutterwave.nibsseasypay.entity.Payment;
import com.flutterwave.nibsseasypay.entity.SourceAccount;
import com.flutterwave.nibsseasypay.exception.AuthenticationException;
import com.flutterwave.nibsseasypay.exception.BadRequestException;
import com.flutterwave.nibsseasypay.exception.ConflictException;
import com.flutterwave.nibsseasypay.exception.NotFoundException;
import com.flutterwave.nibsseasypay.model.constant.ResponseCodeAndMessages;
import com.flutterwave.nibsseasypay.model.request.MandateRequest;
import com.flutterwave.nibsseasypay.model.request.charge.ChargeRequest;
import com.flutterwave.nibsseasypay.model.request.nameenquiry.NameEnquiryRequest;
import com.flutterwave.nibsseasypay.model.response.GetTokenResponse;
import com.flutterwave.nibsseasypay.model.response.PaymentResponse;
import com.flutterwave.nibsseasypay.nibsseasypay.constant.LogType;
import com.flutterwave.nibsseasypay.nibsseasypay.model.request.NibssGetBalanceRequest;
import com.flutterwave.nibsseasypay.nibsseasypay.model.request.NibssMandateRequest;
import com.flutterwave.nibsseasypay.nibsseasypay.model.request.NibssMandateRequestAuthData;
import com.flutterwave.nibsseasypay.nibsseasypay.model.request.NibssMandateRequestData;
import com.flutterwave.nibsseasypay.nibsseasypay.model.request.NibssNameEquiryRequest;
import com.flutterwave.nibsseasypay.nibsseasypay.model.request.NibssTransactionQueryRequest;
import com.flutterwave.nibsseasypay.nibsseasypay.model.request.NibssTransactionRequest;
import com.flutterwave.nibsseasypay.nibsseasypay.model.response.MandateResponse;
import com.flutterwave.nibsseasypay.nibsseasypay.model.response.NibssAuthResponse;
import com.flutterwave.nibsseasypay.nibsseasypay.model.response.NibssBalanceEnquiryResponse;
import com.flutterwave.nibsseasypay.nibsseasypay.model.response.NibssNameEnquiryResponse;
import com.flutterwave.nibsseasypay.nibsseasypay.model.response.NibssTransactionQueryResponse;
import com.flutterwave.nibsseasypay.nibsseasypay.model.response.NibssTransactionResponse;
import com.flutterwave.nibsseasypay.repository.AuthRepository;
import com.flutterwave.nibsseasypay.repository.ConfigurationRepository;
import com.flutterwave.nibsseasypay.repository.MandateConfigurationRepository;
import com.flutterwave.nibsseasypay.repository.MandateRepository;
import com.flutterwave.nibsseasypay.repository.PaymentRepository;
import com.flutterwave.nibsseasypay.repository.SourceAccountRepository;
import com.flutterwave.nibsseasypay.util.JwtUtil;
import com.flutterwave.nibsseasypay.util.TimeUtil;
import com.flutterwave.nibsseasypay.util.UUIDUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import java.util.HashMap;
import java.util.List;
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
  private final Gson gson;
  private final PaymentRepository paymentRepository;
  private final SaveLogService saveLogService;
  private final RestClientProxy restClientProxy;
  private final ObjectMapper objectMapper;
  private final ConfigurationRepository configurationRepository;
  private final SourceAccountRepository sourceAccountRepository;
  private final AuthRepository authRepository;
  private final MandateConfigurationRepository mandateConfigurationRepository;
  private final List<Configuration> configurationList;
  private final MandateRepository mandateRepository;

  @Autowired
  private RestTemplate restTemplate;


  @Autowired
  public PaymentService(Gson gson,
      PaymentRepository paymentRepository,
      RestClientProxy restClientProxy,
      ConfigurationRepository configurationRepository,
      SaveLogService saveLogService,
      ObjectMapper objectMapper,
      SourceAccountRepository sourceAccountRepository,
      AuthRepository authRepository,
      MandateConfigurationRepository mandateConfigurationRepository,
      MandateRepository mandateRepository) {
    this.gson = gson;
    this.paymentRepository = paymentRepository;
    this.saveLogService = saveLogService;
    this.restClientProxy = restClientProxy;
    this.objectMapper = objectMapper;
    this.configurationRepository = configurationRepository;
    this.sourceAccountRepository = sourceAccountRepository;
    this.configurationList = configurationRepository.findAll();
    this.authRepository = authRepository;
    this.mandateConfigurationRepository = mandateConfigurationRepository;
    this.mandateRepository = mandateRepository;
  }


  public PaymentResponse charge(String authorization, ChargeRequest chargeRequest) {

    checkTransactionExist(chargeRequest.getTransaction().getReference());


    Auth auth = getAuth(authorization);


    SourceAccount sourceAccount = fineOneByAccountAndAppUser(chargeRequest.getTransaction()
            .getSourceoffunds().getAccount().getFrom().getNumber(), auth.getAppUser());

    System.out.println(sourceAccount);

//    Configuration configuration = configurationRepository.findOneById(1);


    Configuration configuration = configurationRepository.findOneByAppUser(auth.getAppUser())
        .orElseThrow(() -> new NotFoundException("Configuration not found"));


    String linkingReference =
        configuration.getInstitutionCode() + TimeUtil.getCurrentDateTime() + UUIDUtil
            .RandGeneratedStr();
    NibssTransactionResponse response = null;
    PaymentResponse paymentResponse = new PaymentResponse();
//    try {



      Payment payment =  ChargeRequest.buildPayment(chargeRequest, configuration, linkingReference, sourceAccount);
      payment = paymentRepository.save(payment);

      saveLogService.saveLog(chargeRequest.getTransaction().getReference(),
          LogType.TRANSACTION_CHARGE.name(), gson.toJson(chargeRequest), "", "", "", sourceAccount.getAppUser());

      NibssNameEnquiryResponse nameEnquiryResponse = nameEnquiryInternal(chargeRequest, configuration, auth.getAppUser());

      if(nameEnquiryResponse.getCode() != null) {
        throw new BadRequestException(nameEnquiryResponse.getMessage());
      }
      payment.setBeneficiaryBankVerificationNumber(nameEnquiryResponse.getBankVerificationNumber());
      payment.setBeneficiaryKYCLevel(String.valueOf(nameEnquiryResponse.getKycLevel()));
      payment.setBeneficiaryAccountName(nameEnquiryResponse.getAccountName());
      payment.setNameEnquiryRef(nameEnquiryResponse.getTransactionId());

      payment = paymentRepository.save(payment);

      String url = configuration.getBaseUrl() + "/nipservice/v1/nip/fundstransfer";
      NibssTransactionRequest nibssTransactionRequest = ChargeRequest.chargeRequest(chargeRequest, linkingReference, nameEnquiryResponse, configuration, sourceAccount);

       response = this.restClientProxy.sendRequestProxy(url,
          nibssTransactionRequest, NibssTransactionResponse.class, header(auth.getAppUser()),
          "POST", "application/json", configuration,
          chargeRequest.getTransaction().getReference(), LogType.TRANSACTION_CHARGE.name(), auth.getAppUser());

      System.out.println(gson.toJson(response));

      if(response.getResponseCode() != null && response.getResponseCode().equals(ResponseCodeAndMessages.SUCCESSFUL.code())) {
        payment.setSessionId(response.getSessionId());
        payment.setProviderReference(response.getSessionId());
        payment.setNibssResponseCode(response.getResponseCode());
        payment.setPaymentReference(response.getPaymentReference());
        payment.setResponseMessage(response.getNarration());
      } else {
        payment.setNibssErrorCode(response.getCode());
        payment.setNibssErrorTimestamp(response.getTimestamp());
        payment.setNibssErrorMessage(response.getMessage());
      }
    paymentResponse =  PaymentResponse.buildPaymentResponse(response, chargeRequest, linkingReference);
    payment.setResponseCode(paymentResponse.getResponse().getCode());
    payment.setResponseMessage(paymentResponse.getResponse().getMessage());
    paymentRepository.save(payment);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }


    return paymentResponse;
  }


  public NibssNameEnquiryResponse nameEnquiryInternal(ChargeRequest nameEnquiryRequest, Configuration configuration, String appUser) {
    saveLogService.saveLog(nameEnquiryRequest.getTransaction().getReference(),
        LogType.NAME_ENQUIRY_INTERNAL.name(), gson.toJson(nameEnquiryRequest), "", "", "", configuration.getAppUser());
    String referecne = configuration.getInstitutionCode() + TimeUtil.getCurrentDateTime() + UUIDUtil.RandGeneratedStr();
    String url = configuration.getBaseUrl() + "/nipservice/v1/nip/nameenquiry";
    NibssNameEquiryRequest nibssNameEquiryRequest = NameEnquiryRequest.nameChargeEnquiryRequest(nameEnquiryRequest, referecne);

    NibssNameEnquiryResponse response = this.restClientProxy.sendRequestProxy(url,
        nibssNameEquiryRequest, NibssNameEnquiryResponse.class, header(appUser),
        "POST", "application/json", configuration,
        nameEnquiryRequest.getTransaction().getReference(), LogType.NAME_ENQUIRY_INTERNAL.name(), appUser);
    saveLogService.saveLog(nameEnquiryRequest.getTransaction().getReference(),
        LogType.NAME_ENQUIRY_INTERNAL.name(),"" , "gson.toJson(response)", "", "", configuration.getAppUser());
      return response;
  }

  public PaymentResponse nameEnquiry(String authorization, NameEnquiryRequest nameEnquiryRequest) {
    Auth auth = getAuth(authorization);

    Configuration configuration = configurationRepository.findOneByAppUser(auth.getAppUser())
        .orElseThrow(() -> new NotFoundException("Configuration not found"));

    saveLogService.saveLog(nameEnquiryRequest.getTransaction().getReference(),
        LogType.NAME_ENQUIRY.name(), gson.toJson(nameEnquiryRequest), "", "", "", configuration.getAppUser());

    String referecne = configuration.getInstitutionCode() + TimeUtil.getCurrentDateTime() + UUIDUtil.RandGeneratedStr();
    String url = configuration.getBaseUrl() + "/nipservice/v1/nip/nameenquiry";
    NibssNameEquiryRequest nibssNameEquiryRequest = NameEnquiryRequest.nameEnquiryRequest(nameEnquiryRequest, referecne);

    NibssNameEnquiryResponse response = this.restClientProxy.sendRequestProxy(url,
        nibssNameEquiryRequest, NibssNameEnquiryResponse.class, header(auth.getAppUser()),
        "POST", "application/json", configuration,
        nameEnquiryRequest.getTransaction().getReference(), LogType.NAME_ENQUIRY.name(), auth.getAppUser());

    return PaymentResponse.buildNameEnquiryResponse(response, nameEnquiryRequest, referecne);
  }

  public PaymentResponse getTransactionStatus(String authorization, String transactionId) {
    Auth auth = getAuth(authorization);

    Configuration configuration = configurationRepository.findOneByAppUser(auth.getAppUser())
        .orElseThrow(() -> new NotFoundException("Configuration not found"));

    saveLogService.saveLog(transactionId,
        LogType.TRANSACTION_QUERY.name(), "", "", "", "", configuration.getAppUser());
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
        queryRequest, NibssTransactionQueryResponse.class, header(auth.getAppUser()),
        "POST", "application/json", configuration,transactionId
        , LogType.TRANSACTION_QUERY.name(), auth.getAppUser());
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

  public List<MandateResponse> mandate(String authorization, MandateRequest mandateRequest) {

    try {
      Auth auth = getAuth(authorization);

      saveLogService.saveLog(mandateRequest.getReference(),
          LogType.MANDATE_REQUEST.name(), gson.toJson(mandateRequest), "", "", "",
          auth.getAppUser());

      Configuration configuration = configurationRepository.findOneByAppUser(auth.getAppUser())
          .orElseThrow(() -> new NotFoundException("Configuration not found"));

      MandateConfiguration mandateConfiguration = mandateConfigurationRepository
          .findOneByAppUser(auth.getAppUser())
          .orElseThrow(() -> new NotFoundException("Configuration not found"));

      List<Mandate> mandate = MandateRequest
          .buildMandate(mandateRequest.getMandateRequests(), configuration);
      mandateRepository.saveAll(mandate);

      NibssMandateRequestAuthData mandateAuthRequestData = MandateRequest
          .buildAuthData(mandateConfiguration);
      List<NibssMandateRequestData> mandateRequestData = MandateRequest
          .buildMandateData(mandateRequest.getMandateRequests());
      System.out.println(gson.toJson(mandateRequestData));

      NibssMandateRequest nibssMandateRequest = NibssMandateRequest.builder()
          .auth(mandateAuthRequestData)
          .mandateRequests(mandateRequestData)
          .build();



      saveLogService.saveLog(mandateRequest.getReference(),
          LogType.MANDATE_REQUEST.name(), gson.toJson(mandateRequest), "", "", "",
          auth.getAppUser());

      String url = configuration.getBaseUrl() + "/mandate/v1/create";
//    https://apitest.nibss-plc.com.ng/mandate/v1/create
//    https://apitest.nibss-plc.com.ng

      String response = this.restClientProxy.mandateRequestProxy(url,
          nibssMandateRequest, mandateHeader(auth.getAppUser(), configuration),
          "POST", "application/json", configuration, ""
          , LogType.MANDATE_REQUEST.name());

//
//      saveLogService.saveLog(mandateRequest.getReference(),
//          LogType.MANDATE_REQUEST.name(), gson.toJson(response), "", "", "",
//          auth.getAppUser());
      System.out.println("MANDATE_RESPONSE ====  " + response);

    }catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }


    //    String referecne = configuration.getInstitutionCode() + TimeUtil.getCurrentDateTime() + UUIDUtil.RandGeneratedStr();
//    String url = configuration.getBaseUrl() + "/nipservice/v1/nip/nameenquiry";
//    NibssNameEquiryRequest nibssNameEquiryRequest = NameEnquiryRequest.nameEnquiryRequest(nameEnquiryRequest, referecne);
//    NibssNameEnquiryResponse response = this.restClientProxy.sendRequestProxy(url,
//        nibssNameEquiryRequest, NibssNameEnquiryResponse.class, header(),
//        "POST", "application/json", configuration,
//        nameEnquiryRequest.getTransaction().getReference(), LogType.NAME_ENQUIRY.name());
//    return PaymentResponse.buildNameEnquiryResponse(response, nameEnquiryRequest, referecne);
    return null;
  }


  public NibssBalanceEnquiryResponse getBalance(String authorization, String accountNumber) {

    Auth auth = getAuth(authorization);

    Configuration configuration = configurationRepository.findOneByAppUser(auth.getAppUser())
        .orElseThrow(() -> new NotFoundException("Configuration not found"));

    SourceAccount sourceAccount = fineOneByAccountAndAppUser(accountNumber, auth.getAppUser());
    saveLogService.saveLog("",
        LogType.GET_BALANCE.name(), "", "", "", "", auth.getAppUser());
    String url = configuration.getBaseUrl() + "/nipservice/v1/nip/balanceenquiry";
    String reference = configuration.getInstitutionCode() + TimeUtil.getCurrentDateTime() + UUIDUtil.RandGeneratedStr();
    NibssGetBalanceRequest queryRequest = NibssGetBalanceRequest.builder()
        .authorizationCode(sourceAccount.getAuthorizationCode())
        .billerId(configuration.getBillerId())
        .channelCode("1")
        .destinationInstitutionCode(sourceAccount.getSourceInstitutionCode())
        .targetAccountName(sourceAccount.getSourceAccountName())
        .targetAccountNumber(sourceAccount.getSourceAccountNumber())
        .targetBankVerificationNumber(sourceAccount.getSourceBvn())
        .transactionId(reference)
        .build();

    NibssBalanceEnquiryResponse response = this.restClientProxy.sendRequestProxy(url,
        queryRequest, NibssBalanceEnquiryResponse.class, header(auth.getAppUser()),
        "POST", "application/json", configuration, reference
        , LogType.GET_BALANCE.name(), auth.getAppUser());
    return PaymentResponse.buildBalanceEnquiryResponse(response);
  }

  private void checkTransactionExist(String transactionId) {
    boolean savedPayment = paymentRepository.findOneByTransactionId(transactionId).isPresent();
    if (savedPayment) {
      throw new ConflictException("Transaction with reference (" + transactionId + ") already exists");
    }
  }



  public GetTokenResponse getToken(String appUser) {
//    Configuration configuration = configurationRepository.findOneByAppUser(appUser);
    Configuration configuration = configurationRepository.findOneByAppUser(appUser)
        .orElseThrow(() -> new NotFoundException("Configuration not found"));
    String url = configuration.getBaseUrl() + "/reset";
    NibssAuthResponse nibssAuthResponse = this.restClientProxy.getTokenRequestProxy(url, configuration, NibssAuthResponse.class);
    return  GetTokenResponse.builder()
        .token(nibssAuthResponse.getAccessToken())
        .tokenType(nibssAuthResponse.getTokenType())
        .expiresIn(nibssAuthResponse.getExpiresIn())
        .expires(nibssAuthResponse.getExpiresIn())
        .build();
  }

  public GetTokenResponse getMandateToken(String appUser, Configuration configuration) {
    MandateConfiguration configuration1 = mandateConfigurationRepository.findOneByAppUser(appUser)
        .orElseThrow(() -> new NotFoundException("Configuration not found"));
    configuration.setClientId(configuration1.getClientId());
    configuration.setClientSecret(configuration1.getClientSecret());
    configuration.setScope(configuration1.getScope());
    configuration.setGrantType(configuration1.getGrantType());
    String url = configuration.getBaseUrl() + "/reset";

    System.out.println("configuration1 "+ gson.toJson(configuration1));
    NibssAuthResponse nibssAuthResponse = this.restClientProxy.getTokenRequestProxy(url, configuration, NibssAuthResponse.class);
    return  GetTokenResponse.builder()
        .token(nibssAuthResponse.getAccessToken())
        .tokenType(nibssAuthResponse.getTokenType())
        .expiresIn(nibssAuthResponse.getExpiresIn())
        .expires(nibssAuthResponse.getExpiresIn())
        .build();
  }

  private SourceAccount fineOneByAccount(String accountNumber) {
    return sourceAccountRepository.findOneBySourceAccountNumber(accountNumber).orElseThrow(() ->
        new BadRequestException("Source Account number (" + accountNumber + ") does not exists")
    );
  }

  private SourceAccount fineOneByAccountAndAppUser(String accountNumber, String appUser) {
    return sourceAccountRepository.findOneBySourceAccountNumberAndAppUser(accountNumber, appUser).orElseThrow(() ->
        new BadRequestException("Source Account number (" + accountNumber + ") does not exists")
    );
  }



  private Configuration fineOneByBillerId(String billerId) {
    return configurationRepository.findOneByBillerId(billerId).orElseThrow(() ->
        new BadRequestException("Source Account number (" + billerId + ") does not exists")
    );
  }

  private Payment verifyTransactionExist(String reference) {
    return paymentRepository.findOneByTransactionId(reference).orElseThrow(() ->
        new ConflictException("Transaction with reference (" + reference + ") does not exists")
    );
  }

  private Map<String, String>  mandateHeader(String appUser, Configuration configuration) {
    GetTokenResponse  response = getMandateToken(appUser, configuration);
    System.out.println(gson.toJson(response));
    System.out.println("GetTokenResponse "+ gson.toJson(response));
    Map<String, String> headers = new HashMap<>(1);
    headers.put("Authorization", "Bearer " + response.getToken());
    return headers;
  }

  private Map<String, String> header(String appUser) {
    GetTokenResponse  response = getToken(appUser);
    Map<String, String> headers = new HashMap<>(1);
    headers.put("Authorization", "Bearer " + response.getToken());
    return headers;
  }

  public Auth getAuth(String authCredentials) {
    try {
      authCredentials = authCredentials.replace("Bearer ", "");
      Claims authClaim = JwtUtil.decodeTokenClaims(authCredentials);
      Auth auth = authRepository.findOneByUniqueIdAndStatus(authClaim.get("id").toString(), "ACTIVE").orElseThrow(() ->
          new AuthenticationException("Invalid Token"));
        return auth;
    } catch (Exception e) {
      log.error(":::: ERROR AUTHENTICATING CALLING APP ::", e);
      throw new AuthenticationException("Invalid Token");
    }
  }
}
