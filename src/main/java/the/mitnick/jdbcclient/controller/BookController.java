package the.mitnick.jdbcclient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import the.mitnick.jdbcclient.entity.Book;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final JdbcClient jdbcClient;

    @PostMapping
    public String addNewBook(@RequestBody Book book) {

        jdbcClient.sql("INSERT INTO book(id, name, title) VALUES(?,?,?)")
                .param(List.of(book.getId(), book.getName(), book.getTitle()))
                .update();
        return "A new book added";
    }
}
