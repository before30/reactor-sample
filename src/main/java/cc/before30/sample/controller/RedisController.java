package cc.before30.sample.controller;

import cc.before30.sample.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by before30 on 21/07/2017.
 */
@RestController
@Slf4j
public class RedisController {
    @Autowired
    private RedisService redisService;

    @GetMapping("/redis/mono/{id}")
    public Mono<String> asyncFindOne(@PathVariable("id") String id) {
        return redisService.monoFindOne(id).log();
    }

    @GetMapping("/redis/sync/{id}")
    public String syncFindOne(@PathVariable("id") String id) {
        return redisService.syncFindOne(id);
    }
}
