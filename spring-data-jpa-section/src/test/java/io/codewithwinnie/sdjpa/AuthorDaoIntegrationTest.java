package io.codewithwinnie.sdjpa;

import io.codewithwinnie.sdjpa.dao.AuthorDao;
import io.codewithwinnie.sdjpa.dao.logic.jdbctemplate.AuthorDaoJDBCTemplateImpl;
import io.codewithwinnie.sdjpa.entity.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"io.codewithwinnie.sdjpa.dao"})
public class AuthorDaoIntegrationTest {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private AuthorDao authorDao;
    
    @BeforeEach
    void setUp() {
        authorDao = new AuthorDaoJDBCTemplateImpl(jdbcTemplate);
    }
    
    @Test
    void testFindAllByLastNameNoSorting() {
        List<Author> authors = authorDao.findAllByLastName("Smith");
        authors.forEach(System.err::println);
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThanOrEqualTo(40);
    }
    
    
    @Test
    void testFindAllByLastName_pageableSortByFirstNameDescPage1() {
        List<Author> authors = authorDao.findAllByLastNameSortByFirstName("Smith", PageRequest.of(0, 10,
                Sort.by(Sort.Order.desc("first_name"))));
        authors.forEach(System.err::println);
        assertThat(authors.get(8).getFirstName()).isEqualTo("Tishara");
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThanOrEqualTo(10);
    }
    
    @Test
    void testFindAllByLastName_pageableSortByFirstNameDescPage4() {
        List<Author> authors = authorDao.findAllByLastNameSortByFirstName("Smith", PageRequest.of(3, 10,
                Sort.by(Sort.Order.desc("first_name"))));
        authors.forEach(System.err::println);
        assertThat(authors).isNotNull();
        assertThat(authors.get(9).getFirstName()).isEqualTo("Ahmed");
        assertThat(authors.size()).isGreaterThanOrEqualTo(10);
    }
    
    @Test
    void testFindAll_pageable1NoSorting() {
        List<Author> authors = authorDao.findAll(PageRequest.of(0, 10));
        authors.forEach(System.err::println);
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(10);
    }
    
    @Test
    void testFindAll_pageable2NoSorting() {
        List<Author> authors = authorDao.findAll(PageRequest.of(1, 10));
        authors.forEach(System.err::println);
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(10);
    }
    
    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");
        
        Author saved = authorDao.saveNewAuthor(author);
        
        authorDao.deleteAuthorById(saved.getId());
        
        assertThrows(EmptyResultDataAccessException.class, () -> authorDao.getById(saved.getId()));
        
    }
    
    @Test
    void testUpdateAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");
        
        Author saved = authorDao.saveNewAuthor(author);
        
        saved.setLastName("Thompson");
        Author updated = authorDao.updateAuthor(saved);
        
        assertThat(updated.getLastName()).isEqualTo("Thompson");
    }
    
    @Test
    void testInsertAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");
        
        Author saved = authorDao.saveNewAuthor(author);
        
        assertThat(saved).isNotNull();
    }
    
    @Test
    void testGetAuthorByName() {
        Author author = authorDao.findAuthorByName("Craig", "Walls");
        
        assertThat(author).isNotNull();
    }
    
    @Test
    void testGetAuthor() {
        
        Author author = authorDao.getById(1L);
        System.err.println(author);
        assertThat(author.getId()).isNotNull();
    }
    
    @Test
    void testFindAllAuthors() {
        List<Author> authors = authorDao.findAll();
        
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(10);
    }
}