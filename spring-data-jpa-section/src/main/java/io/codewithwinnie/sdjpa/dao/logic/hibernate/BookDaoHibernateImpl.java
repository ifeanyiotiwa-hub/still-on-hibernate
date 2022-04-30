package io.codewithwinnie.sdjpa.dao.logic.hibernate;

import io.codewithwinnie.sdjpa.dao.BookDao;
import io.codewithwinnie.sdjpa.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class BookDaoHibernateImpl implements BookDao {
    private final EntityManagerFactory eManagerFactory;
    
    public BookDaoHibernateImpl(EntityManagerFactory eManagerFactory) {
        this.eManagerFactory = eManagerFactory;
    }
    
    @Override
    public List<Book> findAllBookSortByTitle(Pageable pageable) {
        EntityManager em = getEntityManager();
        try {
            StringBuilder SQL = new StringBuilder();
            SQL.append("SELECT b FROM Book b ORDER BY b.title ");
            pageable.getSort();
            SQL.append(pageable.getSort().getOrderFor("title").getDirection().name());
    
            TypedQuery<Book> query =
                    em.createQuery(SQL.toString(),Book.class);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        EntityManager em = getEntityManager();
        try {
            StringBuilder SQL = new StringBuilder();
            SQL.append("SELECT b FROM Book b ORDER BY b.title ");
            if (pageable.getSort().getOrderFor("title") != null) {
                SQL.append(pageable.getSort().getOrderFor("title").getDirection().name());
            }
        
            TypedQuery<Book> query =
                    em.createQuery(SQL.toString(),Book.class);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Book> findAllBooks(int pageSize, int offSet) {
        return null;
    }
    
    @Override
    public List<Book> findAllBooks() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public Book saveNewBook(Book book) {
        EntityManager entityManager = getEntityManager();
        try {
//          entityManager.joinTransaction();
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
            em.getTransaction().begin();
            Book book = em.find(Book.class, id);
            em.remove(book);
//            em.flush();
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
    public Book updateBook(Book book) {
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(book);
            em.flush();
            Book saved = em.find(Book.class, book.getId());
            em.getTransaction().commit();
            return saved;
        }finally{
            em.close();
        }
    }
    
    @Override
    public Book findBookByTitle(String title) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT b FROM Book b WHERE b.title = :title", Book.class);
            query.setParameter("title",title);
            return (Book) query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    @Override
    public Book findByISBN(String isbn) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class);
            query.setParameter("isbn",isbn);
            return (Book) query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    @Override
    public int count() {
        return 0;
    }
    
    private EntityManager getEntityManager() {
        return eManagerFactory.createEntityManager();
    }
}
