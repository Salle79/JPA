package com.thorben.janssen.course.model.fluent.hibernate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;

@Entity(name = "PurchaseOrder_Hibernate")
public class PurchaseOrder {

    @Id
    @GeneratedValue
    private Long id;

    private String customer;

    private LocalDate orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private Set<PurchaseOrderItem> orderItems = new HashSet<>();

    @Version
    private int version;

    public Long getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public Set<PurchaseOrderItem> getOrderItems() {
        return orderItems;
    }

    public int getVersion() {
        return version;
    }

    public PurchaseOrder customer(String customer) {
        this.customer = customer;
        return this;
    }

    public PurchaseOrder purchasedOnDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public PurchaseOrder items(Set<PurchaseOrderItem> orderItems) {
        this.orderItems = orderItems;
        return this;
    }
}