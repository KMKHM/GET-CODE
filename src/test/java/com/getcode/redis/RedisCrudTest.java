package com.getcode.redis;

import static org.assertj.core.api.Assertions.*;

import com.getcode.config.redis.RedisService;
import java.time.Duration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisCrudTest {
    final String KEY = "key";
    final String VALUE = "value";
    final Duration DURATION = Duration.ofMillis(2000);

    @Autowired
    RedisService redisService;

    @BeforeEach
    void setUp() {
        redisService.setValues(KEY, VALUE, DURATION);
    }

    @AfterEach
    void tearDown() {
        redisService.deleteValues(KEY);
    }

    @Test
    @DisplayName("Redis에 값을 저장하면 정상적으로 조회된다.")
    void saveTest() {
        // when
        String findValue = redisService.getValues(KEY);

        //then
        assertThat(findValue).isEqualTo(VALUE);
    }

    @Test
    @DisplayName("Redis에 값을 저장한 값을 수정할 수 있다.")
    void updateTest() {
        // given
        String updateValue = "KMK";
        redisService.setValues(KEY, updateValue, DURATION);

        // when
        String findValue = redisService.getValues(KEY);

        //then
        assertThat(findValue).isEqualTo(updateValue);
    }

    @Test
    @DisplayName("Redis에 값을 저장한 값을 삭제할 수 있다.")
    void deleteTest() {
        // when
        redisService.deleteValues(KEY);
        String findValue = redisService.getValues(KEY);

        //then
        assertThat(findValue).isEqualTo("false");
    }

    @Test
    @DisplayName("Redis에 값을 저장한 값은 시간이 지나면 삭제된다.")
    void expiredTest() {
        // when
        sleep(2000);
        String findValue = redisService.getValues(KEY);
        assertThat(findValue).isEqualTo("false");
    }

    private void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
