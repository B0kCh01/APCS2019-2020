// Nathan Choi
// September 24, 2019
// APCS 3rd
// QuadraticClient.java -> Uses the Quadratic Class File

package Client;

import Methods.*;

import java.util.Scanner;

public class QuadraticClient {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            runDescriber();
            if (!menuSelect().equals("1"))
                exit = true;
        }
        System.out.println("See ya!");
    }

    // "Renders" Menu selection
    private static String menuSelect() {
        scrollUp();
        System.out.println("--------------------");
        System.out.println("What would you like to do?");
        System.out.print("[1]: New Quadratic\n[2]: Exit\n\nChoice: ");

        String choice;
        boolean pass = false;
        do {
            choice = sc.nextLine();
            if (choice.equals("1") || choice.equals("2")) {
                pass = true;
            } else {
                scrollUp();
                System.out.println("What you gave me: " + choice);
                System.out.println("--------------------");
                System.out.println("Enter [1] or [2] only please.");
                System.out.print("\n[1]: New Quadratic\n[2]: Exit\n\nChoice: ");
            }
        } while (!pass);
        return choice;
    }

    // Main function
    private static void runDescriber() {
        scrollUp();
        System.out.println("Welcome to QuadraticDescriber");
        System.out.println("\nProvide coefficients for a b c (Enter any real number)");
        System.out.println("--------------------");

        double a = getDouble("a: ");
        double b = getDouble("b: ");
        double c = getDouble("c: ");

        scrollUp();
        System.out.println("\nDescription of the graph:");
        System.out.println(Quadratic.quadrDescriber(a, b, c));
        System.out.println(Quadratic.quadrDetails(a, b, c));

        System.out.println("\nPress [Enter] to continue");
        sc.nextLine();
    }

    // "Jank" substitute for "cls" or "clear"
    private static void scrollUp() {
        for (int i = 0; i < 20; i++)
            System.out.println("\n");
    }

    // Method to get user input (securely)
    private static double getDouble(String prompt) {
        double proposed = 0;
        boolean exit = false;
        while (!exit) {
            // I'm very sorry. This is probably the best way
            try {
                System.out.print(prompt);
                String test = sc.nextLine();
                proposed = Double.parseDouble(test);
                exit = true;
            } catch (NumberFormatException ex) {
                System.out.println("Enter a number please!");
            }
        }
        return proposed;
    }
}