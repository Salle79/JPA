package org.thoughts.on.java.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.thoughts.on.java.jpa.beginners.Course;
import org.thoughts.on.java.jpa.beginners.CourseValue;
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
	public void adhocJpqlFindAllCourses() {
		log.info("... adhocJpqlFindAllCourses ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		// create an adhoc query to select all courses
		TypedQuery<Course> q = 
				em.createQuery("SELECT c FROM Course c", Course.class);
		List<Course> results = q.getResultList();
		
		Assert.assertEquals(10, results.size());
		
		for (Course c : results) {
			log.info(c);
		}
		
		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void namedJpqlFindAllCourses() {
		log.info("... namedJpqlFindAllCourses ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		// create an adhoc query to select all courses
		TypedQuery<Course> q = 
				em.createNamedQuery(Course.FIND_ALL_COURSES, 
									Course.class);
		List<Course> results = q.getResultList();
		
		Assert.assertEquals(10, results.size());
		
		for (Course c : results) {
			log.info(c);
		}
		
		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void adhocJpqlFindAllCourseIdsAndNames() {
		log.info("... adhocJpqlFindAllCourseIdsAndNames ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		// create an adhoc query to select all courses
		Query q = em.createQuery("SELECT c.id, c.name FROM Course c");
		List<Object[]> results = q.getResultList();
		
		Assert.assertEquals(10, results.size());
		
		for (Object[] r : results) {
			log.info(r[0] + " - " + r[1]);
		}
		
		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void adhocJpqlFindAllCourseValues() {
		log.info("... adhocJpqlFindAllCourseValues ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		// create an adhoc query to select all courses
		TypedQuery<CourseValue> q = 
				em.createQuery("SELECT new org.thoughts.on.java.jpa.beginners.CourseValue(c.id, c.name) FROM Course c", CourseValue.class);
		List<CourseValue> results = q.getResultList();
		
		Assert.assertEquals(10, results.size());
		
		for (CourseValue c : results) {
			log.info(c);
		}
		
		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void adhocJpqlFindOneCourseByName() {
		log.info("... adhocJpqlFindOneCourseByName ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		// create an adhoc query to select a course with a given name
		TypedQuery<Course> q = 
				em.createQuery("SELECT c FROM Course c WHERE c.name LIKE :name", 
							   Course.class);
		q.setParameter("name", "Course 1%");
		List<Course> courses = q.getResultList();
		
		Assert.assertEquals(2, courses.size());
		
		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void adhocJpqlFindCoursesByNameOrId() {
		log.info("... adhocJpqlFindCoursesByNameOrId ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		// create an adhoc query to select all courses
		TypedQuery<Course> q = em.createQuery("SELECT c FROM Course c WHERE c.id = :id or c.name = :name", Course.class);
		q.setParameter("id", 3L);
		q.setParameter("name", "Course 5");
		List<Course> courses = q.getResultList();
		
		Assert.assertEquals(2, courses.size());
		
		for (Course c : courses) {
			log.info(c);
		}
		
		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void adhocJpqlJoin() {
		log.info("... adhocJpqlJoin ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
				
		// create an adhoc query to select all courses
		// TypedQuery<Course> q = em.createQuery("SELECT c FROM Course c JOIN c.professor p WHERE p.firstName = :name", Course.class);
		TypedQuery<Course> q = em.createQuery("SELECT c FROM Course c WHERE c.professor.firstName = :name", Course.class);
		q.setParameter("name", "John");
		Course course = q.getSingleResult();
		
		Assert.assertEquals(new Long(1), course.getId());
		
		log.info(course);
		
		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void adhocJpqlCountCourses() {
		log.info("... adhocJpqlCountCourses ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
				
		// create an adhoc query to select all courses
        // Query q = em.createQuery("SELECT c.professor, count(c) FROM Course c GROUP BY c.professor");
		Query q = em.createQuery("SELECT p, count(c) FROM Course c JOIN c.professor p GROUP BY p");
		List<Object[]> results = q.getResultList();
		
		Assert.assertEquals(4L, results.size());
		
		for (Object[] r : results) {
			log.info(r[0] + " teaches " + r[1] + " courses");
		}
		
		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void adhocJpqlHaving() {
		log.info("... adhocJpqlHaving ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
				
		// create an adhoc query to select all courses
		TypedQuery<Professor> q = em.createQuery("SELECT p FROM Course c JOIN c.professor p GROUP BY p HAVING count(c) > 2", Professor.class);
		List<Professor> results = q.getResultList();
		
		Assert.assertEquals(2L, results.size());
		
		for (Professor p : results) {
			log.info(p);
		}
		
		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void adhocJpqlOrderBy() {
		log.info("... adhocJpqlOrderBy ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
				
		TypedQuery<Course> q = em.createQuery("SELECT c FROM Course c ORDER BY c.name DESC", Course.class);
		q.setMaxResults(3);
		q.setFirstResult(2);
		List<Course> results = q.getResultList();
		
		// Assert.assertEquals(10L, results.size());
		
		for (Course c : results) {
			log.info(c);
		}
		
		em.getTransaction().commit();
		em.close();
	}
	
	public Long createCourse() {	
		return createCourse("Software Development 1");
	}
	
	public Long createCourse(String name) {
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
	
	public Long createProfessor() {
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
