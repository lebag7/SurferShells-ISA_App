package com.accenture.surfershells;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

public class DBExportSQL extends MySqlConnection {

    public void exportDB_SQL() {

        String sql = "SELECT stock_name as stockname, price, date as price_date, industry_name as industry FROM isa_db.price_per_date LEFT JOIN industry ON price_per_date.id_industry = industry.id LEFT JOIN stock ON price_per_date.id_stockname = stock.id;";

        try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");
                Statement st = connection.createStatement();
                CSVWriter writer = new CSVWriter(
                    new FileWriter(new File("C:/Users/yasmine.gabel/Desktop/Output/STOCK_DATA_3_EXPORT.csv")),
                    ';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END)) {

            ResultSet rs = st.executeQuery(sql);
            writer.writeAll(rs, true, true);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }  catch (IOException e){
            System.out.println(e.getCause());
        }
    }

}
