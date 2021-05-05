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
            System.out.println("Delete all values from Database: ");

            new MySqlConnection().truncateTable("price_per_date");
            new MySqlConnection().truncateTable("industry");
            new MySqlConnection().truncateTable("stockname");
    
            System.out.println("All values have been deleted!");
        }

    }

    public void searchStockname(Scanner scanner, String input) {
        if ("SEARCH".equals(input)) {
            System.out.println("Type in the first characters to search for a stockname");
            String inputSearchString = scanner.nextLine();
            new MySqlConnection().searchId(inputSearchString);
        }

    }

    public void addPrice(Scanner scanner, String input) {
        if ("ADD".equals(input)) {
            System.out.println("Type in ID:");
            String inputID = scanner.nextLine();
            System.out.println("Type in date:");
            String inputDate = scanner.nextLine();
            System.out.println("Type in Price:");
            String inputPrice = scanner.nextLine();
            new MySqlConnection().addRow(inputID,inputPrice,inputDate);
        }

    }
    

}
