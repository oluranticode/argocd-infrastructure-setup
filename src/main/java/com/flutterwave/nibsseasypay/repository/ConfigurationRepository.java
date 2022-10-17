package com.flutterwave.nibsseasypay.repository;

import com.flutterwave.nibsseasypay.entity.Configuration;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Abdussamad
 */
@Repository
public interface ConfigurationRepository extends
    PagingAndSortingRepository<Configuration, Long> {

    Configuration findOneById(Integer id);

}
