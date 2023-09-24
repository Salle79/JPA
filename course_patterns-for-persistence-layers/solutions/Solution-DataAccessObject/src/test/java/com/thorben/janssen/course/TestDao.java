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

import com.thorben.janssen.course.dao.PurchaseOrderDao;
import com.thorben.janssen.course.dao.PurchaseOrderDaoImpl;
import com.thorben.janssen.course.dao.PurchaseOrderItemDao;
import com.thorben.janssen.course.dao.PurchaseOrderItemDaoImpl;
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

        // get a DAO instance
        PurchaseOrderDao orderDao = getPurchaseOrderDao(em);
        PurchaseOrderItemDao orderItemDao = getPurchaseOrderItemDao(em);
        
        // persist the PurchaseOrder with PurchaseOrderItems object
        orderDao.persistPurchaseOrder(newOrder);
        newOrder.getOrderItems().forEach(i -> orderItemDao.persistPurchaseOrderItem(i));

        // fetch a PurchaseOrder for the customer "My Customer"
        PurchaseOrder order = orderDao.findByCustomerName("My Customer");

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

    private PurchaseOrderDao getPurchaseOrderDao(EntityManager em) {
        return new PurchaseOrderDaoImpl(em);
    }

    private PurchaseOrderItemDao getPurchaseOrderItemDao(EntityManager em) {
        return new PurchaseOrderItemDaoImpl(em);
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
