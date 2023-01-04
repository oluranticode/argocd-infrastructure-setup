package com.flutterwave.nibsseasypay.exception;

/**
 * Created by Aminu Cincin Kabunu.
 * Email aminu@flutterwavego.com Date 19/05/2021   - 20 - 20:08$
 */
public abstract class NibssEasyPayApiException extends RuntimeException {

  public NibssEasyPayApiException(String message) {
    super(message);
  }

  public NibssEasyPayApiException(Throwable cause) {
    super(cause);
  }
}
