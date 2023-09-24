package com.thorben.janssen.hibernate.performance;

import java.util.List;

import com.thorben.janssen.hibernate.performance.model.Author;
import com.thorben.janssen.hibernate.performance.model.Book;
import com.thorben.janssen.hibernate.performance.model.Publisher;
import com.thorben.janssen.hibernate.performance.model.Review;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestFetchStrategies {

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
	public void selectAuthors() {
		log.info("... selectAuthors ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		List<Author> authors = em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
		for (Author author : authors) {
			log.info(author);
		}
		
        em.getTransaction().commit();
        em.close();
	}
	
	@Test
	public void selectBooks() {
		log.info("... selectBooks ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		List<Book> books = em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
		for (Book book : books){
			log.info("Book: "+book);
			log.info("Instance of Publisher? " + (book.getPublisher() instanceof Publisher));
			log.info(book.getPublisher().getClass());
			log.info("Publisher: "+book.getPublisher().getName());
		}

        em.getTransaction().commit();
        em.close();
	}
	
	@Test
	public void selectReviews() {
		log.info("... selectReviews ...");
		
		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
		Book book = em.createQuery("SELECT b FROM Book b WHERE b.id = 1", Book.class).getSingleResult();
		
		log.info("Get number of reviews");
		log.info(book.getTitle()+" has "+book.getReviews().size()+" reviews.");
		
		log.info("Check if a specific review is associated with this Book");
		log.info("Contains review with id=1? " + book.getReviews().contains(em.find(Review.class, 1L)));
		
		log.info("Get a review by index");
		Review r = book.getReviews().get(2);
		log.info("Rating: " + r.getRating() + " stars\nComment: " + r.getComment());

        em.getTransaction().commit();
        em.close();
	}
	
	@Test
	public void selectBook() {
		log.info("... selectBooks ...");

		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Book book = em.find(Book.class, 1L);
		log.info("Book: "+book);
		log.info("Publisher: "+book.getPublisher().getName());

        em.getTransaction().commit();
        em.close();
	}
}
