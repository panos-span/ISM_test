package com.example.webapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private final DBConnection dbConnection = new DBConnection();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    private final String selectUserQuery = "select Role from user where Username=? AND Password=?;";

    public User() {
        try {
            dbConnection.open();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserRole(String username, String password) {
        try {
            stmt = dbConnection.getCon().prepareStatement(selectUserQuery);
            // replacing the first ? with userName and the second ? with
            // userPassword
            stmt.setString(1, username);
            stmt.setString(2, password);
            // execute query
            rs = stmt.executeQuery();
            String role = null;
            if (rs.next())
                role = rs.getString("Role");
            stmt.close();
            rs.close();
            return role;

        } catch (Exception ignored) {

        }
        return null;
    }
}
