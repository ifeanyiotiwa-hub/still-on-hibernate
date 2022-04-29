package io.codewithwinnie.jdbc.dao.persistence;

import io.codewithwinnie.jdbc.dao.BookDao;
import io.codewithwinnie.jdbc.entity.Book;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Component
public class BookDaoImpl implements BookDao {
    private final EntityManagerFactory entityManagerFactory;
    
    public BookDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    
    @Override
    public Book saveNewBook(Book book) {
        EntityManager entityManager = getEntityManager();
        try {
//            entityManager.joinTransaction();
            entityManager.getTransaction().begin();
            entityManager.persist(book);
            entityManager.flush();
            entityManager.getTransaction().commit();
            return entityManager.find(Book.class, book.getId());
        } finally {
            entityManager.close();
        }
    }
    
    @Override
    public void deleteBookById(Long id) {
        EntityManager em = getEntityManager();
        try {
            Book book = em.find(Book.class, id);
            em.getTransaction().begin();
            em.remove(book);
            em.flush();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @Override
    public Book getById(Long id) {
        EntityManager em = getEntityManager();
    
        try {
            return em.find(Book.class, id);
        } finally {
            em.close();
        }
    }
    
    @Override
    public void updateBook(Book saved) {
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(saved);
            em.flush();
            em.getTransaction().commit();
        }finally{
            em.close();
        }
    
    }
    
    @Override
    public Book findBookByTitle(String clean_code) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b where b.title = :title", Book
                                                                                                           .class);
            query.setParameter("title", clean_code);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    @Override
    public Book findByISBN(String isbn) {
        EntityManager em = getEntityManager();
        try {
            // Query query = em.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn");
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class);
            query.setParameter("isbn", isbn);
            Book book = query.getSingleResult();
            return book;
        } finally {
            em.close();
        }
    }
    
    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
