/**
 * Nathan Choi
 * APCS 3rd Period
 * Feburary 25, 2020
 * Bootleg Visicalc
 */

package textExcel;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

// Update this file with your own code.

public class TextExcel {
	public static void main(String[] args) {
		// Creating scanner object and spreadsheet object
		Scanner sc = new Scanner(System.in);
		Spreadsheet currentSheet = new Spreadsheet();

	    // Print title screen once
		System.out.println("|=========================|");
		System.out.println("|  TEXT EXCEL by b0kch01  |");
		System.out.println("|=========================|");

		System.out.println("\nHello, welcome!");
		System.out.println("Enter a command below or press \"quit\" to exit.");

		// Main Loop
		System.out.print("\n[ Command ]: ");
		for (
			String input = sc.nextLine(); // Start by initializing input as first input
			!input.equalsIgnoreCase("quit"); // Keep on running look until "quit" is entered
			System.out.print("[ Command ]: "), // After each iteration, print carrot and get user input again
			input = sc.nextLine()
		) {
			System.out.println(currentSheet.processCommand(input)); // Print Spreadsheet (and error)
		}
	}
}
