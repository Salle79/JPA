package com.thorben.janssen.hibernate.performance;

import static org.assertj.core.api.Assertions.assertThat;

import com.thorben.janssen.hibernate.performance.model.Author;
import com.thorben.janssen.hibernate.performance.model.BookPublisherValue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestNativeQuery {

	private Logger log = LogManager.getLogger(this.getClass().getName());
	
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
	public void testAuthorEntityMapping() {
		log.info("... testAuthorEntityMapping ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		Author a = (Author) em.createNativeQuery("SELECT * FROM Author a WHERE a.id = 1", Author.class).getSingleResult();
		log.info(a.getClass().getName() + " - " + a);

        em.getTransaction().commit();
        em.close();
	}
	
	@Test
	public void testAuthorEntityBookCountMapping() {
		log.info("... testAuthorEntityBookCountMapping ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		Object[] o = (Object[]) em.createNativeQuery("""
                                                        SELECT a.*, count(b.bookid) as bookCount 
                                                        FROM Author a JOIN BookAuthor b ON b.authorid = a.id 
                                                        WHERE a.id = 1 
                                                        GROUP BY a.id, a.firstName, a.lastName, a.version
                                                    """, 
                                                    "AuthorBookCount").getSingleResult();
        assertThat(o[0]).isInstanceOf(Author.class);
        assertThat(o[1]).isInstanceOf(Long.class);
		log.info(o[0] + " wrote " + o[1] + " book(s)");

        em.getTransaction().commit();
        em.close();
	}
	
	@Test
	public void testBookPublisherMapping() {
		log.info("... testBookPublisherMapping ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		BookPublisherValue v = (BookPublisherValue) em.createNativeQuery("""
                                                                            SELECT b.title, p.name 
                                                                            FROM Book b JOIN Publisher p ON b.publisherid = p.id 
                                                                            WHERE b.id = 1
                                                                        """, 
                                                                        "BookPublisher").getSingleResult();
		log.info(v.getClass().getName() + " - " + v.getPublisher() + " published " + v.getTitle());

        em.getTransaction().commit();
        em.close();
	}
}
