package com.flutterwave.nibsseasypay.exception;

/**
 * Created by Aminu Cincin Kabunu.
 * Email aminu@flutterwavego.com Date 19/05/2021   - 20 - 20:08$
 */
public class TimeoutException extends WemaNIPApiException {

  public TimeoutException(String message) {
    super(message);
  }

  public TimeoutException(Throwable cause) {
    super(cause);
  }

}
