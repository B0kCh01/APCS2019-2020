package textExcel;

public class PercentCell extends RealCell {
    public PercentCell(double input) {
        super(input / 100.0);
    }

    @Override
    public String abbreviatedCellText() {
        String stringValue = getDouble() * 100 + "";
        String output =  stringValue.substring(0, stringValue.indexOf('.')) + "%";
        int initLength = output.length();
        if (output.length() < 10)
            for (int i = 0; i < 10 - initLength; i++)
                output += " ";
        return output.substring(0, 10);
    }
}
