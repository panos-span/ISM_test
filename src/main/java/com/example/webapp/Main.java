package com.example.webapp;


import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        Product product1 = new Product("1", "1", 2);
        Product product2 = new Product("1", "1", 2);
        HashSet<Product> products = new HashSet<>();
        System.out.println(product2.equals(product1));
        products.add(product1);
        if (products.contains(product2))
            products.add(product2);
        System.out.println(products.size());
    }

    private static String getSearchId(String x) {
        String[] y = x.split("=");
        y[1] = y[1].substring(0, y[1].length() - 1);
        return y[1];
    }
}
