package com.flutterwave.nibsseasypay.nibsseastpay.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 11/7/22   - 07 - 10:58 AM$
 */
public class ErrorMessageListData {
  @JsonProperty("errorCode[0]")
  @SerializedName("errorCode[0]")
  public String errorCode;
}
