package io.codewithwinnie.jdbc;

import io.codewithwinnie.jdbc.dao.AuthorDao;
import io.codewithwinnie.jdbc.dao.BookDao;
import io.codewithwinnie.jdbc.entity.Author;
import io.codewithwinnie.jdbc.entity.Book;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by jt on 8/28/21.
 */
@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"io.codewithwinnie.jdbc.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DaoIntegrationTest {
    @Autowired
    AuthorDao authorDao;

    @Autowired
    BookDao bookDao;
    
    @Test
    void testFindAllBooks() {
        List<Book> books = bookDao.findAll();
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
        assertThat(fetched).isNotNull();
    }
    
    @Test
    void testFindBookByTitleCriteria() {
        Book book = new Book();
        book.setIsbn("1234" + RandomString.make());
        book.setTitle("ISBN TEST" + RandomString.make());
        
        Book saved = bookDao.saveNewBook(book);
        
        Book fetched = bookDao.findBookByTitleCriteria(book.getTitle());
        assertThat(fetched).isNotNull();
    }
    
    @Test
    void testFindBookByTitleNative() {
        Book book = new Book();
        book.setIsbn("1234" + RandomString.make());
        book.setTitle("ISBN TEST" + RandomString.make());
        
        Book saved = bookDao.saveNewBook(book);
        
        Book fetched = bookDao.findBookByTitleNative(book.getTitle());
        assertThat(fetched).isNotNull();
    }
    
    @Test
    void testFindBookByISBN() {
        Book book = new Book();
        book.setIsbn("1234" + RandomString.make());
        book.setTitle("ISBN TEST");
        
        Book saved = bookDao.saveNewBook(book);
        
        Book fetched = bookDao.findByISBN(book.getIsbn());
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

        Author author = new Author();
        author.setId(3L);

        book.setAuthor(author);
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

        Author author = new Author();
        author.setId(3L);

        book.setAuthor(author);
        Book saved = bookDao.saveNewBook(book);

        assertThat(saved).isNotNull();
    }

    @Test
    void testGetBookByName() {
        Book book = bookDao.findBookByTitle("Clean Code");

        assertThat(book).isNotNull();
    }

    @Test
    void testGetBook() {
        Book book = bookDao.getById(3L);

        assertThat(book.getId()).isNotNull();
    }

    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");

        Author saved = authorDao.saveNewAuthor(author);

        authorDao.deleteAuthorById(saved.getId());
        
        Author deleted = authorDao.getById(saved.getId());

        assertThat(deleted).isNull();

    }

    @Test
    void testUpdateAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");

        Author saved = authorDao.saveNewAuthor(author);
        System.err.println("Saved" + saved);
        saved.setLastName("Thompson");
        System.out.println(saved.getLastName());
        Author updated = authorDao.updateAuthor(saved);
        System.err.println("Updated " + updated);
        assertThat(updated.getLastName()).isEqualTo("Thompson");
    }

    @Test
    void testSaveAuthor() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Thompson");
        Author saved = authorDao.saveNewAuthor(author);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void testGetAuthorByName() {
        Author author = authorDao.findAuthorByName("Craig", "Walls");

        assertThat(author).isNotNull();
    }
    
    @Test
    void testGetAuthorByNameCriteria() {
        Author author = authorDao.findAuthorByNameCriteria("Craig", "Walls");

        assertThat(author).isNotNull();
    }
    
    @Test
    void testGetAuthorByNameNative() {
        Author author = authorDao.findAuthorByNameNative("Craig", "Walls");
        
        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthor() {

        Author author = authorDao.getById(1L);

        assertThat(author).isNotNull();

    }
    
    @Test
    void testListAuthorByLastNameLike() {
        List<Author> authorList = authorDao.listAuthorByLastNameLike("Walls");
        assertThat(authorList).isNotNull();
        assertThat(authorList.size()).isGreaterThan(0);
    }
    
    @Test
    void testFindAllAuthors() {
        List<Author> authors = authorDao.findAll();
        authors.forEach(System.err::println);
        assertThat(authors.size()).isGreaterThan(0);
    }
}
