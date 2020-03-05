package textExcel;

public class FormulaCell extends RealCell {
    private String formula;

    public FormulaCell(String input) {
        super(0);
        setDouble(compute(input));
        formula = input;
    }

    @Override
    public String fullCellText() {
        return formula;
    }

    private double compute(String input) {
        return 0;
    }

    public static boolean isFormula(String value) {
        return value.charAt(0) == '(' && value.charAt(value.length() - 1) == ')';
    }
}

