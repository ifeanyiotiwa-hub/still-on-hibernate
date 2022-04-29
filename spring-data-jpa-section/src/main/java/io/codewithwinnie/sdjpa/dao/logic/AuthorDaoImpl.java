package io.codewithwinnie.sdjpa.dao.logic;

import io.codewithwinnie.sdjpa.dao.AuthorDao;
import io.codewithwinnie.sdjpa.entity.Author;
import io.codewithwinnie.sdjpa.repositories.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorDaoImpl implements AuthorDao {
    private final AuthorRepository authorRepository;
    
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
        return authorRepository.findByFirstNameAndLastName(firstName, lastName);
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
