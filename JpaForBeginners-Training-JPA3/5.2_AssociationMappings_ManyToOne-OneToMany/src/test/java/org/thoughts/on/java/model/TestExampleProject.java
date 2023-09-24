package org.thoughts.on.java.model;

import java.time.LocalDate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.thoughts.on.java.jpa.beginners.Course;
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
	public void manageBidirectionalOneToManyAssociation() {
		log.info("... manageBidirectionalOneToManyAssociation ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		// get the primary key of a course and a professor
		Long courseId = createCourse();
		Long profId = createProfessor();
		
		// load Course and Professor entities by primary key
		Course course = em.find(Course.class, courseId);
		Professor professor = em.find(Professor.class, profId);
		
		// add the association
		professor.getCourses().add(course);
		course.setProfessor(professor);
//		professor.addCourse(course);
		
		em.getTransaction().commit();
		em.close();	
	}
	
	@Test
	public void useOneToManyAssociation() {
		log.info("... useOneToManyAssociation ...");

		Long profId = prepareTestData();
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Professor professor = em.find(Professor.class, profId);
		for (Course c : professor.getCourses()) {
			log.info(c);
		}
		
		em.getTransaction().commit();
		em.close();	
	}
	
	private Long prepareTestData() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		// get the primary key of a course and a professor
		Long courseId = createCourse();
		Long profId = createProfessor();
		
		// load Course and Professor entities by primary key
		Course course = em.find(Course.class, courseId);
		Professor prof = em.find(Professor.class, profId);
		
		// add the association
		prof.getCourses().add(course);
		course.setProfessor(prof);
		
		em.getTransaction().commit();
		em.close();	
		
		return prof.getId();
	}
	
	private Long createCourse() {	
		return createCourse("Software Development 1");
	}
	
	private Long createCourse(String name) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Course course = new Course();
		course.setName(name);
		course.setStartDate(LocalDate.of(2018, 8, 15));
		course.setEndDate(LocalDate.of(2019, 5, 31));
		em.persist(course);
		
		em.getTransaction().commit();
		em.close();
		
		return course.getId();
	}
	
	private Long createProfessor() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Professor prof = new Professor();
		prof.setFirstName("Jane");
		prof.setLastName("Doe");
		em.persist(prof);
		
		em.getTransaction().commit();
		em.close();
		
		return prof.getId();
	}
}
