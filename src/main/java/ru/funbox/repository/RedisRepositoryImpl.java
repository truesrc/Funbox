package ru.funbox.repository;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

@Service
public class RedisRepositoryImpl implements RedisRepository {
    private Jedis jedis = new Jedis();

    @Override
    public Long zadd(String key, double score, String member) {
        return jedis.zadd(key, score, member);
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        return jedis.zrevrangeByScoreWithScores(key, max, min);
    }
}

