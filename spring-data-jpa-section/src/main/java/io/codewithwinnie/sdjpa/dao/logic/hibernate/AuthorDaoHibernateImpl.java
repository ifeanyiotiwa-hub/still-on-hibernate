package io.codewithwinnie.sdjpa.dao.logic.hibernate;

import io.codewithwinnie.sdjpa.dao.AuthorDao;
import io.codewithwinnie.sdjpa.entity.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class AuthorDaoHibernateImpl implements AuthorDao {
    private final EntityManagerFactory entityManagerFactory;
    
    public AuthorDaoHibernateImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    
    @Override
    public List<Author> findAllByLastNameSortByFirstName(String lastName, Pageable pageable) {
        EntityManager em = getEntityManager();
        try {
            String HQL = "SELECT b FROM Author b WHERE b.lastName = :lastName ORDER BY b.firstName " +
                    pageable.getSort().getOrderFor("first_name").getDirection().name();
            TypedQuery<Author> typedQuery = em.createQuery(HQL,
                    Author.class);
            typedQuery.setParameter("lastName",lastName);
            typedQuery.setFirstResult(Math.toIntExact(pageable.getOffset()));
            typedQuery.setMaxResults(pageable.getPageSize());
            return typedQuery.getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Author> findAllByLastName(String lastName) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Author> typedQuery = em.createQuery("SELECT b FROM Author b WHERE b.lastName = :lastName",
                    Author.class);
            typedQuery.setParameter("lastName",lastName);
            return typedQuery.getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Author> findAllByLastName(String lastName, Pageable pageable) {
        EntityManager em = getEntityManager();
        try {
            String hql = "SELECT b FROM Author b WHERE b.lastName = :lastName";
            TypedQuery<Author> typedQuery = em.createQuery(hql,
                    Author.class);
            typedQuery.setFirstResult(Math.toIntExact(pageable.getOffset()));
            typedQuery.setMaxResults(pageable.getPageSize());
            return typedQuery.getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Author> findAll(Pageable pageable) {
        EntityManager em = getEntityManager();
        try {
            String hql = "SELECT b FROM Author b";
            TypedQuery<Author> typedQuery = em.createQuery(hql,
                    Author.class);
            typedQuery.setFirstResult(Math.toIntExact(pageable.getOffset()));
            typedQuery.setMaxResults(pageable.getPageSize());
            return typedQuery.getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Author> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Author> typedQuery = em.createQuery("SELECT b FROM Author b", Author.class);
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
        EntityManager em = getEntityManager();
    try{
        em.getTransaction().begin();
        em.persist(author);
        em.flush();
        em.getTransaction().commit();
        return em.find(Author.class,author.getId());
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
            Author author1 = em.find(Author.class, author.getId());
            em.getTransaction().commit();
            return author1;
        } finally {
            em.close();
        }
    }
    
    @Override
    public void deleteAuthorById(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Author author = em.find(Author.class, id);
            em.remove(author);
            em.flush();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
