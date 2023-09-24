package com.thorben.janssen.course.model.fluent.hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;

@Entity(name = "PurchaseOrderItem_Hibernate")
public class PurchaseOrderItem {

    @Id
    @GeneratedValue
    private Long id;

    private String productName;

    private int quantity;

    @ManyToOne
    private PurchaseOrder order;

    @Version
    private int version;

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public PurchaseOrder getOrder() {
        return order;
    }

    public int getVersion() {
        return version;
    }

    public PurchaseOrderItem product(String productName) {
        this.productName = productName;
        return this;
    }

    public PurchaseOrderItem inQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public PurchaseOrderItem forOrder(PurchaseOrder order) {
        this.order = order;
        return this;
    }
}
