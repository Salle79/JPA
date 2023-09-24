package com.thorben.janssen.course.model.builder;

import com.thorben.janssen.course.model.PurchaseOrderItem;

public class PurchaseOrderItemBuilder {

    private PurchaseOrderItemListBuilder itemListBuilder;
    private String productName;
    private int quantity;

    protected PurchaseOrderItemBuilder(PurchaseOrderItemListBuilder itemListBuilder) {
        this.itemListBuilder = itemListBuilder;
    }  

    public PurchaseOrderItemBuilder withProductName(String productName) {
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        this.productName = productName;
        return this;
    }

    public PurchaseOrderItemBuilder withQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Item quantity has to >= 1.");
        }
        this.quantity = quantity;
        return this;
    }

    private PurchaseOrderItem buildItem() {
        if (productName == null) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("Item quantity has to >= 1.");
        }

        PurchaseOrderItem item = new PurchaseOrderItem();
        item.setProductName(productName);
        item.setQuantity(quantity);
        return item;
    }

    public PurchaseOrderItemListBuilder addToList() {
        PurchaseOrderItem item = buildItem();
        itemListBuilder.addOrderItem(item);
        return itemListBuilder;
    }
}