package com.flutterwave.nibsseasypay.validator;

import com.flutterwave.nibsseasypay.exception.BadRequestException;
import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * Created by Aminu Cincin Kabunu.
 * Email aminu@flutterwavego.com Date 19/05/2021   - 20 - 20:08$
 */
public class InputValidator {

  public static void validate(BindingResult bindingResult, String requestId)
      throws BadRequestException {
    if (!bindingResult.hasErrors()) {
      return;
    }
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    StringBuilder messageBuilder = new StringBuilder();
    for (FieldError fieldError : fieldErrors) {
      messageBuilder.append(fieldError.getField());
      messageBuilder.append(" ");
      messageBuilder.append(fieldError.getDefaultMessage());
      messageBuilder.append("; ");
    }
    throw new BadRequestException(requestId + " " + messageBuilder.toString().trim());
  }
}
