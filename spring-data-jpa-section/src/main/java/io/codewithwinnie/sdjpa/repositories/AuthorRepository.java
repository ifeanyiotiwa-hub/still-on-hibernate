package io.codewithwinnie.sdjpa.repositories;



import io.codewithwinnie.sdjpa.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
    Page<Author> findAuthorByLastName(String lastName, Pageable pageable);
    List<Author> findAllByLastName(String lastName);
}
