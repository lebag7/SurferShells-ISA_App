package com.accenture.surfershells;

import java.sql.*;
import java.util.Date;

public class DBViewSQL extends MySqlConnection {

    public void searchIdSQL(String searchString) {
        try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");
                Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM isa_db.stock WHERE stock_name LIKE '" + searchString + "%';");

            while (rs.next()) {
                String stock_name = rs.getString("stock_name");
                Integer id = rs.getInt("id");
                System.out.println("id: " + id + " - " + stock_name + " ");

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
                // nur ein Schleifendurchlauf, keine andere möglickeit gesehen, den Wert anders zu bekommen.
                max_price = rs.getDouble("max_price");
                System.out.println("Maximum price: " + max_price);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return min_price;
    }

    public void gapSQL(String searchID) {
        
        Double max = maxSQL(searchID);
        Double min = minSQL(searchID);
        Double gap = max - min;
        
        System.out.println("Gap: " + gap);
        
    }

    public void listIndustriesSQL() {

        try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");
                Statement st = connection.createStatement()) {

            ResultSet rs = st.executeQuery("SELECT  count(distinct id_stockname) as amount_stocks, industry_name, id_industry FROM isa_db.price_per_date LEFT JOIN industry ON price_per_date.id_industry = industry.id group by industry_name, id_industry;");
            
            while (rs.next()) {
                Integer idIndustry = rs.getInt("id_industry");
                Integer amount_stocks = rs.getInt("amount_stocks");
                System.out.println("Industry ID: " + idIndustry + " | "  + " Amount of stocks: " + amount_stocks);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }
}
