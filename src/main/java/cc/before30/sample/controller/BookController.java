package cc.before30.sample.controller;

import cc.before30.sample.domain.entity.tables.pojos.Book;
import cc.before30.sample.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

/**
 * Created by before30 on 20/07/2017.
 */
@RestController
@Slf4j
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/sync/books")
    public Mono<List<Book>> syncBooks() {

        return Mono.just(bookService.findAll());
    }

    @GetMapping("/future/books")
    public Mono<List<Book>> futureBooks() {
        return Mono.fromCompletionStage(bookService.futureFindAll())
                .timeout(Duration.ofSeconds(2))
                .log();
    }


    static final String URL1 = "http://localhost:8080/hello";

    WebClient client = WebClient.create();

    @GetMapping("/test/{id}")
    public Mono<Book> test(@PathVariable("id") int id) {
        return client.get().uri(URL1).exchange()
                .flatMap(r -> r.bodyToMono(String.class))
                .doOnNext(arg -> log.info(arg))
                .flatMap(r -> Mono.fromCompletionStage(bookService.futureFindOne(id)))
                .doOnNext(arg -> log.info(arg.toString()))
                .log();

    }


}
