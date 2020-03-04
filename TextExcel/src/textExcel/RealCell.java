package textExcel;

public class RealCell implements Cell {
    private double value;

    public RealCell(double input) {
        value = input;
    }

    @Override
    public String abbreviatedCellText() {
        String output = value + "";
        if (output.length() < 10)
            for (int i = 0; i < 11 - output.length(); i++)
                output += " ";
        return output.substring(0, 10);
    }

    @Override
    public String fullCellText() {
        return value + "";
    }
}
