package com.thorben.janssen.course.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;

@Entity
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Set<PurchaseOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<PurchaseOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}