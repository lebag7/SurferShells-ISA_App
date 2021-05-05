package com.accenture.surfershells;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        
        Scanner scanner = new Scanner(System.in);
        Boolean exit = true;
        while (exit) {
            System.out.println("Welcome to ISA! Available commands: \"IMPORT\" | \"DELETE\" | \"EXIT\" | \"SEARCH\" | \"ADD\"");
            String input = scanner.nextLine();
            new Commandos().importCSV(scanner, input);
            new Commandos().delete(scanner, input);
            new Commandos().searchStockname(scanner, input);
            new Commandos().addPrice(scanner, input);

            if ("EXIT".equals(input)) {
                System.out.println("Exit program: ");
                exit = false;
                scanner.close();
            }
        }
    }
}
