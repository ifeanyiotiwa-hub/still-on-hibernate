package io.codewithwinnie.sdjpa.dao.logic.jdbctemplate;

import io.codewithwinnie.sdjpa.dao.AuthorDao;
import io.codewithwinnie.sdjpa.dao.logic.jdbctemplate.mapper.AuthorRowMapper;
import io.codewithwinnie.sdjpa.entity.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class AuthorDaoJDBCTemplateImpl implements AuthorDao {
    
    private final JdbcTemplate jdbcTemplate;
    
    public AuthorDaoJDBCTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    
    @Override
    public List<Author> findAllByLastNameSortByFirstName(String lastName, Pageable pageable) {
        return jdbcTemplate.query("SELECT * FROM author  WHERE last_name = ? ORDER BY first_name "
                                          +  pageable.getSort().getOrderFor("first_name").getDirection().name()
                                          + " limit ? offset ?",
         getAuthorRowMapper(), lastName, pageable.getPageSize(), pageable.getOffset());
    }
    
    @Override
    public List<Author> findAll(Pageable pageable) {
        return jdbcTemplate.query("SELECT * FROM author limit ? offset ?", getAuthorRowMapper()
                , pageable.getPageSize(), pageable.getOffset());
    }
    
    @Override
    public List<Author> findAllByLastName(String lastName, Pageable pageable) {
        return jdbcTemplate.query("SELECT * FROM author WHERE last_name = ? limit ? offset ?",
                getAuthorRowMapper(), lastName, pageable.getPageSize(), pageable.getOffset());
    }
    
    @Override
    public List<Author> findAllByLastName(String lastName) {
        return jdbcTemplate.query("SELECT * FROM author WHERE last_name = ?", getAuthorRowMapper(), lastName);
    }
    
    @Override
    public List<Author> findAll() {
        return jdbcTemplate.query("SELECT * FROM author", getAuthorRowMapper());
    }
    
    @Override
    public Author getById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * from author WHERE id = ?", getAuthorRowMapper(), id);
    }
    
    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return jdbcTemplate.queryForObject("SELECT * FROM author WHERE first_name = ? AND last_name = ?",
                getAuthorRowMapper(), firstName, lastName);
    }
    
    @Override
    public Author saveNewAuthor(Author author) {
        jdbcTemplate.update("INSERT INTO author(first_name, last_name) VALUES (?, ?)", author.getFirstName(),
                author.getId());
        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        return getById(id);
    }
    
    @Override
    public Author updateAuthor(Author author) {
        jdbcTemplate.update("UPDATE author SET first_name = ?, last_name = ? WHERE id = ?", author.getFirstName(),
                author.getLastName(), author.getId());
        return getById(author.getId());
    }
    
    @Override
    public void deleteAuthorById(Long id) {
        jdbcTemplate.update("DELETE FROM author a WHERE id = ?", id);
    }
    
    
    private RowMapper<Author> getAuthorRowMapper() {
        return new AuthorRowMapper();
    }
}
