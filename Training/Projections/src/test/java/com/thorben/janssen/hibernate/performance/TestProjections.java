package com.thorben.janssen.hibernate.performance;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thorben.janssen.hibernate.performance.model.Author;
import com.thorben.janssen.hibernate.performance.model.Author_;
import com.thorben.janssen.hibernate.performance.model.Book;
import com.thorben.janssen.hibernate.performance.model.BookPublisherValue;
import com.thorben.janssen.hibernate.performance.model.BookValue;
import com.thorben.janssen.hibernate.performance.model.Book_;
import com.thorben.janssen.hibernate.performance.model.Publisher;
import com.thorben.janssen.hibernate.performance.model.Publisher_;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jpa.AvailableHints;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

public class TestProjections {

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
	public void testSelectEntitiesJPQL() {
		log.info("... testSelectEntitiesJPQL ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		List<Author> authors = em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
        assertThat(authors.size()).isEqualTo(11);
        for (Author a : authors) {
            log.info(a);
        }

		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testSelectEntitiesCriteria() {
		log.info("... testSelectEntitiesCriteria ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Author> q = cb.createQuery(Author.class);
		Root<Author> author = q.from(Author.class);
		q.select(author);

		List<Author> authors = em.createQuery(q).getResultList();
        assertThat(authors.size()).isEqualTo(11);
        for (Author a : authors) {
            log.info(a);
        }

		em.getTransaction().commit();
		em.close();
	}

    @Test
    public void testSelectEntityReadOnly() {
        log.info("... testSelectEntityReadOnly ...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a WHERE id = :id", Author.class);
        // query.setHint("org.hibernate.readOnly", true);
        query.setHint(AvailableHints.HINT_READ_ONLY, true);
        query.setParameter("id", 1L);
        Author a = query.getSingleResult();

        a.setFirstName("A changed firstName");
        
        em.getTransaction().commit();
        em.close();
	}

    @Test
    public void testFindEntityReadOnly() {
        log.info("... testFindEntityReadOnly ...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Map<String, Object> hints = new HashMap<>();
        hints.put(AvailableHints.HINT_READ_ONLY, true);
        em.find(Author.class, 1L, hints);
        
        em.getTransaction().commit();
        em.close();
	}

	@Test
	public void testSelectScalarValuesJPQL() {
		log.info("... testSelectScalarValuesJPQL ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		List<Object[]> authorNames = em.createQuery("SELECT a.firstName, a.lastName FROM Author a", Object[].class).getResultList();
        assertThat(authorNames.size()).isEqualTo(11);

		for (Object[] authorName : authorNames) {
			log.info(authorName[0] + " " + authorName[1]);
		}

		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testSelectScalarValuesCriteria() {
		log.info("... testSelectScalarValuesCriteria ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> q = cb.createTupleQuery();
		Root<Author> author = q.from(Author.class);
		Path<String> firstName = author.get(Author_.firstName);
		q.multiselect(firstName, author.get(Author_.lastName).alias("lastName"));

		List<Tuple> authorNames = em.createQuery(q).getResultList();
        assertThat(authorNames.size()).isEqualTo(11);
		for (Tuple authorName : authorNames) {
			log.info(authorName.get(firstName) + " " + authorName.get("lastName"));
		}

		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testSelectDTOsJPQL() {
		log.info("... testSelectDTOsJPQL ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		List<BookPublisherValue> bookPublisherValues = em.createQuery("""
                SELECT new com.thorben.janssen.hibernate.performance.model.BookPublisherValue(
                                    b.title, 
                                    b.publisher.name) 
                FROM Book b""",
				BookPublisherValue.class).getResultList();
        assertThat(bookPublisherValues.size()).isEqualTo(6);

		for (BookPublisherValue bookPublisherValue : bookPublisherValues) {
			log.info(bookPublisherValue.getTitle() + " was published by " + bookPublisherValue.getPublisher());
		}

		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testSelectDTOsCriteria() {
		log.info("... testSelectDTOsCriteria ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BookPublisherValue> q = cb.createQuery(BookPublisherValue.class);
		Root<Book> book = q.from(Book.class);
		Join<Book, Publisher> publisher = book.join(Book_.publisher, JoinType.LEFT);
		q.select(cb.construct(BookPublisherValue.class, book.get(Book_.title), publisher.get(Publisher_.name)));

		List<BookPublisherValue> bookPublisherValues = em.createQuery(q).getResultList();
        assertThat(bookPublisherValues.size()).isEqualTo(6);

		for (BookPublisherValue bookPublisherValue : bookPublisherValues) {
			log.info(bookPublisherValue.getTitle() + " was published by " + bookPublisherValue.getPublisher());
		}

		em.getTransaction().commit();
		em.close();
	}

    @Test
	public void testPerformance() {
        log.info("... testPerformance ...");
        
        warmup();

        // Execute 1000 queries that fetch 1 DTO object
		long startDto = System.currentTimeMillis();
		for (int i = 0; i<1000; i++) {
            selectDto();
        }
        long endDto = System.currentTimeMillis();
		log.info("Fetching DTOs 1000 times: "+(endDto-startDto)+"ms");

        // Execute 1000 queries that fetch 1 entity object
        long startEntity = System.currentTimeMillis();
		for (int i = 0; i<1000; i++) {
            selectEntity();
        }
        long endEntity = System.currentTimeMillis();
		log.info("Fetching entities 1000 times: "+(endEntity-startEntity)+"ms");

        // Execute 1000 queries that fetch 1 read-only entity object
        long startEntityReadOnly = System.currentTimeMillis();
		for (int i = 0; i<1000; i++) {
            selectEntityReadOnly();
        }
        long endEntityReadOnly = System.currentTimeMillis();
		log.info("Fetching read-only entities 1000 times: "+(endEntityReadOnly-startEntityReadOnly)+"ms");
    }

    private void warmup() {
        for (int i = 0; i<1000; i++) {
            selectDto();
        }
    }

	private void selectDto() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        TypedQuery<BookValue> query = em.createQuery("SELECT new com.thorben.janssen.hibernate.performance.model.BookValue(b.id, b.version, b.title, b.publishingDate, b.publisher.id) FROM Book b",
                                                                BookValue.class);
        query.getResultList();
        
        em.getTransaction().commit();
        em.close();
	}
	
	private void selectEntity() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
        query.getResultList();
        
        em.getTransaction().commit();
        em.close();
	}
	
	private void selectEntityReadOnly() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
        query.setHint(AvailableHints.HINT_READ_ONLY, true);
        query.getResultList();
        
        em.getTransaction().commit();
        em.close();
	}
}
