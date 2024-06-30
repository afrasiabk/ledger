package com.led.ger.repository;


import com.led.ger.entity.GeneralLedger;
import com.led.ger.entity.Subscription;
import jakarta.persistence.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query(value = "select * from subscription s where s.client_id=:userId and s.status = 'A'",nativeQuery = true)
    Optional<Subscription> findActiveByUserId(Long userId);
}
