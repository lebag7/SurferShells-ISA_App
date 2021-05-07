package com.accenture.surfershells;

import java.io.File;
import java.io.FileWriter;
import java.sql.*;

import com.opencsv.CSVWriter;

public class DBExportSQL extends MySqlConnection {

    public void exportDB_SQL() {

        String sql = "SELECT stockname, price, date as price_date, industry FROM isa_db.price_per_date LEFT JOIN industry ON price_per_date.id_industry = industry.id LEFT JOIN stockname ON price_per_date.id_stockname = stockname.id;";

        try (Connection connection = this.connectDB("jdbc:mysql://localhost:3306/isa_db", "root", "root");
                Statement st = connection.createStatement()) {

            ResultSet rs = st.executeQuery(sql);

            CSVWriter writer = new CSVWriter(
                    new FileWriter(new File("C:/Users/yasmine.gabel/Desktop/STOCK_DATA_3_EXPORT.csv")),';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            writer.writeAll(rs, true, true);
            writer.close();
        } catch (Exception e) {

        }
    }

}
