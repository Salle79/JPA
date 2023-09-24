package com.thorben.janssen.course.dao;

import com.thorben.janssen.course.model.PurchaseOrderItem;

public interface PurchaseOrderItemDao {

    void persistPurchaseOrderItem(PurchaseOrderItem orderItem);
}
