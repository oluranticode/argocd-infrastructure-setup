package com.flutterwave.nibsseasypay.repository;

import com.flutterwave.nibsseasypay.entity.Token;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends
    PagingAndSortingRepository<Token, Long> {
  Token findOneById(Integer id);
}
