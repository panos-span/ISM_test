package com.example.webapp;

import java.util.ArrayList;


/**
 * <p>Promote class.</p>
 *
 * @author ismgroup52
 * @version $Id: $1.0
 */
public class Promote {

    private static final ArrayList<Customer> customers = new ArrayList<>();
    private static final ArrayList<Product> products = new ArrayList<>();

    /**
     * <p>addCustomer.</p>
     *
     * @param customer a {@link com.example.webapp.Customer} object
     */
    protected static void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * <p>addProduct.</p>
     *
     * @param product a {@link com.example.webapp.Product} object
     */
    protected static void addProduct(Product product) {
        products.add(product);
    }

    /**
     * <p>removeCustomer.</p>
     *
     * @param customer a {@link com.example.webapp.Customer} object
     */
    protected static void removeCustomer(Customer customer) {
        customers.remove(customer);
    }

    /**
     * <p>removeProduct.</p>
     *
     * @param product a {@link com.example.webapp.Product} object
     */
    protected static void removeProduct(Product product) {
        products.remove(product);
    }

    /**
     * <p>Getter for the field <code>customers</code>.</p>
     *
     * @return a {@link java.util.ArrayList} object
     */
    public static ArrayList<Customer> getCustomers() {
        return customers;
    }

    /**
     * <p>Getter for the field <code>products</code>.</p>
     *
     * @return a {@link java.util.ArrayList} object
     */
    public static ArrayList<Product> getProducts() {
        return products;
    }

    /**
     * <p>clearLists.</p>
     */
    public static void clearLists() {
        customers.clear();
        products.clear();
    }
}
