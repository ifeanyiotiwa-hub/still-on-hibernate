package io.codewithwinnie.sdjpa.dao.logic;

import io.codewithwinnie.sdjpa.dao.AuthorDao;
import io.codewithwinnie.sdjpa.entity.Author;
import io.codewithwinnie.sdjpa.repositories.AuthorRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
public class AuthorDaoImpl implements AuthorDao {
    private final AuthorRepository authorRepository;
    
    @Override
    public List<Author> findAllByLastNameSortByFirstName(String lastName, Pageable pageable) {
        return null;
    }
    
    @Override
    public List<Author> findAll(Pageable pageable) {
        return null;
    }
    
    @Override
    public List<Author> findAllByLastName(String smith) {
        return null;
    }
    
    @Override
    public List<Author> findAllByLastName(String lastName, Pageable pageable) {
        return null;
    }
    
    public AuthorDaoImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    
    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }
    
    @Override
    public Author getById(Long id) {
        return authorRepository.getById(id);
    }
    
    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(EntityNotFoundException::new);
    }
    
    @Override
    public Author saveNewAuthor(Author author) {
        return authorRepository.save(author);
    }
    
    @Override
    public Author updateAuthor(Author author) {
        //fetch author from database
        Author foundAuthor = authorRepository.getById(author.getId());
        //update the properties
        foundAuthor.setFirstName(author.getFirstName());
        foundAuthor.setLastName(author.getLastName());
        //then save it
        return authorRepository.save(foundAuthor);
    }
    
    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
