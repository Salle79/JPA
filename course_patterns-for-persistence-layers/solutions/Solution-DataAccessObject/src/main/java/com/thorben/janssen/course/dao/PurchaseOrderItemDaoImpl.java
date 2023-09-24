package com.thorben.janssen.course.dao;

import com.thorben.janssen.course.model.PurchaseOrderItem;

import jakarta.persistence.EntityManager;

public class PurchaseOrderItemDaoImpl implements PurchaseOrderItemDao {

    private EntityManager em;   

    public PurchaseOrderItemDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void persistPurchaseOrderItem(PurchaseOrderItem orderItem) {
        em.persist(orderItem);
    }
    
}
