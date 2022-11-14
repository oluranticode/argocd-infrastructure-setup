package com.flutterwave.nibsseasypay.repository;

import com.flutterwave.nibsseasypay.entity.Auth;
import com.flutterwave.nibsseasypay.entity.Configuration;
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
public interface MandateConfigurationRepository extends PagingAndSortingRepository<MandateConfiguration, Integer> {
  Optional<MandateConfiguration> findOneByUsername(String username);
  Optional<MandateConfiguration> findOneByAppUser(String appUser);
  MandateConfiguration findOneById(Integer id);
  Optional<MandateConfiguration> findOneByBillerId(String billerId);
  List<MandateConfiguration> findAll();

}
