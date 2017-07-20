package cc.before30.sample.service;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Created by before30 on 21/07/2017.
 */
@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisClient redisClient;

    static final String prefix = "cc.before30.sample.redis.";

    public RedisFuture<String> asyncFindOne(String id) {
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        return connect.async().get(prefix + id);
    }

    public Mono<String> monoFindOne(String id) {
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        return connect.reactive().get(prefix + id);
    }

    public String syncFindOne(String id) {
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        return connect.sync().get(prefix + id);
    }

    public void save(String id, String value) {
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        connect.sync().set(prefix + id, value);
    }
}
