package io.codewithwinnie.sdjpa.dao;


import io.codewithwinnie.sdjpa.entity.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookDao {
    
    List<Book> findAllBookSortByTitle(Pageable pageable);
    
    List<Book> findAllBooks(Pageable pageable);
    List<Book> findAllBooks(int pageSize, int offSet);
    List<Book> findAllBooks();
    Book saveNewBook(Book book);
    
    void deleteBookById(Long id);
    
    Book getById(Long id);
    
    Book updateBook(Book book);
    
    Book findBookByTitle(String title);
    
    Book findByISBN(String isbn);
    int count();
}

