package com.flutterwave.nibsseasypay.configuration;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 8/4/22   - 04 - 11:49 AM$
 */
@Configuration
public class RestTemplateConfig {

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) throws NoSuchAlgorithmException, KeyManagementException {

    TrustManager[] trustAllCerts = new TrustManager[] {
        new X509TrustManager() {
          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
          }
          public void checkClientTrusted(
              java.security.cert.X509Certificate[] certs, String authType) {
          }
          public void checkServerTrusted(
              java.security.cert.X509Certificate[] certs, String authType) {
          }
        }
    };
    SSLContext sslContext = SSLContext.getInstance("SSL");
    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
    CloseableHttpClient httpClient = HttpClients.custom()
        .setSslcontext(sslContext)
        .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
        .build();
    HttpComponentsClientHttpRequestFactory customRequestFactory = new HttpComponentsClientHttpRequestFactory();
    customRequestFactory.setHttpClient(httpClient);
    return builder.requestFactory(() -> customRequestFactory).build();
  }
}