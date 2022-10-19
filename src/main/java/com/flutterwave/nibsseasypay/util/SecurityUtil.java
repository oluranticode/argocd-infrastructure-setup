package com.flutterwave.nibsseasypay.util;

import com.flutterwave.nibsseasypay.exception.ProcessingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * Created by Aminu Cincin Kabunu.
 * Email aminu@flutterwavego.com Date 19/05/2021   - 20 - 20:08$
 */
public class SecurityUtil {

  public static String hashWithMd5(String rawKey) throws ProcessingException {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] encrypted = md.digest(rawKey.getBytes());
      return new String(Hex.encodeHex(encrypted));
    } catch (NoSuchAlgorithmException ex) {
      String errorMessage = "Unable to hash this string";
      throw new ProcessingException(errorMessage);
    }
  }

  public static String hashWithSha256(String rawKey) throws ProcessingException {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] encrypted = md.digest(rawKey.getBytes());
      return new String(Hex.encodeHex(encrypted));
    } catch (NoSuchAlgorithmException ex) {
      String errorMessage = "Unable to hash this string";
      throw new ProcessingException(errorMessage);
    }
  }


  public static String hashWithSha512(String rawKey) throws ProcessingException {
    return DigestUtils.sha512Hex(rawKey);
  }

  public static String hashWithHMACSHA1(String key, String data) throws ProcessingException {
    try {
      SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
      Mac mac = Mac.getInstance("HmacSHA1");
      mac.init(signingKey);
      return new String(Hex.encodeHex(mac.doFinal(data.getBytes(
          Charsets.UTF_8))));
    } catch (NoSuchAlgorithmException ex) {
      String errorMessage = "Unable to hash this string";
      throw new ProcessingException(errorMessage);
    } catch (Exception ex) {
      String errorMessage = "Unable to hash this string";
      throw new ProcessingException(errorMessage);
    }
  }

  public static String hashWithHmacSha256(String key, String data) throws ProcessingException {
    try {
      Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
      byte[] decodedPrivateKey = Hex.decodeHex(key.toCharArray());
      SecretKeySpec secret_key = new SecretKeySpec(decodedPrivateKey, "HmacSHA256");
      sha256_HMAC.init(secret_key);
      return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
    } catch (Exception ex) {
      String errorMessage = "Unable to hash this string";
      throw new ProcessingException(errorMessage);
    }
  }

  public static String base64Converter(String input) {
    Base64 base64 = new Base64();
    String encodedString = new String(base64.encode(input.getBytes()));
    return encodedString;
  }

  public static String encryptWithJasypt(String key, String data) {
    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    encryptor.setPassword(key);
    return encryptor.encrypt(data);
  }
  
  public static String decryptWithJasypt(String key, String data) {
    StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
    decryptor.setPassword(key);
    return decryptor.decrypt(data);
  }

  public static String maskMessage(String message, List<String> maskPatterns) {
    if (maskPatterns == null || maskPatterns.isEmpty()) {
      return message;
    }
    Pattern multilinePattern = Pattern
        .compile(maskPatterns.stream().collect(Collectors.joining("|")), Pattern.MULTILINE);
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

  public static String AESencrypt(String value, String key, String encryptionIv) {
    try {
      System.out.println(value);
      IvParameterSpec iv = new IvParameterSpec(encryptionIv.getBytes("UTF-8"));
      SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
      byte[] encrypted = cipher.doFinal(value.getBytes());
      System.out.println(Base64.encodeBase64String(encrypted));
      return Base64.encodeBase64String(encrypted);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public static String AESdecrypt(String value, String key, String encryptionIv) {
    try {
      IvParameterSpec iv = new IvParameterSpec(encryptionIv.getBytes("UTF-8"));
      SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
      byte[] original = cipher.doFinal(Base64.decodeBase64(value));
      return new String(original);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }
}
