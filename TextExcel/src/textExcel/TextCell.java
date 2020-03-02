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
        if (cellText.length() < 10)
            return cellText;
        return cellText.substring(0, 10);
    }

    @Override
    public String fullCellText() {
        return "\"" + cellText + "\"";
    }
}
