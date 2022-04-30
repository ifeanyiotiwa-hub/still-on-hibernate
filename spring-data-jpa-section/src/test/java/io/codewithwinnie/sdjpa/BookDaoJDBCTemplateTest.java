package io.codewithwinnie.sdjpa;

import io.codewithwinnie.sdjpa.dao.BookDao;
import io.codewithwinnie.sdjpa.dao.logic.jdbctemplate.BookDaoJDBCTemplateImpl;
import io.codewithwinnie.sdjpa.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"io.winnie.usingspringjdbctemplate.dao"})
public class BookDaoJDBCTemplateTest {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private BookDao bookdao;
    
    @BeforeEach
    void setUp() {
        bookdao = new BookDaoJDBCTemplateImpl(jdbcTemplate);
    }
    
    @Test
    void testGetById() {
        Book book = bookdao.getById(1L);
        
        assertThat(book).isNotNull();
    }
    @Test
    void testFindByTitle() {
        Book book = bookdao.findBookByTitle("Clean Code");
        assertThat(book).isNotNull();
    }
    @Test
    void testSave() {
        Book book = new Book();
        book.setTitle("Spring in Action 2");
        book.setPublisher("oreilly");
        Book saved = bookdao.saveNewBook(book);
        assertThat(saved).isNotNull();
    }
    @Test
    void testUpdate() {
        Book book = new Book("Spring RM", "ISBN00000", "O'Reilly");
        book.setAuthorId(1L);
        Book saved = bookdao.saveNewBook(book);
        
        saved.setTitle("Spring RM 2nd Edition");
        Book updated = bookdao.updateBook(saved);
        
        assertThat(updated.getTitle()).isEqualTo("Spring RM 2nd Edition");
    }
    @Test
    void testDeleteById() {
        Book newBook = new Book("Delete Test", "DeleteISBN", "O'Reilly");
        Book saved = bookdao.saveNewBook(newBook);
        
        bookdao.deleteBookById(saved.getId());
        assertThrows(EmptyResultDataAccessException.class, () -> bookdao.getById(saved.getId()));
        
    }
}
