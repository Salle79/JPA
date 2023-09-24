package org.thoughts.on.java.university.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.thoughts.on.java.university.model.Professor;

public class ProfessorServiceTest {

	EntityManagerFactory emf;
	
	@Before
	public void init() {
		emf = Persistence.createEntityManagerFactory("my-persistence-unit");
	}
	
	@Test
	public void persistProfessor() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Professor p = new Professor();
		p.setFirstName("Jane");
		p.setLastName("Doe");
		
		ProfessorService ps = new ProfessorService(em);
		ps.saveProfessor(p);
		
		em.getTransaction().commit();
		em.close();
		
		Assert.assertNotNull(p.getId());
	}
	
	@Test
	public void updateProfessor() {
		// persist a new Professor
		Professor p = createProfessor();
		
		// Load and update the Professor
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		ProfessorService ps = new ProfessorService(em);
		p = ps.findById(p.getId());
		p.setFirstName("Marie");
		
		em.getTransaction().commit();
		em.close();
		
		// Validate update
		p = getProfessor(p.getId());
		Assert.assertEquals("Marie", p.getFirstName());
	}
	
	@Test
	public void removeProfessor() {
		// persist a new Professor
		Professor p = createProfessor();
		
		// Load and update the Professor
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		ProfessorService ps = new ProfessorService(em);
		p = ps.findById(p.getId());
		ps.removeProfessor(p);
		
		em.getTransaction().commit();
		em.close();
		
		// Validate update
		p = getProfessor(p.getId());
		Assert.assertNull(p);
	}
	
	private Professor createProfessor() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Professor p = new Professor();
		p.setFirstName("Jane");
		p.setLastName("Doe");
		
		ProfessorService ps = new ProfessorService(em);
		ps.saveProfessor(p);
		
		em.getTransaction().commit();
		em.close();
		
		return p;
	}
	
	private Professor getProfessor(Long id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		ProfessorService ps = new ProfessorService(em);
		Professor p = ps.findById(id);
		
		em.getTransaction().commit();
		em.close();
		
		return p;
	}
}
