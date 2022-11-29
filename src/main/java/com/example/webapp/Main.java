package com.example.webapp;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws SQLException {
        DBConnection dbConnection = new DBConnection();
        dbConnection.open();
        String customer_count = "SELECT COUNT(*) AS count FROM customer;";
        Statement stmt = dbConnection.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(customer_count);
        rs.next();
        System.out.println(rs.getString("count"));
        try {
            String selectAllCustomersQuery = "";
            selectAllCustomersQuery = "select Name,Surname,ID from customer;";
            stmt = dbConnection.getCon().createStatement();
            rs = stmt.executeQuery(selectAllCustomersQuery);

        } catch (Exception ignored) {

        }
    }

    private static String getSearchId(String x) {
        String[] y = x.split("=");
        y[1] = y[1].substring(0, y[1].length() - 1);
        return y[1];
    }
}
