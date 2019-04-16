package ru.funbox.repository;

//import com.crypterium.domain.Country;
//import com.crypterium.repository.CountryRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Tuple;

import java.util.Set;

import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.sleep;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


/**
 * @author truesrc
 * @since 16.04.2019
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisRepositoryTest {
    private static final String KEY = "visitedLinksTest";
    @MockBean
    private RedisRepository redisRepository;

    @Before
    public void fillRedisData() throws InterruptedException {
        redisRepository.zadd(KEY, currentTimeMillis() / 1000, "ya.ru");
        sleep(100);
        redisRepository.zadd(KEY, currentTimeMillis() / 1000, "funbox.ru");
        sleep(100);
        redisRepository.zadd(KEY, currentTimeMillis() / 1000, "stacerflow.com");
        sleep(100);
        redisRepository.zadd(KEY, currentTimeMillis() / 1000, "steckoverflow.com");
        sleep(100);
        redisRepository.zadd(KEY, (currentTimeMillis() / 1000), "seckoverflow.com");
        sleep(100);
        redisRepository.zadd(KEY, 123456, "ackoverflow.com");
        redisRepository.zadd(KEY, 1.2, "A");
        redisRepository.zadd(KEY, 1.9, "A");
        redisRepository.zadd(KEY, 5.6, "A");
        redisRepository.zadd(KEY, 7.6, "A");
        redisRepository.zadd(KEY, 4.6, "A");
        redisRepository.zadd(KEY, 1.1, "D");
        redisRepository.zadd(KEY, 1.7, "C");
        redisRepository.zadd(KEY, 2.5, "B");
        redisRepository.zadd(KEY, 3.2, "R");
    }

    @Test
    public void zrevrangeByScoreWithScoresTest() {
        Set<Tuple> elements = redisRepository.zrevrangeByScoreWithScores(KEY, 7, 5);
        for (Tuple tuple : elements) {
            assertTrue(tuple.getScore() < 7 && tuple.getScore() > 5);
        }
    }


    @Test
    public void zrevrangeByScoreWithScoresExactWordTest() {
        redisRepository.zadd(KEY, 10, "Elixir");
        redisRepository.zadd(KEY, 20, "Erlang");
        redisRepository.zadd(KEY, 30, "Redis");
        Set<Tuple> elements = redisRepository.zrevrangeByScoreWithScores(KEY, 25, 15);
        for (Tuple tuple : elements) {
            assertThat(tuple.getElement(), is("Erlang"));
        }
    }
}