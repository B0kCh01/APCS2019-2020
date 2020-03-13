package textExcel;

import java.util.ArrayList;

public class FormulaCell extends RealCell {
    private boolean DEBUG = true;
    private String formula;
    private String log;
    private Spreadsheet sheet;

    public FormulaCell(Spreadsheet inputSheet, String input) {
        super(0);
        log = compute(input);
        formula = input;
        sheet = inputSheet;
    }

    @Override
    public String fullCellText() {
        return formula;
    }

    @Override
    public String abbreviatedCellText() {
        return super.abbreviatedCellText(getDouble() + "");
    }

    @Override
    public double getDouble() {
        return Double.parseDouble(compute(formula));
    }

    public String getLog() {
        return log;
    }

    private String compute(String input) {
        String[][] operands = {{"*", "/"}, {"+", "-"}};
        String[] commands = {"sum", "avg"};

        String expression = input.substring(1, input.length() - 1);
        System.out.println("\nExpression: " + expression);
        ArrayList<String> structuredExpression = new ArrayList<>();

        // Checks for formatting errors (not order)
        for (int index = 0; index < expression.length();) {
            boolean stop = false;
            boolean isUnverified = false;
            boolean alreadyHasMethod = false;
            String testString = "";

            while (!stop && index < expression.length()) {
                String s = expression.charAt(index) + "";

                if (s.equals(" ")) {
                    if (isUnverified)
                        return "ERROR: \"" + testString + "\" cannot be used in formula.";
                }
                // If c is a possible operand
                else if (contains(operands, s)) {
                    if (isUnverified)
                        return "ERROR: \"" + testString + "\" cannot be used in formula.";
                    if (contains(operands, expression.charAt(index + 1) + ""))
                        return "ERROR: Found two operands adjacent.";
                    testString += s;
                    stop = true;
                }
                // C is a regular term
                else {
                    isUnverified = true;
                    testString += s;

                    if (SpreadsheetLocation.isLocation(testString))
                        stop = true;
                    else if (contains(commands, testString))
                        if (alreadyHasMethod)
                            return "ERROR: You can only use one method per formula.";
                        else {
                            stop = true;
                            alreadyHasMethod = true;
                        }
                    else if (isRealValue(testString))
                        if (index < expression.length() - 1 &&
                            !isRealValue(testString + expression.charAt(index + 1)) ||
                            index == expression.length() - 1) {
                        stop = true;
                    }
                }
                index++;
            }
            structuredExpression.add(testString);
        }
        if (DEBUG) System.out.println(structuredExpression.toString());

        for (int index = 0, // Initialize two things: index and size of the list
             size = structuredExpression.size();
             index < structuredExpression.size(); // Keep running until index is out of bounds
             size = structuredExpression.size(), index++) // Update size every iteration and increase index
        {
            String item = structuredExpression.get(index);

            if (contains(commands, structuredExpression.get(index))) {
                if (index + 2 >= size)
                    return "ERROR: Invalid or missing range; it is " + item + " <cell>-<cell>";

                String start = structuredExpression.get(index + 1);
                String dash = structuredExpression.get(index + 2);
                String end = structuredExpression.get(index + 3);

                if (!isRange(start, dash, end))
                    return "ERROR: The selected range of cells are invalid!";
                if (!SpreadsheetLocation.isLocation(start) ||
                    !SpreadsheetLocation.isLocation(end))
                    return "ERROR: Invalid range; it is " + item + " <cell>-<cell>";
                return "ERROR: Methods not supported ->" + item;
            }
            else if (contains(operands, structuredExpression.get(index))) {
                if (index + 1 >= size ||
                    index == 0)
                    return "ERROR: Operator is not surrounded by values or cells.";

                double[] nums = new double[2];
                String operand = structuredExpression.get(index);

                for (int i = 0; i < 2; i++) {
                    String num = structuredExpression.get(index -1 + i*2);
                    if (isRealValue(num))
                        nums[i] = Double.parseDouble(num);
                    else if (SpreadsheetLocation.isLocation(num)) {
                        Cell possibleRealCell = sheet.getCell(new SpreadsheetLocation(num));
                        if (possibleRealCell instanceof RealCell) {
                            RealCell confirmedRealCell = (RealCell) possibleRealCell;
                            nums[i] = confirmedRealCell.getDouble();
                        }
                    }
                }
            }
         }
        return structuredExpression.get(0);
    }

    public static boolean isFormula(String input) {
        if (input.charAt(0) == '(' && input.charAt(input.length() - 1) == ')')
            return !input.substring(1, input.length() - 1).contains("(") &&
                   !input.substring(1, input.length() - 1).contains(")");
        return false;
    }

    private static boolean contains(String[][] strArray, String s) {
        for (String[] list : strArray)
            for (String match : list)
                if (match.equalsIgnoreCase(s))
                    return true;
        return false;
    }

    private static boolean contains(String[] strArray, String s) {
        for (String match : strArray)
            if (match.equalsIgnoreCase(s))
                return true;
        return false;
    }

    private int occurrence(String string, char character) {
		char[] characters = string.toCharArray();
		int i = 0;

		for (char c : characters)
			if (c == character)
				i++;
		return i;
	}

    private boolean isRealValue(String input) {
		// Check for numeric characters
		String acceptable = "0123456789.-";
		char[] characters = input.toCharArray();

		for (char c : characters)
			if (!acceptable.contains(c + ""))
				return false;

		if (occurrence(input, '.') > 1)
			return false;
		// Check if there is one negative sign in the front
		if (occurrence(input, '-') == 1 && input.charAt(0) != '-')
			return false;

		// Passed all invalid checks --> it's numeric!
		return true;
	}

	private boolean isRange(String start, String dash, String end) {
        return SpreadsheetLocation.isLocation(start) &&
               SpreadsheetLocation.isLocation(end) &&
               dash.equals("-");
    }
}

