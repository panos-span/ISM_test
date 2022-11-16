package com.example.webapp;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer {

    private final DBConnection dbConnection = new DBConnection();
    private Statement stmt = null;
    private PreparedStatement stmt1 = null, stmt2 = null, stmt3 = null, stmt4 = null, stmt5 = null;
    private PreparedStatement search = null, searchPhones = null;
    private ResultSet rs = null;
    private final String insertCustomerQuery = "insert into customer (ID, Name, Surname, VAT, Address, Email, Details) values (?,?,?,?,?,?,?);";
    private final String searchCustomerQuery = "select * FROM customer WHERE ID=?;";
    private final String searchCustomerPhonesQuery = "select Phone FROM customer_phones WHERE ID=?;";
    private final String insertCustomerPhonesQuery = "insert into customer_phones (ID,Phone) values (?,?);";
    private final String editCustomerQuery = "update customer set Name=?, Surname=?, VAT=?, Address=?, Email=?, Details=? WHERE (ID=?);";
    //private final String editCustomerPhonesQuery = "update customer_phones set Phone=? WHERE (ID=?);";
    private final String deleteCustomerPhonesQuery = "delete from customer_phones WHERE (ID=?);";

    public Customer() {
        try {
            dbConnection.open();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAllCustomers() {
        try {
            if (dbConnection.getCon() == null) {
                return null;
            }
            String selectAllCustomersQuery = "";
            selectAllCustomersQuery = "select Name,Surname,ID from customer;";
            stmt = dbConnection.getCon().createStatement();
            rs = stmt.executeQuery(selectAllCustomersQuery);
            return rs;
        } catch (Exception e5) {
            return null;
        }
    }

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

    public void insertNewCustomer(String[] params, String[] phones) {
        if (dbConnection.getCon() == null) {
            return;
            //throw new SQLException(errorMessages); String id,String name,String surname,String vat,String address,String email,String details
        }

        try {
            stmt1 = dbConnection.getCon().prepareStatement(insertCustomerQuery);

            String id = String.valueOf(getRandomId());
            int index = 1;

            stmt1.setString(index, id);
            /*for (String param : params) {
                index++;
                stmt1.setString(index, param);
            }
            // execute query
            stmt1.executeUpdate();
            stmt1.close();*/
            enterCustInfo(stmt1, params, index);
            enterCustPhonesInfo(stmt2, phones, id);
            /*for (String phone : phones) {
                if (phone != null) {
                    stmt2 = dbConnection.getCon().prepareStatement(insertCustomerPhonesQuery);
                    stmt2.setString(1, id);
                    stmt2.setString(2, phone);
                    stmt2.executeUpdate();
                }
            }
            stmt2.close();*/

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
        for (String phone : phones) {
            if (!phone.equals("")) {
                stmt = dbConnection.getCon().prepareStatement(insertCustomerPhonesQuery);
                stmt.setString(1, id);
                stmt.setString(2, phone);
                stmt.executeUpdate();
            }
        }
        stmt.close();
    }

    public void editCustomer(String[] params, String[] phones, String id) {
        if (dbConnection.getCon() == null) {
            return;
            //throw new SQLException(errorMessages); String id,String name,String surname,String vat,String address,String email,String details
        }

        try {
            stmt3 = dbConnection.getCon().prepareStatement(editCustomerQuery);
            //dbConnection.getCon().setAutoCommit(true);
            int index = 0;
            stmt3.setString(7, id);
            enterCustInfo(stmt3, params, index);
            //Problems with keys
            stmt4 = dbConnection.getCon().prepareStatement(deleteCustomerPhonesQuery);
            stmt4.setString(1, id);
            stmt4.executeUpdate();
            stmt4.close();

            enterCustPhonesInfo(stmt5, phones, id);


        } catch (Exception ignored) {
        }
    }

    private int getRandomId() {
        SecureRandom random = new SecureRandom();
        return random.nextInt(90000) + 1;
    }


}
