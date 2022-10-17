package com.flutterwave.nibsseasypay.nibsseastpay.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 10/4/22   - 04 - 12:40 PM$
 */

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NibssAuthResponse {

  @JsonProperty("token_type")
  @SerializedName("token_type")
  public String tokenType;

  @JsonProperty("expires_in")
  @SerializedName("expires_in")
  public int expiresIn;

  @JsonProperty("ext_expires_in")
  @SerializedName("ext_expires_in")
  public int extExpiresIn;

  @JsonProperty("access_token")
  @SerializedName("access_token")
  public String accessToken;
}
