package com.flutterwave.nibsseasypay.util;

import com.flutterwave.nibsseasypay.security.JwtHandler;
import com.google.gson.GsonBuilder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.impl.DefaultJwtParser;

/**
 * Created by Aminu Cincin Kabunu.
 *
 * Email aminu@flutterwavego.com
 * Date 11/9/22   - 09 - 7:53 AM$
 */
public class JwtUtil {

  public static Claims decode(String authCredentials, String clientSecreteKey) {
    Claims claims = JwtHandler.decodeJWT(authCredentials, clientSecreteKey);
    System.out.println(claims.toString());
    return claims;
  }

  public static Claims decodeTokenClaims(String token) {
    String[] splitToken = token.split("\\.");
    System.out.println(token);
    String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";
    DefaultJwtParser parser = new DefaultJwtParser();
    Jwt<?, ?> jwt = parser.parse(unsignedToken);
    Claims claims = (Claims) jwt.getBody();
    return claims;
  }
}
