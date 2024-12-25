package com.dev.reCode.repository;

import com.dev.reCode.models.UsersSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface UsersSubscriptionRepository extends JpaRepository<UsersSubscription, Long> {
    @Modifying
    @Query("UPDATE User u SET u.hasSubscription = false WHERE u.id IN (" +
            "SELECT us.user.id FROM UsersSubscription us WHERE us.expirationDate < :now)")
    void updateExpiredSubscriptions(@Param("now") LocalDate now);
}
