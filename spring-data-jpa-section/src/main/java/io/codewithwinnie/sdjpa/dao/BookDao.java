package io.codewithwinnie.sdjpa.dao;


import io.codewithwinnie.sdjpa.entity.Book;

import java.util.List;

public interface BookDao {
    
    List<Book> findAll();
    Book saveNewBook(Book book);
    
    void deleteBookById(Long id);
    
    Book getById(Long id);
    
    void updateBook(Book saved);
    
    Book findBookByTitle(String clean_code);
    
    Book findByISBN(String isbn);
    
    Book findBookByTitleCriteria(String title);
    
    Book findBookByTitleNative(String title);
}

