package ru.funbox.repository;

import redis.clients.jedis.Tuple;

import java.util.Set;

public interface RedisRepository {

    Long zadd(final String key, final double score, final String member);

    Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min);

}