package com.flutterwave.nibsseasypay.model.request;
import com.flutterwave.nibsseasypay.entity.Configuration;
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

public class UpsertSourceAccountRequest {

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
  private String authorizationCode;


  public SourceAccount toConfiguration(SourceAccount sourceAccount) {
    if(sourceAccount == null) {
      sourceAccount = new SourceAccount();
    }

    sourceAccount.setAppUser(appUser);
    sourceAccount.setBillerId(billerId);
    sourceAccount.setMandateReferenceNumber(mandateReferenceNumber);
    sourceAccount.setSourceAccountName(sourceAccountName);
    sourceAccount.setSourceAccountNumber(sourceAccountNumber);
    sourceAccount.setSourceBvn(sourceBvn);
    sourceAccount.setSourceKycLevel(sourceKycLevel);
    sourceAccount.setSourceInstitutionCode(sourceInstitutionCode);
    sourceAccount.setAuthorizationCode(authorizationCode);
    return sourceAccount;
  }

}
