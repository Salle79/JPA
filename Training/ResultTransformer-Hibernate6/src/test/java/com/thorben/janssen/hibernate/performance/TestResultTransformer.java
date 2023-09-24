package com.thorben.janssen.hibernate.performance;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import com.thorben.janssen.hibernate.performance.model.BookPublisherValue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.TupleTransformer;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestResultTransformer {

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
    public void AliasToBeanResultTransformer() {
        log.info("... AliasToBeanResultTransformer ...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Session session = em.unwrap(Session.class);

        BookPublisherValue bpv = (BookPublisherValue) session
                .createQuery("SELECT b.title as title, b.publisher.name as publisher FROM Book b WHERE id = 1", Object[].class)
                .setTupleTransformer(new AliasToBeanResultTransformer<BookPublisherValue>(BookPublisherValue.class)).getSingleResult();
        assertThat(bpv.getTitle()).isEqualTo("Effective Java");
        assertThat(bpv.getPublisher()).isEqualTo("Addison Wesley");

        log.info(bpv.getTitle() + " was published by " + bpv.getPublisher());

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void AliasToEntityMapResultTransformer() {
        log.info("... AliasToEntityMapResultTransformer ...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Session session = em.unwrap(Session.class);

        Map<String, Object> bpv = (Map<String, Object>) session
                .createQuery("SELECT b.title as title, b.publisher.name as publisher FROM Book b WHERE id = 1", Object[].class)
                .setTupleTransformer(AliasToEntityMapResultTransformer.INSTANCE)
                .getSingleResult();
        assertThat(bpv.get("title")).isEqualTo("Effective Java");
        assertThat(bpv.get("publisher")).isEqualTo("Addison Wesley");

        log.info(bpv.get("title") + " was published by " + bpv.get("publisher"));

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void testCustomTupleTransformer() {
        log.info("... testCustomTupleTransformer ...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Session session = em.unwrap(Session.class);

        // BookPublisherValue bpv = (BookPublisherValue) session
        //         .createQuery("SELECT b.title as title, b.publisher.name as publisher FROM Book b WHERE id = 1", Object[].class)
        //         .setTupleTransformer(new TupleTransformer<BookPublisherValue>() {
        //             Logger log = LogManager.getLogger(this.getClass().getName());

        //             @Override
        //             public BookPublisherValue transformTuple(Object[] tuple, String[] aliases) {
        //                 log.info("Transform tuple");
        //                 BookPublisherValue bpv = new BookPublisherValue();
        //                 bpv.setTitle((String) tuple[0]);
        //                 bpv.setPublisher((String) tuple[1]);
        //                 return bpv;
        //             }
        //         }).getSingleResult();
        BookPublisherValue bpv = (BookPublisherValue) session
                .createQuery("SELECT b.title as title, b.publisher.name as publisher FROM Book b WHERE id = 1", Object[].class)
                .setTupleTransformer((tuple, aliases) -> {
                        log.info("Transform tuple");
                        BookPublisherValue v = new BookPublisherValue();
                        v.setTitle((String) tuple[0]);
                        v.setPublisher((String) tuple[1]);
                        return v;
                }).getSingleResult();
        assertThat(bpv.getTitle()).isEqualTo("Effective Java");
        assertThat(bpv.getPublisher()).isEqualTo("Addison Wesley");

        log.info(bpv.getTitle() + " was published by " + bpv.getPublisher());

        em.getTransaction().commit();
        em.close();
    }
}
