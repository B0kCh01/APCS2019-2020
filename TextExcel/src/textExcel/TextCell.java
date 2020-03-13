package textExcel;

public class TextCell implements Cell{
    private String cellText;

    public TextCell() {
        cellText = "";
    }

    public TextCell(String input) {
        cellText = getTextBetween(input, '"');;
    }

    @Override
    public String abbreviatedCellText() {
        String output = cellText;
        int initLength = output.length();
        if (output.length() < 10)
            for (int i = 0; i < 10 - initLength; i++)
                output += " ";
        return output.substring(0, 10);
    }

    @Override
    public String fullCellText() {
        return "\"" + cellText + "\"";
    }

    // Gets text between two chars
	private String getTextBetween(String input, char c) {
		if (input.charAt(0) == c &&
			input.charAt(input.length() - 1) == c)
			return input.substring(1, input.length() - 1);
		return input;
	}
}
