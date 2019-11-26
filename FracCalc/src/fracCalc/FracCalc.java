// Nathan Choi
// APCS 3rd
// November 25, 2019
// Fraction Calcualtor -> Was able to achieve in 118 lines but readability...

package fracCalc;
import java.util.*;

public class FracCalc {
    // MAIN  METHOD (Super optimized)
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("FracCalc by Nathan Choi (b0kch01)\nEnter \"quit\" to quit!\n\nInput: ");
        for (String input = sc.nextLine(); // First time prompt (Text hangs off line above)
             !input.equalsIgnoreCase("quit"); // While input is not "quit", continue
             System.out.print("\nInput: "), input = sc.nextLine()) // After every iteration, prompt user
        {
            System.out.println("Answer: " + produceAnswer(input));
        }
    }
    // Format array[whole, num, den, neg] into a readable String
    private static String formatAnswer(int[] mixednum) {
        String output = (mixednum[3] == -1) ?  "-" : ""; // If negative, add negative sign
        if (mixednum[0] != 0) // If there is a whole number, add it
            output += mixednum[0];
        if (mixednum[1] != 0) { // If there a fraction, add it
            if (mixednum[0] != 0)
                output += "_"; // Add underscore if whole number is also present
            output += mixednum[1] + "/" + mixednum[2];
        }
        return (output.equals("") || output.equals("-")) ? "0" : output; // Return "0" if nothing applies
    }
    // Main method to produce the answer and return it to the main method
    protected static String produceAnswer(String input) {
        for (String character : input.split("")) // Check for invalid characters in the whole input
            if (!"+-*1234567890_/ ".contains(character))
                return "Error: I found an invalid symbol!";
        if (input.equals("") || input.contains("  ") || input.charAt(0) == ' ') // Check for inappropriate spacing
            return "Error: Spaces = Nothing = Not a correct expression.";

        String[] inputs = input.split(" "); // Split the raw input into a list of arguments

        if (inputs.length <= 2) { // A expression MUST have a three parts (value, operand, value)
            return "Error: Not an expression or you forgot to add a space.";
        } else { // If number requirements met, continue to calculate.
            int[] out = new int[4]; // Output fraction initialized

            /*
             *  Approach:
             *  Input is going to be read in groups of three -> [fraction1, operand, fraction2]
             *      After computation, the result will substitute as the new fraction1
             *      and calculation will continue with that group of three.
             */

            for (int group = 0; // Groups are zero-base indexed
                 group < inputs.length / 2; // If there is enough fractions for the next group, continue
                 group++, toMixed(reduceFraction(out))) // Moves to the next group and fixes out for fraction1
            {
                int[] fraction1Int = out; // Initialize fraction1 as the output of the previous calculation.

                if (group == 0) { // If this is the first iteration, fraction1 is the first element
                    String fraction1 = parseFraction(inputs[0]); // If no fraction, return the whole number
                    if (!isInt(fraction1.substring(fraction1.length()-1))) // If there is an error, return it
                        return fraction1;
                    fraction1Int = toIntArray(fraction1.split(","));
                }

                // Get fraction2 from input. If there is an error, return it
                String fraction2 = parseFraction(inputs[2*group + 2]);
                if (!isInt(fraction2.substring(fraction2.length()-1)))
                    return fraction2;
                int[] fraction2Int = toIntArray(fraction2.split(",")); // Convert to an integer array

                // Get operand from current group.
                String operand = inputs[group*2 + 1];
                if (operand.length() > 1) // If operand is more than one character, return an error
                    return "Error: Yikes! That looks like an alien language.";
                toImproper(fraction1Int); // Convert fractions to improper for calculation
                toImproper(fraction2Int);

                /*
                 * Approach:
                 * I had the computation split into two categories, (+ or -), and (* or /)
                 */

                if (operand.equals("+") || operand.equals("-")) {
                    out = addSubtract(fraction1Int, fraction2Int, operand.equals("+"));
                } else if (operand.equals("*") || operand.equals("/")) {
                    // Check if dividing by 0
                    if (operand.equals("/") && fraction2Int[0] == 0 && fraction2Int[1] == 0)
                        return "Error: Cannot divide by zero";
                    out = multiplyDivision(fraction1Int, fraction2Int, operand.equals("*"));
                }
            }
            // After the whole expression is evaluated, send the output to formatAnswer for terminal output
            return formatAnswer(out);
        }
    }
    // An important method that turns a raw input string into an organized format, supporting error messages
    private static String parseFraction(String mixed) {
        // Checking for invalid characters
        boolean hasNum = false;
        for (String character : mixed.split(""))
            if (!"-1234567890_/".contains(character))
                return "Error: I found an invalid symbol!";
            else if (isInt(character))
                hasNum = true;
        // Check if the fraction has a number
        if (!hasNum || mixed.charAt(mixed.length()-1) == '_')
            return "Error: Oh no! Make sure to have numbers";

        int whole = 0, numerator = 0, denominator = 1; // Default values of the different fraction properties
        String[] mixArray = mixed.split("_"); // Attempt to split input to the whole number and fraction
        String[] fraction = String.join("", // Split fraction from mixArray into [numerator, denominator]
                mixArray[mixArray.length - 1]).split("/");
        try {
            if (fraction.length == 2) { // If there is a fraction, continue
                numerator = Integer.parseInt(fraction[0]); // Get numerical values from String List
                denominator = Integer.parseInt(fraction[1]);
                if (mixArray.length == 2) // If there is a whole number, get it
                    whole = Integer.parseInt(mixArray[0]);
                if (denominator == 0) // Return an error if denominator is 0
                    return "Error: Haha nice one! I don't want zeros in my denominator!";
            } else if (mixArray.length == 2) // If mixArray has a space for fraction, but is empty, return error
                return "Error: Make sure to have a fraction after \"_\".";
            else
                whole = Integer.parseInt(mixArray[0]); // If no fraction and only has whole number
        } catch (NumberFormatException e) { // Throw error if there are too many numbers
            return "Error: Too many characters!";
        }
        // Format the fraction in a readable format for toIntArray()
        return whole + "," + numerator + "," + denominator;
    }
    // Takes the string format and parses it into a math-ready int array
    private static int[] toIntArray(String[] split) {
        return new int[]{
                Integer.parseInt(split[0]), // Whole number
                Integer.parseInt(split[1]), // Numerator
                Integer.parseInt(split[2]), // Denominator
                0 // Unknown negative
        };
    }
    // Quick absolute value method
    private static int abs(int n) {
        return n < 0 ? -n : n;
    }
    // Corrects the negatives in a fraction array (last index)
    private static int[] fixNeg(int[] fraction) {
        int sign = 1; // Determine if fraction is negative by toggling bool negative
        for (int index = 0; index < 3; index++) {
            if (fraction[index] < 0)
                sign *= -1;
            fraction[index] = abs(fraction[index]); // Absolute value all values
        }
        fraction[3] *= sign;
        if (fraction[3] == 0)
            fraction[3] = sign; // If negative, the whole number is negative
        return fraction;
    }
    // Convert proper fraction to mixed number
    private static void toMixed(int[] prop) {
        prop[0] = abs(prop[1]) / abs(prop[2]); // Whole#: integer division between numerator/denominator
        prop[1] = abs(prop[1]) % abs(prop[2]); // Numerator is the remainder
    }
    // Convert mixed number to Improper
    private static void toImproper(int[] mixed) {
        fixNeg(mixed); // Fix the negatives before conversion
        mixed[1] = mixed[2]*mixed[0] + mixed[1]; // Numerator is whole*denominator + numerator
        mixed[0] = 0;
    }
    // Reduce improper fraction
    private static int[] reduceFraction(int[] improper) {
        for (int gcf = getGCF(abs(improper[1]), // Calculate in the init section
             abs(improper[2])), i = 1;
             i <= 2; i++) // Iterate for numerator and denominator
        {
            // Divide numerator or denominator by gcf
            improper[i] /= gcf;
        }
        return improper;
    }
    // Get the GCF/GCD of 2 numbers
    private static int getGCF(int a, int b) {
        // Uses recursion (looks neater this way) to find gcf
        return (b == 0) ? a : getGCF(b, a%b);
    }
    // Add/Subtract two fractions
    private static int[] addSubtract(int[] a, int[] b, boolean add) {
        /*
         * Approach:
         *    After finding the common denominator (lazily), multiply each numerator by
         *    the denominator of the other fraction, ensuring that the value of the fraction persists.
         *    Also, multiply it by the negative sign, if it has one
         */
        int numeratorA = a[1] * b[2] * a[3],
            numeratorB = b[1] * a[2] * b[3];
        if (add) // If method is called to add, add both numerators
            return fixNeg(new int[]{0, numeratorA + numeratorB, a[2] * b[2], 1});
        return fixNeg(new int[]{0, numeratorA - numeratorB, a[2] * b[2], 1});
    }
    // Multiply/Divide two fractions
    private static int[] multiplyDivision(int[] a, int[] b, boolean multiply) {
        /*
         * Approach:
         *    If multiply, multiply numerator to numerator and
         *    denominator to denominator
         *    Do the opposite if dividing (numerator x denominator)
         */
        if (multiply) // If method is called to multiply, multiply
            return new int[]{0, a[1] * b[1], a[2] * b[2], a[3] * b[3]};
        return new int[]{0, a[1] * b[2], a[2] * b[1], a[3] * b[3]};
    }
    // Check if input String is a number (Thanks Nathan and Sebastian)
    private static boolean isInt(String string) {
        if(string.contains("-"))
            if (string.indexOf("-") != 0)
                return false;
            else
                string = string.substring(1);
        for (char c : string.toCharArray())
            if (!Character.isDigit(c)) return false;
        return true;
    }
    //**METHOD PRINTS THE FRACTION OUT FOR CHECKPOINT 2**
    private static String printFrac(String fraction) {
        String[] fractionProp = parseFraction(fraction).split(",");
        if (fractionProp.length != 1)
            return String.format("whole:%s numerator:%s denominator:%s",
                    fractionProp[0], fractionProp[1], fractionProp[2]);
        return fractionProp[0];
    }
}

// RIP Here lies one operand expression
// String fractionString = parseFraction(inputs[0]);
// if (!isInt(fractionString.substring(fractionString.length()-1))) return fractionString;
// int[] fractionInteger = toIntArray(fractionString);
// toImproper(fractionInteger);
// toMixed(reduceFraction(fractionInteger));
// return formatAnswer(fractionInteger);