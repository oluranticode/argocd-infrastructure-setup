package com.flutterwave.nibsseasypay.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Aminu Cincin Kabunu.
 * Email aminu@flutterwavego.com Date 19/05/2021   - 20 - 20:08$
 */
@Component
public class LoggingUtil {

  static final Logger LOGGER = LoggerFactory.getLogger(LoggingUtil.class);
  private final RollbarService rollbarService;

  @Autowired
  public LoggingUtil(RollbarService rollbarService) {
    this.rollbarService = rollbarService;
  }

  public void info(String message) {
    LOGGER.info(message);
  }

  public void info(String message, Object object) {
    LOGGER.info(message, object);
  }

  public void error(Throwable throwable) {
    String message = "An error occurred: " + throwable.getMessage();
    LOGGER.error(message, throwable);
    rollbarService.sendError(throwable, message);
  }

  public void error(String message, Throwable throwable) {
    LOGGER.error(message, throwable);
    rollbarService.sendError(throwable, message);
  }
}
