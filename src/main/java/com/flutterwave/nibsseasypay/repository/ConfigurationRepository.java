package com.flutterwave.nibsseasypay.repository;

import com.flutterwave.nibsseasypay.entity.Configuration;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Abdussamad
 */
@Repository
public interface ConfigurationRepository extends
    PagingAndSortingRepository<Configuration, Long> {

    Configuration findOneById(Integer id);
    Optional<Configuration> findOneByAppUser(String appUser);
    Optional<Configuration> findOneByBillerId(String billerId);
    List<Configuration> findAll();

}
