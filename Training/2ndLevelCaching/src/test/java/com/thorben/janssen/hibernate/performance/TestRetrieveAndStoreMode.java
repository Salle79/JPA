package com.thorben.janssen.hibernate.performance;

import java.util.HashMap;
import java.util.Map;

import com.thorben.janssen.hibernate.performance.model.Author;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestRetrieveAndStoreMode {

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
	public void testRetrieveMode() {
		log.info("... testRetrieveMode ...");
		
		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		Author a = em.find(Author.class, 1L);
		log.info(a);
		
		em.getTransaction().commit();
        em.close();
        
        // 2nd session
        log.info("2nd session - CacheRetrieveMode = BYPASS");
        em = emf.createEntityManager();
        em.getTransaction().begin();

		// BYPASS the cache and read the author from the database directly
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("jakarta.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
		a = em.find(Author.class, 1L, props);
		log.info(a);
		
		em.getTransaction().commit();
        em.close();
        
        // 3rd session
        log.info("3rd session - CacheRetrieveMode = USE");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
		// Read the author from the cache
		props = new HashMap<String, Object>();
		props.put("jakarta.persistence.cache.retrieveMode", CacheRetrieveMode.USE);
		a = em.find(Author.class, 1L, props);
		log.info(a);

        em.getTransaction().commit();
        em.close();
	}
    
	@Test
	public void testStoreMode() {
		log.info("... testStoreMode ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		// BYPASS: update entities in the cache but don't add new ones
		em.setProperty("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS);
		
		Author a = new Author();
		a.setId(1000L);
		a.setFirstName("new");
		a.setLastName("author");
		em.persist(a);

        em.getTransaction().commit();
        em.close();
	
		log.info("2nd session - StoreMode = USE");

		em = emf.createEntityManager();
        em.getTransaction().begin();
        
        // USE: add and update entities in the cache
     	em.setProperty("javax.persistence.cache.storeMode", CacheStoreMode.USE);
     	
		a = em.find(Author.class, 1000L);
		log.info(a);

        em.getTransaction().commit();
        em.close();
	
        log.info("3rd session - StoreMode = REFRESH");

		em = emf.createEntityManager();
        em.getTransaction().begin();
        
		// REFRESH: get entities from the database and update them in the cache
		em.setProperty("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
				
		a = em.find(Author.class, 1000L);
		log.info(a);

		em.getTransaction().commit();
		em.close();
	}
}
