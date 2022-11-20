package com.example.webapp;

import java.util.Objects;

public class Customer {

    private final String id;
    private final String name;
    private final String surname;

    public Customer(String id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (!Objects.equals(id, customer.id)) return false;
        if (!Objects.equals(name, customer.name)) return false;
        return Objects.equals(surname, customer.surname);
    }

    @Override
    public String toString() {
        return id + "," + name + "," + surname;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
