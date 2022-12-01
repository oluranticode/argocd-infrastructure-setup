package com.flutterwave.nibsseasypay.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.flutterwave.nibsseasypay.entity.Configuration;
import com.flutterwave.nibsseasypay.entity.Mandate;
import com.flutterwave.nibsseasypay.entity.MandateConfiguration;
import com.flutterwave.nibsseasypay.nibsseasypay.model.request.NIbssMandateFileRequest;
import com.flutterwave.nibsseasypay.nibsseasypay.model.request.NibssMandateRequestAuthData;
import com.flutterwave.nibsseasypay.nibsseasypay.model.request.NibssMandateRequestData;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 11/7/22   - 07 - 10:42 AM$
 */


@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MandateRequest {
    private ArrayList<MandateRequestData> mandateRequests;
    private String reference;


    public static  NibssMandateRequestAuthData buildAuthData(
        MandateConfiguration mandateConfiguration) {
        return  NibssMandateRequestAuthData.builder()
            .apiKey(mandateConfiguration.getApiKey())
            .password(mandateConfiguration.getPassword())
            .username(mandateConfiguration.getUsername())
            .build();
    }


    public static List<NibssMandateRequestData>  buildMandateData(List<MandateRequestData> mandateRequests) {
      return mandateRequests.stream().map(data -> {
        NibssMandateRequestData mandateRequestData = new NibssMandateRequestData();
        mandateRequestData.setAccountNumber(data.getAccountNumber());
        mandateRequestData.setProductId(data.getProductId());
//        mandateRequestData.setPayeeName(data.getPayeeName());
        mandateRequestData.setBankCode(data.getBankCode());
//        mandateRequestData.setPayeeAddress(data.getPayeeAddress());
        mandateRequestData.setAccountName(data.getAccountName());
        mandateRequestData.setAmount(data.getAmount());
        mandateRequestData.setPayerName(data.getPayerName());
        mandateRequestData.setPayerAddress(data.getPayerAddress());
        mandateRequestData.setPhoneNumber(data.getPhoneNumber());
        mandateRequestData.setNarration(data.getNarration());
        mandateRequestData.setEmailAddress(data.getEmailAddress());
        mandateRequestData.setSubscriberCode(data.getSubscriberCode());
        mandateRequestData.setStartDate(data.getStartDate());
        mandateRequestData.setEndDate(data.getEndDate());
        mandateRequestData.setMandateFile(NIbssMandateFileRequest.builder()
            .fileExtension(data.getFileExtension())
            .fileBase64EncodedString(data.getFileBase64EncodedString())
            .build());
        return mandateRequestData;
      }).collect(Collectors.toList());
    }

   public static List<Mandate> buildMandate(List<MandateRequestData> mandateRequestData, Configuration configuration) {
     return mandateRequestData.stream().map(data -> {
      Mandate mandate = new Mandate();
       mandate.setReference(data.getReference());
     mandate.setAccountNumber(data.getAccountNumber());
     mandate.setAppUser(configuration.getAppUser());
     mandate.setBillerId(configuration.getBillerId());
     mandate.setProductId(data.getProductId());
     mandate.setBankCode(data.getBankCode());
     mandate.setPayeeName("");
     mandate.setPayerAddress(data.getPayerAddress());
     mandate.setPayeeAddress("");
     mandate.setAccountName(data.getAccountName());
     mandate.setAmount(data.getAmount());
     mandate.setPayerName(data.getPayerName());
     mandate.setPhoneNumber(data.getPhoneNumber());
     mandate.setNarration(data.getNarration());
     mandate.setEmailAddress(data.getEmailAddress());
     mandate.setSubscriberCode(data.getSubscriberCode());
     mandate.setStartDate(data.getStartDate());
     mandate.setEndDate(data.getEndDate());
     mandate.setFileExtension(data.getFileExtension());
     mandate.setFileBase64EncodedString(data.getFileBase64EncodedString());
     return mandate;
     }).collect(Collectors.toList());
   }
}
