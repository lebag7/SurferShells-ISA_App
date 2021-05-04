package com.accenture.surfershells;

import java.util.Scanner;

public class Commandos {

    public void importCSV(Scanner scanner, String input) {
        if ("IMPORT".equals(input)) {
            System.out.println("INSERT value: ");

            new MySqlConnection().importCSVFileToDB("C:/Users/yasmine.gabel/Desktop/STOCK_DATA_2.csv");
        }

    }

    public void delete(Scanner scanner, String input) {
        if ("DELETE".equals(input)) {
            System.out.println("Delete value: ALL ");

            new MySqlConnection().truncateTable("price_per_date");
            new MySqlConnection().truncateTable("industry");
            new MySqlConnection().truncateTable("stockname");

            System.out.println("All values have been deleted!");
        }

    }

}
