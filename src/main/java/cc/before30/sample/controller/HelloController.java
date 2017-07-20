package cc.before30.sample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by before30 on 20/07/2017.
 */
@RestController
@Slf4j
public class HelloController {

    @GetMapping("/hello")
    public Mono<String> hello() {
        log.info("hello world");
        return Mono.just("world");
    }
}
