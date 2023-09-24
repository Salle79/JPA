package com.thorben.janssen.course.model.builder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.thorben.janssen.course.model.PurchaseOrder;
import com.thorben.janssen.course.model.PurchaseOrderItem;

public class PurchaseOrderItemListBuilder {

    private Set<PurchaseOrderItem> orderItems = new HashSet<>();

    private PurchaseOrderBuilder orderBuilder;

    protected PurchaseOrderItemListBuilder(PurchaseOrderBuilder orderBuilder) {
        this.orderBuilder = orderBuilder;
    }

    public PurchaseOrderItemBuilder addItem() {
        return new PurchaseOrderItemBuilder(this);
    }

    protected void addOrderItem(PurchaseOrderItem item) {
        Optional<PurchaseOrderItem> duplicateItem = orderItems.stream()
                                                              .filter(i -> {return i.getProductName().equals(item.getProductName());})
                                                              .findFirst();
        if (duplicateItem.isPresent()) {
            duplicateItem.get().setQuantity(duplicateItem.get().getQuantity()+item.getQuantity());
        } else {
            orderItems.add(item);
        }
    }

    protected void assignItemsToOrder(PurchaseOrder order) {
        order.setOrderItems(this.orderItems);
        this.orderItems.forEach(i -> i.setOrder(order));
    }

    public PurchaseOrderBuilder endItems() {
        return orderBuilder;
    }

    
}
