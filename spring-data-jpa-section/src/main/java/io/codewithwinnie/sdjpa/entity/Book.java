package io.codewithwinnie.sdjpa.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * <PRE>Created by on 04/28/22.</PRE>
 * @author Ifeanyichukwu Otiwa
 */

@NamedQuery(name = "Book.jpaNamed", query = "FROM Book b WHERE b.title = :title")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String isbn;
    private String publisher;
    private Long authorId;

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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
    
    @Override
    public String toString() {
        return new StringJoiner("", "\n" + Book.class.getSimpleName() + " = {\n", "}")
                .add("        \"id\": \"" + id + "\",\n")
                .add("        \"title\": \"" + title + "\",\n")
                .add("        \"isbn\": \"" + isbn + "\",\n")
                .add("        \"publisher\": \"" + publisher + "\",\n")
                .add("        \"authorId\": \"" + authorId + "\"\n")
                .toString();
    }
}
