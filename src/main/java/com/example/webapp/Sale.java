package com.example.webapp;

/**
 * <p>Sale class.</p>
 *
 * @author ismgroup52
 * @version $Id: $1.0
 */
public class Sale {

    private final String prod_id;
    private final double sale_value;
    private final int quantity;

    /**
     * <p>Constructor for Sale.</p>
     *
     * @param prod_id a {@link java.lang.String} object
     * @param sale_value a double
     * @param quantity a int
     */
    public Sale(String prod_id, double sale_value, int quantity) {
        this.prod_id = prod_id;
        this.sale_value = sale_value;
        this.quantity = quantity;
    }

    /**
     * <p>Getter for the field <code>prod_id</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getProd_id() {
        return prod_id;
    }

    /**
     * <p>Getter for the field <code>sale_value</code>.</p>
     *
     * @return a double
     */
    public double getSale_value() {
        return sale_value;
    }

    /**
     * <p>Getter for the field <code>quantity</code>.</p>
     *
     * @return a int
     */
    public int getQuantity() {
        return quantity;
    }

}
