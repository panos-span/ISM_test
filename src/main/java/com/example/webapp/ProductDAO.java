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
    private final String searchProduct = ";";

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

        return null;
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

}
