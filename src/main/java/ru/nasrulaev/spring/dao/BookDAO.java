package ru.nasrulaev.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.nasrulaev.spring.models.Book;
import ru.nasrulaev.spring.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BookMapper());
    }

    public List<Book> index(Integer holderId) {
        return jdbcTemplate.query("SELECT * FROM book WHERE holder_id=?", new BookMapper(), holderId);
    }

    public Optional<Book> show(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id=?", new BookMapper(), id)
                .stream().findAny();
    }

    public Optional<Person> showHolder(int id) {
        return jdbcTemplate.query("SELECT person.* FROM book JOIN person ON person.user_id = book.holder_id" +
                " WHERE book_id=?", new PersonMapper(), id) .stream().findAny();
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book (author_name, book_name, publication_year) VALUES (?, ?, ?)",
                book.getAuthorName(), book.getBookName(), book.getPublicationYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE book SET author_name=?, book_name=?, publication_year=? WHERE book_id=?",
                updatedBook.getAuthorName(), updatedBook.getBookName(), updatedBook.getPublicationYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?", id);
    }

    public void changeHolder(int bookId, @Nullable Integer personId) {
        jdbcTemplate.update("UPDATE book SET holder_id=? WHERE book_id=?", personId, bookId);
    }

}
