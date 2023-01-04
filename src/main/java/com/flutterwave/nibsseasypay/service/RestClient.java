package com.flutterwave.nibsseasypay.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flutterwave.nibsseasypay.entity.Payment;
import com.flutterwave.nibsseasypay.exception.ProcessingException;
import com.flutterwave.nibsseasypay.exception.TimeoutException;
import com.flutterwave.nibsseasypay.model.constant.ResponseCodeAndMessages;
import com.flutterwave.nibsseasypay.repository.PaymentRepository;
import com.flutterwave.nibsseasypay.util.RestTemplateResponseErrorHandler;
import com.google.gson.Gson;
import com.sun.istack.Nullable;
import java.net.URI;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * @author Abdussamad
 */
@Component
public class RestClient {

  private static final ObjectMapper objectMapper = new ObjectMapper();
  private final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);
//  private RestTemplate restTemplate = new RestTemplate();

  @Autowired
  private RestTemplate restTemplate;
  private final PaymentRepository paymentRepository;
  private final Gson gson;

  @Autowired
  public RestClient(RestTemplateBuilder restTemplateBuilder,
      RestTemplateResponseErrorHandler errorHandler,
      PaymentRepository paymentRepository, Gson gson) {
    this.paymentRepository = paymentRepository;
    this.gson = gson;
    this.restTemplate = restTemplateBuilder
        .errorHandler(errorHandler)
        .build();
  }

  public <T> T get(String requestPath,
      Class<T> responseClass,
      Map<String, String> headers,
      String requestId,
      Payment payment) {
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    headers.forEach(requestHeaders::set);
    try {
      LOGGER.info("::: URI ::" + requestPath);
      URI uri = new URI(requestPath);
      HttpEntity<?> requestEntity = new HttpEntity<>("", requestHeaders);
      ResponseEntity<String> responseEntity = restTemplate
          .exchange(uri, HttpMethod.GET, requestEntity, String.class);
      LOGGER.info("response payload from mpesa : " + responseEntity.getBody());
      LOGGER.info(
          "response HTTP status code from mpesa : " + responseEntity.getStatusCode().toString());
      return objectMapper.readValue(responseEntity.getBody(), responseClass);
    } catch (ResourceAccessException resourceAccessException) {
      LOGGER.error(requestId + " Connection Exception " + resourceAccessException.getMessage());
      if(payment != null) {
        payment.setResponseCode(ResponseCodeAndMessages.TIMEOUT.code());
        payment.setResponseMessage(ResponseCodeAndMessages.TIMEOUT.message());
        paymentRepository.save(payment);
      }
      throw new TimeoutException(ResponseCodeAndMessages.TIMEOUT.message());
    } catch (Exception e) {
      LOGGER.error("Request failed", e);
      throw new ProcessingException(e.getMessage());
    }
  }

  public <T> T put(
      String url, Object requestObject, Class<T> responseClass,
      @Nullable Map<String, String> headers, String requestId, Payment payment) {
    HttpServerErrorException httpServerErrorException;
    try {
      LOGGER.info("request payload to mpesa"
          + " : " + objectMapper.writeValueAsString(requestObject));
      HttpHeaders requestHeaders = new HttpHeaders();
      requestHeaders.setContentType(MediaType.APPLICATION_JSON);
      if (headers != null) {
        headers.forEach(requestHeaders::set);
      }
      HttpEntity<?> requestEntity = new HttpEntity<>(requestObject, requestHeaders);
      ResponseEntity<String> responseEntity = restTemplate
          .exchange(url, HttpMethod.PUT, requestEntity, String.class, requestObject);
      LOGGER.info("response payload from mpesa : " + responseEntity.getBody());
      LOGGER.info("response HTTP status code from mpesa : " + responseEntity.getStatusCode()
          .toString());
      return objectMapper.readValue(responseEntity.getBody(), responseClass);

    } catch (ResourceAccessException resourceAccessException) {
      LOGGER.error(requestId + " Connection Exception " + resourceAccessException.getMessage());
      if(payment != null) {
        payment.setResponseCode(ResponseCodeAndMessages.TIMEOUT.code());
        payment.setResponseMessage(ResponseCodeAndMessages.TIMEOUT.message());
        paymentRepository.save(payment);
      }
      throw new TimeoutException(ResponseCodeAndMessages.TIMEOUT.message());
    } catch (Exception e) {
      LOGGER.error("Request failed", e);
      LOGGER.error("response from mpesa (Error): " + e.getMessage());
      throw new ProcessingException("An error occurred");
    }
  }

  public <T> T post(
      String url, Object requestObject, Class<T> responseClass,
      @Nullable Map<String, String> headers, String requestId, Payment payment) {
    HttpServerErrorException httpServerErrorException;
    try {
      LOGGER.info("request payload to mpesa"
          + " : " + objectMapper.writeValueAsString(requestObject));
      HttpHeaders requestHeaders = new HttpHeaders();
      requestHeaders.setContentType(MediaType.APPLICATION_JSON);
      if (headers != null) {
        headers.forEach(requestHeaders::set);
      }

      HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);
      ResponseEntity<String> responseEntity = restTemplate
          .exchange(url, HttpMethod.POST, requestEntity, String.class);

      LOGGER.info("response payload from imali : " + responseEntity.getBody());
      LOGGER.info("response HTTP status code from imali : " + responseEntity.getStatusCode()
          .toString());
//      String data = "";
//      if(responseEntity.getBody().contains("[")) {
//        data = StringUtils.substringAfter(responseEntity.getBody(), "[");
//        data = StringUtils.substringBefore(responseEntity.getBody(), "]");
//        return objectMapper.readValue(data, responseClass);
//      }else {
//        return objectMapper.readValue(responseEntity.getBody(), responseClass);
//      }
      return objectMapper.readValue(responseEntity.getBody(), responseClass);

    }catch (ResourceAccessException resourceAccessException) {
      LOGGER.error(requestId + " Connection Exception " + resourceAccessException.getMessage());
      if(payment != null) {
        payment.setResponseCode(ResponseCodeAndMessages.TIMEOUT.code());
        payment.setResponseMessage(ResponseCodeAndMessages.TIMEOUT.message());
        paymentRepository.save(payment);
      }
      throw new TimeoutException(ResponseCodeAndMessages.TIMEOUT.message());
    } catch (Exception e) {
      LOGGER.error("Request failed", e);
      LOGGER.error("response from imali (Error): " + e.getMessage());
      throw new ProcessingException("An error occurred");
    }
  }
}