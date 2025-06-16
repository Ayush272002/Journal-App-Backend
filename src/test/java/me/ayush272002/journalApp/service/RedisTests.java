package me.ayush272002.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
@Disabled
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    @Disabled
    void testRedis() {
        redisTemplate.opsForValue().set("email", "test@test.com");
        Object email = redisTemplate.opsForValue().get("email");
        int a = 1;
    }
}
