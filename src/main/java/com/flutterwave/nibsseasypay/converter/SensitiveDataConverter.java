package com.flutterwave.nibsseasypay.converter;

import com.flutterwave.nibsseasypay.util.SecurityUtil;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.beans.factory.annotation.Value;

@Converter
public class SensitiveDataConverter implements AttributeConverter<String, String> {

  @Value("${maven}")
  private String key;

  @Override
  public String convertToDatabaseColumn(String data) {
    return SecurityUtil.encryptWithJasypt(key, data);
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    return SecurityUtil.decryptWithJasypt(key, dbData);
  }
}
