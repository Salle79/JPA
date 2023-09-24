package com.thorben.janssen.course.dao;

import com.thorben.janssen.course.model.PurchaseOrder;

public interface PurchaseOrderDao {

    PurchaseOrder findByCustomerName(String customerName);

    void persistPurchaseOrder(PurchaseOrder order);
}
