package com.flutterwave.nibsseasypay.service;

import static java.net.Proxy.Type.HTTP;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flutterwave.nibsseasypay.entity.Configuration;
import com.flutterwave.nibsseasypay.exception.ProcessingException;
import com.flutterwave.nibsseasypay.util.RestTemplateResponseErrorHandler;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/**
 * @author Oluwafemi
 */
@Component
@Slf4j
public class RestClientProxy {

  private static final ObjectMapper objectMapper = new ObjectMapper();
  private final RestTemplate restTemplate;
  private final Gson gson;
  private final SaveLogService saveLogService;

  @Autowired
  public RestClientProxy(RestTemplateBuilder restTemplateBuilder,
      RestTemplateResponseErrorHandler gatewayProviderResponseErrorHandler, Gson gson,
      SaveLogService saveLogService) {
    this.saveLogService = saveLogService;
    this.restTemplate = restTemplateBuilder
        .errorHandler(gatewayProviderResponseErrorHandler)
        .build();
    this.gson = gson;
  }


  public <T> T post(
      String url, Object requestObject, Class<T> responseClass,
      @Nullable Map<String, String> headers, Configuration configuration) {
    try {
      log.info("Headers " + headers);
      log.info("request payload to wema : " + objectMapper.writeValueAsString(requestObject));
      HttpHeaders requestHeaders = new HttpHeaders();
      requestHeaders.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
      if (headers != null) {
        headers.forEach(requestHeaders::set);
      }
      HttpEntity<?> requestEntity = new HttpEntity<>(requestObject, requestHeaders);
      ResponseEntity<String> responseEntity = restTemplate
          .exchange(url, HttpMethod.POST, requestEntity, String.class, requestObject);
      log.info("response payload from wema =====: " + responseEntity.toString());
      log.info("response payload from wema : " + responseEntity.getBody());
      log.info("response HTTP status code from wema : " + responseEntity.getStatusCode()
          .toString());
//      if (responseEntity != null && responseEntity.getBody() != null) {
//        if (!responseEntity.getBody().contains("{")) {
//          String[] responseBody = responseEntity.getBody().split("\\|");
//          return objectMapper.readValue(objectMapper
//                  .writeValueAsString(WemaPaymentResponse.builder().status(responseBody[0]).build()),
//              responseClass);
//        }
//      }
      return objectMapper.readValue(responseEntity.getBody(), responseClass);

    } catch (Exception e) {
      log.error("Request failed", e);
      log.error("response from Flutterwave (Error): " + e.getMessage());
      throw new ProcessingException("response from Flutterwave (Error): " + e.getMessage());
    }
  }

  public <T> T sendRequestProxy(String url, Object reqBody, Class<T> responseClass,
      Map<String, String> headers, String method, String contentType,
      Configuration configuration, String requestId, String operationType) {
    if(reqBody != null)
      log.info(String.format("%s request payload to Union Bank:  %s ", requestId,  gson.toJson(reqBody)));
    String reqMediaType = contentType;
    String proxyIP = configuration.getProxyUrl();
    int proxyPort = configuration.getProxyPort();
    final String username = configuration.getProxyUsername();
    String password = configuration.getProxyPassword();
    RequestBody body = null;
    Proxy proxy = new Proxy(HTTP, new InetSocketAddress(proxyIP, proxyPort));
    final TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
          @Override
          public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
              String authType) throws CertificateException {
          }

          @Override
          public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
              String authType) throws CertificateException {
          }

          @Override
          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
          }
        }
    };

    try {

      final SSLContext sslContext = SSLContext.getInstance("TLS");
      sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
      final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
      Authenticator proxyAuthenticator = new Authenticator() {
        @Override
        public Request authenticate(Route route, Response response) throws IOException {
          String credential = Credentials.basic(username, password);
          return response.request().newBuilder()
              .header("Proxy-Authorization", credential)
              .build();
        }
      };
      OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
      clientBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
      clientBuilder.hostnameVerifier(new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      });
      OkHttpClient client = clientBuilder.proxy(proxy).proxyAuthenticator(proxyAuthenticator)
          .build();
      MediaType mediaType = MediaType.parse(reqMediaType);
      if ("POST".equalsIgnoreCase(method)) {
        body = RequestBody.create(mediaType, objectMapper.writeValueAsString(reqBody));
      }

      Builder builder = new Builder()
          .url(url)
          .method(method.toUpperCase(), body);
      if (Objects.nonNull(headers)) {
        headers.forEach((key, value) -> {
          builder.addHeader(key, value);
        });
      }
      Request request = builder.build();
      Response response = client.newCall(request).execute();
      String resBody = response.body().string();
      log.info(String.format("%s response payload from Union Bank %s ", requestId, gson.toJson(resBody)));
      log.info(String.format(" %s response HTTP status code from Union Bank ", requestId),
          response.code());
      saveLogService.saveLog(requestId, operationType + "_LOG", gson.toJson(reqBody), gson.toJson(resBody), String.valueOf(response.code()), String.valueOf(response.code()));
      return gson.fromJson(resBody, responseClass);
    } catch (Exception e) {
      log.info(String.format("%s Request failed", requestId), e.getMessage());
      saveLogService.saveLog(requestId, operationType + "_LOG", gson.toJson(reqBody), e.getMessage(), "500", "ERROR");
      throw new ProcessingException(e.getMessage());
    }
  }

  public String mandateRequestProxy(String url, Object reqBody,
      Map<String, String> headers, String method, String contentType,
      Configuration configuration, String requestId, String operationType) {
    if(reqBody != null)
      log.info(String.format("%s request payload to Union Bank:  %s ", requestId,  gson.toJson(reqBody)));
    String reqMediaType = contentType;
    String proxyIP = configuration.getProxyUrl();
    int proxyPort = configuration.getProxyPort();
    final String username = configuration.getProxyUsername();
    String password = configuration.getProxyPassword();
    RequestBody body = null;
    Proxy proxy = new Proxy(HTTP, new InetSocketAddress(proxyIP, proxyPort));
    final TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
          @Override
          public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
              String authType) throws CertificateException {
          }

          @Override
          public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
              String authType) throws CertificateException {
          }

          @Override
          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
          }
        }
    };

    try {

      final SSLContext sslContext = SSLContext.getInstance("TLS");
      sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
      final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
      Authenticator proxyAuthenticator = new Authenticator() {
        @Override
        public Request authenticate(Route route, Response response) throws IOException {
          String credential = Credentials.basic(username, password);
          return response.request().newBuilder()
              .header("Proxy-Authorization", credential)
              .build();
        }
      };
      OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
      clientBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
      clientBuilder.hostnameVerifier(new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      });
      OkHttpClient client = clientBuilder.proxy(proxy).proxyAuthenticator(proxyAuthenticator)
          .build();
      MediaType mediaType = MediaType.parse(reqMediaType);
      if ("POST".equalsIgnoreCase(method)) {
        body = RequestBody.create(mediaType, objectMapper.writeValueAsString(reqBody));
      }

      Builder builder = new Builder()
          .url(url)
          .method(method.toUpperCase(), body);
      if (Objects.nonNull(headers)) {
        headers.forEach((key, value) -> {
          builder.addHeader(key, value);
        });
      }
      Request request = builder.build();
      Response response = client.newCall(request).execute();
      String resBody = response.body().string();
      log.info(String.format("%s response payload from NIBSS EASY PAY %s ", requestId, gson.toJson(resBody)));
      log.info(String.format(" %s response HTTP status code from  NIBSS EASY PAY ", requestId),
          response.code());
      saveLogService.saveLog(requestId, operationType + "_LOG", gson.toJson(reqBody), gson.toJson(resBody), String.valueOf(response.code()), String.valueOf(response.code()));
      return resBody;
    } catch (Exception e) {
      log.info(String.format("%s Request failed", requestId), e.getMessage());
      saveLogService.saveLog(requestId, operationType + "_LOG", gson.toJson(reqBody), e.getMessage(), "500", "ERROR");
      throw new ProcessingException(e.getMessage());
    }
  }

  public <T> T sendAuthenticationRequestProxy(String url, String reqBody, Class<T> responseClass,
      Map<String, String> headers, String method, String reqMediaType, String reference,
      String operationType, boolean saveLog, Configuration configuration) {
    String proxyIP = configuration.getProxyUrl();
    int proxyPort = configuration.getProxyPort();
    final String username = configuration.getProxyUsername();
    String password = configuration.getProxyPassword();
    RequestBody body = null;
    Proxy proxy = new Proxy(HTTP, new InetSocketAddress(proxyIP, proxyPort));
    final TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
          @Override
          public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
              String authType) throws CertificateException {
          }

          @Override
          public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
              String authType) throws CertificateException {
          }

          @Override
          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
          }
        }
    };
    try {
      final SSLContext sslContext = SSLContext.getInstance("TLS");
      sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
      final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
      Authenticator proxyAuthenticator = new Authenticator() {
        @Override
        public Request authenticate(Route route, Response response) throws IOException {
          String credential = Credentials.basic(username, password);
          return response.request().newBuilder()
              .header("Proxy-Authorization", credential)
              .build();
        }
      };
      OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
      clientBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
      clientBuilder.hostnameVerifier(new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      });
      OkHttpClient client = clientBuilder.proxy(proxy).proxyAuthenticator(proxyAuthenticator)
          .connectTimeout(40, TimeUnit.SECONDS)
          .writeTimeout(40, TimeUnit.SECONDS)
          .readTimeout(55, TimeUnit.SECONDS)
          .build();
      MediaType mediaType = MediaType.parse(reqMediaType);
      if ("POST".equalsIgnoreCase(method)) {
        body = RequestBody.create(mediaType, reqBody);
      }

      Request.Builder builder = new Request.Builder()
          .url(url)
          .method(method.toUpperCase(), body);
      if (Objects.nonNull(headers)) {
        headers.forEach((key, value) -> {
          builder.addHeader(key, value);
        });
      }
      Request request = builder.build();
      log.info(
          "request payload to nibss :  " + request != null && request.body() != null ? request.body()
              .toString() : null);
      log.info("url : " + url);
      System.out.println("request payload to nibss :  " + request != null && request.body() != null ? request.body()
          .toString() : null);
      Response response = client.newCall(request).execute();
      String resBody = response.body().string();
      log.info("response payload from nibss : " + resBody);
      log.info("response HTTP status code from nibss : " + response.code());
      System.out.println("response HTTP status code from nibss : " + response.code());
      System.out.println("response payload from nibss : " + resBody);

      saveLogService.saveLog(reference, reqBody, resBody, operationType, String.valueOf(response.code()), "");
      if (reqMediaType.equals("application/json")) {
        return objectMapper.readValue(resBody, responseClass);
      }
      return objectMapper.readValue(resBody, responseClass);
    } catch (Exception e) {
      log.error("Request failed", e);
      throw new ProcessingException(e.getMessage());
    }
  }

  public <T> T getTokenRequestProxy(String url,  Configuration configuration, Class<T> responseClass
     ) {
    log.info(configuration.getClientId() + " Get token request : ");
    String proxyIP = configuration.getProxyUrl();
    int proxyPort = configuration.getProxyPort();
    final String username = configuration.getProxyUsername();
    String password = configuration.getProxyPassword();
    Proxy proxy = new Proxy(HTTP, new InetSocketAddress(proxyIP, proxyPort));
    final TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
          @Override
          public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
              String authType) throws CertificateException {
          }

          @Override
          public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
              String authType) throws CertificateException {
          }

          @Override
          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
          }
        }
    };
    try {
      final SSLContext sslContext = SSLContext.getInstance("TLS");
      sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
      final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
      Authenticator proxyAuthenticator = new Authenticator() {
        @Override
        public Request authenticate(Route route, Response response) throws IOException {
          String credential = Credentials.basic(username, password);
          return response.request().newBuilder()
              .header("Proxy-Authorization", credential)
              .build();
        }
      };
      OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
      clientBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
      clientBuilder.hostnameVerifier(new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      });
      OkHttpClient client = clientBuilder.proxy(proxy).proxyAuthenticator(proxyAuthenticator)
          .build();
      RequestBody formBody = new FormBody.Builder()
          .add("client_secret", configuration.getClientSecret())
          .add("client_id", configuration.getClientId())
          .add("grant_type", configuration.getGrantType())
          .add("scope", configuration.getScope())
          .build();

      Request request = new Request.Builder()
          .url(url)
          .addHeader("Content-Type", "application/x-www-form-urlencoded")
          .post(formBody)
          .build();
      Call call = client.newCall(request);
      Response response = call.execute();
      String resBody = response.body().string();
      log.info(String.format("%s response payload from Union Bank %s ", configuration.getClientId(), gson.toJson(resBody)));
      log.info(String.format("%s %s  response HTTP status code from Union Bank ", configuration.getClientId(),
          response.code()));
      return gson.fromJson(resBody, responseClass);
    } catch (Exception e) {
      log.info(String.format("%s Request failed %s", configuration.getClientId(), e.getMessage()));
      saveLogService.saveLog(configuration.getClientId(), "GET_TOKEN" + "_LOG", "", e.getMessage(), "500", "ERROR");
      throw new ProcessingException(e.getMessage());
    }
  }
}