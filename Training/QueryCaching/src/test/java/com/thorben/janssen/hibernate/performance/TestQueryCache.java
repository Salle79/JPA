package com.thorben.janssen.hibernate.performance;

import com.thorben.janssen.hibernate.performance.model.Author;
import com.thorben.janssen.hibernate.performance.model.Book;
import com.thorben.janssen.hibernate.performance.model.BookPublisherValue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jpa.AvailableHints;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;

public class TestQueryCache {

	Logger log = LogManager.getLogger(this.getClass().getName());
	
	private EntityManagerFactory emf;

    @Before
    public void init() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    @After
    public void close() {
        emf.close();
    }
    
	@Test
	public void testQueryCacheAdHocQuery() {
		log.info("... testQueryCacheAdHocQuery ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		Session s = em.unwrap(Session.class);
    	Query<Author> q = s.createQuery("SELECT a FROM Author a WHERE id = :id", Author.class);
        q.setParameter("id", 1L);
		q.setCacheable(true);
		log.info(q.uniqueResult());
		
		log.info(q.uniqueResult());

        em.getTransaction().commit();
        em.close();
	}
	
	@Test
	public void testQueryCacheWithTwoTransactions() {
		log.info("... testQueryCache2transactions ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		Session s = em.unwrap(Session.class);
		Query<Author> q = s.createQuery("SELECT a FROM Author a WHERE id = :id", Author.class);
		q.setParameter("id", 1L);
		q.setCacheable(true);
		log.info(q.list().get(0));

        em.getTransaction().commit();
        em.close();
        
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
		s = em.unwrap(Session.class);
		q = s.createQuery("SELECT a FROM Author a WHERE id = :id", Author.class);
		q.setParameter("id", 1L);
		q.setCacheable(true);
		log.info(q.list().get(0));

        em.getTransaction().commit();
        em.close();
	}

    @Test
	public void testQueryCacheWithDifferentParameter() {
		log.info("... testQueryCacheWithDifferentParameter ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		Session s = em.unwrap(Session.class);
		Query<Author> q = s.createQuery("SELECT a FROM Author a WHERE id = :id", Author.class);
		q.setParameter("id", 1L);
		q.setCacheable(true);
		log.info(q.list().get(0));
		
		q = s.createQuery("SELECT a FROM Author a WHERE id = :id", Author.class);
		q.setParameter("id", 2L);
		q.setCacheable(true);
		log.info(q.list().get(0));

        em.getTransaction().commit();
        em.close();
	}
	
	@Test
	public void testQueryCacheUpdateEntity() {
		log.info("... testQueryCacheUpdateEntity ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		Session s = em.unwrap(Session.class);
		Query<Author> q = s.createQuery("SELECT a FROM Author a WHERE id = :id", Author.class);
		q.setParameter("id", 1L);
		q.setCacheable(true);
		Author a = (Author)q.list().get(0);
		
		a.setFirstName("changed");
		
		log.info(a);
		
		log.info(q.list().get(0));

        em.getTransaction().commit();
        em.close();
	}

    @Test
	public void testQueryCacheUpdateQuery() {
		log.info("... testQueryCacheUpdateQuery ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		Session s = em.unwrap(Session.class);
		Query<Author> q = s.createQuery("SELECT a FROM Author a WHERE id = :id", Author.class);
		q.setParameter("id", 1L);
		q.setCacheable(true);
		q.uniqueResult();
		
		em.createQuery("UPDATE Author a SET a.lastName=:lastName WHERE id = :id")
            .setParameter("lastName", "changed")
            .setParameter("id", 1)
            .executeUpdate();
		
		log.info(q.uniqueResult());

        em.getTransaction().commit();
        em.close();
	}
	
	@Test
	public void testQueryCacheUpdateDifferentEntity() {
		log.info("... testQueryCacheUpdateDifferentEntity ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		Session s = em.unwrap(Session.class);
		Query<Tuple> q = s.createQuery("SELECT a.firstName, a.lastName FROM Author a WHERE a.id = :id", Tuple.class);
		// Query<Tuple> q = s.createQuery("SELECT a.firstName, a.lastName, b.title FROM Author a JOIN a.books b WHERE a.id = :id", Tuple.class);
		q.setParameter("id", 1L);
		q.setCacheable(true);
		q.list();
		
		Book b = s.find(Book.class, 1L);
		b.setTitle("changed");
		
		q.list();

        em.getTransaction().commit();
        em.close();
	}
		
	@Test
	public void testQueryCacheDtoProjection() {
		log.info("... testQueryCacheWithDifferentParameter ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        log.info("First query");
		TypedQuery<BookPublisherValue> q = em.createQuery("""
			SELECT new com.thorben.janssen.hibernate.performance.model.BookPublisherValue(b.title, b.publisher.name) 
			FROM Book b""", BookPublisherValue.class);
        q.setHint(AvailableHints.HINT_CACHEABLE, true);
        log.info(q.getResultList().get(0));

        em.getTransaction().commit();
        em.close();
        
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        log.info("Second query");
		q = em.createQuery("""
			SELECT new com.thorben.janssen.hibernate.performance.model.BookPublisherValue(b.title, b.publisher.name)
			FROM Book b""", BookPublisherValue.class);
		q.setHint(AvailableHints.HINT_CACHEABLE, true);
		log.info(q.getResultList().get(0));
		
        em.getTransaction().commit();
        em.close();
	}
	
}
