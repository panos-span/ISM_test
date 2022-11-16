package com.example.webapp;


import java.sql.*;

public class DBConnection {
    private String errorMessages = "";

    private Connection con = null;

    /**
     * Provides a connection with the Database Server. Initializes JDBC driver
     * for MySQL. Establishes a connection with the Database Server.
     *
     * @throws SQLException (with the appropriate message) if any driver or connection
     *                      error occurred.
     */
    public void open() throws SQLException {
        try {
            // for JDBC driver to connect to mysql, the .newInstance() method
            // can be ommited
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e1) {
            errorMessages = "MySQL Driver error: <br>" + e1.getMessage();
            throw new SQLException(errorMessages);
        }

        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://195.251.249.131:3306/ismgroup52",
                    "ismgroup52", "fk3qqz");
        } catch (Exception e2) {
            errorMessages = "Could not establish connection with the Database Server: <br>"
                    + e2.getMessage();
            con = null;
            throw new SQLException(errorMessages);
        }

    }

    /**
     * Ends the connection with the database Server. Closes all Statements and
     * ResultSets. Finally, closes the connection with the Database Server.
     *
     * @throws SQLException (with the appropriate message) if any error occurred.
     */
    public void close() throws SQLException {
        try {
            if (con != null)
                con.close();
        } catch (Exception e3) {
            errorMessages = "Could not close connection with the Database Server: <br>"
                    + e3.getMessage();
            throw new SQLException(errorMessages);
        }
    }

    public Connection getCon() {
        return con;
    }
}
