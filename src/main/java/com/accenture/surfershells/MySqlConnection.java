package com.accenture.surfershells;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class MySqlConnection {
    private Connection connectDB(String url, String user, String password) {

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

    private void insertDB(String stockname, Double price, Date price_date, String industry) {

        String sql = "INSERT INTO all_in_one(stockname,price,price_date,industry) VALUES(?,?,?,?)";

        try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");

                PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, stockname);
            pstmt.setDouble(2, price);
            pstmt.setDate(3, price_date);
            pstmt.setString(4, industry);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void importCSVFileToDB(String path) {

        try {
            BufferedReader buff = new BufferedReader(new FileReader(path));
            Integer rowNo = 1;
            String line = "";
            while ((line = buff.readLine()) != null) {
                String[] values = line.split(";");
                values[1] = values[1].replaceAll("€", "").replaceAll(" ", "").replace(",", ".");
                values[2] = values[2].replaceAll("€", "").replaceAll(" ", "");

                if (rowNo > 1) {

                    values[1] = values[1].replaceAll("€", "").replaceAll(" ", "").replace(",", ".");
                    System.out.println(values[0] + " | " + values[1] + " | " + values[2] + " | " + values[3]);
                    String[] date = values[2].split("\\.");
                    String day = date[0];
                    String month = date[1];
                    String year = date[2];
                    values[2] = "20" + year + "-" + month + "-" + day;
                    System.out.println(values[0] + " | " + values[1] + " | " + values[2] + " | " + values[3]);
                    insertDB(values[0], Double.parseDouble(values[1]), Date.valueOf(values[2]), values[3]);
                }
                rowNo++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void truncateTable(String table) {
        String sql = "TRUNCATE " + table;
        try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");

                PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
