package io.codewithwinnie.sdjpa;


import io.codewithwinnie.sdjpa.dao.BookDao;
import io.codewithwinnie.sdjpa.entity.Book;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
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
//    @Autowired
//    AuthorDao authorDao;

    @Autowired
    BookDao bookDao;
    
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

//    @Test
//    void testDeleteAuthor() {
//        Author author = new Author();
//        author.setFirstName("john");
//        author.setLastName("t");
//
//        Author saved = authorDao.saveNewAuthor(author);
//
//        authorDao.deleteAuthorById(saved.getId());
//
//        Author deleted = authorDao.getById(saved.getId());
//
//        assertThat(deleted).isNull();
//
//    }
//
//    @Test
//    void testUpdateAuthor() {
//        Author author = new Author();
//        author.setFirstName("john");
//        author.setLastName("t");
//
//        Author saved = authorDao.saveNewAuthor(author);
//        System.err.println("Saved" + saved);
//        saved.setLastName("Thompson");
//        System.out.println(saved.getLastName());
//        Author updated = authorDao.updateAuthor(saved);
//        System.err.println("Updated " + updated);
//        assertThat(updated.getLastName()).isEqualTo("Thompson");
//    }
//
//    @Test
//    void testSaveAuthor() {
//        Author author = new Author();
//        author.setFirstName("John");
//        author.setLastName("Thompson");
//        Author saved = authorDao.saveNewAuthor(author);
//
//        assertThat(saved).isNotNull();
//        assertThat(saved.getId()).isNotNull();
//    }
//
//    @Test
//    void testGetAuthorByName() {
//        Author author = authorDao.findAuthorByName("Craig", "Walls");
//
//        assertThat(author).isNotNull();
//    }
//
//    @Test
//    void testGetAuthorByNameCriteria() {
//        Author author = authorDao.findAuthorByNameCriteria("Craig", "Walls");
//
//        assertThat(author).isNotNull();
//    }
//
//    @Test
//    void testGetAuthorByNameNative() {
//        Author author = authorDao.findAuthorByNameNative("Craig", "Walls");
//
//        assertThat(author).isNotNull();
//    }
//
//    @Test
//    void testGetAuthor() {
//
//        Author author = authorDao.getById(1L);
//
//        assertThat(author).isNotNull();
//
//    }
//
//    @Test
//    void testListAuthorByLastNameLike() {
//        List<Author> authorList = authorDao.listAuthorByLastNameLike("Walls");
//        assertThat(authorList).isNotNull();
//        assertThat(authorList.size()).isGreaterThan(0);
//    }
//
//    @Test
//    void testFindAllAuthors() {
//        List<Author> authors = authorDao.findAll();
//        authors.forEach(System.err::println);
//        assertThat(authors.size()).isGreaterThan(0);
//    }
}
