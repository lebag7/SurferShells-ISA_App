package com.accenture.surfershells;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    private void insertDB(Integer id, String stockname, Double price, Date price_date, String industry) {

        String sql1 = "INSERT INTO stockname(id,stockname) VALUES(?,?) ON DUPLICATE KEY UPDATE stockname = VALUES(stockname);";

        try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");

                PreparedStatement pstmt = connection.prepareStatement(sql1)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, stockname);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sql2 = "INSERT INTO industry(id,industry) VALUES(?,?) ON DUPLICATE KEY UPDATE industry = VALUES(industry)";

        try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");

                PreparedStatement pstmt = connection.prepareStatement(sql2)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, industry);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sql3 = "INSERT INTO price_per_date(id,price,date,id_stockname,id_industry) VALUES(?,?,?,?,?)";

        try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");
                Statement st = connection.createStatement();
                PreparedStatement pstmt = connection.prepareStatement(sql3)) {

            ResultSet rsIndustry = st.executeQuery("SELECT * FROM industry WHERE industry='" + industry + "';");
            int industryID = 0;
            while (rsIndustry.next()) {
                industryID = rsIndustry.getInt("id");
            }

            ResultSet rsStockname = st.executeQuery("SELECT * FROM stockname WHERE stockname='" + stockname + "';");
            int stocknameID = 0;
            while (rsStockname.next()) {
                stocknameID = rsStockname.getInt("id");
            }

            pstmt.setInt(1, id);
            pstmt.setDouble(2, price);
            pstmt.setDate(3, price_date);
            pstmt.setInt(4, stocknameID);
            pstmt.setInt(5, industryID);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Date parseDate(String date) {
        return Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yy")));
    }

    private Double convertToDouble(String value) {
        return Double.parseDouble(value.replaceAll("€", "").replaceAll(" ", "").replace(",", "."));
    }

    public void importCSVFileToDB(String path) {

        try {
            BufferedReader buff = new BufferedReader(new FileReader(path));
            Integer rowNo = 0;
            String line = "";
            while ((line = buff.readLine()) != null) {

                String[] values = line.split(";");

                if (rowNo > 0) {
                    System.out.println(values[0] + " | " + values[1] + " | " + values[2] + " | " + values[3]);
                    insertDB(rowNo, values[0], convertToDouble(values[1]), parseDate(values[2]), values[3]);
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

    public void searchId(String searchString) {
        try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");
                Statement st = connection.createStatement()) {
            ResultSet rs = st
                    .executeQuery("SELECT * FROM isa_db.stockname where stockname like '" + searchString + "%';");

            while (rs.next()) {
                String stockname = rs.getString("stockname");
                Integer id = rs.getInt("id");
                System.out.println("ID: " + id + " - " + stockname + " ");

            }
        } catch (Exception e) {

        }
    }

    public void addRow(String inputID, String inputPrice, String inputDate) {

        Integer id = Integer.parseInt(inputID);
        System.out.println(inputPrice);
        Double price = convertToDouble(inputPrice);
        Date price_date = parseDate(inputDate);

        String sql = "INSERT INTO price_per_date(price,date,id_stockname,id_industry) VALUES(?,?,?,?)";

        try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");
                Statement st = connection.createStatement();
                PreparedStatement pstmt = connection.prepareStatement(sql)) {

            ResultSet rsIndustry = st.executeQuery("SELECT * FROM price_per_date WHERE id_stockname='" + id + "';");
            int industryID = 0;
            while (rsIndustry.next()) {
                industryID = rsIndustry.getInt("id_industry");
            }

            pstmt.setDouble(1, price);
            pstmt.setDate(2, price_date);
            pstmt.setInt(3, id);
            pstmt.setInt(4, industryID);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void exportToCSV() {
        String sql = "SELECT * FROM isa_db.price_per_date LEFT JOIN industry ON price_per_date.id_industry = industry.id LEFT JOIN stockname ON price_per_date.id_stockname = stockname.id;";
        
    }

}
