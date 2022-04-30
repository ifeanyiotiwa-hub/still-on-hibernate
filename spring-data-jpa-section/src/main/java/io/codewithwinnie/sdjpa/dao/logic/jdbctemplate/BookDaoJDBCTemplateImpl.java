package io.codewithwinnie.sdjpa.dao.logic.jdbctemplate;

import io.codewithwinnie.sdjpa.dao.BookDao;
import io.codewithwinnie.sdjpa.dao.logic.jdbctemplate.mapper.BookRowMapper;
import io.codewithwinnie.sdjpa.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;


//@Component
public class BookDaoJDBCTemplateImpl implements BookDao {
    public final JdbcTemplate jdbcTemplate;
    
    public BookDaoJDBCTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    
    @Override
    public int count() {
      List<Book> books =  jdbcTemplate.query("SELECT * FROM book ", getBookRowMapper());
      return books.size();
    }
    
    @Override
    public List<Book> findAllBookSortByTitle(Pageable pageable) {
        String SQL =
                "SELECT * FROM book order by title " + pageable.getSort().getOrderFor("title").getDirection().name() +
                             " limit ? offset ?";
        return jdbcTemplate.query(SQL, getBookRowMapper(), pageable.getPageSize(), pageable.getOffset());
    }
    
    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        return jdbcTemplate.query("SELECT * FROM book limit ? offset ?",getBookRowMapper()
                                        , pageable.getPageSize(), pageable.getOffset());
    }
    
    @Override
    public List<Book> findAllBooks(int pageSize, int offSet) {
        return jdbcTemplate.query("SELECT * FROM book limit ? offset ?",getBookRowMapper(), pageSize, offSet);
    }
    
    @Override
    public List<Book> findAllBooks() {
        return jdbcTemplate.query("SELECT * from book", getBookRowMapper());
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
