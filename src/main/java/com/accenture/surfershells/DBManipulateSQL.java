package com.accenture.surfershells;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DBManipulateSQL extends MySqlConnection {

    public void importCSVFileToDB_SQL(String path) {

        try (BufferedReader csvReader = new BufferedReader(new FileReader(path));) {

            Integer rowNo = 0;
            String line; 
            while ((line = csvReader.readLine()) != null) {

                String[] values = line.split(";");

                if (rowNo > 0) {
                    System.out.println(rowNo + " | " + values[0] + " | " + values[1] + " | " + values[2] + " | " + values[3]);

                    Integer id = rowNo;
                    String stock_name = values[0];
                    Double price = DBType.parseToDouble(values[1]);
                    Date price_date = DBType.parseDate(values[2]); //e.g. date =20.05.20 -> wird als Date an Variable übergeben
                    String industry_name = values[3];

                    String sql1 = "INSERT IGNORE INTO stock(id,stock_name) VALUES(?,?);";
                    String sql2 = "INSERT IGNORE INTO industry(id,industry_name) VALUES(?,?);";
                    String sql3 = "INSERT INTO price_per_date(id,price,date,id_stockname,id_industry) VALUES "
                            + "(?,?,?,(SELECT id FROM stock WHERE stock_name = ?),(SELECT id FROM industry WHERE industry_name = ?));";

                    try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");
                            PreparedStatement pstmt = connection.prepareStatement(sql1);
                            PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                            PreparedStatement pstmt3 = connection.prepareStatement(sql3)) {
                        pstmt.setInt(1, id);
                        pstmt.setString(2, stock_name);
                        pstmt.executeUpdate();

                        pstmt2.setInt(1, id);
                        pstmt2.setString(2, industry_name);
                        pstmt2.executeUpdate();

                        pstmt3.setInt(1, id);
                        pstmt3.setDouble(2, price);
                        pstmt3.setDate(3, price_date);
                        pstmt3.setString(4, stock_name);
                        pstmt3.setString(5, industry_name);
                        pstmt3.executeUpdate();

                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
                rowNo++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getCause());
        }
    }

    public void deleteAllValuesOfTable(String table) {
        String sql = "DELETE FROM " + table + ";";
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

            ResultSet rs = st.executeQuery("SELECT id_industry FROM price_per_date WHERE id_stockname='" + id_stockname + "';");
            int industryID = 0;
            while (rs.next()) {
                industryID = rs.getInt("id_industry");
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

        String sql = "UPDATE industry SET industry_name = ? WHERE id = ?;";

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
