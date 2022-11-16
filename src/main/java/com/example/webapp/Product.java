package com.example.webapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Product {

    private final DBConnection dbConnection = new DBConnection();
    private Statement stmt = null;
    private ResultSet rs = null;

    private PreparedStatement stmt1 = null, stmt2 = null;
    private PreparedStatement search = null;

    public Product() {
        try {
            dbConnection.open();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAllProducts() {
        try {
            if (dbConnection.getCon() == null) {
                //errorMessages = "You must establish a connection first!";
                return null;
            }
            String selectAllProductsQuery = "";


            selectAllProductsQuery = "select Name,ID from product;";
            stmt = dbConnection.getCon().createStatement();
            rs = stmt.executeQuery(selectAllProductsQuery);
            return rs;
        } catch (Exception e5) {
            //errorMessages = "Error while getting all students from database!<br>"
            //+ e5.getMessage();
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

}
