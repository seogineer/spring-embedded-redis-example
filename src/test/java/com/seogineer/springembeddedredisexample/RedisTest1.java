package com.seogineer.springembeddedredisexample;

import com.seogineer.springembeddedredisexample.domain.Point;
import com.seogineer.springembeddedredisexample.domain.PointRedisRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@SpringBootTest
public class RedisTest1 {

    @Autowired
    private PointRedisRepository pointRedisRepository;

    @AfterEach
    public void tearDown() throws Exception {
        pointRedisRepository.deleteAll();
    }

    @Test
    public void 기본_등록_조회기능() {
        //given
        String id = "seogineer";
        LocalDateTime refreshTime = LocalDateTime.of(2022, 7, 18, 0, 0);
        Point point = Point.builder()
                .id(id)
                .amount(1000L)
                .refreshTime(refreshTime)
                .build();

        //when
        pointRedisRepository.save(point);

        //then
        Point savedPoint = pointRedisRepository.findById(id).get();
        assertThat(savedPoint.getAmount(), is(1000L));
        assertThat(savedPoint.getRefreshTime(), is(refreshTime));
    }
}
