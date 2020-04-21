/*======== // Nathan Choi // =========|
|  Last updated: 4.20.20              |
|                                     |
|  About this file:                   |
|    * APCS 3rd Period                |
|    * FormulaCell.java               |
|    * Processes formula commands     |
|-------------------------------------*/
package textExcel;

import java.util.ArrayList;

public class FormulaCell extends RealCell {
    private String formula;
    private String log;
    private Spreadsheet sheet;

    public FormulaCell(SpreadsheetLocation loc, Spreadsheet inputSheet, String input) {
        // When extending an object, it is required to call super() before anything else.
        super(0);
        // Then, we need to keep an instance of the spreadsheet, the formula.
        sheet = inputSheet;
        formula = input;
        // Here, we need to compute the value of the formula before to check for errors
        log = compute(input);
    }
    @Override
    public String fullCellText() {
        return formula;
    }
    // Every time getDouble() is called, formula cell will re-calculate its formula
    @Override
    public double getDouble() {
        return Double.parseDouble(compute(formula));
    }

    //====/ Public Methods /====//
    // Returns the initial calculation status. (Error checking)
    public String getLog() {
        return log;
    }
    // Returns a boolean value that checks if isFormula is parse-able by compute()
    public static boolean isFormula(String input) {
        int index = input.lastIndexOf(")");
        if (index != -1)
            if (containsOnlySpaces(input.substring(index + 1).split("")))
                input = input.substring(0, index + 1);

        if (input.charAt(0) == '(' && input.charAt(input.length() - 1) == ')')
            return !input.substring(1, input.length() - 1).contains("(") &&
                   !input.substring(1, input.length() - 1).contains(")");
        return false;
    }

    //====/ Private Methods /====//
    // Parses the formula and returns an error or numerical value
    private String compute(String input) {
        // 1. raw formula string ~> formatted list
        String expression = input.substring(1, input.length() - 1);
        ArrayList<String> structuredExpression = new ArrayList<>();
        // Checks for formatting errors
        String exitResult = makeListOfCommandsFromString(structuredExpression, expression);
        if (!exitResult.equals(":)"))
            return exitResult;

        // 2. Given a formatted list of commands, calculate the value
        exitResult = simplify(structuredExpression);
        // Checks for numerical and method errors
        if (!exitResult.equals(":)"))
            return exitResult;

        // 3. Take the first index (the final value) and return it.
        return structuredExpression.get(0);
    }
    // Parses raw formulas and eliminates formatting errors for simplify()
    private String makeListOfCommandsFromString(ArrayList<String> list, String expression) {
        boolean alreadyHasMethod = false;
        boolean fullCommandFound = false;
        boolean cannotBeOperand = false;
        boolean previousWasOperand = true;
        String possibleCommand = "";

        // Loop through every char in the expression
        for (int index = 0; index < expression.length(); index++) {
            // Many of the algorithms below utilize the next char in expression
            char nextChar = (index == expression.length() - 1) ? '!' : expression.charAt(index + 1);
            char currentChar = expression.charAt(index);

            // If whitespace, ignore, but it still signifies a new command
            if (currentChar == ' ') {
                // If the command is unrecognizable & there is whitespace (signals end of a command)
                if (cannotBeOperand)
                    return "ERROR: \"" + possibleCommand + "\" cannot be used in formula.";
            }
            // If currentChar is a possible operand
            else if (isOperand(currentChar)) {
                // If the command is unrecognizable & there is an operand (signals end of a command)
                if (cannotBeOperand) return "ERROR: \"" + possibleCommand + "\" cannot be used in formula.";

                // Operand is an individual value in the output list if not a negative sign
                if (!(currentChar == '-' && previousWasOperand)) {
                    fullCommandFound = true;
                    previousWasOperand = true;
                }

                // Give an error if there is an adjacent operand
                else if (isOperand(nextChar))
                    return "ERROR: " + currentChar + " is placed where it doesn't make sense.";

                // Passed all checks, currentChar is a valid operand in a valid position
                possibleCommand += currentChar;
            }
            // currentChar is a numeric term
            else {
                previousWasOperand = false; // Current value is not an operand (used for next iteration)
                cannotBeOperand = true; // Current command cannot be an operand
                possibleCommand += currentChar; // Command now is checked with a currentChar added

                // DEALING WITH CELL LOCATIONS
                if (SpreadsheetLocation.isLocation(possibleCommand)) {
                    // If next char completes the location
                    if (SpreadsheetLocation.isLocation(possibleCommand + nextChar)) {
                        possibleCommand += nextChar;
                        index++; // Skip an iteration
                    }
                    fullCommandFound = true;
                }
                // DEALING WITH METHODS (SUM, AVG)
                else if (isMethod(possibleCommand)) {
                    if (alreadyHasMethod) return "ERROR: You can only use one method per formula.";
                    else {
                        fullCommandFound = true;
                        alreadyHasMethod = true;
                    }
                }
                else if (isRealValue(possibleCommand)) {
                    // If it is safe to check the next char and its not a number
                    if (!isRealValue(possibleCommand + nextChar)) {
                        fullCommandFound = true;
                    }
                }
            }

            if (fullCommandFound) {
                list.add(possibleCommand); // Add the command to the list
                // Re-initialize some variables
                possibleCommand = "";
                fullCommandFound = false;
                cannotBeOperand = false;
            }
        }
        if (list.size() == 0)
            return "ERROR: No formula given.";
        return ":)";
    }
    // Takes a correctly-formatted list, finds the value, and returns numerical and method errors.
    private String simplify(ArrayList<String> list) {
        // If formula uses a method
        if (isMethod(list.get(0))) {
            // Checks list size to avoid out of bounds exceptions
            if (list.size() != 4)
                return "ERROR: Methods in formulas must follow -> (<METHOD> <cell>-<cell>)";
            // Grabs first location and second location
            String first = list.get(1).toUpperCase();
            String second = list.get(3).toUpperCase();

            // All methods must be: 0-method, 1-start, 2-"-", 3-end
            if (!isRange(first, list.get(2), second))
                return "ERROR: Methods in formulas must follow -> (<METHOD> <cell>-<cell>)";

            // Creating a list of doubles
            ArrayList<Double> numbers = new ArrayList<>();

            // Define column boundaries and if traveling direction.
            int colStart = first.charAt(0) - 'A';
            int colEnd = second.charAt(0) - 'A';
            int colPropagateDirection = (colEnd - colStart < 0) ? -1 : 1;
            // Same with rows
            int rowStart = Integer.parseInt(first.substring(1));
            int rowEnd = Integer.parseInt(second.substring(1));
            int rowPropagateDirection = (rowEnd - rowStart < 0) ? -1 : 1;

            // Loop through all rows in each column and stops when index overshoots end by one.
            for (int col = colStart; col != colEnd + colPropagateDirection; col += colPropagateDirection) {
                for (int row = rowStart; row != rowEnd + rowPropagateDirection; row += rowPropagateDirection) {
                    // Construct the location, get value and check for errors
                    String errorCheck = getValue((char) (col + 'A') + "" + row);
                    if (errorCheck.startsWith("ERROR"))
                        return errorCheck;
                    // Add number to numbers list
                    numbers.add(Double.parseDouble(errorCheck));
                }
            }

            // Store sum of numbers in a variable (avg and sum needs it)
            double sum = 0;
            for (double d : numbers) sum += d;
            // Set first index of the formatted list to sum
            if (list.get(0).equalsIgnoreCase("sum"))
                list.set(0, sum + "");
            // Set first index of the formatted list to mean avg
            else list.set(0, sum / numbers.size() + "");

            // Everything went A-OK, stop function
            return ":)";
        }

        // If formula does not use a method:
        // If formula only contains one number
        if (list.size() == 1)
            list.set(0, getValue(list.get(0)));
        else {
            for (int iteration = 0; iteration < 2; iteration++) {
                // Iteration: 0 (multiply and divide)
                // Iteration: 1 (add and subtract)
                for (int index = 0; index < list.size(); index += 2) {
                    // If the list is big enough for (term operand term)
                    if (list.size() - index >= 3) {
                        boolean computed = true;
                        double[] terms = new double[2]; // Terms to be evaluated
                        String operand = list.get(index + 1); // Store operand

                        // Get first and third string from list and get its value
                        for (int i = 0; i < 2; i++) {
                            // Error checking
                            String errorCheck = getValue(list.get(index + 2 * i));
                            if (errorCheck.startsWith("ERROR"))
                                return errorCheck;
                            // Add it to one of the terms
                            terms[i] = Double.parseDouble(errorCheck);
                        }

                        // Read the operand, and undergo calculations
                        if (iteration == 0) {
                            if (operand.equals("*"))
                                list.set(index, terms[0] * terms[1] + "");
                            else if (operand.equals("/")) {
                                // Check if denominator is 0
                                if (terms[1] == 0) {
                                    return "ERROR: Cannot divide by 0.";
                                } else {
                                    list.set(index, terms[0] / terms[1] + "");
                                }
                            } else {
                                computed = false;
                            }
                        }
                        else {
                            if (operand.equals("+"))
                                list.set(index, terms[0] + terms[1] + "");
                            else if (operand.equals("-"))
                                list.set(index, terms[0] - terms[1] + "");
                            else computed = false;
                        }

                        // Only run below if something was calculated
                        if (computed) {
                            // Remove that operand and
                            list.remove(index + 1);
                            list.remove(index + 1);
                            index -= 2;
                        }
                    }
                }
            }
        }
        // Everything went smoothly
        return ":)";
    }
    // Gets the value of the string given. (Value or Cell)
    private String getValue(String input) {
        // If input is a number, its already a value
        if (isRealValue(input)) return input;

        // If negative, keep track of it and remove the sign
        boolean negative = false;
        if (input.startsWith("-")) {
            input = input.substring(1);
            negative = true;
        }
        // Check if its a location
        if (SpreadsheetLocation.isLocation(input)) {
            SpreadsheetLocation locationOfInput = new SpreadsheetLocation(input);
            // Get the cell and check if its a RealCell
            if (sheet.getCell(locationOfInput) instanceof RealCell) {
                // Cast to a RealCell and get its value, remembering the negative.
                RealCell inputCell = (RealCell) sheet.getCell(locationOfInput);
                return (negative) ? -inputCell.getDouble() + "" : inputCell.getDouble() + "";
            } else {
                return "ERROR: " + input + " does not contain a numeric value.";
            }
        }
        // Check if it's a method (which is bad)
        if (isMethod(input))
            return "ERROR: Invalid formula!";
        return "ERROR: " + input + "does not belong here.";
    }
    // Check if a character is an operand (+, -, *, /)
    private static boolean isOperand(char c) {
        for (char o : new char[]{'+', '-', '*', '/'})
            if (c == o) return true;
        return false;
    }
    // Check if input is equal to "sum" or "avg"
    private static boolean isMethod(String s) {
        return s.equalsIgnoreCase("sum") || s.equalsIgnoreCase("avg");
    }
    // Check if input only contains spaces
    private static boolean containsOnlySpaces(String[] strArray) {
        for (String test : strArray)
            if (!test.equals(" ")) return false;
        return true;
    }
    // Check if string input can be parsed
    private boolean isRealValue(String input) {
        // Check for non-numeric characters
        String acceptable = "0123456789.-";
        for (char c : input.toCharArray())
            if (!acceptable.contains(c + "")) return false;
        // Check if there are too many decimal points
        if (occurrence(input, '.') > 1) return false;
        // Check if there is one negative sign in the front
        return occurrence(input, '-') <= 1 && input.indexOf("-") <= 0;
    }
    // Check if start, dash, and end make up a range
    private boolean isRange(String start, String dash, String end) {
        return SpreadsheetLocation.isLocation(start) &&
                SpreadsheetLocation.isLocation(end) &&
                dash.equals("-");
    }
    // Return the amount of time a character appears in a string
    private int occurrence(String string, char character) {
		int i = 0;
		for (char c : string.toCharArray())
			if (c == character) i++;
		return i;
	}
}

