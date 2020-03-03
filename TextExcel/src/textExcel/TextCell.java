package textExcel;

public class TextCell implements Cell{
    private String cellText;

    public TextCell() {
        cellText = "";
    }

    public TextCell(String input) {
        cellText = input;
    }

    @Override
    public String abbreviatedCellText() {
        String output = cellText;
        if (output.length() < 10)
            for (int i = 0; i < 11 - cellText.length(); i++)
                output += " ";
        return output.substring(0, 10);
    }

    @Override
    public String fullCellText() {
        return cellText;
    }
}
