package com.flutterwave.nibsseasypay.converter;

import com.flutterwave.nibsseasypay.util.SecurityUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 2/24/22   - 24 - 3:58 PM$
 */


@Converter
public class MaskLogSensitiveData implements AttributeConverter<String, String> {

  @Override
  public String convertToDatabaseColumn(String data) {
    if (data == null) {
      return null;
    }
    return SecurityUtil.maskMessage(data, getSensitiveDataPattern());
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    return dbData;
  }

  //define sensitive parameters pattern here
  private List<String> getSensitiveDataPattern() {
    List<String> maskPatterns= new ArrayList<>();
    maskPatterns.add("\\\"pin\\\"\\s*:\\s*\\\"(.*?)\\\"");
    return (!maskPatterns.isEmpty()) ? maskPatterns : null;
  }
}
