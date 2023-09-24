package com.thorben.janssen.hibernate.performance;

import java.util.Arrays;
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
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class TestBindParameter {

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
	public void testNativeSqlQueryPositionalParameter() {
		log.info("... testNativeSqlQuery ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Query q = em.createNativeQuery("SELECT a.* FROM author a WHERE a.id = ?", Author.class);
		q.setParameter(1, 1L);
		Author author = (Author) q.getSingleResult();
		
		log.info(author.getFirstName()+" "+author.getLastName());

		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testJPQLPositionalParameter() {
		log.info("... testJPQLPositionalParameter ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		TypedQuery<Author> q = em.createQuery("SELECT a FROM Author a WHERE a.id = ?1", Author.class);
		q.setParameter(1, 1L);
		Author a = q.getSingleResult();
		
		log.info(a);

		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void testJPQLNamedParameter() {
		log.info("... testJPQLNamedParameter ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		TypedQuery<Author> q = em.createQuery("SELECT a FROM Author a WHERE a.id = :id", Author.class);
		q.setParameter("id", 1L);
		Author a = q.getSingleResult();
		
		log.info(a);

		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void testCriteriaNamedParameter() {
		log.info("... testCriteriaNamedParameter ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<Author> q = qb.createQuery(Author.class);
		Root<Author> r = q.from(Author.class);
		q.select(r);
		q.where(qb.equal(r.get(Author_.id), qb.parameter(Long.class, "id")));
		
		TypedQuery<Author> query = em.createQuery(q);
		query.setParameter("id", 1L);
		Author a = (Author) query.getSingleResult();
		
		log.info(a);

		em.getTransaction().commit();
		em.close();
	}

    @Test
	public void testParameterPadding() {
		log.info("... testParameterPadding ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		TypedQuery<Author> q = em.createQuery("SELECT a FROM Author a WHERE a.id IN (:id)", Author.class);
		q.setParameter("id", Arrays.asList(new Long[]{1L, 2L, 3L}));
		List<Author> authors = q.getResultList();
		
        for (Author a : authors) {
		    log.info(a);
        }

		em.getTransaction().commit();
		em.close();
	}
}
