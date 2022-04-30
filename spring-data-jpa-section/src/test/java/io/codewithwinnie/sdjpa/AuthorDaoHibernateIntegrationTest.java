package io.codewithwinnie.sdjpa;

import io.codewithwinnie.sdjpa.dao.AuthorDao;
import io.codewithwinnie.sdjpa.dao.logic.hibernate.AuthorDaoHibernateImpl;
import io.codewithwinnie.sdjpa.entity.Author;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;


import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"io.codewithwinnie.sdjpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorDaoHibernateIntegrationTest {
    
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    AuthorDao authorDao;
    
    @BeforeEach
    void setUp() {
        authorDao = new AuthorDaoHibernateImpl(entityManagerFactory);
    }
    
    @Order(1)
    @Test
    void testFindAllByLastNameNoSorting() {
        List<Author> authors = authorDao.findAllByLastName("Smith");
        authors.forEach(System.err::println);
        assertThat(authors).isNotNull();
        System.err.println(authors.size());
        assertThat(authors.size()).isGreaterThanOrEqualTo(40);
    }
    
    @Order(2)
    @Test
    void testFindAllByLastName_pageableSortByFirstNameDescPage1() {
        List<Author> authors = authorDao.findAllByLastNameSortByFirstName("Smith", PageRequest.of(0, 10,
                Sort.by(Sort.Order.desc("first_name"))));
        authors.forEach(System.err::println);
        assertThat(authors.get(8).getFirstName()).isEqualTo("Tishara");
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThanOrEqualTo(10);
    }
    @Order(3)
    @Test
    void testFindAllByLastName_pageableSortByFirstNameDescPage4() {
        List<Author> authors = authorDao.findAllByLastNameSortByFirstName("Smith", PageRequest.of(3, 10,
                Sort.by(Sort.Order.desc("first_name"))));
        authors.forEach(System.err::println);
        assertThat(authors).isNotNull();
        assertThat(authors.get(9).getFirstName()).isEqualTo("Ahmed");
        assertThat(authors.size()).isGreaterThanOrEqualTo(10);
    }
    @Order(4)
    @Test
    void testFindAll_pageable1NoSorting() {
        List<Author> authors = authorDao.findAll(PageRequest.of(0, 10));
        authors.forEach(System.err::println);
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(10);
    }
    
    
    @Order(5)
    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");

        Author saved = authorDao.saveNewAuthor(author);

        authorDao.deleteAuthorById(saved.getId());

        Author deleted = authorDao.getById(saved.getId());

        assertThat(deleted).isNull();

    }

    @Order(6)
    @Test
    void testUpdateAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");

        Author saved = authorDao.saveNewAuthor(author);
        System.err.println("Saved" + saved);
        saved.setLastName("Thompson");
        System.out.println(saved.getLastName());
        Author updated = authorDao.updateAuthor(saved);
        System.err.println("Updated " + updated);
        assertThat(updated.getLastName()).isEqualTo("Thompson");
    }

    @Order(7)
    @Test
    void testSaveAuthor() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Thompson");
        Author saved = authorDao.saveNewAuthor(author);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
    }

    @Order(8)
    @Test
    void testGetAuthorByName() {
        Author author = authorDao.findAuthorByName("Craig", "Walls");

        assertThat(author).isNotNull();
    }

    @Order(9)
    @Test
    void testGetAuthor() {

        Author author = authorDao.getById(1L);

        assertThat(author).isNotNull();

    }

    @Order(10)
    @Test
    void testFindAllAuthors() {
        List<Author> authors = authorDao.findAll();
        authors.forEach(System.err::println);
        assertThat(authors.size()).isGreaterThan(0);
    }
}
