package io.codewithwinnie.jdbc.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * <PRE>Created by on 04/28/22.</PRE>
 * @author Ifeanyichukwu Otiwa
 */

@NamedQueries({
        @NamedQuery(name = "book_find_all", query = "FROM Book"),
        @NamedQuery(name = "find_book_by_title", query = "FROM Book b WHERE b.title = :title")
})
@NamedQuery(name = "get_all_books", query = "FROM Book")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String isbn;
    private String publisher;
    @Transient
    private Author authorId;

    public Book() {

    }

    public Book(String title, String isbn, String publisher) {
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Author getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Author authorId) {
        this.authorId = authorId;
    }
    
    public void setAuthor(Author author) {
    }
}
