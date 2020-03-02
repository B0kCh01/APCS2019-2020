package textExcel;

//Update this file with your own code.

public class SpreadsheetLocation implements Location {
    private int col, row;

    public SpreadsheetLocation(String cellName) {
        cellName = cellName.toUpperCase();
        char firstChar = cellName.charAt(0);

        this.col = firstChar - 'A'; // Upper case character - 65 ('A' integer value)
        this.row = Integer.parseInt(cellName.substring(1)) - 1;

    }

    public static boolean isLocation(String cellName) {
        // Check for false characters
        String[] characters = cellName.split("");
        for (String s : characters)
            if (!"12345678ABCDEFGHIJKL".contains(s))
                return false;
        int valueOfFirstChar = Character.toUpperCase(cellName.charAt(0)) - 'A';
        if (valueOfFirstChar >= 0 && valueOfFirstChar <= 11)
            if (Integer.parseInt(cellName.substring(1)) < 12)
                return true;
        return false;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        // TODO Auto-generated method stub
        return col;
    }
}
