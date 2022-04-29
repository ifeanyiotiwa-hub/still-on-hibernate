package io.codewithwinnie.jdbc.dao.persistence;

import io.codewithwinnie.jdbc.dao.AuthorDao;
import io.codewithwinnie.jdbc.entity.Author;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * <PRE>Created by on 04/28/22.</PRE>
 * @author Ifeanyichukwu Otiwa
 */
@Component
public class AuthorDaoImpl implements AuthorDao {
    
    private final EntityManagerFactory entityManagerFactory;
    
    public AuthorDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    
    
    @Override
    public List<Author> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Author> typedQuery = em.createNamedQuery("author", Author.class);
            return typedQuery.getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public Author getById(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.joinTransaction();
            em.flush();
            em.getTransaction().commit();
            return em.find(Author.class,id);
        } finally {
            em.close();
        }
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Author> query = em.createQuery("SELECT b FROM Author b " +
                                                              "WHERE b.firstName = :first_name AND b.lastName = " +
                                                              ":last_name",
                    Author.class);
            query.setParameter("first_name", firstName);
            query.setParameter("last_name", lastName);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Author saveNewAuthor(Author author) {
        EntityManager em = this.getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(author);
            em.flush();
            em.getTransaction().commit();
            Author saved = em.find(Author.class,author.getId());
            return saved;
        } finally {
            em.close();
        }
    }

    @Override
    public Author updateAuthor(Author author) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(author);
            em.flush();
            em.getTransaction().commit();
            em.clear();
            Author author1 = em.find(Author.class, author.getId());
            return author1;
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteAuthorById(Long id) {
        EntityManager em = getEntityManager();
        try {
            Author author = em.find(Author.class, id);
            em.getTransaction()
                    .begin();
            em.remove(author);
            em.flush();
            em.getTransaction()
                    .commit();
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Author> listAuthorByLastNameLike(String lastName) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT a FROM Author a WHERE a.lastName LIKE :lastName");
            query.setParameter("lastName", "%" + lastName + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
