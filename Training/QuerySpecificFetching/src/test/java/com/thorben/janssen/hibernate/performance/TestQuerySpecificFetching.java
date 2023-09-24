package com.thorben.janssen.hibernate.performance;

import java.util.List;
import java.util.stream.Collectors;

import com.thorben.janssen.hibernate.performance.model.Author;
import com.thorben.janssen.hibernate.performance.model.Author_;
import com.thorben.janssen.hibernate.performance.model.Book;
import com.thorben.janssen.hibernate.performance.model.Book_;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.graph.EntityGraphs;
import org.hibernate.graph.GraphParser;
import org.hibernate.graph.RootGraph;
import org.hibernate.jpa.AvailableHints;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Subgraph;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public class TestQuerySpecificFetching {

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
	public void testAnnotatedRelation() {
		log.info("... annotatedRelation ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		List<Author> authors = em.createQuery("SELECT a FROM Author a", Author.class).getResultList();

        writeAuthorMessage(authors);

        em.getTransaction().commit();
        em.close();
	}

	@Test
	public void testFetchJoinJPQL() {
		log.info("... fetchJoinJPQL ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		List<Author> authors = em.createQuery(
				"SELECT a FROM Author a JOIN FETCH a.books b",
				Author.class).getResultList();

        em.getTransaction().commit();
        em.close();

        writeAuthorMessage(authors);
	}

    // Only required for Hibernate < 6.0.0
    @Test
	public void testFetchJoinJPQLDistinct() {
		log.info("... testFetchJoinJPQLDistinct ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		List<Author> authors = em.createQuery(
				"SELECT DISTINCT a FROM Author a JOIN FETCH a.books b", Author.class)
                // .setHint(org.hibernate.annotations.QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();

        em.getTransaction().commit();
        em.close();

        writeAuthorMessage(authors);
	}

	@Test
	public void testFetchJoinCriteria() {
		log.info("... fetchJoinCriteria ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Author> q = cb.createQuery(Author.class);
		Root<Author> author = q.from(Author.class);
		author.fetch(Author_.books, JoinType.INNER);
		q.select(author);

		List<Author> authors = em.createQuery(q).getResultList();

        em.getTransaction().commit();
        em.close();

        writeAuthorMessage(authors);
	}

	@Test
	public void testNamedFetchGraphAuthorBooks() {
		log.info("... namedFetchGraphAuthorBooks ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		EntityGraph<?> graph = em.getEntityGraph("graph.AuthorBooks");

		List<Author> authors = em
				.createQuery("SELECT a FROM Author a", Author.class)
				.setHint(AvailableHints.HINT_SPEC_FETCH_GRAPH, graph)
                .getResultList();

        em.getTransaction().commit();
        em.close();

        writeAuthorMessage(authors);
	}

    @Test
	public void testNamedLoadGraphAuthorBooks() {
		log.info("... testNamedLoadGraphAuthorBooks ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		EntityGraph<?> graph = em.getEntityGraph("graph.AuthorBooks");

		List<Author> authors = em
				.createQuery("SELECT a FROM Author a", Author.class)
				.setHint(AvailableHints.HINT_SPEC_FETCH_GRAPH, graph)
                .getResultList();

        em.getTransaction().commit();
        em.close();

        writeAuthorMessage(authors);
	}

	@Test
	public void testNamedFetchGraphAuthorBooksReviews() {
		log.info("... namedFetchGraphAuthorBooksReviews ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		EntityGraph<?> graph = em.getEntityGraph("graph.AuthorBooksReviews");

		List<Author> authors = em
				.createQuery("SELECT a FROM Author a", Author.class)
				.setHint(AvailableHints.HINT_SPEC_FETCH_GRAPH, graph)
                .getResultList();

        em.getTransaction().commit();
        em.close();

        writeAuthorMessageWithReviews(authors);
	}
	
	@Test
    @SuppressWarnings("rawtypes")
	public void fetchGraphAuthorBooksReviews() {
		log.info("... fetchGraphAuthorBooksReviews ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		EntityGraph graph = em.createEntityGraph(Author.class);
		Subgraph bookSubGraph = graph.addSubgraph(Author_.books);
		bookSubGraph.addAttributeNodes(Book_.reviews);

		List<Author> authors = em
				.createQuery("SELECT a FROM Author a", Author.class)
				.setHint(AvailableHints.HINT_SPEC_FETCH_GRAPH, graph)
                .getResultList();

        em.getTransaction().commit();
        em.close();

        writeAuthorMessageWithReviews(authors);
	}

    @Test
	public void parseFetchGraphAuthorBooksReviews() {
		log.info("... parseFetchGraphAuthorBooksReviews ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        RootGraph<Author> graph = GraphParser.parse(Author.class, "books(reviews)", em);
		
		List<Author> authors = em
				.createQuery("SELECT a FROM Author a", Author.class)
				.setHint(AvailableHints.HINT_SPEC_FETCH_GRAPH, graph)
                .getResultList();

        em.getTransaction().commit();
        em.close();

        writeAuthorMessageWithReviews(authors);
	}

    @Test
    public void mergeFetchGraphBooksReviewsAuthors() {
		log.info("... mergeFetchGraphBooksReviewsAuthors ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        RootGraph<Book> graph1 = GraphParser.parse(Book.class, "reviews", em);
        RootGraph<Book> graph2 = GraphParser.parse(Book.class, "authors", em);
        EntityGraph<Book> graph = EntityGraphs.merge(em, Book.class, graph1, graph2);

		List<Book> books = em
				.createQuery("SELECT b FROM Book b", Book.class)
				.setHint(AvailableHints.HINT_SPEC_FETCH_GRAPH, graph)
                .getResultList();

        em.getTransaction().commit();
        em.close();

        for (Book b : books) {
            log.info("The book " + b.getTitle() + " was written by "
                    + b.getAuthors().stream().map(a -> a.getFirstName() + " " + a.getLastName()).collect(Collectors.joining(", "))
                    + " and has " + b.getReviews().size() + " reviews.");
        }
	}
	
	private void writeAuthorMessage(List<Author> authors) {
		for (Author a : authors) {
			log.info("Author " + a.getFirstName() + " " + a.getLastName() + " wrote "
				+ a.getBooks().stream().map(b -> b.getTitle()).collect(Collectors.joining(", ")));
		}
	}
	
	private void writeAuthorMessageWithReviews(List<Author> authors) {
		for (Author a : authors) {
			log.info("Author " + a.getFirstName() + " " + a.getLastName() + " wrote "
				+ a.getBooks().stream().map(b -> b.getTitle() + "(" + b.getReviews().size() + " reviews)").collect(Collectors.joining(", ")));
		}
	}
}
