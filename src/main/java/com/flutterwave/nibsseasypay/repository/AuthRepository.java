package com.flutterwave.nibsseasypay.repository;

import com.flutterwave.nibsseasypay.entity.Auth;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Aminu Cincin Kabunu.
 * <p>
 * Email aminu@flutterwavego.com Date 08/06/2021   - 08 - 18:24$
 */
@Repository
public interface AuthRepository extends PagingAndSortingRepository<Auth, Integer> {
  Optional<Auth> findOneByUsername(String username);
  Optional<Auth> findOneByUniqueId(String uniqueId);
  Optional<Auth> findOneByUsernameAndPassword(String username, String password);
  Optional<Auth> findOneByUniqueIdAndStatus(String uniqueId, String status);

}
