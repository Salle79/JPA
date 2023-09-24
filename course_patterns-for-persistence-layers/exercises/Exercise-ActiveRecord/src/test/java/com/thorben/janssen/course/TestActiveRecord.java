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
import com.thorben.janssen.course.util.HibernateSessionHandling;

public class TestActiveRecord {

    Logger log = LogManager.getLogger(this.getClass().getName());

    /**
     * Implement the active record pattern for the PurchaseOrder and
     * the PurchaseOrderItem entity that persist and fetch a PurchaseOrder. 
     * A PurchaseOrder aggregate consists of the PurchaseOrder entity object 
     * and a Set of PurchaseOrderItem objects.
     * 
     * Use the active record to persist the PurchaseOrder object created 
     * by the createPurchaseOrder() method and to fetch a PurchaseOrder 
     * for the customer "My Customer - Active Record". 
     */
	@Test
	public void exercise3() {
		log.info("... exercise3 ...");

        HibernateSessionHandling.openTransaction();
        
        // a new PurchaseOrder that needs to be persisted
        PurchaseOrder newOrder = createPurchaseOrder();
       
        // persist the PurchaseOrder object


        // fetch a PurchaseOrder for the customer "My Customer - Active Record"
        PurchaseOrder order = null;

        HibernateSessionHandling.commitSession(); 

        validatePurchaseOrder(order);
	}



	@Before
	public void init() {
		HibernateSessionHandling.start();
	}

	@After
	public void close() {
		HibernateSessionHandling.shutdown();
	}

    private PurchaseOrder createPurchaseOrder() {
        Set<PurchaseOrderItem> items = new HashSet<>();

        PurchaseOrder order = new PurchaseOrder();
        order.setCustomer("My Customer - Active Record");
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
        assertThat(order.getCustomer()).isEqualTo("My Customer - Active Record");
        assertThat(order.getOrderItems()).isNotEmpty();
    }

}
