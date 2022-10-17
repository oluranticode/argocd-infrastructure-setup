package com.flutterwave.nibsseasypay.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Aminu Cincin Kabunu.
 * Email aminu@flutterwavego.com Date 19/05/2021   - 20 - 20:08$
 */
public class TimeUtil {

  public static String getIsoTime(Timestamp timestamp) {
    if (timestamp == null) {
      return null;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    return timestamp.toLocalDateTime().format(formatter);
  }

  public static Timestamp nowByTimeZoneLagos() {
    TimeZone timeZone1 = TimeZone.getTimeZone("Africa/Lagos");
    return Timestamp.valueOf(ZonedDateTime.now(timeZone1.toZoneId()).toLocalDateTime());
  }

  public static Timestamp now() {
    Date date = new Date();
    return new Timestamp(date.getTime());
  }

  public static Timestamp futureTime(int seconds) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.SECOND, seconds);
    Date expiryDate = cal.getTime();
    return new Timestamp(expiryDate.getTime());
  }

  public static Integer timestampToUnixSeconds() {
    Date date = new Date();
    return (int) (date.getTime() / 1000);
  }

  public static String getGMTTime() {
    final Date currentTime = new Date();
    final SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss z");
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    return sdf.format(currentTime);
  }

  public static Date convertStringToDate(String inDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh24:mm:ss");
    Date date = new Date();
    try {
      date = sdf.parse(inDate);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return date;
  }

  public static String getCurrentDateTime() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
    return dateFormat.format(new Date());
  }

}
