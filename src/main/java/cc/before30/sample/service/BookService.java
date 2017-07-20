package cc.before30.sample.service;

import cc.before30.sample.domain.entity.tables.pojos.Book;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.jooq.RecordMapperProvider;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static cc.before30.sample.domain.entity.Tables.BOOK;

/**
 * Created by before30 on 20/07/2017.
 */
@Service
@Slf4j
public class BookService {

    @Autowired
    DSLContext dsl;

    @Transactional
    public void create(int id, int authorId, String title) {
        dsl.insertInto(BOOK)
                .set(BOOK.ID, id)
                .set(BOOK.AUTHOR_ID, authorId)
                .set(BOOK.TITLE, title)
                .execute();
    }

    public List<Book> findAll() {
        return dsl.selectFrom(BOOK)
                .fetch()
                .into(Book.class);
    }

    @Async
    public CompletableFuture<List<Book>> futureFindAll() {
        return CompletableFuture.completedFuture(findAll());
    }

    public Stream<Book> streamFindAll() {

        return dsl.selectFrom(BOOK)
                .fetchStream()
                .map(r -> {
                    return new Book(r.getId(),
                            r.getAuthorId(),
                            r.getCoAuthorId(),
                            r.getDetailsId(),
                            r.getTitle(),
                            r.getPublishedIn(),
                            r.getLanguageId(),
                            r.getContentText(),
                            r.getContentPdf(),
                            r.getRecVersion(),
                            r.getRecTimestamp()
                    );
                });
    }

    public Book findOne(int id) {
        return dsl.selectFrom(BOOK)
                .where(BOOK.ID.eq(id))
                .fetchOne()
                .into(Book.class);
    }

    @Async
    public CompletableFuture<Book> futureFindOne(int id) {
        return CompletableFuture.supplyAsync(
                () -> dsl.selectFrom(BOOK)
                        .where(BOOK.ID.eq(id))
                        .fetchOne()
                        .into(Book.class)
        );
    }
}
