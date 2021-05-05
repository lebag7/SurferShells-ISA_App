package com.accenture.surfershells;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DBManipulateSQL extends MySqlConnection {

    private void insertDB(Integer id, String stockname, Double price, Date price_date, String industry) {

        // String sql1 = "INSERT INTO stockname(id,stockname) VALUES(?,?) ON DUPLICATE
        // KEY UPDATE stockname = VALUES(stockname);";
        String sql1 = "INSERT IGNORE INTO stockname(id,stockname) VALUES(?,?);";

        try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");

                PreparedStatement pstmt = connection.prepareStatement(sql1)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, stockname);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // String sql2 = "INSERT INTO industry(id,industry) VALUES(?,?) ON DUPLICATE KEY
        // UPDATE industry = VALUES(industry)";
        String sql2 = "INSERT IGNORE INTO industry(id,industry) VALUES(?,?);";

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

    public void importCSVFileToDB_SQL(String path) {

        try {
            BufferedReader buff = new BufferedReader(new FileReader(path));
            Integer rowNo = 0;
            String line = "";
            while ((line = buff.readLine()) != null) {

                String[] values = line.split(";");

                if (rowNo > 0) {
                    System.out.println(values[0] + " | " + values[1] + " | " + values[2] + " | " + values[3]);
                    insertDB(rowNo, values[0], DBType.parseToDouble(values[1]), DBType.parseDate(values[2]), values[3]);
                }
                rowNo++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void truncateTableSQL(String table) {
        String sql = "TRUNCATE " + table;
        try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");
                PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addRowSQL(String inputStocknameID, String inputPrice, String inputDate) {

        Integer id_stockname = Integer.parseInt(inputStocknameID);
        Double price = DBType.parseToDouble(inputPrice);
        Date price_date = DBType.parseDate(inputDate);

        String sql = "INSERT INTO price_per_date(price,date,id_stockname,id_industry) VALUES(?,?,?,?)";

        try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");
                Statement st = connection.createStatement();
                PreparedStatement pstmt = connection.prepareStatement(sql)) {

            ResultSet rsIndustry = st
                    .executeQuery("SELECT * FROM price_per_date WHERE id_stockname='" + id_stockname + "';");
            int industryID = 0;
            while (rsIndustry.next()) {
                industryID = rsIndustry.getInt("id_industry");
            }

            pstmt.setDouble(1, price);
            pstmt.setDate(2, price_date);
            pstmt.setInt(3, id_stockname);
            pstmt.setInt(4, industryID);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateIndustrySQL(String industryID, String industryUpdateName) {

        String sql = "UPDATE industry SET industry = ? WHERE id = ?;";

        try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");

                PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, industryUpdateName);
            pstmt.setInt(2, Integer.parseInt(industryID));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
