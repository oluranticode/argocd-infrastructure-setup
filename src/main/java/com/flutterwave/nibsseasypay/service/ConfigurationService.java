package com.flutterwave.nibsseasypay.service;

import com.flutterwave.nibsseasypay.entity.Auth;
import com.flutterwave.nibsseasypay.exception.ConflictException;
import com.flutterwave.nibsseasypay.exception.NotFoundException;
import com.flutterwave.nibsseasypay.model.request.AuthRequest;
import com.flutterwave.nibsseasypay.model.request.UpsertConfigurationRequest;
import com.flutterwave.nibsseasypay.model.request.UpsertSourceAccountRequest;
import com.flutterwave.nibsseasypay.model.response.AuthResponse;
import com.flutterwave.nibsseasypay.model.response.AuthResponseData;
import com.flutterwave.nibsseasypay.model.response.ConfigurationResponse;
import com.flutterwave.nibsseasypay.repository.AuthRepository;
import com.flutterwave.nibsseasypay.repository.ConfigurationRepository;
import com.flutterwave.nibsseasypay.repository.SourceAccountRepository;
import com.flutterwave.nibsseasypay.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Abdussamad
 */
@Slf4j
@Service
public class ConfigurationService {

  @Value("${auth.issuer}")
  private String issuer;

  @Value("${auth.expiry}")
  private Integer expiry;

  private final ConfigurationRepository accessBankConfigurationRepository;
  private final SourceAccountRepository sourceAccountRepository;
  private final AuthRepository authRepository;
  private static final Integer DEFAULT_CONFIGURATION_ID = 1;

  public ConfigurationService(
      ConfigurationRepository accessBankConfigurationRepository,
      SourceAccountRepository sourceAccountRepository,
      AuthRepository authRepository) {
    this.accessBankConfigurationRepository = accessBankConfigurationRepository;
    this.sourceAccountRepository = sourceAccountRepository;
    this.authRepository = authRepository;
  }

  public ConfigurationResponse createConfiguration(
      UpsertConfigurationRequest upsertConfigurationRequest) {
    accessBankConfigurationRepository.save(upsertConfigurationRequest.toConfiguration(null));
    return ConfigurationResponse.builder().status(true).message("Operation successful").build();
  }

  public ConfigurationResponse updateConfiguration(
      UpsertConfigurationRequest upsertConfigurationRequest) {
    accessBankConfigurationRepository.save(upsertConfigurationRequest
          .toConfiguration(accessBankConfigurationRepository.findOneById(DEFAULT_CONFIGURATION_ID)));
      return ConfigurationResponse.builder().status(true).message("Operation successful").build();
  }

  public AuthResponse createAppUser(AuthRequest authRequest) {
  Boolean isExist  = authRepository.findOneByUsername(authRequest.getUsername()).isPresent();
  if(isExist) {
    throw   new ConflictException("Record already exist");
  }
    Auth auth = authRequest.toAuth(authRequest, UUIDUtil.generateType1UUID(), issuer, expiry);
    authRepository.save(auth);
    return AuthResponse.builder()
        .response(AuthResponseData.builder().code("00").message("Successful").build()).build();
  }

  public AuthResponse updateAppUser(AuthRequest authRequest) {
    Auth auth = authRepository.findOneByUsername(authRequest.getUsername())
        .orElseThrow(() ->
        new NotFoundException("Record not found"));
    auth.setPassword(auth.getPassword());
    if(!authRequest.getExpiry().equals("")) {
      auth.setExpiry(authRequest.getExpiry());
    }
    if(!authRequest.getStatus().equals("")) {
      auth.setStatus(authRequest.getStatus());
    }

    authRepository.save(auth);
    return AuthResponse.builder()
        .response(AuthResponseData.builder().code("00").message("Successful").build()).build();
  }

  public ConfigurationResponse createSourceAccount(
      UpsertSourceAccountRequest upsertConfigurationRequest) {

    checkAccountExistForClient(upsertConfigurationRequest.getBillerId(), upsertConfigurationRequest.getSourceAccountNumber());
    sourceAccountRepository.save(upsertConfigurationRequest.toConfiguration(null));
    return ConfigurationResponse.builder().status(true).message("Operation successful").build();
  }

  public ConfigurationResponse updateSourceAccount(
      UpsertSourceAccountRequest upsertConfigurationRequest) {
    sourceAccountRepository.save(upsertConfigurationRequest
        .toConfiguration(sourceAccountRepository.findOneById(DEFAULT_CONFIGURATION_ID)));
    return ConfigurationResponse.builder().status(true).message("Operation successful").build();
  }


  private void checkAccountExistForClient(String billerId, String accountNumber) {
    boolean savedPayment = sourceAccountRepository.findOneByBillerIdAndSourceAccountNumber(billerId, accountNumber).isPresent();
    if (savedPayment) {
      throw new ConflictException("Account (" + accountNumber + ") number already exists");
    }
  }

}
