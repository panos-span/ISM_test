package com.example.webapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductDAO {

    private final DBConnection dbConnection = new DBConnection();

    private PreparedStatement productprice = null;
    private final String getProductPrice = "select Price from product where ID=?;";

    private Statement stmt = null;
    private ResultSet rs = null;

    public ProductDAO() {
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

    public double getProductPrice(String ID) {
        try {
            if (dbConnection.getCon() == null) {
                //errorMessages = "You must establish a connection first!";
                return -1;
            }
            productprice = dbConnection.getCon().prepareStatement(getProductPrice);
            productprice.setString(1, ID);
            rs = productprice.executeQuery();
            rs.next();
            return Double.parseDouble(rs.getString("Price"));
        } catch (Exception e5) {
            //errorMessages = "Error while getting all students from database!<br>"
            //+ e5.getMessage();
            return -1;
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
