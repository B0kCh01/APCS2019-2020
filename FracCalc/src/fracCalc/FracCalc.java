package fracCalc;
import java.util.*;

public class FracCalc {
    public static void main(String[] args) { // MAIN  METHOD (Super optimized)
        Scanner sc = new Scanner(System.in);
        System.out.print("FracCalc by Nathan Choi (b0kch01)\nEnter \"quit\" to quit!\n\nInput: ");
        for (String input = sc.nextLine(); !input.equalsIgnoreCase("quit"); System.out.print("\nInput: "), input = sc.nextLine())
            System.out.println("Answer: " + produceAnswer(input));
    }
    // Main method to produce the answer and return it to the main method
    protected static String produceAnswer(String input) {
        for (String character : input.split("")) // Check for invalid characters
            if (!"+-*1234567890_/ ".contains(character)) return "Error: I found an invalid symbol!";
        if (input.equals("") || input.contains("  ") || input.charAt(0) == ' ') // Check for inappropriate spacing
            return "Error: Spaces = Nothing = Not a correct expression.";
        String[] inputs = input.split(" ");
        if (inputs.length == 1) return "Error: Not an expression."; // R.I.P One fraction "expression" below
        else if (inputs.length > 2) {
            int[] out = new int[4];
            for (int index = 0; index < inputs.length / 2; index++, toMixed(reduceFraction(out))) {
                int[] fraction1Int = out;
                if (index == 0) {
                    String fraction1 = parseFraction(inputs[0]); // If no fraction, return the whole number
                    if (!isInt(fraction1.substring(fraction1.length()-1))) return fraction1;
                    fraction1Int = toIntArray(fraction1.split(","));
                }
                String fraction2 = parseFraction(inputs[2*index + 2]);
                if (!isInt(fraction2.substring(fraction2.length()-1))) return fraction2;
                int[] fraction2Int = toIntArray(fraction2.split(","));
                String operand = inputs[index*2 + 1];
                if (operand.length() > 1) return "Error: Yikes! Try doing that on pencil and paper.";
                toImproper(fraction1Int);
                toImproper(fraction2Int);
                if (operand.equals("+") || operand.equals("-")) out = addSubtract(fraction1Int, fraction2Int, operand.equals("+"));
                else if (operand.equals("*") || operand.equals("/"))
                    if (operand.equals("/") && fraction2Int[0] == 0 && fraction2Int[1] == 0) return "Error: Cannot divide by zero";
                    else out = multiplyDivision(fraction1Int, fraction2Int, operand.equals("*"));
            }
            return formatAnswer(out);
        }
        return "Error: Missing operator or operand?";
    }
    private static int[] toIntArray(String[] split) {
        return new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), 0};
    }
    private static String formatAnswer(int[] mixednum) { // Format array[whole, num, den, neg] into a readable String
        String output = (mixednum[3] == -1) ?  "-" : ""; // If negative, add negative sign
        if (mixednum[0] != 0) output += mixednum[0]; // If there is a whole number, add it
        if (mixednum[1] != 0) {
            if (mixednum[0] != 0) output += "_";
            output += mixednum[1] + "/" + mixednum[2];
        }
        return (output.equals("") || output.equals("-")) ? "0" : output;
    }
    private static String parseFraction(String mixed) { // Main program that turns input string fraction into organized formats, supporting error messages
        boolean hasNum = false; // Checking for invalid characters and if the fraction has a number
        for (String character : mixed.split(""))
            if (!"-1234567890_/".contains(character)) return "Error: I found an invalid symbol!";
            else if (isInt(character)) hasNum = true;
        if (!hasNum || mixed.charAt(mixed.length()-1) == '_') return "Error: Oh no! Make sure to have numbers";
        int whole = 0, numerator = 0, denominator = 1; // Default values of fraction properties
        String[] mixArray = mixed.split("_"); // Attempt to split input to whole and frac
        String[] fraction = String.join("", mixArray[mixArray.length - 1]).split("/"); // Try to get frac
        try {
            if (fraction.length == 2) { // If there is a fraction
                numerator = Integer.parseInt(fraction[0]); // Parse numerator / denominator
                denominator = Integer.parseInt(fraction[1]);
                if (mixArray.length == 2) whole = Integer.parseInt(mixArray[0]); // Get whole # & more error check
                if (denominator == 0) return "Error: Haha nice one! I don't want zeros in my denominator!";
            } else if (mixArray.length == 2) return "Error: Make sure to have a fraction after \"_\".";
            else whole = Integer.parseInt(mixArray[0]); // If no fraction and only has whole number
        } catch (NumberFormatException e) { return "Error: Too many characters!"; } // If there are too many digits
        return whole + "," + numerator + "," + denominator;
    }
    private static int abs(int n) { return n < 0 ? n*-1 : n; } // Quick absolute value method
    private static int[] fixNeg(int[] fraction) { // Simplifies the negatives in a fraction
        int sign = 1; // Determine if fraction is negative by toggling bool negative
        for (int index = 0; index < 3; index++) {
            if (fraction[index] < 0) sign *= -1;
            fraction[index] = abs(fraction[index]); // Absolute value all values
        }
        fraction[3] *= sign;
        if (fraction[3] == 0) fraction[3] = sign; // If negative, the whole number is negative
        return fraction;
    }
    private static void toMixed(int[] prop) { // Convert proper fraction to mixed number
        prop[0] = abs(prop[1]) / abs(prop[2]);
        prop[1] = prop[1] - (abs(prop[1]) / abs(prop[2]))*prop[2];
    }
    private static void toImproper(int[] mixed) { // Convert mixed number to Improper
        fixNeg(mixed);
        mixed[1] = mixed[2]*mixed[0] + mixed[1];
        mixed[0] = 0;
    }
    private static int[] reduceFraction(int[] improper) { // Reduce improper fraction
        for (int gcf = getGCF(abs(improper[1]), abs(improper[2])), i = 1; i <= 2; i++) improper[i] /= gcf;
        return improper;
    }
    private static int[] addSubtract(int[] a, int[] b, boolean add) { // If add is true add, if not, subtract
        int[] out =  {0, 0, a[2] * b[2], 0}; // Numerators are proportional to denominator and added / subtracted
        if (add) out[1] = (a[2] * b[2])/a[2] * a[1] * a[3] + (a[2] * b[2])/b[2] * b[1] * b[3];
        else out[1] = (a[2] * b[2])/a[2] * a[1] * a[3] - (a[2] * b[2])/b[2] * b[1] * b[3];
        return fixNeg(out);
    }
    private static int[] multiplyDivision(int[] a, int[] b, boolean multiply) { // If multiply is true, multiply, if not, divide
        if (multiply) return new int[]{0, a[1] * b[1], a[2] * b[2], a[3] * b[3]};
        return new int[]{0, a[1] * b[2], a[2] * b[1], a[3] * b[3]};
    }
    private static int getGCF(int a, int b) { return (b == 0) ?  a : getGCF(b, a%b); } // Get the GCF/GCD of 2 numbers
    private static boolean isInt(String string) { // Check if input String is a number (Thanks Nathan and Sebastian)
        if(string.contains("-"))
            if (string.indexOf("-") != 0) return false;
            else string = string.substring(1);
        for(char c : string.toCharArray())
            if (!Character.isDigit(c)) return false;
        return true;
    }

//    // Method prints the fraction out for checkpoint 2
//    private static String printFrac(String fraction) {
//        String[] fractionProp = parseFraction(fraction).split(",");
//        // If there isn't an error message
//        if (fractionProp.length != 1)
//            return String.format("whole:%s numerator:%s denominator:%s",
//                    fractionProp[0],
//                    fractionProp[1],
//                    fractionProp[2]);
//        else
//            return fractionProp[0];
//    }
}

// RIP Here lies one operand expression
// String fractionString = parseFraction(inputs[0]);
// if (!isInt(fractionString.substring(fractionString.length()-1))) return fractionString;
// int[] fractionInteger = toIntArray(fractionString);
// toImproper(fractionInteger);
// toMixed(reduceFraction(fractionInteger));
// return formatAnswer(fractionInteger);