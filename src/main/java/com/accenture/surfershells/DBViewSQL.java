package com.accenture.surfershells;

import java.sql.*;
import java.util.Date;

public class DBViewSQL extends MySqlConnection {
    public void searchIdSQL(String searchString) {
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

    public void showStockValuesSQL(String searchID) {

        String sql = "SELECT * FROM isa_db.price_per_date WHERE id_stockname = ? ORDER BY date DESC LIMIT 10;";

        try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");
                PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(searchID));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("id_stockname");
                Double price = rs.getDouble("price");
                Date date = rs.getDate("date");
                System.out.println("ID: " + id + " | " + date + " | " + " price: " + price);
            }
        } catch (Exception e) {

        }
    }

    public Double maxSQL(String searchID) {

        String sql = "SELECT max(price) as max_price FROM isa_db.price_per_date WHERE id_stockname = ?;";
        Double max_price = 0.0;
        try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");
                PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(searchID));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                max_price = rs.getDouble("max_price");
                System.out.println("Maximum price: " + max_price);

            }
        } catch (Exception e) {

        }
        return max_price;
    }

    public Double minSQL(String searchID) {

        String sql = "SELECT min(price) as min_price FROM isa_db.price_per_date WHERE id_stockname = ?;";
        Double min_price = 0.0;
        try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");
                PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(searchID));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                min_price = rs.getDouble("min_price");
                System.out.println("Minimum price: " + min_price);
            }
        } catch (Exception e) {

        }
        return min_price;
    }

    public void gapSQL(String searchID) {

        Double max = maxSQL(searchID);
        Double min = minSQL(searchID);
        Double gap = max - min;

        if (max == min) {
            System.out.println("No gap!");
        } else {
            System.out.println("Gap: " + gap);
        }
    }

    public void showIndustriesSQL() {

        try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");
                Statement st = connection.createStatement()) {

            ResultSet rs = st.executeQuery("SELECT  count(id_stockname) as amount_stocknames, industry, id_industry FROM isa_db.price_per_date LEFT JOIN industry ON price_per_date.id_industry = industry.id group by industry, id_industry;");
            
            while (rs.next()) {
                Integer id = rs.getInt("id_industry");
                Integer amount_stocknames = rs.getInt("amount_stocknames");
                System.out.println("ID: " + id + " | "  + " Amount of stocks: " + amount_stocknames);
            }
        } catch (Exception e) {

        }
    }
}
