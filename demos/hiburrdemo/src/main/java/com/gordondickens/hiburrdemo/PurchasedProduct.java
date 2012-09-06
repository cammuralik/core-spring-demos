package com.gordondickens.hiburrdemo;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class PurchasedProduct {

    @ManyToOne
    private Product product;

    private int quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "PurchasedProduct [product=" + product + ", quantity=" + quantity
                + "]";
    }

}
