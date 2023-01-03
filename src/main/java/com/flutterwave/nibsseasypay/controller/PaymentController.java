package com.flutterwave.nibsseasypay.controller;

import com.flutterwave.nibsseasypay.model.request.MandateRequest;
import com.flutterwave.nibsseasypay.model.request.charge.ChargeRequest;
import com.flutterwave.nibsseasypay.model.request.nameenquiry.NameEnquiryRequest;
import com.flutterwave.nibsseasypay.model.response.GetTokenResponse;
import com.flutterwave.nibsseasypay.model.response.PaymentResponse;
import com.flutterwave.nibsseasypay.nibsseasypay.model.response.MandateResponse;
import com.flutterwave.nibsseasypay.nibsseasypay.model.response.NibssBalanceEnquiryResponse;
import com.flutterwave.nibsseasypay.service.PaymentService;
import com.flutterwave.nibsseasypay.validator.InputValidator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 3/15/22   - 15 - 7:43 AM$
 */

@RestController
@Slf4j
public class PaymentController {

  private final PaymentService paymentService;
  private final Gson gson;
  private final Gson gsonForResponse;

  @Autowired
  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
    this.gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    this.gsonForResponse = new GsonBuilder().setPrettyPrinting().create();
  }

  @PostMapping(path = "/payment/charge", produces = "application/json")
  public ResponseEntity<PaymentResponse> charge(@RequestHeader("Authorization") String authorization, @Valid @RequestBody ChargeRequest request, BindingResult bindingResult) {
    log.info("Generate transaction request : " + gsonForResponse.toJson(request));
    InputValidator.validate(bindingResult, request.getTransaction().getReference());
    PaymentResponse response = paymentService.charge(authorization, request);
    log.info("Generate transaction response " + gsonForResponse.toJson(response));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

    @PostMapping(path = "/charge/nameenquiry", produces = "application/json")
  public ResponseEntity<PaymentResponse> nameEnquiry(@RequestHeader("Authorization") String authorization, @Valid @RequestBody NameEnquiryRequest request, BindingResult bindingResult) {
    log.info(String.format("Generate transaction request : %s ",  gsonForResponse.toJson(request)));
    InputValidator.validate(bindingResult, request.getTransaction().getReference());
    PaymentResponse response = paymentService.nameEnquiry(authorization, request);
    log.info(String.format("Generate transaction response: %s ",  gsonForResponse.toJson(response)));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping(path = "/payment/charge/status",  produces = "application/json")
  public ResponseEntity<PaymentResponse> getTransactionStatus(
      @RequestHeader("Authorization") String authorization,
      @RequestParam(name="reference", required = true ) String reference) {
    log.info("Get transaction status :: " + gson.toJson(reference));
    PaymentResponse response = paymentService.getTransactionStatus(authorization, reference);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping(path = "/payment/balance",  produces = "application/json")
  public ResponseEntity<NibssBalanceEnquiryResponse> getBalance(@RequestHeader("Authorization") String authorization, @RequestParam(name="accountNumber", required = true ) String accountNumber) {
    System.out.println("bearerToken :::::" + authorization);
    log.info("Get Balance :: ");
    NibssBalanceEnquiryResponse response = paymentService.getBalance(authorization, accountNumber);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping(path = "/charge/auth",  produces = "application/json")
  public ResponseEntity<GetTokenResponse> getToken(@RequestParam(name="appUser", required = true ) String appUser) {
    GetTokenResponse response = paymentService.getToken(appUser);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping(path = "/mandate",  produces = "application/json")
  public ResponseEntity<List<MandateResponse>> mandate(@RequestHeader("Authorization") String authorization, @Valid @RequestBody MandateRequest request, BindingResult bindingResult) {
    log.info("Mandate request :: " + request.getReference());
    log.info("Mandate request :: " + gson.toJson(request));
    System.out.println("Mandate request :: " +request.toString());
    InputValidator.validate(bindingResult, request.getReference());
    List<MandateResponse> response = paymentService.mandate(authorization, request);
    log.info("Mandate Response :: " + gsonForResponse.toJson(response));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
  
}
