package com.example.webapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * <p>CustomerDAO class.</p>
 *
 * @author ismgroup52
 * @version $Id: $1.0
 */
public class CustomerDAO {

    private final DBConnection dbConnection = new DBConnection();
    private Statement stmt = null;
    private PreparedStatement stmt1 = null, stmt2 = null, stmt3 = null, stmt4 = null, stmt5 = null;
    private PreparedStatement search = null, searchPhones = null;
    private ResultSet rs = null;
    private final String insertCustomerQuery = "insert into customer (Name, Surname, VAT, Address, Email, Details) values (?,?,?,?,?,?);";
    private final String searchCustomerQuery = "select * FROM customer WHERE ID=?;";
    private final String searchCustomerPhonesQuery = "select Phone,POSITION FROM customer_phones WHERE ID=?;";
    private final String insertCustomerPhonesQuery = "insert into customer_phones (ID,Phone,POSITION) values (?,?,?);";
    private final String editCustomerQuery = "update customer set Name=?, Surname=?, VAT=?, Address=?, Email=?, Details=? WHERE (ID=?);";
    private final String deleteCustomerPhonesQuery = "delete from customer_phones WHERE (ID=?);";

    /**
     * <p>Constructor for CustomerDAO.</p>
     */
    public CustomerDAO() {
        try {
            dbConnection.open();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>getAllCustomers.</p>
     *
     * @return a {@link java.util.ArrayList} object
     */
    public ArrayList<Customer> getAllCustomers() {
        try {
            if (dbConnection.getCon() == null) {
                return null;
            }
            String selectAllCustomersQuery;
            selectAllCustomersQuery = "select Name,Surname,ID from customer;";
            stmt = dbConnection.getCon().createStatement();
            rs = stmt.executeQuery(selectAllCustomersQuery);
            ArrayList<Customer> customers = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("Name");
                String surname = rs.getString("Surname");
                String id = rs.getString("id");
                customers.add(new Customer(id, name, surname, null));
            }
            rs.close();
            return customers;
        } catch (Exception e5) {
            return null;
        }
    }

    public boolean checkDuplicate(String email) {
        try {
            if (dbConnection.getCon() == null) {
                return false;
            }
            String getEmailQuery;
            getEmailQuery = "select Email from customer where Email=?;";
            stmt1 = dbConnection.getCon().prepareStatement(getEmailQuery);
            stmt1.setString(1, email);
            rs = stmt1.executeQuery();
            boolean f = rs.next();
            rs.close();
            stmt1.close();
            return f;
        } catch (Exception e5) {
            return false;
        }
    }


    /**
     * <p>searchCustomer.</p>
     *
     * @param id a {@link java.lang.String} object
     * @return a {@link java.sql.ResultSet} object
     */
    public ResultSet searchCustomer(String id) {
        try {
            if (dbConnection.getCon() == null) {
                return null;
            }
            search = dbConnection.getCon().prepareStatement(searchCustomerQuery);
            search.setString(1, id);
            rs = search.executeQuery();
            return rs;
        } catch (Exception e5) {
            return null;
        }
    }

    /**
     * <p>searchCustomerPhones.</p>
     *
     * @param id a {@link java.lang.String} object
     * @return a {@link java.sql.ResultSet} object
     */
    public ResultSet searchCustomerPhones(String id) {
        try {
            if (dbConnection.getCon() == null) {
                return null;
            }
            searchPhones = dbConnection.getCon().prepareStatement(searchCustomerPhonesQuery);
            searchPhones.setString(1, id);
            rs = searchPhones.executeQuery();
            return rs;
        } catch (Exception e5) {

            return null;
        }
    }


    /**
     * <p>close.</p>
     */
    public void close() {
        try {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            dbConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>insertNewCustomer.</p>
     *
     * @param params an array of {@link java.lang.String} objects
     * @param phones an array of {@link java.lang.String} objects
     */
    public void insertNewCustomer(String[] params, String[] phones) {
        if (dbConnection.getCon() == null) {
            return;
            //throw new SQLException(errorMessages); String id,String name,String surname,String vat,String address,String email,String details
        }

        try {
            stmt1 = dbConnection.getCon().prepareStatement(insertCustomerQuery);
            int index = 0;
            enterCustInfo(stmt1, params, index);
            String getLastCustId = "select ID from customer ORDER BY id DESC LIMIT 1;";
            stmt = dbConnection.getCon().createStatement();
            rs = stmt.executeQuery(getLastCustId);
            rs.next();
            enterCustPhonesInfo(stmt2, phones, rs.getString("ID"));
        } catch (Exception ignored) {

        }
    }

    private void enterCustInfo(PreparedStatement stmt, String[] params, int index) throws SQLException {
        for (String param : params) {
            index++;
            stmt.setString(index, param);
        }
        // execute query
        stmt.executeUpdate();
        stmt.close();
    }

    private void enterCustPhonesInfo(PreparedStatement stmt, String[] phones, String id) throws SQLException {
        int position = 1;
        for (String phone : phones) {
            if (!phone.equals("")) {
                stmt = dbConnection.getCon().prepareStatement(insertCustomerPhonesQuery);
                stmt.setString(1, id);
                stmt.setString(2, phone);
                stmt.setString(3, String.valueOf(position));
                stmt.executeUpdate();
                position++;
            }
        }
        stmt.close();

    }

    /**
     * <p>editCustomer.</p>
     *
     * @param params an array of {@link java.lang.String} objects
     * @param phones an array of {@link java.lang.String} objects
     * @param id     a {@link java.lang.String} object
     */
    public void editCustomer(String[] params, String[] phones, String id) {
        if (dbConnection.getCon() == null) {
            return;
            //throw new SQLException(errorMessages);
        }

        try {
            stmt3 = dbConnection.getCon().prepareStatement(editCustomerQuery);
            //dbConnection.getCon().setAutoCommit(true);
            int index = 0;
            stmt3.setString(7, id);
            enterCustInfo(stmt3, params, index);
            stmt3.close();
            //Problems with keys
            stmt4 = dbConnection.getCon().prepareStatement(deleteCustomerPhonesQuery);
            stmt4.setString(1, id);
            stmt4.executeUpdate();
            stmt4.close();
            enterCustPhonesInfo(stmt5, phones, id);
        } catch (Exception ignored) {
        }
    }


}
