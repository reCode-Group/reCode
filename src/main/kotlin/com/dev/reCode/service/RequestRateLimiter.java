package com.dev.reCode.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.ConsumptionProbe;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RequestRateLimiter {
    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    public boolean isRequestAllowed(String userId) {
        Bucket bucket = buckets.computeIfAbsent(userId, k -> createNewBucket());
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        return probe.isConsumed();
    }

    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.simple(4, Duration.ofHours(4));
        return Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

}
