package com.flutterwave.nibsseasypay.controller;

import com.flutterwave.nibsseasypay.model.request.AuthRequest;
import com.flutterwave.nibsseasypay.model.request.UpsertConfigurationRequest;
import com.flutterwave.nibsseasypay.model.response.AuthResponse;
import com.flutterwave.nibsseasypay.model.response.ConfigurationResponse;
import com.flutterwave.nibsseasypay.model.response.PaymentApiResponse;
import com.flutterwave.nibsseasypay.service.ConfigurationService;
import com.flutterwave.nibsseasypay.validator.InputValidator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Abdussamad
 */
@Slf4j
@RestController
public class ConfigurationController {

  private final ConfigurationService configurationService;
  private final Gson gson;
  private final Gson gsonForResponse;

  @Autowired
  public ConfigurationController(
      ConfigurationService configurationService) {
    this.configurationService = configurationService;
    this.gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    this.gsonForResponse = new GsonBuilder().setPrettyPrinting().create();
  }

  @PostMapping(path = "/configurations", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PaymentApiResponse> create(
      @Valid @RequestBody UpsertConfigurationRequest request, BindingResult bindingResult) {
    log.info("Create configuration request " + gson.toJson(request));
    InputValidator.validate(bindingResult, request.getClientId());
    ConfigurationResponse response = configurationService.createConfiguration(request);
    log.info("Create configuration response " + gsonForResponse.toJson(response));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PutMapping(path = "/configurations", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PaymentApiResponse> update(
      @Valid @RequestBody UpsertConfigurationRequest request, BindingResult bindingResult) {
    log.info("Update configuration request " + gson.toJson(request));
    InputValidator.validate(bindingResult, request.getClientId());
    ConfigurationResponse response = configurationService.updateConfiguration(request);
    log.info("Update configuration response " + gsonForResponse.toJson(response));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping(path = "/appusers", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PaymentApiResponse> createAuthCredentials(
      @Valid @RequestBody AuthRequest request, BindingResult bindingResult) {
    log.info("Create appUser request " + gson.toJson(request));
    InputValidator.validate(bindingResult, request.getUsername());
    AuthResponse response = configurationService.createAppUser(request);
    log.info("Create appUser response " + gsonForResponse.toJson(response));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PutMapping(path = "/appusers", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PaymentApiResponse> UpdateAuthCredentials(
      @Valid @RequestBody AuthRequest request, BindingResult bindingResult) {
    log.info("Update appUser request " + gson.toJson(request));
    InputValidator.validate(bindingResult, request.getUsername());
    AuthResponse response = configurationService.updateAppUser(request);
    log.info("Update appUser response " + gsonForResponse.toJson(response));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
