package io.codewithwinnie.sdjpa;

import io.codewithwinnie.sdjpa.dao.AuthorDao;
import io.codewithwinnie.sdjpa.entity.Author;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"io.codewithwinnie.sdjpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthorDaoImplTest {
    
    @Autowired
    AuthorDao authorDao;
    
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
                Sort.by(Sort.Order.desc("firstName"))));
        authors.forEach(System.err::println);
        assertThat(authors.get(8).getFirstName()).isEqualTo("Tishara");
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThanOrEqualTo(10);
    }
    
    @Order(11)
    @Test
    void testFindAllByLastName_pageableSortByFirstNameAscPage1() {
        List<Author> authors = authorDao.findAllByLastNameSortByFirstName("Smith", PageRequest.of(0, 10,
                Sort.by(Sort.Order.asc("firstName"))));
        authors.forEach(System.err::println);
        assertThat(authors.get(0).getFirstName()).isEqualTo("Ahmed");
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThanOrEqualTo(10);
    }
    @Order(3)
    @Test
    void testFindAllByLastName_pageableSortByFirstNameDescPage4() {
        List<Author> authors = authorDao.findAllByLastNameSortByFirstName("Smith", PageRequest.of(3, 10,
                Sort.by(Sort.Order.desc("firstName"))));
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
    
}