package com.thorben.janssen.course;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thorben.janssen.course.model.PurchaseOrder;
import com.thorben.janssen.course.model.PurchaseOrderItem;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestDao {

    Logger log = LogManager.getLogger(this.getClass().getName());

	private EntityManagerFactory emf;

    /**
     * Implement DAOs for the PurchaseOrder and PurchaseOrderItem entities 
     * that persist and fetch a PurchaseOrder and PurchaseOrderItem. 
     *  
     * Use the DAOs to persist the PurchaseOrder object with associated 
     * PurchaseOrderItems objects created by the createPurchaseOrder() 
     * method and to fetch a PurchaseOrder for the customer "My Customer".
     */
	@Test
	public void exercise3() {
		log.info("... exercise3 ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
        // a new PurchaseOrder that needs to be persisted
        PurchaseOrder newOrder = createPurchaseOrder();

        // persist the PurchaseOrder with PurchaseOrderItems object


        // fetch a PurchaseOrder for the customer "My Customer"
        PurchaseOrder order = null;

		em.getTransaction().commit();
		em.close();

        validatePurchaseOrder(order);
	}



    
    @Before
	public void init() {
		emf = Persistence.createEntityManagerFactory("my-persistence-unit");
	}

	@After
	public void close() {
		emf.close();
	}
    
    private PurchaseOrder createPurchaseOrder() {
        Set<PurchaseOrderItem> items = new HashSet<>();

        PurchaseOrder order = new PurchaseOrder();
        order.setCustomer("My Customer");
        order.setOrderDate(LocalDate.now());

        PurchaseOrderItem item = new PurchaseOrderItem();
        item.setProductName("Persistence Hub membership");
        item.setQuantity(1);
        item.setOrder(order);
        items.add(item);
        
        order.setOrderItems(items);

        return order;
    }

    private void validatePurchaseOrder(PurchaseOrder order) {
        assertThat(order.getId()).isNotNull();
        assertThat(order.getCustomer()).isEqualTo("My Customer");
        assertThat(order.getOrderItems()).isNotEmpty();
    }

}
