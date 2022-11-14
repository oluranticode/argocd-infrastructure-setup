package com.flutterwave.nibsseasypay.controller;

import com.flutterwave.nibsseasypay.model.request.GetTokenRequest;
import com.flutterwave.nibsseasypay.model.response.GetTokenResponse;
import com.flutterwave.nibsseasypay.model.response.PaymentApiResponse;
import com.flutterwave.nibsseasypay.service.AuthService;
import com.flutterwave.nibsseasypay.validator.InputValidator;
import com.google.gson.Gson;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 07/09/2021   - 07 - 03:49$
 */
@RestController
@Slf4j
public class AuthController {

  private final AuthService authService;
  private final Gson gson;
  private final Gson gsonForResponse;

  public AuthController(AuthService authService, Gson gson, Gson gsonForResponse) {
    this.authService = authService;
    this.gson = gson;
    this.gsonForResponse = gsonForResponse;
  }

  @PostMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PaymentApiResponse> auth(
      @Valid @RequestBody GetTokenRequest request, BindingResult bindingResult) {
    log.info("Auth request " + gson.toJson(request));
    InputValidator.validate(bindingResult, request.getUsername());
    GetTokenResponse response = authService.getToken(request);
    log.info("Auth response " + gsonForResponse.toJson(response));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

//  @PostMapping(path = "/auth/verify", produces = MediaType.APPLICATION_JSON_VALUE)
//  public ResponseEntity<Claims> verify(
//      @Valid @RequestBody GetTokenRequest request, BindingResult bindingResult) {
//    log.info("Create appUser request " + gson.toJson(request));
//    InputValidator.validate(bindingResult);
//    Claims response = authService.getClaims(request.getClientId(), "");
//    log.info("Create appUser response " + gsonForResponse.toJson(response));
//    return new ResponseEntity<>(response, HttpStatus.OK);
//  }
}
