package com.dev.reCode.service;

import com.dev.reCode.repository.UsersSubscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class SubscriptionService {
    private final UsersSubscriptionRepository usersSubscriptionRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void updateSubscriptionStatuses() {
        LocalDate now = LocalDate.now();
        System.out.println("schedule");
        usersSubscriptionRepository.updateExpiredSubscriptions(now);
    }
}
