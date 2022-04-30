package io.codewithwinnie.sdjpa.dao;

import io.codewithwinnie.sdjpa.entity.Author;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <PRE>Created by on 04/28/22.</PRE>
 * @author Ifeanyichukwu Otiwa
 */
public interface AuthorDao {
    
    List<Author> findAllByLastNameSortByFirstName(String lastName, Pageable pageable);
    List<Author> findAll();
    List<Author> findAll(Pageable pageable);
    Author getById(Long id);
    Author findAuthorByName(String firstName, String lastName);
    Author saveNewAuthor(Author author);
    Author updateAuthor(Author author);
    void deleteAuthorById(Long id);
    List<Author> findAllByLastName(String lastName);
    List<Author> findAllByLastName(String lastName, Pageable pageable);
}
