package com.thorben.janssen.hibernate.performance;

import static org.assertj.core.api.Assertions.fail;

import java.util.HashMap;
import java.util.Map;

import com.thorben.janssen.hibernate.performance.model.Author;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.LockTimeoutException;
import jakarta.persistence.Persistence;

public class TestPessimisticLocking {

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
	public void testReadLock() {

		EntityManager em1 = emf.createEntityManager();
		em1.getTransaction().begin();

		EntityManager em2 = emf.createEntityManager();
		em2.getTransaction().begin();

		Author a1 = em1.find(Author.class, 1L, LockModeType.PESSIMISTIC_READ);
		log.info("Transaction 1: " + a1);

		Author a2 = em2.find(Author.class, 1L, LockModeType.PESSIMISTIC_READ);
		log.info("Transaction 2: " + a2);

		log.info("Commit transaction 1");
		em1.getTransaction().commit();
		em1.close();

		log.info("Commit transaction 2");
		em2.getTransaction().commit();
		em2.close();
	}

	@Test
	public void testConcurrentReadAndWrite() throws InterruptedException {

		EntityManager em1 = emf.createEntityManager();
		em1.getTransaction().begin();

		Author a1 = em1.find(Author.class, 1L, LockModeType.PESSIMISTIC_READ);
		log.info("Transaction 1: " + a1);

		// perform update in a 2nd thread
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				EntityManager em2 = emf.createEntityManager();
				em2.getTransaction().begin();

				log.info("Try to select Author with write lock in transaction 2");
				Author a2 = em2.find(Author.class, 1L,
						LockModeType.PESSIMISTIC_WRITE);
				log.info("Update Author in transaction 2");
				a2.setFirstName("changed");

				log.info("Commit transaction 2");
				em2.getTransaction().commit();
				em2.close();
			}
		});
		t.start();

		log.info("Sleep for 3 seconds before committing transaction 1");
		Thread.sleep(3000);

		log.info("Commit transaction 1");
		em1.getTransaction().commit();
		em1.close();

		t.join();
	}

	@Test
	public void testConcurrentWriteAndRead() throws InterruptedException {

		EntityManager em1 = emf.createEntityManager();
		em1.getTransaction().begin();

		log.info("Try to select Author with write lock in transaction 1");
		Author a1 = em1.find(Author.class, 1L, LockModeType.PESSIMISTIC_WRITE);
		log.info("Update Author in transaction 1");
		a1.setFirstName("changed");

		// read Author entity in a 2nd thread
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				EntityManager em2 = emf.createEntityManager();
				em2.getTransaction().begin();

				Author a2 = em2.find(Author.class, 1L,
						LockModeType.PESSIMISTIC_READ);
				log.info("Transaction 2: " + a2);

				log.info("Commit transaction 2");
				em2.getTransaction().commit();
				em2.close();
			}
		});
		t.start();

		log.info("Sleep for 3 seconds before committing transaction 1");
		Thread.sleep(3000);

		log.info("Commit transaction 1");
		em1.getTransaction().commit();
		em1.close();

		t.join();
	}

	@Test
	public void testTimeout() {

		EntityManager em1 = emf.createEntityManager();
		em1.getTransaction().begin();
		EntityManager em2 = emf.createEntityManager();
		em2.getTransaction().begin();

		Author a1 = em1.find(Author.class, 1L, LockModeType.PESSIMISTIC_READ);
		log.info("Transaction 1: " + a1);

		// try to acquire write lock with NOWAIT clause
		log.info("Try to select Author with write lock in transaction 2");
		try {
			Map<String, Object> hints = new HashMap<String, Object>();
			hints.put("jakarta.persistence.lock.timeout", 0);
			
			@SuppressWarnings("unused")
			Author a2 = em2.find(Author.class, 1L,
					LockModeType.PESSIMISTIC_WRITE, hints);

			fail("LockTimeoutException expected");
		} catch (LockTimeoutException e) {
			log.info(e);
			em2.close();
		}

		log.info("Commit transaction 1");
		em1.getTransaction().commit();
		em1.close();
	}
}
