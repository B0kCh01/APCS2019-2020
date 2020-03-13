package textExcel;

//Update this file with your own code.

public class SpreadsheetLocation implements Location {
    private int col, row;

    public SpreadsheetLocation(String cellName) {
        cellName = cellName.toUpperCase();
        char firstChar = cellName.charAt(0);

        col = firstChar - 'A'; // Upper case character - 65 ('A' integer value)
        row = Integer.parseInt(cellName.substring(1)) - 1;

    }

    public static boolean isLocation(String cellName) {
        cellName = cellName.toUpperCase();
        // Check for false characters
        String[] characters = cellName.split("");
        if (characters.length < 2)
            return false;

        for (String s : characters)
            if (!"012345678ABCDEFGHIJKL".contains(s))
                return false;
        int valueOfFirstChar = cellName.charAt(0) - 'A';
        if (!hasOnlyNumbers(cellName.substring(1)))
            return false;
        int row = Integer.parseInt(cellName.substring(1));
        if (valueOfFirstChar >= 0 && valueOfFirstChar <= 11)
            return row <= 21 && row > 0;
        return false;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    // Checks for numbers
	private static boolean hasOnlyNumbers(String input) {
		for (char c : input.toCharArray())
			if (c < '0' || c > '9')
				return false;
		return true;
	}
}
