package com.flutterwave.nibsseasypay.service;

import com.flutterwave.nibsseasypay.entity.Auth;
import com.flutterwave.nibsseasypay.entity.Configuration;
import com.flutterwave.nibsseasypay.exception.AuthenticationException;
import com.flutterwave.nibsseasypay.exception.NotFoundException;
import com.flutterwave.nibsseasypay.model.request.GetTokenRequest;
import com.flutterwave.nibsseasypay.model.response.GetTokenResponse;
import com.flutterwave.nibsseasypay.repository.AuthRepository;
import com.flutterwave.nibsseasypay.repository.ConfigurationRepository;
import com.flutterwave.nibsseasypay.security.JwtHandler;
import com.flutterwave.nibsseasypay.util.JwtUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 04/06/2021   - 04 - 13:44$
 */
@Slf4j
@Service
public class AuthService {

  private final AuthRepository authRepository;

  @Autowired
  public AuthService(
      AuthRepository authRepository) {
//    this.configuration = configuration;
//    this.configuration = repository.findOneById(1);
    this.authRepository = authRepository;
  }

  public boolean authenticate(String value) {
    boolean valid = false;
//    try {
//      String toHash = configuration.getAuthUsername() + ":"
//          + configuration.getAuthPassword();
//      String hashValue = SecurityUtil.base64Converter(toHash);
//      String BASIC_STRING = "Basic ";
//      hashValue = BASIC_STRING.concat(hashValue);
//      if (hashValue.equals(value)) {
//        valid = true;
//      }
//    } catch (Exception e) {
//      log.info(">>>>>>>hashValue hashValue: " + e.getMessage());
//    }
    return valid;
  }

//  public boolean authenticateMonitor(String value) {
//    boolean valid = false;
//    try {
//      String toHash = configuration.getMonitorUsername() + ":"
//          + configuration.getMonitorPassword();
//      String hashValue = SecurityUtil.base64Converter(toHash);
//      String BASIC_STRING = "Basic ";
//      hashValue = BASIC_STRING.concat(hashValue);
//      if (hashValue.equals(value)) {
//        valid = true;
//      }
//    } catch (Exception e) {
//      log.info(">>>>>>>Auth Error: " + e.getMessage());
//    }
//    return valid;
//  }

  public GetTokenResponse getToken(GetTokenRequest getTokenRequest) {

    Auth auth = authRepository.findOneByUsernameAndPassword(getTokenRequest.getUsername(),
        getTokenRequest.getPassword()).orElseThrow(() ->
        new NotFoundException("Invalid username or password"));
    GetTokenResponse getTokenResponse = new GetTokenResponse();
    String jwt = JwtHandler
        .createJWT(auth.getUniqueId(), auth.getIssuer(), "Flutterwave",
            auth.getExpiry(), auth.getPassword());
    getTokenResponse.setToken(jwt);
    getTokenResponse.setExpires(auth.getExpiry());
    return getTokenResponse;
  }

  public Claims getClaims(String authCredentials, String path) {
    try {
      authCredentials = authCredentials.replace("Bearer ", "");
      System.out.println("Bereeeee" + authCredentials);
      Claims authClaim = JwtUtil.decodeTokenClaims(authCredentials);
      Auth auth = authRepository.findOneByUniqueIdAndStatus(authClaim.get("id").toString(), "ACTIVE").orElseThrow(() ->
          new AuthenticationException("Invalid Token"));
      System.out.println(new Gson().toJson(auth));
      if(auth.getType().equals("CONFIGURATION") && (path.equals("/configurations") || path.equals("/appusers") || path.equals("/profile/sourceaccount"))) {
        return JwtUtil.decode(authCredentials, auth.getPassword());
//      Claims claims = JwtHandler.decodeJWT(authCredentials, auth.getClientSecret());
//      log.info("claims=" + new GsonBuilder().create().toJson(claims));
//      return claims;
      }else if(auth.getType().equals("API") && !path.equals("/configurations")) {
        return JwtUtil.decode(authCredentials, auth.getPassword());
      }else {
        throw new AuthenticationException("Invalid Token");
      }
    } catch (Exception e) {
      log.error(":::: ERROR AUTHENTICATING CALLING APP ::", e);
      throw new AuthenticationException("Invalid Token");
    }
  }


//  private Claims decode(String authCredentials, String clientSecreteKey) {
//    Claims claims = JwtHandler.decodeJWT(authCredentials, clientSecreteKey);
//    log.info("claims=" + new GsonBuilder().create().toJson(claims));
//    return claims;
//  }
//
//  public Claims decodeTokenClaims(String token) {
//    String[] splitToken = token.split("\\.");
//    String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";
//    DefaultJwtParser parser = new DefaultJwtParser();
//    Jwt<?, ?> jwt = parser.parse(unsignedToken);
//    Claims claims = (Claims) jwt.getBody();
//    return claims;
//  }
}
