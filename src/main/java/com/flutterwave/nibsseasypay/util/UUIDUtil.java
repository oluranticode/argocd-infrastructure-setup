package com.flutterwave.nibsseasypay.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 06/09/2021   - 06 - 17:04$
 */
public class UUIDUtil {

  public static String generateType1UUID() {

    long most64SigBits = get64MostSignificantBitsForVersion();
    long least64SigBits = get64LeastSignificantBitsForVersion();

    return new UUID(most64SigBits, least64SigBits).toString();
  }

  private static long get64LeastSignificantBitsForVersion() {
    Random random = new Random();
    long random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
    long variant3BitFlag = 0x8000000000000000L;
    return random63BitLong + variant3BitFlag;
  }

  private static long get64MostSignificantBitsForVersion() {
    LocalDateTime start = LocalDateTime.of(1582, 10, 15, 0, 0, 0);
    Duration duration = Duration.between(start, LocalDateTime.now());
    long seconds = duration.getSeconds();
    long nanos = duration.getNano();
    long timeForUuidIn100Nanos = seconds * 10000000 + nanos * 100;
    long least12SignificatBitOfTime = (timeForUuidIn100Nanos & 0x000000000000FFFFL) >> 4;
    long version = 1 << 12;
    return
        (timeForUuidIn100Nanos & 0xFFFFFFFFFFFF0000L) + version + least12SignificatBitOfTime;
  }

  public static String RandGeneratedStr() {

    String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
    String numbers = "0123456789";

    // combine all strings
    String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

    // create random string builder
    StringBuilder sb = new StringBuilder();

    // create an object of Random class
    Random random = new Random();
    int length = 12;

    for (int i = 0; i < length; i++) {

      int index = random.nextInt(alphaNumeric.length());

      char randomChar = alphaNumeric.charAt(index);
      sb.append(randomChar);
    }
    return  sb.toString();
  }
}
