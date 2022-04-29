package io.codewithwinnie.jdbc.repositories;

import io.codewithwinnie.jdbc.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
