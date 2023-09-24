package com.thorben.janssen.hibernate.performance;

import java.util.List;

import com.thorben.janssen.hibernate.performance.model.Author;
import com.thorben.janssen.hibernate.performance.model.Author_;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;

public class TestBulkOperations {

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
	public void testEntityUpdate() {
		log.info("... testEntityUpdate ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		long start = System.currentTimeMillis();
		List<Author> authors = em.createQuery("SELECT a FROM Author a WHERE a.id >= 3", Author.class).getResultList();
		for (Author a : authors) {
			a.setFirstName(a.getFirstName() + " - updated");
		}
		
		authors = em.createQuery("SELECT a FROM Author a WHERE id <100 AND id > 90", Author.class).getResultList();
		long end = System.currentTimeMillis();
		log.info("Update took "+(end-start)+"ms");
		for (Author author : authors) {
			log.info(author);
		}

        em.getTransaction().commit();
        em.close();
	}
    
    @Test
	public void testJPQLUpdate() {
		log.info("... testJPQLUpdate ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		long start = System.currentTimeMillis();
		
		Query q = em.createQuery("UPDATE Author a SET a.firstName = CONCAT(a.firstName ,' - updated') WHERE a.id >= 3");
		q.executeUpdate();
		
		List<Author> authors = em.createQuery("SELECT a FROM Author a WHERE id <100 AND id > 90", Author.class).getResultList();
		long end = System.currentTimeMillis();
		log.info("Update took "+(end-start)+"ms");
		for (Author author : authors) {
			log.info(author);
		}

        em.getTransaction().commit();
        em.close();
	}
    
    @Test
	public void testJPQLDelete() {
		log.info("... testJPQLDelete ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        em.createQuery("DELETE Author a WHERE a.id >= 5").executeUpdate();
		
		List<Author> authors = em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
		for (Author a : authors) {
			log.info(a);
		}

        em.getTransaction().commit();
        em.close();
	}
    	
	@Test
	public void testCriteriaUpdate() {
		log.info("... testCriteriaUpdate ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		long start = System.currentTimeMillis();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		// create update
		CriteriaUpdate<Author> update = cb.createCriteriaUpdate(Author.class);

		// set the root class
		Root<Author> a = update.from(Author.class);

		// set update and where clause
		update.set(Author_.firstName, cb.concat(a.get(Author_.firstName), " - updated"));
		update.where(cb.greaterThanOrEqualTo(a.get(Author_.id), 3L));

		// perform update
		Query q = em.createQuery(update);
		q.executeUpdate();
		
		List<Author> authors = em.createQuery("SELECT a FROM Author a WHERE id <100 AND id > 90", Author.class).getResultList();
		long end = System.currentTimeMillis();
		log.info("Update took "+(end-start)+"ms");
		for (Author author : authors) {
			log.info(author);
		}

        em.getTransaction().commit();
        em.close();
	}
	
	@Test
	public void testCriteriaDelete() {
		log.info("... testCriteriaDelete ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		CriteriaBuilder cb = em.getCriteriaBuilder();

		// create CriteriaDelete
		CriteriaDelete<Author> delete = cb.createCriteriaDelete(Author.class);

		// set the root class
		Root<Author> e = delete.from(Author.class);

		// set the where clause
		delete.where(cb.greaterThanOrEqualTo(e.get(Author_.id), 5L));

		// perform delete
		em.createQuery(delete).executeUpdate();
		
		List<Author> authors = em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
		for (Author a : authors) {
			log.info(a);
		}

        em.getTransaction().commit();
        em.close();
	}
	
	@Test
	public void testOutdatedPersistenceContext() {
		log.info("... testJPQLUpdate ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Author a = em.find(Author.class, 1L);

        Query q = em.createQuery("UPDATE Author a SET a.firstName = 'Changed first name' WHERE a.id = 1");
		q.executeUpdate();
		
		log.info("Before em.find: "+a);
		
		a = em.find(Author.class, 1L);
		log.info("After em.find: "+a);

		a = em.createQuery("SELECT a FROM Author a WHERE a.id = 1", Author.class).getSingleResult();
		log.info("After query: "+a);
		
		em.detach(a);
		
		a = em.find(Author.class, 1L);
		log.info("After detach: "+a);

        em.getTransaction().commit();
        em.close();
	}
}
