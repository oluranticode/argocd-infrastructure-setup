package com.flutterwave.nibsseasypay.service;

import com.flutterwave.nibsseasypay.entity.Log;
import com.flutterwave.nibsseasypay.repository.LogRepository;
import com.google.gson.Gson;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 27/09/2021   - 27 - 12:53$
 */
@Service
public class SaveLogService {

  private final LogRepository logRepository;
  private final Gson gson;

  public SaveLogService(LogRepository logRepository, Gson gson) {
    this.logRepository = logRepository;
    this.gson = gson;
  }
  @Async
  public void saveLog(String reference, String operationType, Object requestBody,
      Object responseBody, String responseCode, String status, String appUser) {
    Log logs = new Log();
    logs.setReference(reference);
    logs.setAppUser(appUser);
    logs.setOperationType(operationType);
    logs.setRequestBody(gson.toJson(requestBody));
    logs.setResponseBody(gson.toJson(responseBody));
    logs.setResponseCode(responseCode);
    logs.setStatus(status);
    logRepository.save(logs);
  }

}
