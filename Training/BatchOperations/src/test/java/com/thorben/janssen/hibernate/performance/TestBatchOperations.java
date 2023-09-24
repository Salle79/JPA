package com.thorben.janssen.hibernate.performance;

import java.util.HashSet;
import java.util.List;

import com.thorben.janssen.hibernate.performance.model.Author;
import com.thorben.janssen.hibernate.performance.model.Book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestBatchOperations {

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

		for (int i = 1; i <= 10; i++) {
			Author a = new Author();
			a.setFirstName("FirstName" + i);
			a.setLastName("LastName" + i);
			em.persist(a);

			if (i % 5 == 0) {
				em.flush();
				em.clear();
			}
		}

		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testInsertAuthorsBooks() {
		log.info("... testInsertAuthorsBooks ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		for (int i = 0; i < 10; i++) {
			Author a = new Author();
			a.setFirstName("FirstName" + i);
			a.setLastName("LastName" + i);
			em.persist(a);

			Book b = new Book();
			b.setTitle("Title" + i);
			HashSet<Author> bookAuthors = new HashSet<>();
			bookAuthors.add(a);
			b.setAuthors(bookAuthors);
			em.persist(b);
		}

		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testUpdateAuthorsBooks() {
		log.info("... testUpdateAuthorsBooks ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		List<Author> authors = em.createQuery(
						"SELECT a FROM Author a JOIN FETCH a.books b JOIN FETCH b.publisher",
						Author.class).getResultList();
		for (Author a : authors) {
			a.setFirstName(a.getFirstName() + " - updated");
			a.getBooks().forEach(b -> b.setTitle(b.getTitle() + " - updated"));
		}

		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void testDeleteAuthorsWithoutBatch() {
		log.info("... testDeleteAuthorsWithoutBatch ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		List<Author> authors = em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
		for (Author a : authors) {
			em.remove(a);
		}

		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testDeleteAuthorsWithBatch() {
		log.info("... testDeleteAuthorsWithBatch ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		em.createQuery("DELETE Review r").executeUpdate();
		em.createQuery("DELETE Book b").executeUpdate();

		List<Author> authors = em.createQuery("SELECT a FROM Author a LEFT JOIN FETCH a.books", Author.class).getResultList();
		for (Author a : authors) {
			em.remove(a);
		}

		em.getTransaction().commit();
		em.close();
	}
}
