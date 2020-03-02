package textExcel;

// Update this file with your own code.

// TODO: Delete line under (not needed for production)
import java.util.Arrays;

public class Spreadsheet implements Grid {
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
		if (command.length() == 0)
			return getGridText();
		// Text cleaning
		if (command.charAt(command.length() - 1) == ' ')
			command = command.substring(0, command.lastIndexOf(' '));

		String[] arguments = command.split(" ");
		if (arguments.length > 1) {
			if (arguments[0].equalsIgnoreCase("clear") &&
				SpreadsheetLocation.isLocation(arguments[1])) {
				SpreadsheetLocation selectedCell = new SpreadsheetLocation(arguments[1].toUpperCase());
				sheet[selectedCell.getRow()][selectedCell.getCol()] = new EmptyCell();
			} else if (arguments[1].equals("=")) {
				System.out.println("found equals");
				System.out.println(Arrays.toString(arguments));
				if (SpreadsheetLocation.isLocation(arguments[0].toUpperCase())) {
					System.out.println("modifying");
					SpreadsheetLocation selectedCell = new SpreadsheetLocation(arguments[0].toUpperCase());
					sheet[selectedCell.getRow()][selectedCell.getCol()] = new TextCell(arguments[2]);
				}
			}
		} else if (arguments.length == 1) {
			if (arguments[0].equalsIgnoreCase("clear")) {
				clearSheet();
			} else if (SpreadsheetLocation.isLocation(arguments[0])) {
				return getCell(new SpreadsheetLocation(arguments[0])).fullCellText();
			}
		}
		return getGridText();
	}

	@Override
	public int getRows() {
		// TODO Auto-generated method stub
		return 20;
	}

	@Override
	public int getCols() {
		// TODO Auto-generated method stub
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
			output += c + "          |";
		output += "\n";
		// Building main Cells
		for (int row = 0; row < 20; row++){
			output += (row + 1) + " ";
			if (row < 9)
				output += " ";
			output += "|";

			for (Cell cell : sheet[row]) {
				String abbreviatedText = cell.abbreviatedCellText();
				output += abbreviatedText;
				for (int i = 0; i < 11 - abbreviatedText.length(); i++)
					output += " ";
				output += "|";
			}
			output += "\n";
		}
		return output + "\n";
	}

}
