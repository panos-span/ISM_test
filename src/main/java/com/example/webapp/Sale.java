package com.example.webapp;

public class Sale {

    private final String prod_id;
    private final double sale_value;
    private final int quantity;

    public Sale(String prod_id, double sale_value, int quantity) {
        this.prod_id = prod_id;
        this.sale_value = sale_value;
        this.quantity = quantity;
    }

    public String getProd_id() {
        return prod_id;
    }

    public double getSale_value() {
        return sale_value;
    }

    public int getQuantity() {
        return quantity;
    }

}
