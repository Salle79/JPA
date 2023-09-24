package com.thorben.janssen.hibernate.performance;

import com.thorben.janssen.hibernate.performance.model.Book;
import com.thorben.janssen.hibernate.performance.model.Publisher;
import com.thorben.janssen.hibernate.performance.model.Review;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestBytecodeEnhancement {

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
	public void testLazyAttributeLoading() {
		log.info("... testLazyAttributeLoading ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Review r = em.find(Review.class, 1L);
		log.info("Rating: " + r.getRating());
		log.info("Comment: " + r.getComment());

        em.getTransaction().commit();
        em.close();
	}
	
	@Test
	public void testNoProxy() {
		log.info("... testNoProxy ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		Book book = em.createQuery("SELECT b FROM Book b WHERE b.id = 1", Book.class).getSingleResult();
        log.info("Book: "+book);
        log.info("Instance of Publisher? " + (book.getPublisher() instanceof Publisher));
        log.info(book.getPublisher().getClass());
        log.info("Publisher: "+book.getPublisher().getName());
        em.getTransaction().commit();
        em.close();
	}
}
