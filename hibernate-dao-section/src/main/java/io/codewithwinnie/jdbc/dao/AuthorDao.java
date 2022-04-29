package io.codewithwinnie.jdbc.dao;

import io.codewithwinnie.jdbc.entity.Author;

import java.util.List;

/**
 * <PRE>Created by on 04/28/22.</PRE>
 * @author Ifeanyichukwu Otiwa
 */
public interface AuthorDao {
    Author getById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);
    
    List<Author> listAuthorByLastNameLike(String lastName);
}
