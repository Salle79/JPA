package com.thorben.janssen.hibernate.performance;

import com.thorben.janssen.hibernate.performance.model.Author;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestPrimaryKey {

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
	public void testInsertAuthors() {
		log.info("... testInsertAuthors ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		for (int i = 0; i < 20; i++) {
			Author a = new Author();
			a.setFirstName("FirstName" + i);
			a.setLastName("LastName" + i);
			em.persist(a);
		}

        log.info("Commit transaction");
		em.getTransaction().commit();
		em.close();
	}
}
