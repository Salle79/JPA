package com.thorben.janssen.hibernate.performance;

import static org.assertj.core.api.Assertions.assertThat;

import com.thorben.janssen.hibernate.performance.model.Book;
import com.thorben.janssen.hibernate.performance.model.Manuscript;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestOneToOneMapping {

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
	public void bidirectionalOneToOne() {
		log.info("... bidirectionalOneToOne ...");

        EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		em.find(Book.class,  1L);
		
		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void unidirectionalOneToOne() {
		log.info("... unidirectionalOneToOne ...");

		// Persist Book and Manuscript
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Book b = new Book();
		b.setTitle("Hibernate Tips - More than 70 solutions to common Hibernate problems");
		em.persist(b);
		
		Manuscript m = new Manuscript();
		m.setBook(b);
		em.persist(m);
		
		em.getTransaction().commit();
		em.close();
		
        // Traverse unidirectional association
		em = emf.createEntityManager();
		em.getTransaction().begin();

		m = em.find(Manuscript.class, m.getId());
		assertThat(b.getId()).isEqualTo(m.getBook().getId());
		
        em.getTransaction().commit();
		em.close();

        // Find Manuscript of Book
        em = emf.createEntityManager();
		em.getTransaction().begin();

		b = em.find(Book.class,  b.getId());
		m = em.find(Manuscript.class, b.getId());
		
		em.getTransaction().commit();
		em.close();
	}
}
