package com.flutterwave.nibsseasypay.repository;

import com.flutterwave.nibsseasypay.entity.Bank;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends PagingAndSortingRepository<Bank, Long> {
  Optional<Bank> findOneByCode(String bankCode);
  List<Bank> findAll();
}
