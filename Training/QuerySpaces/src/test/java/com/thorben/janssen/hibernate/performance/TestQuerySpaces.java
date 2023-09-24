package com.thorben.janssen.hibernate.performance;

import com.thorben.janssen.hibernate.performance.model.Author;
import com.thorben.janssen.hibernate.performance.model.Book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.MappingException;
import org.hibernate.jpa.AvailableHints;
import org.hibernate.query.SynchronizeableQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class TestQuerySpaces {

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
    public void queryDifferentEntity() {
        log.info("... queryDifferentEntity ...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Author a = em.find(Author.class, 1L);
        a.setFirstName("Thorben");
        a.setLastName("Janssen");

        log.info("JPQL Query");
        em.createQuery("SELECT b FROM Book b WHERE b.title LIKE '%Tips%'", Book.class)
                .getResultList();

        log.info("Flush and Commit");
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void queryAndPersistEntities() {
        log.info("... queryAndPersistEntities ...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Author a = em.find(Author.class, 1L);
        a.setFirstName("Thorben");
        a.setLastName("Janssen");

        Book b = new Book();
        b.setTitle("test");
        em.persist(b);

        log.info("JPQL query and flush");
        em.createQuery("SELECT b FROM Book b WHERE b.title LIKE '%Tips%'", Book.class)
                .getResultList();

        log.info("Commit");
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void testWithoutQuerySpaceNative() {
        log.info("... testWithoutQuerySpaceNative ...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Author a = em.find(Author.class, 1L);
        a.setFirstName("Thorben");
        a.setLastName("Janssen");

        log.info("Native Query");
        em.createNativeQuery("SELECT * FROM Book b WHERE b.title LIKE '%Tips%'", Book.class)
                .getResultList();

        log.info("Flush and Commit");
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void testQuerySpaceApi() throws MappingException {
        log.info("... testQuerySpaceApi ...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Author a = em.find(Author.class, 1L);
        a.setFirstName("Thorben");
        a.setLastName("Janssen");

        log.info("Native Query");
        Query q = em.createNativeQuery("SELECT * FROM Book b WHERE b.title LIKE '%Tips%'",
                Book.class);
        SynchronizeableQuery hq = q.unwrap(SynchronizeableQuery.class);
        hq.addSynchronizedEntityClass(Book.class);
        q.getResultList();
		
        log.info("Flush and Commit");
		em.getTransaction().commit();
		em.close();
	}

    @Test
    public void testQuerySpaceHint() {
        log.info("... testQuerySpaceHint ...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Author a = em.find(Author.class, 1L);
        a.setFirstName("Thorben");
        a.setLastName("Janssen");

        log.info("Native Query");
        Query q = em.createNativeQuery("SELECT * FROM Book b WHERE b.title LIKE '%Tips%'",
                Book.class);
        q.setHint(AvailableHints.HINT_NATIVE_SPACES, Book.class.getSimpleName());
        q.getResultList();
		
        log.info("Flush and Commit");
		em.getTransaction().commit();
		em.close();
	}

    @Test
    public void testNativeUpdate() {
        log.info("... testQuerySpaceHint ...");

        log.info("Add Book 1 and Author 2 to the cache");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.find(Book.class, 1L);
        em.find(Author.class, 2L);

        em.getTransaction().commit();
		em.close();


        log.info("Perform a native update.");
        em = emf.createEntityManager();
        em.getTransaction().begin();

        Query q = em.createNativeQuery("UPDATE Book SET title = 'changed' WHERE id = 1",
                Book.class);
        q.setHint(AvailableHints.HINT_NATIVE_SPACES, Book.class.getSimpleName());
        q.executeUpdate();
		
        em.getTransaction().commit();
		em.close();

        
        log.info("Get Book 1 and Author 2");
        em = emf.createEntityManager();
        em.getTransaction().begin();

        em.find(Book.class, 1L);
        em.find(Author.class, 2L);
        
        em.getTransaction().commit();
		em.close();

	}
}
