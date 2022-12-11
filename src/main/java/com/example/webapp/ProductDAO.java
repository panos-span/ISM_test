package com.example.webapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductDAO {

    private final DBConnection dbConnection = new DBConnection();
    private PreparedStatement productprice = null;
    private final String getProductPrice = "select Price from product where ID=?;";
    private PreparedStatement search = null, stmt1 = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public ProductDAO() {
        try {
            dbConnection.open();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Product> getAllProducts() {
        try {
            if (dbConnection.getCon() == null) {
                //errorMessages = "You must establish a connection first!";
                return null;
            }
            String selectAllProductsQuery = "";
            selectAllProductsQuery = "select Name,ID from product;";
            stmt = dbConnection.getCon().createStatement();
            rs = stmt.executeQuery(selectAllProductsQuery);
            ArrayList<Product> products = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("Name");
                String id = rs.getString("id");
                products.add(new Product(id, name, 0));
            }
            rs.close();
            return products;
        } catch (Exception e5) {
            return null;
        }
    }

    public ResultSet searchProduct(String id) {
        try {
            if (dbConnection.getCon() == null) {
                return null;
            }
            String searchQuery = "select * from product where ID=?";
            search = dbConnection.getCon().prepareStatement(searchQuery);
            search.setString(1, id);
            rs = search.executeQuery();
            return rs;
        } catch (Exception e) {
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
    public void insertNewProduct(String[] params) {
        if (dbConnection.getCon() == null) {
            return;
            //throw new SQLException(errorMessages); String id,String name,String description, String price
        }

        try {
            String insertQuery = "insert into product (Name, Price, Category, Description) values (?,?,?,?);";
            stmt1 = dbConnection.getCon().prepareStatement(insertQuery);
            stmt1.setString(1, params[0]);
            stmt1.setDouble(2, Double.parseDouble(params[1]));
            stmt1.setString(3, params[2]);
            stmt1.setString(4, params[3]);
            stmt1.executeUpdate();
            stmt1.close();
        } catch (Exception e) {
            //this is an exception
        }
    }

    public void editProduct(String[] params, String id) {
        if (dbConnection.getCon() == null) {
            return;
            //throw new SQLException(errorMessages); String id,String name,String surname,String vat,String address,String email,String details
        }

        try {
            String editQuery = "update product set Name=?, Price=?, Category=?, Description=? WHERE (ID=?);";
            stmt1 = dbConnection.getCon().prepareStatement(editQuery);
            stmt1.setInt(5, Integer.parseInt(id));
            stmt1.setString(1, params[0]);
            stmt1.setDouble(2, Double.parseDouble(params[1]));
            stmt1.setString(3, params[2]);
            stmt1.setString(4, params[3]);
            stmt1.executeUpdate();
            stmt1.close();
        } catch (Exception ignored) {

        }
    }
}

