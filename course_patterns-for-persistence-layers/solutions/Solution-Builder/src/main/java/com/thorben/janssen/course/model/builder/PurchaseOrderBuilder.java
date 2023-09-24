package com.thorben.janssen.course.model.builder;

import java.time.LocalDate;

import com.thorben.janssen.course.model.PurchaseOrder;

public class PurchaseOrderBuilder {

    private PurchaseOrderItemListBuilder orderItemListBuilder = new PurchaseOrderItemListBuilder(this);

    private String customer;
    private LocalDate orderDate;

    public PurchaseOrderBuilder withCustomer(String customer) {
        this.customer = customer;
        return this;
    }

    public PurchaseOrderBuilder withOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public PurchaseOrderItemListBuilder withPurchaseOrderItems() {
        return this.orderItemListBuilder;
    }
    
    public PurchaseOrder buildPurchaseOrder() {
        PurchaseOrder order = new PurchaseOrder();
        order.setCustomer(customer);
        order.setOrderDate(orderDate);
        
        this.orderItemListBuilder.assignItemsToOrder(order);

        return order;
    }
}
