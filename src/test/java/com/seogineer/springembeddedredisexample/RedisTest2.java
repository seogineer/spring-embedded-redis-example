package com.seogineer.springembeddedredisexample;

import com.seogineer.springembeddedredisexample.domain.Point;
import com.seogineer.springembeddedredisexample.domain.PointRedisRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "job.name=a")
public class RedisTest2 {
    @Autowired
    private PointRedisRepository pointRedisRepository;

    @AfterEach
    public void tearDown() throws Exception {
        pointRedisRepository.deleteAll();
    }

    @Test
    public void 수정기능() {
        //given
        String id = "seogineer";
        LocalDateTime refreshTime = LocalDateTime.of(2022, 7, 18, 0, 0);
        pointRedisRepository.save(Point.builder()
                .id(id)
                .amount(1000L)
                .refreshTime(refreshTime)
                .build());

        //when
        Point savedPoint = pointRedisRepository.findById(id).get();
        savedPoint.refresh(2000L, LocalDateTime.of(2022,8,1,0,0));
        pointRedisRepository.save(savedPoint);

        //then
        Point refreshPoint = pointRedisRepository.findById(id).get();
        assertThat(refreshPoint.getAmount(), is(2000L));
    }
}
