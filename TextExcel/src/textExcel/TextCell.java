/*======== // Nathan Choi // ==========|
|  Last updated: 4.20.20               |
|                                      |
|  About this file:                    |
|    * APCS 3rd Period                 |
|    * TextCell.java                   |
|    * Cell that stores strings        |
|-------------------------------------*/
package textExcel;

public class TextCell implements Cell{
    private String cellText;

    public TextCell(String input) {
        cellText = getTextBetweenQuotes(input, '"');;
    }
    @Override
    public String abbreviatedCellText() {
        return (cellText + "          ").substring(0, 10);
    }
    @Override
    public String fullCellText() {
        return "\"" + cellText + "\"";
    }
    // Gets text between two quotes
	private String getTextBetweenQuotes(String input, char c) {
		if ( input.startsWith("\"") && input.endsWith("\"") )
			return input.substring(1, input.length() - 1);
		return input;
	}
}
