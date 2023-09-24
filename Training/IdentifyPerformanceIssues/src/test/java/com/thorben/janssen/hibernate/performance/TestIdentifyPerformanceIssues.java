package com.thorben.janssen.hibernate.performance;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.thorben.janssen.hibernate.performance.model.Author;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.stat.QueryStatistics;
import org.hibernate.stat.Statistics;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestIdentifyPerformanceIssues {

	Logger log = LogManager.getLogger(this.getClass().getName());

	private EntityManagerFactory emf;

	private Session s;

	@Before
	public void init() {
		emf = Persistence.createEntityManagerFactory("my-persistence-unit");
	}

	@After
	public void close() {
		emf.close();
	}

	@Test
	public void selectAuthors() {
		log.info("... selectAuthors ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		List<Author> authors = em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
		for (Author author : authors) {
			log.info(author + " has written " + author.getBooks().size() + " books.");
		}

		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void statisticsApi() {
		log.info("... statisticsApi ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		List<Author> authors = em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
		for (Author author : authors) {
			log.info(author + " has written " + author.getBooks().size() + " books.");
		}

        s = em.unwrap(Session.class);
		Statistics stat = s.getSessionFactory().getStatistics();
        		
        // Cache statistics
        log.info("Cache hit count: "+stat.getSecondLevelCacheHitCount());
        log.info("Cache put count: "+stat.getSecondLevelCachePutCount());
        log.info("Cache miss count: "+stat.getSecondLevelCacheMissCount());

        // Query statistics
        log.info("Slowest query: "+stat.getQueryExecutionMaxTimeQueryString());
		assertThat(stat.getQueryExecutionMaxTimeQueryString()).isEqualTo("SELECT a FROM Author a");
        
		QueryStatistics queryStat = stat.getQueryStatistics("SELECT a FROM Author a");
		log.info("Execution count: "+queryStat.getExecutionCount());
		assertThat(queryStat.getExecutionCount()).isEqualTo(1L);
        log.info("Average execution time: "+queryStat.getExecutionAvgTime());
        log.info("Min execution time: "+queryStat.getExecutionMinTime());
        log.info("Max execution time: "+queryStat.getExecutionMaxTime());
        log.info("Query plan compilation time: "+queryStat.getPlanCompilationTotalMicroseconds());
        log.info("Query plan cache hits: "+queryStat.getPlanCacheHitCount());

        em.getTransaction().commit();
        em.close();
	}
}

