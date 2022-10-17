package com.flutterwave.nibsseasypay.scheduler;

import com.flutterwave.nibsseasypay.model.constant.TransactionType;
import com.flutterwave.nibsseasypay.service.SaveLogService;
import com.flutterwave.nibsseasypay.service.PaymentService;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 17/08/2021   - 17 - 14:00$
 */

@Component
@Slf4j
public class CronJobScheduler {

  private final PaymentService paymentService;
  private final SaveLogService saveLogService;

  @Value("${spring.profiles.active}")
  private String activeProfile;

  public CronJobScheduler(
      PaymentService paymentService,
      SaveLogService saveLogService) {
    this.paymentService = paymentService;
    this.saveLogService = saveLogService;
  }


//  @Scheduled(cron = "0 */12 * * * ?")
  public void cronJobMonthlySch() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    Date now = new Date();
    String strDate = sdf.format(now);
    System.out.println("Java cron job expression:: 1df73054-1d24-11ec-union-9621-0242ac130002" + strDate);
    log.info("Report cron job expression::  - 1df73054-1d24-11ec-union-9621-0242ac130002 " + strDate);
    saveLogService.saveLog(sdf.toString(),
        TransactionType.GET_TOKEN.name(),
        "Running monthly Scheduler",
        "",
        "",
        "Running");
  }

}
