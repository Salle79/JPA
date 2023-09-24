package com.thorben.janssen.course.jpa;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thorben.janssen.course.model.fluent.jpa.PurchaseOrder;
import com.thorben.janssen.course.model.fluent.jpa.PurchaseOrderItem;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestJpaFluentApi {

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

    /**
	 * Apply the fluent interface pattern to the PurchaseOrder and PurchaseOrderItem
	 * entity classes and ensure that these classes stay compliant with the JPA specification.
	 * 
	 * Use the API to persist a new PurchaseOrder with 2 PurchaseOrderItems.
	 */
	@Test
	public void exercise3() {
		log.info("... exercise3 ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		PurchaseOrder order = new PurchaseOrder().customer("My Customer")
												 .purchasedOnDate(LocalDate.now());

		PurchaseOrderItem item1 = new PurchaseOrderItem().product("Hibernate Tips ebook")
														 .inQuantity(1)
														 .forOrder(order);

		PurchaseOrderItem item2 = new PurchaseOrderItem().product("Persistence Hub membership")
														 .inQuantity(1)
														 .forOrder(order);

		order.getOrderItems().add(item1);
		order.getOrderItems().add(item2);

		em.persist(order);

		em.getTransaction().commit();
		em.close();
	}

}
