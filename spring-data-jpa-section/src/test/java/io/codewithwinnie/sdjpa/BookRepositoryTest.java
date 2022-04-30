package io.codewithwinnie.sdjpa;

import io.codewithwinnie.sdjpa.entity.Book;
import io.codewithwinnie.sdjpa.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"io.codewithwinnie.sdjpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    
    @Autowired
    BookRepository bookRepository;
    
    
    @Test
    void testBookFutures() throws Exception {
        Future<Book> bookFuture = bookRepository.queryByTitle("Clean Code");
        Book book = bookFuture.get();
        assertNotNull(book);
    }
    
    @Test
    void testBookStream() {
        AtomicInteger count = new AtomicInteger();
        
        bookRepository.findAllByTitleNotNull().forEach(book -> {
            count.incrementAndGet();
        });
        
        assertThat(count.get()).isGreaterThan(5);
    }
    
    @Test
    void testBookAuthorIdStream() {
        AtomicInteger count = new AtomicInteger();
        
        bookRepository.findAllByAuthorIdNull().forEach(book -> {
            System.err.println(book);
            count.incrementAndGet();
        });
        
        assertThat(count.get()).isGreaterThan(5);
    }
    
    
    @Test
    void testEmptyResultException() {
        
        assertThrows(EmptyResultDataAccessException.class, () -> {
            Book book = bookRepository.readByTitle("foobar4");
        });
    }
    
    @Test
    void testNullParam() {
        assertNull(bookRepository.getByTitle(null));
    }
    
    @Test
    void testNoException() {
        
        assertNull(bookRepository.getByTitle("foo"));
    }
}