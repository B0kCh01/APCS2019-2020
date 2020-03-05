package textExcel;

// Update this file with your own code.

import java.util.Arrays;

public class Spreadsheet implements Grid {
	public boolean DEBUG = false;
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

		// If whitespace is destroying the split
		if (arguments.length > 2 && arguments[1].equals(""))
			arguments = new String[]{arguments[0], arguments[2]};

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

				// If <value> has only numbers
				if (isNumeric(arguments[2])) {
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
				// If <value> is meant for TextCell
				else {
					if (DEBUG) System.out.println("Failed isNumeric(): it's text!");
					String fullText = getTextBetween(arguments[2], '"');
					sheet[selectedCell.getRow()][selectedCell.getCol()] = new TextCell(fullText);
				}
			}
		// One worded command
		} else if (arguments.length == 1) {
			if (arguments[0].equalsIgnoreCase("clear")) {
				clearSheet();
			} else if (SpreadsheetLocation.isLocation(arguments[0])) {
				return getCell(new SpreadsheetLocation(arguments[0])).fullCellText();
			} else {
				return "ERROR: No a valid command!";
			}
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

	@Override
	public Cell getCell(Location loc) {
		int col = loc.getCol();
		int row = loc.getRow();

		return sheet[row][col];
	}

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
	private String getTextBetween(String input, char c) {
		if (input.charAt(0) == c &&
			input.charAt(input.length() - 1) == c)
			return input.substring(1, input.length() - 1);
		return input;
	}

	private int occurance(String string, char character) {
		char[] characters = string.toCharArray();
		int i = 0;

		for (char c : characters)
			if (c == character)
				i++;

		return i;
	}

	private boolean isDouble(String number) {
		double numericNumber = Double.parseDouble(number);
		// Integer overflow
		if (numericNumber <= -2147483648.0 || numericNumber >= 2147483649.0)
			return true;
		return number.contains(".");
	}

	private boolean isNumeric(String input) {
		if (DEBUG) System.out.printf("Checking if %s is Numeric:\n", input);
		// Check for numeric characters
		String acceptable = "0123456789.-%";
		char[] characters = input.toCharArray();

		for (char c : characters)
			if (!acceptable.contains(c + ""))
				return false;
		if (DEBUG) System.out.println("  * Passed whitelist test!");

		if (input.split(".").length > 2)
			return false;
		if (DEBUG) System.out.println("  * Passed decimal point test!");
		// Check if there is one negative sign in the front
		if (occurance(input, '-') == 1 && input.charAt(0) != '-')
			return false;
		if (DEBUG) System.out.println("  * Passed negative test!");
		// Check if there is one percent sign at the back
		if (occurance(input, '%') == 1 && input.charAt(input.length() - 1) != '%')
			return false;
		if (DEBUG) System.out.println("  * Passed percent test!");

		// Passed all invalid checks --> it's numeric!
		if (DEBUG) System.out.println(input + " is a real value!\n");
		return true;
	}
}
