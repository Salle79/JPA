package org.thoughts.on.java.university.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.thoughts.on.java.university.model.Course;
import org.thoughts.on.java.university.model.Curriculum;

public class CurriculumServiceTest {

	EntityManagerFactory emf;
	
	@Before
	public void init() {
		emf = Persistence.createEntityManagerFactory("my-persistence-unit");
	}
	
	@Test
	public void persistCurriculum() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Course course = createCourse(em);
		
		Curriculum c = new Curriculum();
		c.setDescription("You will learn a lot of theory about software development ...");
		c.setCourse(course);
		course.setCurriculum(c);
		
		CurriculumService cs = new CurriculumService(em);
		cs.saveCurriculum(c);
		
		em.getTransaction().commit();
		em.close();
		
		Assert.assertNotNull(c.getId());
	}
	
	@Test
	public void updateCurriculum() {
		// persist a new Curriculum
		Curriculum c = createCurriculum();
		
		// Load and update the Curriculum
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		CurriculumService ss = new CurriculumService(em);
		c = ss.findById(c.getId());
		c.setDescription("All you need to know about Java");
		
		em.getTransaction().commit();
		em.close();
		
		// Validate update
		c = getCurriculum(c.getId());
		Assert.assertEquals("All you need to know about Java", c.getDescription());
	}
	
	@Test
	public void removeCurriculum() {
		// persist a new Curriculum
		Curriculum c = createCurriculum();
		
		// Load and update the Curriculum
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		CurriculumService cs = new CurriculumService(em);
		c = cs.findById(c.getId());
		cs.removeCurriculum(c);
		
		em.getTransaction().commit();
		em.close();
		
		// Validate update
		c = getCurriculum(c.getId());
		Assert.assertNull(c);
	}
	
	private Curriculum createCurriculum() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Course course = createCourse(em);
		
		Curriculum c = new Curriculum();
		c.setDescription("You will learn a lot of theory about software development ...");
		c.setCourse(course);
		course.setCurriculum(c);
		
		CurriculumService cs = new CurriculumService(em);
		cs.saveCurriculum(c);
		
		em.getTransaction().commit();
		em.close();
		
		return c;
	}
	
	private Course createCourse(EntityManager em) {
		Course c = new Course();
		c.setName("Software Development");
		
		CourseService cs = new CourseService(em);
		cs.saveCourse(c);
		
		return c;
	}
	
	private Curriculum getCurriculum(Long id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		CurriculumService cs = new CurriculumService(em);
		Curriculum c = cs.findById(id);
		
		em.getTransaction().commit();
		em.close();
		
		return c;
	}
}
