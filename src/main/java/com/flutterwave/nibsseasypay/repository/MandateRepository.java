package com.flutterwave.nibsseasypay.repository;

import com.flutterwave.nibsseasypay.entity.Mandate;
import com.flutterwave.nibsseasypay.entity.MandateConfiguration;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 08/06/2021   - 08 - 18:24$
 */
@Repository
public interface MandateRepository extends PagingAndSortingRepository<Mandate, Integer> {
  List<Mandate> findAll();
}
