package com.flutterwave.nibsseasypay.configuration;


import com.flutterwave.nibsseasypay.exception.AuthenticationException;
import com.flutterwave.nibsseasypay.service.AuthService;
import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 04/06/2021   - 04 - 13:27$
 */
@Slf4j
@Component
public class AppInterceptor implements HandlerInterceptor {


  private final AuthService authService;

  @Autowired
  public AppInterceptor(AuthService authService) {
    this.authService = authService;
  }

  @Override
  public boolean preHandle
      (@NotNull HttpServletRequest request,  @NotNull HttpServletResponse response, @NotNull Object handler) {
    try {

      String authorization = request.getHeader("Authorization");
      Claims claims =  authService.getClaims(authorization, request.getRequestURI());
      if(claims.isEmpty()) {
        throw new AuthenticationException("Unauthorized");
      }else {
        return true;
      }
    } catch (Exception e) {
      log.info("authorization================  {} ",  e.getMessage());
      throw new AuthenticationException("Unauthorized");
    }
//    return true;
  }
  @Override
  public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
      @NotNull  Object handler, ModelAndView modelAndView) {
    log.info("");
  }
  @Override
  public void afterCompletion
      (@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object
          handler, Exception exception) {
    log.info("");
  }
}