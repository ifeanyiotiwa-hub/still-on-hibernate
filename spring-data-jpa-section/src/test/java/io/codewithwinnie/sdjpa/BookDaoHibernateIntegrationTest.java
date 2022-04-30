package io.codewithwinnie.sdjpa;


import io.codewithwinnie.sdjpa.dao.BookDao;
import io.codewithwinnie.sdjpa.entity.Book;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created on 04/30/22.
 * @author <b>Ifeanyichukwu Otiwa</b>
 */
@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"io.codewithwinnie.sdjpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoHibernateIntegrationTest {

    @Autowired
    BookDao bookDao;
    
    
    @Test
    void testFindAllBooks_pageableWithSort() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(0, 10
                , Sort.by(Sort.Order.desc("title"))));
        books.forEach(System.err::println);
        
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }
    
    @Test
    void testFindAllBooks_pageableSortByTitle() {
        List<Book> books = bookDao.findAllBookSortByTitle(PageRequest.of(0, 10
                , Sort.by(Sort.Order.desc("title"))));
        books.forEach(System.err::println);
    
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }
    
    @Test
    void testFindAllBooks_pageable() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(0, 10));
    
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }
    
    @Test
    void testFindAllBooks() {
        List<Book> books = bookDao.findAllBooks();
        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThan(0);
    }
    
    @Test
    void testFindBookByTitle() {
        Book book = new Book();
        book.setIsbn("1234" + RandomString.make());
        book.setTitle("Title-" + RandomString.make() + " TEST");
        book.setPublisher("Publisher-" + RandomString.make());
        
        Book saved = bookDao.saveNewBook(book);
        
        Book fetched = bookDao.findBookByTitle(book.getTitle());
        System.err.println(fetched);
        assertThat(fetched).isNotNull();
    }
    
    @Test
    void testFindBookByISBN() {
        Book book = new Book();
        book.setIsbn("1234" + RandomString.make());
        book.setTitle("ISBN TEST");
        
        Book saved = bookDao.saveNewBook(book);
        
        Book fetched = bookDao.findByISBN(book.getIsbn());
        assertThat(saved).isEqualTo(fetched);
        assertThat(fetched).isNotNull();
    }

    @Test
    void testDeleteBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        Book saved = bookDao.saveNewBook(book);

        bookDao.deleteBookById(saved.getId());

        Book deleted = bookDao.getById(saved.getId());

        assertThat(deleted).isNull();
    }

    @Test
    void updateBookTest() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        Book saved = bookDao.saveNewBook(book);

        saved.setTitle("New Book");
        bookDao.updateBook(saved);

        Book fetched = bookDao.getById(saved.getId());

        assertThat(fetched.getTitle()).isEqualTo("New Book");
    }

    @Test
    void testSaveBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");

        Book saved = bookDao.saveNewBook(book);
        System.err.println(saved);
        assertThat(saved).isNotNull();
    }

    @Test
    void testGetBookByName() {
        Book book = bookDao.findBookByTitle("Clean Code");
        System.err.println(book);
        assertThat(book).isNotNull();
    }

    @Test
    void testGetBook() {
        Book book = bookDao.getById(3L);
        System.err.println(book);
        assertThat(book.getId()).isNotNull();
    }
}
