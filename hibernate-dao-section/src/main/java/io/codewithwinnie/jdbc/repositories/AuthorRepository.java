package io.codewithwinnie.jdbc.repositories;


import io.codewithwinnie.jdbc.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
