package org.thoughts.on.java.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.thoughts.on.java.jpa.beginners.Student;
import org.thoughts.on.java.jpa.beginners.StudentStatus;

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
	public void createStudent() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Student s = new Student();
		s.setFirstName("Peter");
		s.setLastName("Doe");
		s.setState(StudentStatus.ENROLLED);
		em.persist(s);
		
		em.getTransaction().commit();
		em.close();
	}
}
