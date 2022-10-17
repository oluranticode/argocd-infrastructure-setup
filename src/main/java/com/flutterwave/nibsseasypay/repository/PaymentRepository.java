package com.flutterwave.nibsseasypay.repository;

import com.flutterwave.nibsseasypay.entity.Payment;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 29/04/2021   - 29 - 17:35$
 */
@Repository
public interface PaymentRepository extends
    PagingAndSortingRepository<Payment, Long> {

  Optional<Payment> findOneByTransactionId(String transactionId);


  @Query(nativeQuery = true, value = "Select * from payment where created_at BETWEEN :startDate AND :endDate")
  List<Payment> findAllByCreatedAtBetween(@Param("startDate") Timestamp startDate,
      @Param("endDate") Timestamp endDate);
}
