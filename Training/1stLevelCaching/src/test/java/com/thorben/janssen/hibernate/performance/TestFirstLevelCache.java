package com.thorben.janssen.hibernate.performance;

import com.thorben.janssen.hibernate.performance.model.Author;
import com.thorben.janssen.hibernate.performance.model.AuthorValue;
import com.thorben.janssen.hibernate.performance.model.Book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestFirstLevelCache {
	
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
	public void testEMFind() {
		log.info("... testEMFind ...");
		
		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
		
		for (int i = 0; i < 2; i++) {
			log.info("Iteration: "+i);
			
			Author a = em.find(Author.class, 1L);
			log.info(a);
			
			for (Book b : a.getBooks()) {
				log.info(b);
			}
		}
		
        em.getTransaction().commit();
        em.close();
	}

    @Test
	public void test2sessions() {
		log.info("... test2sessions ...");
		
		EntityManager em1 = emf.createEntityManager();
        em1.getTransaction().begin();
		Author a1 = em1.find(Author.class, 1L);
		log.info(a1);
		
		EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
		Author a2 = em2.find(Author.class, 1L);
		log.info(a2);
		
        em1.getTransaction().commit();
        em1.close();
        em2.getTransaction().commit();
        em2.close();
	}
	
	@Test
	public void testJPQLQuery() {
		log.info("... testJPQLQuery ...");
		
		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		for (int i = 0; i < 2; i++) {
			log.info("Iteration: "+i);
			
			Book b = em.createQuery("SELECT b FROM Book b WHERE id = ?1", Book.class).setParameter(1, 1L).getSingleResult();
			log.info(b);
		}
		
        em.getTransaction().commit();
        em.close();
	}
	
	@Test
	public void testConstructorQuery() {
		log.info("... testConstructorQuery ...");
		
		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		for (int i = 0; i < 2; i++) {
			log.info("Iteration: "+i);
			
			AuthorValue a1 = em.createQuery("""
                    SELECT new com.thorben.janssen.hibernate.performance.model.AuthorValue(a.id, 
                                                                                           a.version, 
                                                                                           a.firstName, 
                                                                                           a.lastName) 
                    FROM Author a WHERE id = ?1""", AuthorValue.class)
                .setParameter(1, 1L)
                .getSingleResult();
			log.info(a1);
		}
		
        em.getTransaction().commit();
        em.close();
	}
}
