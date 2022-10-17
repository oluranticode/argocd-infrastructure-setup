package com.flutterwave.nibsseasypay.model.request;

import com.flutterwave.nibsseasypay.entity.Auth;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.SafeHtml;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 04/06/2021   - 04 - 10:59$
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AuthRequest {
  @NotNull
  @NotEmpty
  @SafeHtml
  public String username;

  @NotNull
  @NotEmpty
  @SafeHtml
  public String password;

  public Integer expiry;

  @SafeHtml
  public String status;



  public Auth toAuth(AuthRequest authRequest, String uniqueId,
      String issuer, Integer expiry) {
    Auth auth = new Auth();
    auth.setUsername(authRequest.getUsername());
    auth.setPassword(authRequest.getPassword());
    if (authRequest.equals("")) {
      auth.setExpiry(expiry);
    } else {
      auth.setExpiry(expiry);
    }
    auth.setUniqueId(uniqueId);
    auth.setStatus("ACTIVE");
    auth.setType("API");
    auth.setIssuer(issuer);
    return auth;
  }
}
