package com.accenture.surfershells;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Boolean exit = true;

        System.out.println( "|*****************|");
        System.out.println( "| Welcome to ISA! |");
        System.out.println( "|*****************|");

        while (exit) {
            System.out.println(
                    "Available commands: \"IMPORT\" | \"DELETE\" | \"EXIT\" | \"SEARCH\" | \"ADD\" | \"SHOW\"  | \"MAX\" | \"MIN\" | \"GAP\" | \"UPDATE-INDUSTRY\" | \"INDUSTRIES\"| \"EXPORT\"");
           
            String input = scanner.nextLine();
            new Commandos().importCSV(input);
            new Commandos().delete(input);
            new Commandos().searchStock(scanner, input);
            new Commandos().addPrice(scanner, input);
            new Commandos().showStockValues(scanner, input);
            new Commandos().maxStockValue(scanner, input);
            new Commandos().minStockValue(scanner, input);
            new Commandos().gapStockValue(scanner, input);
            new Commandos().updateIndustry(scanner, input);
            new Commandos().showIndustries( input);
            new Commandos().exportDB(input);

            if ("EXIT".equals(input)) {
                System.out.println("Exit program: ");
                exit = false;
                scanner.close();
            }
        }
    }
}
