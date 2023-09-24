package com.thorben.janssen.course.repository;

import com.thorben.janssen.course.model.PurchaseOrder;

import jakarta.persistence.EntityManager;

public class PurchaseOrderRepositoryImpl implements PurchaseOrderRepository {

    private EntityManager em;   

    public PurchaseOrderRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public PurchaseOrder findByCustomerName(String customerName) {
        return em.createQuery("SELECT o FROM PurchaseOrder o JOIN FETCH o.orderItems WHERE o.customer = :customer", PurchaseOrder.class)
                 .setParameter("customer", customerName)
                 .getSingleResult();
    }

    @Override
    public void persistPurchaseOrder(PurchaseOrder order) {
        em.persist(order);
        order.getOrderItems().forEach(i -> em.persist(i));
    }
    
}
