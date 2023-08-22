package ru.nasrulaev.spring.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.nasrulaev.spring.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();

        book.setId(rs.getInt("book_id"));
        book.setAuthorName(rs.getString("author_name"));
        book.setTitle(rs.getString("title"));
        book.setPublicationYear(rs.getInt("publication_year"));
        book.setHolderId(rs.getObject("holder_id", Integer.class));

        return book;
    }
}
