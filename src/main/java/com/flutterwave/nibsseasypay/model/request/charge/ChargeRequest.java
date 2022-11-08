package com.flutterwave.nibsseasypay.model.request.charge;

import com.flutterwave.nibsseasypay.entity.Configuration;
import com.flutterwave.nibsseasypay.entity.Payment;
import com.flutterwave.nibsseasypay.entity.SourceAccount;
import com.flutterwave.nibsseasypay.nibsseastpay.model.request.NibssTransactionRequest;
import com.flutterwave.nibsseasypay.nibsseastpay.model.response.NibssNameEnquiryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 10/6/22   - 06 - 1:13 PM$
 */

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ChargeRequest {
    private String merchant;
    private String location;
    private String channelCode;
    private String transactionLocation;
    private ChargeOrderData order;
    private ChargeTransactionData transaction;

    public static NibssTransactionRequest chargeRequest(ChargeRequest chargeRequest, String linkingReference,
        NibssNameEnquiryResponse nameEnquiryResponse, Configuration configuration, SourceAccount sourceAccount) {

       return NibssTransactionRequest.builder()
           .amount(chargeRequest.getOrder().getAmount())
           .beneficiaryAccountName(nameEnquiryResponse.getAccountName())
           .beneficiaryAccountNumber(nameEnquiryResponse.getAccountNumber())
           .beneficiaryBankVerificationNumber(nameEnquiryResponse.getBankVerificationNumber())
           .beneficiaryKYCLevel(String.valueOf(nameEnquiryResponse.getKycLevel()))
           .beneficiaryNarration(chargeRequest.getTransaction().getNarration())
           .billerId(configuration.getBillerId())
           .channelCode(chargeRequest.getChannelCode())
           .destinationInstitutionCode(chargeRequest.getTransaction().getSourceoffunds().getAccount().getTo().getBank().getCode())
           .mandateReferenceNumber(sourceAccount.getMandateReferenceNumber())
           .nameEnquiryRef(nameEnquiryResponse.getTransactionId())
           .originatorAccountName(sourceAccount.getSourceAccountName())
           .originatorAccountNumber(sourceAccount.getSourceAccountNumber())
           .originatorBankVerificationNumber(sourceAccount.getSourceBvn())
           .originatorKYCLevel(sourceAccount.getSourceKycLevel())
           .originatorNarration(chargeRequest.getTransaction().getNarration())
           .paymentReference(chargeRequest.getTransaction().getReference())
           .SourceInstitutionCode(Integer.valueOf(sourceAccount.getSourceInstitutionCode()))
           .transactionId(linkingReference)
           .transactionLocation(chargeRequest.getTransactionLocation())
           .build();
    }

    public static Payment buildPayment(ChargeRequest request, Configuration configuration, String transactionId, SourceAccount sourceAccount) {
        Payment payment = new Payment();
        payment.setLinkingReference(transactionId);
        payment.setTransactionId(request.getTransaction().getReference());
        payment.setAmount(request.getOrder().getAmount());
        payment.setBeneficiaryAccountName(request.getTransaction().getSourceoffunds().getAccount().getTo().getName());
        payment.setBeneficiaryAccountNumber(request.getTransaction().getSourceoffunds().getAccount().getTo().getNumber());
        payment.setDestinationInstitutionCode(request.getTransaction().getSourceoffunds().getAccount().getTo().getBank().getCode());
        payment.setOriginatorAccountName(sourceAccount.getSourceAccountName());
        payment.setOriginatorAccountNumber(sourceAccount.getSourceAccountNumber());
        payment.setOriginatorBankVerificationNumber(sourceAccount.getSourceBvn());
        payment.setOriginatorKYCLevel(sourceAccount.getSourceKycLevel());
        payment.setBeneficiaryNarration(request.getTransaction().getNarration());
        payment.setOriginatorNarration(request.getTransaction().getNarration());
        payment.setInstitutionCode(configuration.getInstitutionCode());
        payment.setChannelCode(request.getChannelCode());
        payment.setMandateReferenceNumber(sourceAccount.getMandateReferenceNumber());
        payment.setTransactionLocation(request.getTransactionLocation());
        payment.setBillerId(configuration.getBillerId());
        return payment;

    }
}
