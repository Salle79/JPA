package com.thorben.janssen.course;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thorben.janssen.course.model.PurchaseOrder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestBuilder {

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
     * Create a set of builder classes to create and persist a PurchaseOrder entity object
     * with a Set of PurchaseOrderItem objects. When adding a new PurchaseOrderItem to the Set,
     * your builder class shall check if there is already an item with the given productName.
     * If that's the case, the quantity of the existing item shall be increased instead of adding
     * another PurchaseOrderItem.
     * 
     * Persist the following Order:
     *      customer: My Customer
     *      orderDate: today
     *      PurchaseOrderItems: [{productName: Hibernate Tips ebook, quantity: 1},
     *                           {productName: Persistence Hub membership, quantity: 1},
     *                           {productName: Persistence Hub membership, quantity: 1}]
     */
	@Test
	public void exercise3() {
		log.info("... exercise3 ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

        // use the builder API to create a PurchaseOrder
        PurchaseOrder order = null;
        em.persist(order);

		em.getTransaction().commit();
		em.close();

        validatePurchaseOrder(order);
	}

    private void validatePurchaseOrder(PurchaseOrder order) {
        assertThat(order.getId()).isNotNull();
        assertThat(order.getCustomer()).isEqualTo("My Customer");
        assertThat(order.getOrderDate()).isToday();
        assertThat(order.getOrderItems()).as("The bidirectional association between PurchaseOrder and PurchaseOrderItem has to get initialized.").allMatch(i -> {return i.getOrder() != null && i.getOrder().getId().equals(order.getId());});
        assertThat(order.getOrderItems()).as("Expected an item for product [Hibernate Tips ebook] with quantity [1].").filteredOn(i -> i.getProductName().equals("Hibernate Tips ebook") && i.getQuantity() == 1).hasSize(1);
        assertThat(order.getOrderItems()).as("Expected an item for product [Persistence Hub membership] with quantity [2].").filteredOn(i -> i.getProductName().equals("Persistence Hub membership") && i.getQuantity() == 2).hasSize(1);
    }

}
