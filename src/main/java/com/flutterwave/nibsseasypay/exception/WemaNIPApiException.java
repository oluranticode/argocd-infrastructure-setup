package com.flutterwave.nibsseasypay.exception;

/**
 * Created by Aminu Cincin Kabunu.
 * Email aminu@flutterwavego.com Date 19/05/2021   - 20 - 20:08$
 */
public abstract class WemaNIPApiException extends RuntimeException {

  public WemaNIPApiException(String message) {
    super(message);
  }

  public WemaNIPApiException(Throwable cause) {
    super(cause);
  }
}
