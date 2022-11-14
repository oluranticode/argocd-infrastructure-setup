package com.flutterwave.nibsseasypay.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 04/06/2021   - 04 - 13:25$
 */
@SuppressWarnings("deprecation")
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

  @Autowired
  AppInterceptor appInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(appInterceptor).addPathPatterns(
        "/configurations"
//        "/charge",
//        "/charge/status",
//        "/charge/banks",
//        "/charge/balance",
//        "/charge/statement",
//        "/payment/token"
        );
  }
}