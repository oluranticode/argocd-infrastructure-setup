package com.flutterwave.nibsseasypay.configuration;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 12/1/22   - 01 - 3:44 PM$
 */

public class MaskingPatternLayout extends PatternLayout {

  private Pattern multilinePattern;
  private List<String> maskPatterns = new ArrayList<>();

  public void addMaskPattern(String maskPattern) {
    maskPatterns.add(maskPattern);
    multilinePattern = Pattern
        .compile(maskPatterns.stream().collect(Collectors.joining("|")), Pattern.MULTILINE);
  }

  @Override
  public String doLayout(ILoggingEvent event) {
    return maskMessage(super.doLayout(event));
  }

  private String maskMessage(String message) {
    if (multilinePattern == null) {
      return message;
    }
    StringBuilder sb = new StringBuilder(message);
    Matcher matcher = multilinePattern.matcher(sb);
    while (matcher.find()) {
      IntStream.rangeClosed(1, matcher.groupCount()).forEach(group -> {
        if (matcher.group(group) != null) {
          IntStream.range(matcher.start(group), matcher.end(group))
              .forEach(i -> sb.setCharAt(i, '*'));
        }
      });
    }
    return sb.toString();
  }
}
