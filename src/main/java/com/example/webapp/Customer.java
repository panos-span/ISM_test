package com.example.webapp;

import java.util.Objects;

/**
 * <p>Customer class.</p>
 *
 * @author ismgroup52
 * @version $Id: $1.0
 */
public class Customer {

    private final String id;
    private final String name;
    private final String surname;
    private final String email;

    /**
     * <p>Constructor for Customer.</p>
     *
     * @param id a {@link java.lang.String} object
     * @param name a {@link java.lang.String} object
     * @param surname a {@link java.lang.String} object
     * @param email a {@link java.lang.String} object
     */
    public Customer(String id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (!Objects.equals(id, customer.id)) return false;
        if (!Objects.equals(name, customer.name)) return false;
        if (!Objects.equals(surname, customer.surname)) return false;
        return Objects.equals(email, customer.email);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return id + "," + name + "," + surname;
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
     * <p>Getter for the field <code>surname</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getSurname() {
        return surname;
    }

    /**
     * <p>Getter for the field <code>email</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getEmail() {
        return email;
    }
}
