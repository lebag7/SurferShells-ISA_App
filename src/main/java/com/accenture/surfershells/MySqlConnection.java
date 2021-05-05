package com.accenture.surfershells;

import java.sql.*;

public class MySqlConnection {
    protected Connection connectDB(String url, String user, String password) {

        Connection connection = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            // System.out.println("Connection is established: " + url);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connection;
    }

}
