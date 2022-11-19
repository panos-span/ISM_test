package com.example.webapp;

import java.util.ArrayList;



public class Promote {

    private static final ArrayList<Customer> customers = new ArrayList<>();
    private static final ArrayList<Product> products = new ArrayList<>();

    protected static void addCustomer(Customer customer) {
        customers.add(customer);
    }

    protected static void addProduct(Product product) {
        products.add(product);
    }

    protected static void removeCustomer(Customer customer) {
        customers.remove(customer);
    }

    protected static void removeProduct(Product product) {
        products.remove(product);
    }

    public static ArrayList<Customer> getCustomers() {
        return customers;
    }

    public static ArrayList<Product> getProducts() {
        return products;
    }
}
