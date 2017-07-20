package cc.before30.sample;

import cc.before30.sample.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static java.util.stream.Collectors.joining;

@SpringBootApplication
@EnableAsync
@Slf4j
public class ReactorSampleApplication implements CommandLineRunner {

	@Autowired
	private RedisService redisService;

	public static void main(String[] args) {
		SpringApplication.run(ReactorSampleApplication.class, args);
	}

	@Autowired
	private RedisServer redisServer;

	@PostConstruct
	public void start() {
		log.info("starting redis...");
		if (!redisServer.isActive()) {
			redisServer.start();
		}
		log.info("redis listen ports: {}", redisServer.ports().stream()
				.map(Object::toString).collect(joining(",")));

	}

	@PreDestroy
	public void stop() {
		log.info("shutting down redis...");
		redisServer.stop();
	}

	@Override
	public void run(String... args) throws Exception {
		for (int i=0; i<10; i++) {
			redisService.save(String.valueOf(i), "test_" + i);
//			log.info(redisService.syncFindOne(String.valueOf(i)));
		}
	}
}
