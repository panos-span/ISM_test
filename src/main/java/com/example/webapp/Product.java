package com.example.webapp;

import java.util.Objects;

/**
 * <p>Product class.</p>
 *
 * @author ismgroup52
 * @version $Id: $1.0
 */
public class Product {

    private final String id;
    private final String name;
    private final double price;

    /**
     * <p>Constructor for Product.</p>
     *
     * @param id a {@link java.lang.String} object
     * @param name a {@link java.lang.String} object
     * @param price a double
     */
    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (Double.compare(product.price, price) != 0) return false;
        if (!Objects.equals(id, product.id)) return false;
        return Objects.equals(name, product.name);
    }


    /** {@inheritDoc} */
    @Override
    public String toString() {
        return id + "," + name + "," + price;
    }

    /**
     * <p>Getter for the field <code>id</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getId() {
        return id;
    }

    /**
     * <p>Getter for the field <code>name</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Getter for the field <code>price</code>.</p>
     *
     * @return a double
     */
    public double getPrice() {
        return price;
    }
}
