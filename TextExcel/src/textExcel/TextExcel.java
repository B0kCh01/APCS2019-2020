/*======== // Nathan Choi // =========|
|  Last updated: 4.20.20              |
|                                     |
|  About this file:                   |
|    * APCS 3rd Period                |
|    * TextExcel.java                 |
|    * TextExcel Client Code (main)   |
|-------------------------------------*/
package textExcel;

import java.util.Scanner;


public class TextExcel {
	public static void main(String[] args) {
		// Creating scanner object and spreadsheet object
		Scanner sc = new Scanner(System.in);
		Spreadsheet currentSheet = new Spreadsheet();

	    // Print title screen once
		System.out.println("<=======================================>");
		System.out.println(">  TEXT EXCEL by b0kch01 (Nathan Choi)  <");
		System.out.println("<=======================================>");

		System.out.println("\n1. Hello, welcome! Here are the basic commands:");
		System.out.println("---------------------------------------------");
		System.out.println("  * (set a cell a value) | <cell> = <#, %, \"\", (f)>");
		System.out.println("  * (get a cell's value) | <cell>");
		System.out.println("  * (clear a cell)       | clear <cell>");
		System.out.println("  * (clear whole sheet)  | clear");
		System.out.println("  * (exit the program)   | quit");

		System.out.println("\n2. Here are the formula commands:");
		System.out.println("---------------------------------------------");
		System.out.println("  * (eval. Expressions)  | <cell> = (-1-2*4/2) ~> [-5]");
		System.out.println("  * (refer to cells)     | <cell> = (-<cell> * 2 + 8)");
		System.out.println("  * (calculate sum)      | <cell> = (SUM <cell>-<cell>)");
		System.out.println("  * (calculate mean)     | <cell> = (AVG <cell>-<cell>)");

		// Main Loop
		System.out.print("\n[ Command ]: ");
		for (
			String input = sc.nextLine(); // Start input as first input
			!input.equalsIgnoreCase("quit"); // Repeat until input is "quit"
			input = sc.nextLine() // Ask again each iteration
		) {
			System.out.println(currentSheet.processCommand(input)); // Print result
			System.out.print("[ Command (\"quit\" to exit) ]: ");
		}
	}
}
