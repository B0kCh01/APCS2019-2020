package textExcel;

// Update this file with your own code.

import java.util.Arrays;

public class Spreadsheet implements Grid {
	private boolean DEBUG = true;
	private Cell[][] sheet;

	public Spreadsheet() {
		this.sheet = new Cell[20][12];
		clearSheet();
	}

	private void clearSheet() {
		for (int row = 0; row < 20; row++)
			for (int cell = 0; cell < 12; cell++)
				sheet[row][cell] = new EmptyCell();
	}

	@Override
	public String processCommand(String command) {
		// If the command is nothing, return nothing
        if (command.length() == 0) return "";

		String[] arguments = command.split(" ", 3);

		if (DEBUG) System.out.println("Input[] = " + Arrays.toString(arguments));

		// More than one word command
		if (arguments.length > 1) {
			// If the command is "clear <Location>"
			if (arguments[0].equalsIgnoreCase("clear")) {
				if (!SpreadsheetLocation.isLocation(arguments[1]))
					return "ERROR: Not a valid cell at \"clear <cell>\"";
				SpreadsheetLocation selectedCell = new SpreadsheetLocation(arguments[1].toUpperCase());
				sheet[selectedCell.getRow()][selectedCell.getCol()] = new EmptyCell();
			// If the command is "<Location> = <value>"
			} else if (arguments[1].equals("=")) {
				if (!SpreadsheetLocation.isLocation(arguments[0]))
					return "ERROR: Not a valid cell at \"<cell> = <value>\"";
				SpreadsheetLocation selectedCell = new SpreadsheetLocation(arguments[0]);

				// If <value> has only number valid formatting
				if (isRealValue(arguments[2])) {
					if (DEBUG) System.out.println("It is a number!");
					if (arguments[2].contains("%")) {
						double fullNum = Double.parseDouble(arguments[2].substring(0, arguments[2].length() - 1));
						sheet[selectedCell.getRow()][selectedCell.getCol()] = new PercentCell(fullNum);
					} else {
						double fullNum = Double.parseDouble(arguments[2]);
						// If the number is an integer
						if (!isDouble(arguments[2])) {
							sheet[selectedCell.getRow()][selectedCell.getCol()] = new ValueCell((int) fullNum);
						}
						// If the number is a double
						else {
							sheet[selectedCell.getRow()][selectedCell.getCol()] = new ValueCell(fullNum);
						}
					}
				}
				// If <value> is meant for formulas
				else if (FormulaCell.isFormula(arguments[2])) {
					if (DEBUG) System.out.println("Passed isFormula: it's a formula!");
					sheet[selectedCell.getRow()][selectedCell.getCol()] = new FormulaCell(arguments[2]);

					FormulaCell testlog = (FormulaCell) sheet[selectedCell.getRow()][selectedCell.getCol()];
					if (testlog.getLog().contains("ERROR:"))
						return testlog.getLog();
				}
				// If <value> is meant for TextCell
				else {
					if (DEBUG) System.out.println("Failed other checks: it's text!");
					sheet[selectedCell.getRow()][selectedCell.getCol()] = new TextCell(arguments[2]);
				}
			}
		// One worded command
		} else if (arguments.length == 1) {
			if (arguments[0].equalsIgnoreCase("clear"))
				clearSheet();
			else if (SpreadsheetLocation.isLocation(arguments[0]))
				return getCell(new SpreadsheetLocation(arguments[0])).fullCellText();
			else
				return "ERROR: No a valid command!";
		}
		return getGridText();
	}

	@Override
	public int getRows() {
		return 20;
	}
	@Override
	public int getCols() {
		return 12;
	}
	// Returns the cell given a location
	@Override
	public Cell getCell(Location loc) {
		int col = loc.getCol();
		int row = loc.getRow();

		return sheet[row][col];
	}
	// Returns a string of the board
	@Override
	public String getGridText() {
		String output = "   |";
		// Building Header
		for (char c = 'A'; c <= 'L'; c++)
			output += c + "         |";
		output += "\n";
		// Building main Cells
		for (int row = 0; row < 20; row++){
			output += (row + 1) + " ";
			if (row < 9)
				output += " ";
			output += "|";

			for (Cell cell : sheet[row]) {
				String abbreviatedText = cell.abbreviatedCellText();
				output += abbreviatedText + "|";
			}
			output += "\n";
		}
		return output;
	}

	// ====[ Private Methods ]===== //
	// Gets the amount of characters in a string
	private int occurrence(String string, char character) {
		char[] characters = string.toCharArray();
		int i = 0;

		for (char c : characters)
			if (c == character)
				i++;

		return i;
	}
	// Check if a string can be an integer
	private boolean isDouble(String number) {
		double numericNumber = Double.parseDouble(number);
		// Integer overflow
		if (numericNumber <= -2147483648.0 || numericNumber >= 2147483649.0)
			return true;
		return number.contains(".");
	}
	// Check if the String can be numeric
	private boolean isRealValue(String input) {
		if (DEBUG) System.out.printf("Checking if %s is Numeric:\n", input);
		// Check for numeric characters
		String acceptable = "0123456789.-%";
		char[] characters = input.toCharArray();

		for (char c : characters)
			if (!acceptable.contains(c + ""))
				return false;
		if (DEBUG) System.out.println("  * Passed whitelist test!");

		if (occurrence(input, '.') > 1)
			return false;
		if (DEBUG) System.out.println("  * Passed decimal point test!");
		// Check if there is one negative sign in the front
		if (occurrence(input, '-') == 1 && input.charAt(0) != '-')
			return false;
		if (DEBUG) System.out.println("  * Passed negative test!");
		// Check if there is one percent sign at the back
		if (occurrence(input, '%') == 1 && input.charAt(input.length() - 1) != '%')
			return false;
		if (DEBUG) System.out.println("  * Passed percent test!");

		// Passed all invalid checks --> it's numeric!
		if (DEBUG) System.out.println(input + " is a real value!\n");
		return true;
	}
	// Checks for numbers
	private boolean hasOnlyNumbers(String input) {
		for (char c : input.toCharArray())
			if (c < '0' || c > '9')
				return false;
		return true;
	}
}
