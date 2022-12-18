package com.example.webapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>SaleDAO class.</p>
 *
 * @author ismgroup52
 * @version $Id: $1.0
 */
public class SaleDAO {

    private DBConnection dbConnection = new DBConnection();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    private final String insertSaleQuery = "insert into sale (Cust_id, Prod_id, Sale_Date, Quantity, Sale_Value) values (?,?,?,?,?);";
    private final String getCustomerSales = "select * from sale where Cust_id=?;";
    private final String getCustomerSalesDates2 = "select * from sale where Cust_id=? and Sale_Date >=? and Sale_Date <=?;";
    private final String getCustomerSalesDatesStart = "select * from sale where Cust_id=? and Sale_Date >=?;";
    private final String getCustomerSalesDatesEnd = "select * from sale where Cust_id=? and Sale_Date <=?;";


    /**
     * <p>Constructor for SaleDAO.</p>
     */
    public SaleDAO() {
        try {
            dbConnection.open();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
     * <p>insertNewSale.</p>
     *
     * @param params an array of {@link java.lang.String} objects
     */
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

    /**
     * <p>getAllCustomerSales.</p>
     *
     * @param cust_id a {@link java.lang.String} object
     * @param startDate a {@link java.lang.String} object
     * @param endDate a {@link java.lang.String} object
     * @return a {@link java.sql.ResultSet} object
     */
    public ResultSet getAllCustomerSales(String cust_id, String startDate, String endDate) {
        if (dbConnection.getCon() == null) {
            return null;
        }
        try {

            if ((startDate == null) && (endDate == null)) {
                stmt = dbConnection.getCon().prepareStatement(getCustomerSales);
                stmt.setString(1, cust_id);
            } else if (startDate == null) {
                stmt = dbConnection.getCon().prepareStatement(getCustomerSalesDatesEnd);
                stmt.setString(1, cust_id);
                stmt.setString(2, endDate);
            } else if (endDate == null) {
                stmt = dbConnection.getCon().prepareStatement(getCustomerSalesDatesStart);
                stmt.setString(1, cust_id);
                stmt.setString(2, startDate);
            } else {
                stmt = dbConnection.getCon().prepareStatement(getCustomerSalesDates2);
                stmt.setString(1, cust_id);
                stmt.setString(2, startDate);
                stmt.setString(3, endDate);
            }

            rs = stmt.executeQuery();
            return rs;
        } catch (Exception ignored) {
            return null;
        }
    }

}
