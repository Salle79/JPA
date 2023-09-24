package com.thorben.janssen.course.repository;

import java.util.List;

import com.thorben.janssen.course.model.PurchaseOrder;

public interface PurchaseOrderRepository {

    PurchaseOrder findByCustomerName(String customerName);
    void persistPurchaseOrder(PurchaseOrder order);
}
