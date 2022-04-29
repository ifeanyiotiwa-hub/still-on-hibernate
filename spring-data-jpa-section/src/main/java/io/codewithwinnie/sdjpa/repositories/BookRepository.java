package io.codewithwinnie.sdjpa.repositories;


import io.codewithwinnie.sdjpa.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
