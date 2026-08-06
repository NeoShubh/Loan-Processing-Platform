package com.rcu.apigateway.ratelimit;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;

@Component
public class RedisRateLimiter {
    private final StringRedisTemplate redisTemplate;
    private final DefaultRedisScript<Long> tokenBucketScript;

    @Value("${rate-limit.capacity:20}")
    private int capacity;

    @Value("${rate-limit.refill-rate:5}")
    private double refillRate;

    public RedisRateLimiter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.tokenBucketScript = new DefaultRedisScript<>();
        this.tokenBucketScript.setLocation(new ClassPathResource("scripts/token_bucket.lua"));
        this.tokenBucketScript.setResultType(Long.class);
    }

    public boolean isAllowed(String bucketKey) {
        long nowMs = System.currentTimeMillis();
        Long result = redisTemplate.execute(
                tokenBucketScript,
                Collections.singletonList(bucketKey),
                String.valueOf(capacity),
                String.valueOf(refillRate),
                String.valueOf(nowMs),
                "1"
        );
        return result != null && result == 1L;
    }
}
