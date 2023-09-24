package com.thorben.janssen.hibernate.performance.materialized;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.thorben.janssen.hibernate.performance.model.materialized.Book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestMaterializedLOBs {

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
	public void materializedLobs() {
		log.info("... materializedLobs ...");
	
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Book b = new Book();
		b.setTitle("Hibernate Tips - More than 70 solutions to common Hibernate problems");
		b.setCover(getCover());
		b.setContent("This is a veeeery loooong text with almost all the content that you can find in the book ;)");
		em.persist(b);
		
		em.getTransaction().commit();
		em.close();
		
		em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Book b2 = em.find(Book.class, b.getId());
		assertThat(b.getCover()).isEqualTo(b2.getCover());
		assertThat(b.getContent()).isEqualTo(b2.getContent());
		
		em.getTransaction().commit();
		em.close();
	}
	
	public byte[] getCover() {
		File file = new File("cover.jpg");
		FileInputStream fis = null;
        byte[] bArray = new byte[(int) file.length()];
        try{
            fis = new FileInputStream(file);
            fis.read(bArray);
            fis.close();        
            
        }catch(IOException ioExp){
            ioExp.printStackTrace();
        }
        return bArray;
	}
}
