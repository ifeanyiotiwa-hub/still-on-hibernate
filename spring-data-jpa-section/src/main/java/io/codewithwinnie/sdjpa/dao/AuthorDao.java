package io.codewithwinnie.sdjpa.dao;

import io.codewithwinnie.sdjpa.entity.Author;

import java.util.List;

/**
 * <PRE>Created by on 04/28/22.</PRE>
 * @author Ifeanyichukwu Otiwa
 */
public interface AuthorDao {
    
    List<Author> findAll();
    Author getById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);
    
    List<Author> listAuthorByLastNameLike(String lastName);
    
    Author findAuthorByNameCriteria(String firstName, String lastName);
    
    Author findAuthorByNameNative(String firstName, String lastName);
}
