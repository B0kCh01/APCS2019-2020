/*======== // Nathan Choi // =========|
|  Last updated: 4.20.20              |
|                                     |
|  About this file:                   |
|    * APCS 3rd Period                |
|    * SpreadSheet.java               |
|    * Stores cells and does cmds     |
|------------------------------------*/
package textExcel;

public class Spreadsheet implements Grid {
	private Cell[][] sheet;

	public Spreadsheet() {
		sheet = new Cell[20][12];
		clearSheet();
	}
	@Override
	public String processCommand(String command) {
		try { // Catching StackOverflow (circular referencing)
			// If the command is nothing, return nothing
			if (command.length() == 0) return "";

			String[] arguments = command.split(" ", 3);
			// More than one word command
			if (arguments.length > 1) {
				// If the command is "clear <Location>"
				if (arguments[0].equalsIgnoreCase("clear")) {
					if (!SpreadsheetLocation.isLocation(arguments[1]))
						return "ERROR: Not a valid cell at \"clear <cell>\"";
					SpreadsheetLocation selectedCell = new SpreadsheetLocation(arguments[1].toUpperCase());
					sheet[selectedCell.getRow()][selectedCell.getCol()] = new EmptyCell();
				}
				// If the command is "<cell> = <value>"
				else if (arguments[1].equals("=")) {
					// Check for invalid formatting
					if (arguments.length == 2 || arguments[2].equals(""))
						return "ERROR: No value specified at \"<cell> = <value>\"";

					// Check if <cell> is a location, then store it in a location object
					if (!SpreadsheetLocation.isLocation(arguments[0]))
						return "ERROR: Not a valid cell at \"<cell> = <value>\"";
					SpreadsheetLocation selectedCell = new SpreadsheetLocation(arguments[0]);

					// If <value> is a number
					if (isRealValue(arguments[2])) {
						if (arguments[2].contains("%")) {
							// Remove the "%" at the end
							double fullNum = Double.parseDouble(arguments[2].substring(0, arguments[2].length() - 1));
							sheet[selectedCell.getRow()][selectedCell.getCol()] = new PercentCell(fullNum);
						} else {
							double fullNum = Double.parseDouble(arguments[2]);
							// If the number is an integer
							if (!isDouble(arguments[2]))
								sheet[selectedCell.getRow()][selectedCell.getCol()] = new ValueCell((int) fullNum);
							// If the number is a double
							else
								sheet[selectedCell.getRow()][selectedCell.getCol()] = new ValueCell(fullNum);
						}
					}
					// If <value> is meant for formulas
					else if (FormulaCell.isFormula(arguments[2])) {
						sheet[selectedCell.getRow()][selectedCell.getCol()] = new FormulaCell(selectedCell, this, arguments[2]);
						String errorCheck = ((FormulaCell) sheet[selectedCell.getRow()][selectedCell.getCol()]).getLog();
						if (errorCheck.startsWith("ERROR"))
							return errorCheck;
					}
					// If <value> is meant for TextCell (anything else)
					else {
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
					return "ERROR: Not a valid command!";
			}

			// return grid if no errors found (except circular referencing)
			return getGridText();
		} catch (StackOverflowError e) {
			return "ERROR: Your cells refer to one another infinitely.";
		}
	}

	// ==== [ Getters ] ==== //
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
		// Check for numeric characters
		String acceptable = "0123456789.-%";
		char[] characters = input.toCharArray();

		if (input.equals("")) return false;
		for (char c : characters)
			if (!acceptable.contains(c + ""))
				return false;
		if (occurrence(input, '.') > 1)
			return false;
		// Check if there is one negative sign in the front
		if (occurrence(input, '-') == 1 && input.charAt(0) != '-')
			return false;
		// Check if there is one percent sign at the back
		if (occurrence(input, '%') == 1 && input.charAt(input.length() - 1) != '%')
			return false;
		// Passed all invalid checks --> it's numeric!
		return true;
	}
	// Checks for numbers
	private boolean hasOnlyNumbers(String input) {
		for (char c : input.toCharArray())
			if (c < '0' || c > '9')
				return false;
		return true;
	}
	// Replaces all cells with EmptyCells
	private void clearSheet() {
		for (int row = 0; row < 20; row++)
			for (int cell = 0; cell < 12; cell++)
				sheet[row][cell] = new EmptyCell();
	}
}
