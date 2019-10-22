// Nathan Choi
// APCS 3rd
// October 12, 2019
// Create an Hour Glass Program


/*
 *  For the main, method, I used a bunch of println and print, but the hourglass
 *  building itself only took 10 prints.
 */

import java.util.Scanner;

public class Hourglass {
    public static void main(String[] args) {
        // Create new Scanner Object and print welcome message
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello! Welcome to Hourglass.java!\n");
        System.out.println("Enter the width of the hourglass, and I'll make one for ya! ");

        // Prompt user for the width of the hourglass
        System.out.print("Your length (I love big numbers): ");
        int width = sc.nextInt();

        if (width > 300) {
            System.out.println(
                    "\nOh my! That's a really big number. \n[ Enter ] \"yes!\" if you are sure.");
            System.out.print("> ");
            sc.nextLine();
            String sure = sc.nextLine();
            if (sure.equals("yes!")) {
                // Build and print the hour glass with the given width
                buildHGlass(width);
            } else {
                System.out.println("\nYou weren't sure.");
            }
        } else {
            buildHGlass(width);
            System.out.println("\nTa Da!");
        }
    }

    private static void buildHGlass(int width) {
        buildTop(width);
        repeatThis(width - 1, " ");
        System.out.println("|   |"); // Size of middle is fixed
        buildBottom(width);
    }

    private static void buildTop(int width){
        System.out.print("\n\n| ");
        repeatThis(width, "\" ");
        System.out.println("|");

        for (int row = 1; row <= (width - 2) / 2; row++) {
            repeatThis(row, "  ");
            System.out.print("\\ ");
            repeatThis(-2*row+width, ": ");
            System.out.println("/");
        }
    }

    private static void buildBottom(int width) {
        for (int row = (width - 2) / 2; row >= 1; row--) {
            repeatThis(row, "  ");
            System.out.print("/ ");
            repeatThis(-2*row+width, ": ");
            System.out.println("\\ ");
        }

        System.out.print("| ");
        repeatThis(width, "\" ");
        System.out.println("|");
    }

    private static void repeatThis(int times, String character) {
        for (int i = 0; i < times; i++)
            System.out.print(character);
    }
}
