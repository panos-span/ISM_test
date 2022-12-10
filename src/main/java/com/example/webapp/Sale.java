package com.example.webapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sale {

    private DBConnection dbConnection = new DBConnection();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    private final String insertSaleQuery = "insert into sale (Cust_id, Prod_id, Sale_Date, Quantity, Sale_Value) values (?,?,?,?,?);";
    private final String getCustomerSales = "select * from sale where Cust_id=?;";
    private final String getCustomerSalesDates2 = "select * from sale where Cust_id=? and Sale_Date >=? and Sale_Date <=?;";
    private final String getCustomerSalesDatesStart = "select * from sale where Cust_id=? and Sale_Date >=?;";
    private final String getCustomerSalesDatesEnd = "select * from sale where Cust_id=? and Sale_Date <=?;";


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

    public ResultSet getAllCustomerSales(String cust_id, String startDate, String endDate) {
        if (dbConnection.getCon() == null) {
            return null;
        }
        try {
            //different cases if start or end Date is null or both (3 cases)
            stmt = dbConnection.getCon().prepareStatement(getCustomerSales);
            stmt.setString(1, cust_id);
            rs = stmt.executeQuery();
            return rs;
        } catch (Exception ignored) {

        }
        return null;
    }

}
