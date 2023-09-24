package org.thoughts.on.java.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.thoughts.on.java.jpa.beginners.Professor;

public class TestExampleProject {

	Logger log = Logger.getLogger(this.getClass().getName());

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
	public void createProfessor() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		for (int i=0; i<10; i++) {
			Professor prof = new Professor();
			prof.setFirstName("Jane");
			prof.setLastName("Doe");
			em.persist(prof);
		}
		
		em.getTransaction().commit();
		em.close();
	}
}
