package com.thorben.janssen.hibernate.performance.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import com.thorben.janssen.hibernate.performance.model.jdbc.Book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.engine.jdbc.BlobProxy;
import org.hibernate.engine.jdbc.ClobProxy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestLocatorLOBs {

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
	public void locatorLobs() throws SQLException {
		log.info("... locatorLobs ...");
	
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Book b = new Book();
		b.setTitle("Hibernate Tips - More than 70 solutions to common Hibernate problems");
		b.setCover(BlobProxy.generateProxy(getCover()));
		b.setContent(ClobProxy.generateProxy("This is a veeeery loooong text with almost all the content that you can find in the book ;)"));
		em.persist(b);
		
		em.getTransaction().commit();
		em.close();
		
		em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Book b2 = em.find(Book.class, b.getId());		
		assertThat(b.getCover().getBytes(1, Long.valueOf(b.getCover().length()).intValue())).isEqualTo(b2.getCover().getBytes(1, Long.valueOf(b2.getCover().length()).intValue()));
		assertThat(b.getContent().getSubString(1, Long.valueOf(b.getContent().length()).intValue())).isEqualTo(b2.getContent().getSubString(1, Long.valueOf(b.getContent().length()).intValue()));
		
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
