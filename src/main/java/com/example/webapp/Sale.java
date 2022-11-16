package com.example.webapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


public class Sale {

    private DBConnection dbConnection = new DBConnection();

    private PreparedStatement stmt = null;

    private ResultSet rs = null;

    private final String insertSaleQuery = "insert into sale (Cust_id, Prod_id, Sale_Date, Quantity, Sale_Value) values (?,?,?,?,?);";


    public Sale() {
        try {
            dbConnection.open();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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


    public void insertNewSale(String[] params) {
        if (dbConnection.getCon() == null) {
            return;
            //throw new SQLException(errorMessages); String id,String name,String surname,String vat,String address,String email,String details
        }
        try {
            stmt = dbConnection.getCon().prepareStatement(insertSaleQuery);
            for (int i = 0; i < 5; i++) {
                stmt.setString(i + 1, params[i]);
            }
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception ignored) {

        }
    }

}
