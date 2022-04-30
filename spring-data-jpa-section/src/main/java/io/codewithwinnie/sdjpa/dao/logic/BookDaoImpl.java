package io.codewithwinnie.sdjpa.dao.logic;

import io.codewithwinnie.sdjpa.dao.BookDao;
import io.codewithwinnie.sdjpa.entity.Book;
import io.codewithwinnie.sdjpa.repositories.BookRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Component
public class BookDaoImpl implements BookDao {
    private final BookRepository bookRepository;
    
    public BookDaoImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    @Override
    public List<Book> findAllBooks(int pageSize, int offSet) {
        return null;
    }
    
    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }
    
    @Override
    public Book saveNewBook(Book book) {
        return bookRepository.save(book);
    }
    
    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
    
    @Override
    public Book getById(Long id) {
        return bookRepository.getById(id);
    }
    
    @Override
    public Book updateBook(Book saved) {
        Book foundBook = bookRepository.getById(saved.getId());
        foundBook.setAuthorId(saved.getAuthorId());
        foundBook.setIsbn(saved.getIsbn());
        foundBook.setPublisher(saved.getPublisher());
        foundBook.setTitle(saved.getTitle());
        return bookRepository.save(foundBook);
    }
    
    @Override
    public Book findBookByTitle(String title) {
        return bookRepository.findByTitle(title).orElseThrow(EntityNotFoundException::new);
    }
    
    @Override
    public Book findByISBN(String isbn) {
        return bookRepository.findByIsbn(isbn).orElseThrow(EntityNotFoundException::new);
    }
    
    @Override
    public int count() {
        return 0;
    }
}
