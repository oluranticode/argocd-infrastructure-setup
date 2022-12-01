package com.flutterwave.nibsseasypay.model.request;
import com.flutterwave.nibsseasypay.entity.MandateConfiguration;
import com.flutterwave.nibsseasypay.entity.SourceAccount;
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

public class UpsertMandateConfigurationRequest {

  @Expose
  @SafeHtml
  @NotNull
  @NotEmpty
  private String billerId;

  @Expose
  @SafeHtml
  @NotNull
  @NotEmpty
  private String appUser;

  @Expose
  @SafeHtml
  @NotNull
  @NotEmpty
  private String apiKey;

  @Expose
  @SafeHtml
  @NotNull
  @NotEmpty
  private String username;

  @Expose
  @SafeHtml
  @NotNull
  @NotEmpty
  private String password;

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



  public MandateConfiguration toConfiguration(MandateConfiguration mandateConfiguration) {
    if(mandateConfiguration == null) {
      mandateConfiguration = new MandateConfiguration();
    }
    mandateConfiguration.setAppUser(appUser);
    mandateConfiguration.setBillerId(billerId);
    mandateConfiguration.setApiKey(apiKey);
    mandateConfiguration.setPassword(password);
    mandateConfiguration.setUsername(username);
    mandateConfiguration.setClientSecret(clientSecret);
    mandateConfiguration.setClientId(clientId);
    mandateConfiguration.setGrantType(grantType);
    mandateConfiguration.setScope(scope);
    return mandateConfiguration;
  }

}
