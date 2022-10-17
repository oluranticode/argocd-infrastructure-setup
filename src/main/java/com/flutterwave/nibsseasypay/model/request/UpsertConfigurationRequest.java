package com.flutterwave.nibsseasypay.model.request;
import com.flutterwave.nibsseasypay.entity.Configuration;
import com.google.gson.annotations.Expose;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.SafeHtml;

/**
 * @author Abdussamad
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor

public class UpsertConfigurationRequest {

  @Expose
  @SafeHtml
  @NotNull
  @NotEmpty
  private String baseUrl;

  @Expose
  @SafeHtml
  @NotNull
  @NotEmpty
  private String clientId;

  @SafeHtml
  @NotNull
  @NotEmpty
  private String clientSecret;

  @Expose
  @SafeHtml
  @NotNull
  @NotEmpty
  private String grantType;


  @Expose
  @SafeHtml
  @NotNull
  @NotEmpty
  private String scope;


  @Expose
  @SafeHtml
  @NotNull
  @NotEmpty
  private String billerId;

  @Expose
  @SafeHtml
  @NotNull
  @NotEmpty
  private String institutionCode;


  private String mandateReferenceNumber;
  @Expose
  @SafeHtml
  @NotNull
  @NotEmpty
  private String sourceAccountName;

  @Expose
  @SafeHtml
  @NotNull
  @NotEmpty
  private String sourceAccountNumber;

  @Expose
  @SafeHtml
  @NotNull
  @NotEmpty
  private String sourceInstitutionCode;

  @Expose
  @SafeHtml
  @NotNull
  @NotEmpty
  private String sourceBvn;

  @Expose
  @SafeHtml
  @NotNull
  @NotEmpty
  private String sourceKycLevel;

  @Expose
  @SafeHtml
  private String proxyUsername;

  @Expose
  @SafeHtml
  private String authorizationCode;

  private String proxyPassword;
  @Expose
  @SafeHtml

  private String proxyUrl;
  @Expose
  private Integer proxyPort;

  public Configuration toConfiguration(Configuration configuration) {
    if(configuration == null) {
      configuration = new Configuration();
    }

    configuration.setBaseUrl(baseUrl);
    configuration.setClientId(clientId);
    configuration.setClientSecret(clientSecret);
    configuration.setBillerId(billerId);
    configuration.setGrantType(grantType);
    configuration.setScope(scope);
    configuration.setInstitutionCode(institutionCode);
    configuration.setMandateReferenceNumber(mandateReferenceNumber);
    configuration.setSourceAccountName(sourceAccountName);
    configuration.setSourceAccountNumber(sourceAccountNumber);
    configuration.setSourceBvn(sourceBvn);
    configuration.setSourceKycLevel(sourceKycLevel);
    configuration.setSourceInstitutionCode(sourceInstitutionCode);
    configuration.setProxyUrl(proxyUrl);
    configuration.setProxyPort(proxyPort);
    configuration.setProxyUsername(proxyUsername);
    configuration.setProxyPassword(proxyPassword);
    configuration.setAuthorizationCode(authorizationCode);
    return configuration;
  }

}
