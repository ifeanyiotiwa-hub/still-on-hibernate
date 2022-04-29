package io.codewithwinnie.jdbc.dao;

import io.codewithwinnie.jdbc.entity.Book;

public interface BookDao {
    Book saveNewBook(Book book);
    
    void deleteBookById(Long id);
    
    Book getById(Long id);
    
    void updateBook(Book saved);
    
    Book findBookByTitle(String clean_code);
}
