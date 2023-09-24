package com.thorben.janssen.hibernate.performance;

import com.thorben.janssen.hibernate.performance.model.Author;
import com.thorben.janssen.hibernate.performance.model.Book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestManyToManyMapping {

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
	public void addAuthor() {
		log.info("... addAuthor ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
        Author a = new Author();
        a.setId(100L);
        a.setFirstName("Thorben");
        a.setLastName("Janssen");
        em.persist(a);

		Book b = em.find(Book.class, 1L);
		b.getAuthors().add(a);
		
		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void removeAuthor() {
		log.info("... removeAuthor ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Book b = em.find(Book.class, 1L);
		Author a = b.getAuthors().iterator().next();
		b.getAuthors().remove(a);
		
		em.getTransaction().commit();
		em.close();
	}
}
