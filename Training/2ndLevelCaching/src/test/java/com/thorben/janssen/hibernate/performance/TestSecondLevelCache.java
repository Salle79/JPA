package com.thorben.janssen.hibernate.performance;

import java.util.List;
import java.util.stream.Collectors;

import com.thorben.janssen.hibernate.performance.model.Author;
import com.thorben.janssen.hibernate.performance.model.Book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestSecondLevelCache {

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
	public void test2TX() {
		log.info("... test2TX ...");
		
		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		Author a1 = em.find(Author.class, 1L);
		log.info(a1);
		
		em.getTransaction().commit();
        em.close();
        
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
		Author a2 = em.find(Author.class, 1L);
		log.info(a2);

        em.getTransaction().commit();
        em.close();
	}
	
	@Test
	public void test2TXQueries() {
		log.info("... test2TXQueries ...");
		
		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
		
		em.getTransaction().commit();
        em.close();
        
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        List<Author> authors = em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
        authors.get(0).setFirstName("changed");
        
        em.getTransaction().commit();
        em.close();
        
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Statistics stat = em.unwrap(Session.class).getSessionFactory().getStatistics();
        log.info("hit: "+stat.getSecondLevelCacheHitCount());
        log.info("put: "+stat.getSecondLevelCachePutCount());
        log.info("miss: "+stat.getSecondLevelCacheMissCount());
        
        em.getTransaction().commit();
        em.close();
	}
	
	@Test
	public void testToOneAssociationCaching() {
		log.info("... testToOneAssociationCaching ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		// get author and his books
		Book b = em.find(Book.class, 1L);
		b.getPublisher().getName();

        em.getTransaction().commit();
        em.close();
	
        // 2nd session
		em = emf.createEntityManager();
        em.getTransaction().begin();
        
		// get author and his books
        b = em.find(Book.class, 1L);
        log.info(b.getPublisher().getClass().getName());
		b.getPublisher().getName();
		

        em.getTransaction().commit();
        em.close();
	}
	
    @Test
	public void testAssociationCaching() {
		log.info("... testAssociationCaching ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		// get author and his books
		Author a = em.find(Author.class, 1L);
		writeMessage(a);

        em.getTransaction().commit();
        em.close();
	
        // 2nd session
		em = emf.createEntityManager();
        em.getTransaction().begin();
        
		// get author and his books
		a = em.find(Author.class, 1L);
		writeMessage(a);
		

        em.getTransaction().commit();
        em.close();
	}
	
	private void writeMessage(Author a) {
		log.info("Author " + a.getFirstName() + " " + a.getLastName() + " wrote "
			+ a.getBooks().stream().map(b -> b.getTitle()).collect(Collectors.joining(", ")));
	}
}
