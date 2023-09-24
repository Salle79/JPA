package com.thorben.janssen.hibernate.performance;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.thorben.janssen.hibernate.performance.model.Book;
import com.thorben.janssen.hibernate.performance.model.Review;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.StoredProcedureQuery;

public class TestNamedStoredProcedureQuery {

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
	public void calculate() {

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		StoredProcedureQuery query = em.createNamedStoredProcedureQuery("calculate");
		query.setParameter("x", 1.23d);
		query.setParameter("y", 4d);
		query.execute();
		Double sum = (Double) query.getOutputParameterValue("sum");
        assertThat(sum).isEqualTo(5.23);
		log.info("Calculation result: 1.23 + 4 = " + sum);

        em.getTransaction().commit();
        em.close();
	}
	
	@Test
    @SuppressWarnings("unchecked")
	public void getBooks() {

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        StoredProcedureQuery query = em.createNamedStoredProcedureQuery("getBooks");
		List<Book> books = (List<Book>) query.getResultList();
        assertThat(books.size()).isEqualTo(6);
		for (Book b : books) {
			log.info(b.getTitle());
		}

        em.getTransaction().commit();
        em.close();
	}
	
	@Test
    @SuppressWarnings("unchecked")
	public void getReviews() {

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		List<Book> books = (List<Book>) em.createNamedStoredProcedureQuery("getBooks").getResultList();
        assertThat(books.size()).isEqualTo(6);
		for (Book b : books) {
			log.info(b.getTitle());

            StoredProcedureQuery q = em.createNamedStoredProcedureQuery("getReviews");
			q.setParameter(2, b.getId());
			List<Review> reviews = q.getResultList();
            assertThat(reviews.size()).isGreaterThan(0);
			for (Review r : reviews) {
				log.info(r);
			}
		}

        em.getTransaction().commit();
        em.close();
	}
}
