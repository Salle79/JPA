package org.thoughts.on.java.university.service;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.thoughts.on.java.university.model.Course;

public class CourseServiceTest {

	EntityManagerFactory emf;
	
	@Before
	public void init() {
		emf = Persistence.createEntityManagerFactory("my-persistence-unit");
	}
	
	@Test
	public void persistCourse() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Course c = new Course();
		c.setName("Software Development");
		c.setStartDate(LocalDate.of(2019, 1, 1));
		c.setEndDate(LocalDate.of(2019,12,31));
		
		CourseService cs = new CourseService(em);
		cs.saveCourse(c);
		
		em.getTransaction().commit();
		em.close();
		
		Assert.assertNotNull(c.getId());
	}
	
	@Test
	public void updateCourse() {
		// persist a new Course
		Course c = createCourse();
		
		// Load and update the Course
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		CourseService ss = new CourseService(em);
		c = ss.findById(c.getId());
		c.setName("Java 1");
		
		em.getTransaction().commit();
		em.close();
		
		// Validate update
		c = getCourse(c.getId());
		Assert.assertEquals("Java 1", c.getName());
	}
	
	@Test
	public void removeCourse() {
		// persist a new Course
		Course c = createCourse();
		
		// Load and update the Course
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		CourseService cs = new CourseService(em);
		c = cs.findById(c.getId());
		cs.removeCourse(c);
		
		em.getTransaction().commit();
		em.close();
		
		// Validate update
		c = getCourse(c.getId());
		Assert.assertNull(c);
	}
	
	private Course createCourse() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Course c = new Course();
		c.setName("Software Development");
		c.setStartDate(LocalDate.of(2019, 1, 1));
		c.setEndDate(LocalDate.of(2019,12,31));
		
		CourseService cs = new CourseService(em);
		cs.saveCourse(c);
		
		em.getTransaction().commit();
		em.close();
		
		return c;
	}
	
	private Course getCourse(Long id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		CourseService cs = new CourseService(em);
		Course c = cs.findById(id);
		
		em.getTransaction().commit();
		em.close();
		
		return c;
	}
}
