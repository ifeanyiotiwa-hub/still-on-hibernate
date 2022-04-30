package io.codewithwinnie.sdjpa.dao.logic.jdbctemplate;

import io.codewithwinnie.sdjpa.dao.BookDao;
import io.codewithwinnie.sdjpa.dao.logic.jdbctemplate.mapper.BookRowMapper;
import io.codewithwinnie.sdjpa.entity.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;


//@Component
public class BookDaoJDBCTemplateImpl implements BookDao {
    private final JdbcTemplate jdbcTemplate;
    
    public BookDaoJDBCTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<Book> findAllBooks() {
        return null;
    }
    
    @Override
    public Book saveNewBook(Book book) {
        jdbcTemplate.update("INSERT INTO book(isbn, publisher, title, author_id) VALUES (?, ?, ?, ?)"
                , book.getIsbn(), book.getPublisher(), book.getTitle(), book.getAuthorId());
    
        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        return this.getById(id);
    }
    
    @Override
    public void deleteBookById(Long id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }
    
    @Override
    public Book getById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id = ?", getBookRowMapper(),id);
    }
    
    @Override
    public Book updateBook(Book book) {
        jdbcTemplate.update("UPDATE book SET title = ?, isbn = ?, publisher = ?, author_id = ? WHERE id = ?",
                book.getTitle(), book.getIsbn(), book.getPublisher(), book.getAuthorId(), book.getId());
    
        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        return this.getById(id);
    }
    
    @Override
    public Book findBookByTitle(String title) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE title = ?", getBookRowMapper(), title);
    }
    
    @Override
    public Book findByISBN(String isbn) {
        return null;
    }
    
    private RowMapper<Book> getBookRowMapper() {
        return new BookRowMapper();
    }
}
