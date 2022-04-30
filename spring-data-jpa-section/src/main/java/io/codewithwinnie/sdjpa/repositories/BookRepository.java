package io.codewithwinnie.sdjpa.repositories;


import io.codewithwinnie.sdjpa.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface BookRepository extends JpaRepository<Book, Long> {
    
    Book jpaNamed(@Param("title") String title);
    
    @Query(value = "SELECT * FROM book where title = :title", nativeQuery = true)
    Book findByTitleWithNativeQuery(@Param("title") String title);
    
    @Query(value = "SELECT b FROM Book b WHERE b.title = ?1")
    Book findBookByTitleWithQuery(String title);
    
    @Query(value = "SELECT b FROM Book b WHERE b.title = :title")
    Book findByTitleWithQueryNamed(@Param("title") String title);
    Optional<Book> findByTitle(String title);
    Optional<Book> findByIsbn(String isbn);
    
    Book readByTitle(String title);
    
    @Nullable
    Book getByTitle(@Nullable String title);
    
    Stream<Book> findAllByTitleNotNull();
    
    @Async
    Future<Book> queryByTitle(@Nullable String title);
    
    Stream<Book> findAllByAuthorIdNull();
}
