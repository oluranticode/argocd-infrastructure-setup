package com.flutterwave.nibsseasypay.configuration;
import com.flutterwave.nibsseasypay.util.SecurityUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 12/1/22   - 01 - 3:45 PM$
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

  private List<String> getSensitiveDataPattern() {
    List<String> maskPatterns = new ArrayList<>();
    maskPatterns.add("\\\"bvn\\\"\\s*:\\s*\\\"(.*?)\\\"");
    maskPatterns.add("\\\"apiKey\\\"\\s*:\\s*\\\"(.*?)\\\"");
    maskPatterns.add("\\\"password\\\"\\s*:\\s*\\\"(.*?)\\\"");
    return (!maskPatterns.isEmpty()) ? maskPatterns : null;
  }
}
