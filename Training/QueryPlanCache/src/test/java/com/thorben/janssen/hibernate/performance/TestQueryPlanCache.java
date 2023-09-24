package com.thorben.janssen.hibernate.performance;

import com.thorben.janssen.hibernate.performance.model.Author;
import com.thorben.janssen.hibernate.performance.model.Book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestQueryPlanCache {

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
    public void testJpqlQuery() {
        log.info("... testJpqlQuery ...");

        EntityManager em = emf.createEntityManager();
        Statistics stats = emf.unwrap(SessionFactory.class).getStatistics();
        stats.clear();
        em.getTransaction().begin();

        for (int i = 0; i < 1000; i++) {
            em.createQuery("SELECT a FROM Author a", Author.class);

            em.createQuery("SELECT b FROM Book b", Book.class);
        }

        long totalCompilationTime = 0;
        for (String query : stats.getQueries()) {
            totalCompilationTime += stats.getQueryStatistics(query).getPlanCompilationTotalMicroseconds();
            log.info("-------------------------------------");
            log.info("Query: " + query);
            log.info("Compilation time in ms: " + stats.getQueryStatistics(query).getPlanCompilationTotalMicroseconds());
            log.info("Cache miss count: " + stats.getQueryStatistics(query).getPlanCacheMissCount());
            log.info("Cache hit count: " + stats.getQueryStatistics(query).getPlanCacheHitCount());
        }
        log.info("=====================================");
        log.info("Total compilation time in ms: "+totalCompilationTime);
        log.info("Total miss count: " + stats.getQueryPlanCacheMissCount());
        log.info("Total hit count: " + stats.getQueryPlanCacheHitCount());

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void testNativeQuery() {
        log.info("... testNativeQuery ...");

        EntityManager em = emf.createEntityManager();
        Statistics stats = emf.unwrap(SessionFactory.class).getStatistics();
        stats.clear();
        em.getTransaction().begin();

        long total = 0;
        for (int i = 0; i < 1000; i++) {
            long start = System.nanoTime();
            em.createNativeQuery("SELECT * FROM Author a", Author.class);

            em.createNativeQuery("SELECT * FROM Book b", Book.class);
            long end = System.nanoTime();
            total += end-start;
        }
        log.info("Total compilation time in ms: "+total/1000000);
        log.info("Total miss count: " + stats.getQueryPlanCacheMissCount());
        log.info("Total hit count: " + stats.getQueryPlanCacheHitCount());
        
        em.getTransaction().commit();
        em.close();
    }
}
