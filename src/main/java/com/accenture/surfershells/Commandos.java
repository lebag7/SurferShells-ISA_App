package com.accenture.surfershells;

import java.util.Scanner;

public class Commandos {

    public void importCSV(String input) {
        if ("IMPORT".equals(input)) {
            new DBManipulateSQL().importCSVFileToDB_SQL("C:/Users/yasmine.gabel/Desktop/STOCK_DATA_3.csv");
        }

    }

    public void delete(String input) {
        if ("DELETE".equals(input)) {
            new DBManipulateSQL().deleteAllValuesOfTable("price_per_date");
            new DBManipulateSQL().deleteAllValuesOfTable("industry");
            new DBManipulateSQL().deleteAllValuesOfTable("stock");

            System.out.println("All values have been deleted!");
        }

    }

    public void searchStock(Scanner scanner, String input) {
        if ("SEARCH".equals(input)) {
            System.out.println("Type in the first characters to search for a stock:");
            String inputSearchString = scanner.nextLine();
            new DBViewSQL().searchIdSQL(inputSearchString);
        }

    }

    public void addPrice(Scanner scanner, String input) {
        if ("ADD".equals(input)) {
            System.out.println("Type in ID of stock:");
            String inputStocknameID = scanner.nextLine();
            System.out.println("Type in date:");
            String inputDate = scanner.nextLine();
            System.out.println("Type in price:");
            String inputPrice = scanner.nextLine();
            new DBManipulateSQL().addRowSQL(inputStocknameID, inputPrice, inputDate);
        }

    }

    public void showStockValues(Scanner scanner, String input) {
        if ("SHOW".equals(input)) {
            System.out.println("Type in ID of stock:");
            String inputID = scanner.nextLine();
            new DBViewSQL().showStockValuesSQL(inputID);
        }

    }

    public void maxStockValue(Scanner scanner, String input) {
        if ("MAX".equals(input)) {
            System.out.println("Type in ID of stock:");
            String inputID = scanner.nextLine();
            new DBViewSQL().maxSQL(inputID);
        }

    }

    public void minStockValue(Scanner scanner, String input) {
        if ("MIN".equals(input)) {
            System.out.println("Type in ID of stock:");
            String inputID = scanner.nextLine();
            new DBViewSQL().minSQL(inputID);
        }

    }

    public void gapStockValue(Scanner scanner, String input) {
        if ("GAP".equals(input)) {
            System.out.println("Type in ID of stock:");
            String inputID = scanner.nextLine();
            new DBViewSQL().gapSQL(inputID);
        }

    }

    public void updateIndustry(Scanner scanner, String input) {
        if ("UPDATE-INDUSTRY".equals(input)) {
            System.out.println("Type in ID of industry:");
            String inputID = scanner.nextLine();
            System.out.println("Type in industry name for update:");
            String inputIndustry = scanner.nextLine();
            new DBManipulateSQL().updateIndustrySQL(inputID, inputIndustry);
        }

    }

    public void showIndustries(String input) {
        if ("INDUSTRIES".equals(input)) {
            new DBViewSQL().showIndustriesSQL();
        }

    }

    public void exportDB(String input) {
        if ("EXPORT".equals(input)) {
            new DBExportSQL().exportDB_SQL();
        }

    }
    
}
