package ru.funbox.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Tuple;
import ru.funbox.model.VisitedDomains;
import ru.funbox.model.VisitedLinks;
import ru.funbox.repository.RedisRepository;
import ru.funbox.utils.Utils;

import java.util.Set;

import static java.lang.System.currentTimeMillis;

/**
 * @author truesrc
 * @since 16.04.2019
 */

@RestController
public class WebCtrl {
    private static Logger log = LoggerFactory.getLogger(WebCtrl.class);
    private static final String KEY = "visitedLinks";
    private RedisRepository redisRepository;

    @Autowired
    public WebCtrl(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }
    /*
     *Оставляются только уник. домены.
     *
     *https://redis.io/commands/zadd
     *
     *
     */

    @PostMapping("/visited_links")
    public String visitedLinks(@RequestBody VisitedLinks visitedLinks) {
        visitedLinks.getLinks().stream()
                .map(Utils::getDomainName)
                .distinct()
                .forEach(domain -> redisRepository.zadd(KEY, currentTimeMillis() / 1000, domain));
        return "{\"status\": \"ok\"}";
    }

    /*
     *
     *
     *https://redis.io/commands/zrevrangebyscore
     *
     *
     */
    @GetMapping("/visited_domains")
    public VisitedDomains visitedDomains(@RequestParam long from, @RequestParam long to) {
        VisitedDomains visitedDomains = new VisitedDomains();
        Set<Tuple> elements = redisRepository.zrevrangeByScoreWithScores(KEY, to, from);
        for (Tuple tuple : elements) {
            visitedDomains.getDomains().add(tuple.getElement());
        }
        visitedDomains.setStatus("ok");
        log.info(visitedDomains.toString());
        return visitedDomains;
    }
}
