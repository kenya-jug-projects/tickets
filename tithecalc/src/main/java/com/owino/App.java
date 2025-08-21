package com.owino;
import java.util.Scanner;
public class App {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.println("Welcome to Tithe Calc...");
        System.out.println("What is your name?");
        // do...while...success
        // WORA...jar
        boolean success = false;
        var name = scanner.nextLine();
        do {
            System.out.println("Enter Your Recent Income");
            if (scanner.hasNext()){
                String input = scanner.next();
                try {
                    float income = Float.parseFloat(input);
                    var tithe = new TitheCalculatorCore().getTithe(income);
                    var outputAmount = String.format("Kes. %.2f",tithe);
                    System.out.println("%s Your mandated tithe is " + outputAmount);
                    success = true;
                } catch (NumberFormatException ex){
                    var errorMsg = String.format("System failure! Invalid input %s! Use a number",input);
                    System.err.println(errorMsg);
                }
            } else {
                System.err.println("Failed tot calculate tithe...unknown error");
            }
        } while(!success);
    }
}
