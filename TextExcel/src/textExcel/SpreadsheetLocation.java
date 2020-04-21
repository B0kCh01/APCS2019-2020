/*======== // Nathan Choi // ==========|
|  Last updated: 4.20.20               |
|                                      |
|  About this file:                    |
|    * APCS 3rd Period                 |
|    * SpreadsheetLocation.java        |
|    * Location helper for a sheet     |
|-------------------------------------*/
package textExcel;

public class SpreadsheetLocation implements Location {
    private int col, row;

    public SpreadsheetLocation(String cellName) {
        col = cellName.toUpperCase().charAt(0) - 'A'; // Upper case character - 65 ('A' integer value)
        row = Integer.parseInt(cellName.substring(1)) - 1;
    }
    @Override
    public int getRow() { return row; }
    @Override
    public int getCol() { return col; }

    //====[ Public Methods]====//
    public static boolean isLocation(String cellName) {
        cellName = cellName.toUpperCase();
        // Check for false characters
        String[] characters = cellName.split("");
        if (characters.length < 2)
            return false;

        for (String s : characters)
            if (!"0123456789ABCDEFGHIJKL".contains(s))
                return false;
        int valueOfFirstChar = cellName.charAt(0) - 'A';
        if (!hasOnlyNumbers(cellName.substring(1)))
            return false;
        int row = Integer.parseInt(cellName.substring(1));
        if (valueOfFirstChar >= 0 && valueOfFirstChar <= 11)
            return row <= 21 && row > 0;
        return false;
    }

    //====[ Private Methods ]====//
	private static boolean hasOnlyNumbers(String input) {
		for (char c : input.toCharArray())
			if (c < '0' || c > '9') return false;
		return true;
	}
}
