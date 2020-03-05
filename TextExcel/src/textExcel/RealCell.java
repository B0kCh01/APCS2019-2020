package textExcel;

public class RealCell implements Cell {
    private double value;
    private boolean isInt;

    public RealCell(int input) {
        isInt = true;
        value = input;
    }

    public RealCell(double input) {
        isInt = false;
        value = input;
    }

    @Override
    public String abbreviatedCellText() {
        String output = value + "";
        int initLength = output.length();
        if (output.length() < 10)
            for (int i = 0; i < 10 - initLength; i++)
                output += " ";
        return output.substring(0, 10);
    }

    @Override
    public String fullCellText() {
        String output = value + "";
        if (isInt)
            return output.substring(0, output.length() - 2);
        return output;
    }

    double getDouble() {
        return value;
    }

    void setDouble(double value) {
        this.value = value;
    }
}
