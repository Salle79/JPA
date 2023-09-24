package org.thoughts.on.java.model;

import java.util.Date;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.thoughts.on.java.jpa.beginners.Course;


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
	public void persistCourse() {
		log.info("... persistCourse ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		// create a new Course entity
		Course course = new Course();
		course.setName("Software Development 1");
		// 2018/08/15
		course.setStartDate(new Date(118, 7, 15));
		// 10 hours and 30 minutes
		course.setBeginLecture(new Date(10*60*60*1000+30*60*1000));
		// 2019/03/22 10:30am
		course.setExam(new Date(119, 2, 22, 10, 30));
		
		// persist the course entity
		em.persist(course);
		
		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void findCourse() {
		log.info("... findCourse ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		// get the primary key of a course
		Long courseId = createCourse();
		
		// load Course entity by primary key
		Course course = em.find(Course.class, courseId);
		
		log.info(course);
		
		em.getTransaction().commit();
		em.close();
	}
	

	public Long createCourse() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Course course = new Course();
		course.setName("Software Development 1");
		course.setStartDate(new Date(118, 7, 15));
		course.setExam(new Date(119, 2, 22, 10, 30));
		course.setBeginLecture(new Date(10*60*60*1000+30*60*1000));
		em.persist(course);
		
		em.getTransaction().commit();
		em.close();
		
		return course.getId();
	}
}
