package com.thorben.janssen.hibernate.performance;

import static org.assertj.core.api.Assertions.fail;

import com.thorben.janssen.hibernate.performance.model.Author;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.Persistence;
import jakarta.persistence.RollbackException;
import jakarta.persistence.TypedQuery;

public class TestOptimisticLocking {

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
	public void testUpdate() {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Author a = em.find(Author.class, 1L);
		a.setFirstName("changed");

		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testConcurrentUpdate() {

		EntityManager em1 = emf.createEntityManager();
		em1.getTransaction().begin();

		EntityManager em2 = emf.createEntityManager();
		em2.getTransaction().begin();

		Author a1 = em1.find(Author.class, 1L);
		a1.setFirstName("changed");

		Author a2 = em2.find(Author.class, 1L);
		a2.setFirstName("something else");

		em1.getTransaction().commit();
		em1.close();

		try {
			em2.getTransaction().commit();
			
			fail("RollbackExecption expected");
		} catch (RollbackException e) {
			if (e.getCause() instanceof OptimisticLockException) {
				log.info(e.getCause());
			} else {
				fail("OptimisticLockException expected");
			}
		}

		em2.close();
	}
	
	@Test
	public void testForceIncrement() {

		// read Author 8 in transaction 1 and remove 1st book
		EntityManager em1 = emf.createEntityManager();
		em1.getTransaction().begin();
		
		TypedQuery<Author> q1 = em1.createQuery("SELECT a FROM Author a WHERE id = 8", Author.class);
		q1.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
		Author a1 = q1.getSingleResult();
		a1.getBooks().remove(a1.getBooks().toArray()[0]);
		
		// do something useful based on the Books this Author has written
		log.info(a1 + " wrote " + a1.getBooks().toArray()[0] + ".");
		
		// read Author 8 in transaction 2 and remove 2nd book
		EntityManager em2 = emf.createEntityManager();
		em2.getTransaction().begin();
		
		TypedQuery<Author> q2 = em2.createQuery("SELECT a FROM Author a WHERE id = 8", Author.class);
		q2.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
		Author a2 = q2.getSingleResult();
		a2.getBooks().remove(a2.getBooks().toArray()[1]);
		
		// do something useful based on the Books this Author has written
		log.info(a2 + " wrote " + a2.getBooks().toArray()[0] + ".");
		
		// commit transaction 1
		em1.getTransaction().commit();
		em1.close();
		
		// commit transaction 2
		try {
			em2.getTransaction().commit();
			em2.close();
            fail("RollbackExecption expected");
		} catch (RollbackException e) {
			log.error(e.getCause());
		}
	}
}
