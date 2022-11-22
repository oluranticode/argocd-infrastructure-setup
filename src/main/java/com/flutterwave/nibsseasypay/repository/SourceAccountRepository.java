package com.flutterwave.nibsseasypay.repository;

import com.flutterwave.nibsseasypay.entity.Configuration;
import com.flutterwave.nibsseasypay.entity.SourceAccount;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Abdussamad
 */
@Repository
public interface SourceAccountRepository extends
    PagingAndSortingRepository<SourceAccount, Long> {

    SourceAccount findOneById(Integer id);
    Optional<SourceAccount> findOneBySourceAccountNumber(String accountNumber);
    Optional<SourceAccount> findOneByBillerIdAndSourceAccountNumber(String billerId, String accountNumber);

}
