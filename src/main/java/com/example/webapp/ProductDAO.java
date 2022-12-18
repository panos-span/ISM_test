package com.example.webapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * <p>ProductDAO class.</p>
 *
 * @author ismgroup52
 * @version $Id: $1.0
 */
public class ProductDAO {

    private final DBConnection dbConnection = new DBConnection();
    private PreparedStatement productprice = null;
    private final String getProductPrice = "select Price from product where ID=?;";
    private PreparedStatement search = null, stmt1 = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    /**
     * <p>Constructor for ProductDAO.</p>
     */
    public ProductDAO() {
        try {
            dbConnection.open();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>getAllProducts.</p>
     *
     * @return a {@link java.util.ArrayList} object
     */
    public ArrayList<Product> getAllProducts() {
        try {
            if (dbConnection.getCon() == null) {
                //errorMessages = "You must establish a connection first!";
                return null;
            }
            String selectAllProductsQuery = "";
            selectAllProductsQuery = "select Name,ID,Price from product;";
            stmt = dbConnection.getCon().createStatement();
            rs = stmt.executeQuery(selectAllProductsQuery);
            ArrayList<Product> products = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("Name");
                String id = rs.getString("id");
                double price = rs.getDouble("Price");
                products.add(new Product(id, name, price));
            }
            rs.close();
            return products;
        } catch (Exception e5) {
            return null;
        }
    }

    public boolean checkDuplicate(String name){
        try {
            if (dbConnection.getCon() == null) {
                //errorMessages = "You must establish a connection first!";
                return false;
            }
            String getNameQuery = "";
            getNameQuery = "select Name from product where Name=?;";
            stmt1 = dbConnection.getCon().prepareStatement(getNameQuery);
            stmt1.setString(1, name);
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
     * <p>searchProduct.</p>
     *
     * @param id a {@link java.lang.String} object
     * @return a {@link java.sql.ResultSet} object
     */
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

    /**
     * <p>getProductPrice.</p>
     *
     * @param ID a {@link java.lang.String} object
     * @return a double
     */
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
     * <p>insertNewProduct.</p>
     *
     * @param params an array of {@link java.lang.String} objects
     */
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

    /**
     * <p>editProduct.</p>
     *
     * @param params an array of {@link java.lang.String} objects
     * @param id a {@link java.lang.String} object
     */
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

