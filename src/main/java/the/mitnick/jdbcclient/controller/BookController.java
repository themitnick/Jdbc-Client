package the.mitnick.jdbcclient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.*;
import the.mitnick.jdbcclient.entity.Book;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final JdbcClient jdbcClient;

    @PostMapping
    public String addNewBook(@RequestBody Book book) {

        jdbcClient.sql("INSERT INTO book(id, name, title) VALUES(?,?,?)")
                .params(List.of(book.getId(), book.getName(), book.getTitle()))
                .update();
        return "A new book added";
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return jdbcClient.sql("SELECT * FROM book")
                .query(Book.class)
                .list();
    }

    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable int id) {
        return jdbcClient.sql("SELECT * FROM book WHERE id =: id")
                .param("id", id)
                .query(Book.class).optional();
    }

    @PutMapping("/{id}")
    public String updateBook(@PathVariable int id, @RequestBody Book book) {
        jdbcClient.sql("UPDATE book set name = ?, title = ? WHERE id = ?")
                .params(List.of(book.getName(),book.getTitle(), id))
                .update();

        return "Book has been modified";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        jdbcClient.sql("DELETE FROM book WHERE id=: id")
                .param("id", id)
                .update();

        return "Book has been deleted in the system";
    }

}
