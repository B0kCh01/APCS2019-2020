// Nathan Choi
// August - 2019
// PrintingThings.java -> Printing various symbols and drawings

public class PrintingThings {
	// Method to print Pikachu Ascii Art
	public static void printPika() {
		System.out.println("Welcome to the world of pikachu!");
		System.out.println("\t  (\\_/)");
		System.out.println("\t  (^.^)");
		System.out.println("\t z(\")(\")");
	}

	// Method to print three ascii boxes
	public static void printBoxes() {
		System.out.println("\nHere are three boxes:");
		for (int i = 0; i < 3; i++) {
			System.out.println("+-------+");
			System.out.println("|       |");
			System.out.println("|       |");
			System.out.println("|       |");
			System.out.println("+-------+\n");
		}
	}

	// Main method
	public static void main(String[] args) {
		// Call the methods to print the art
		printPika();
		printBoxes();
	}
}