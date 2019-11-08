package fracCalc;

import java.util.*;

public class FracCalc {

    public static void main(String[] args) {
        int[] a = {0, 1, 2, -1};
        int[] b = {0, 1, 2, 1};
        //System.out.println(Arrays.toString(addSubtract(a, b, true)));
    	// MAIN  METHOD
    	Scanner sc = new Scanner(System.in);
    	System.out.println("FracCalc by Nathan Choi (b0kch01)");
    	System.out.println("Enter \"quit\" to quit!");
    	
    	boolean quit = false;
    	while (!quit) {
	    	System.out.print("\nInput: ");
	    	String input = sc.nextLine();
	    	if (input.equals("quit")) {
	    		System.out.print("Bye!");
	    		quit = true;
	    	}
	    	else {
	    		System.out.println("Answer: " + produceAnswer(input));
	    	}
    	}
    	sc.close();
    }

    public static String produceAnswer(String input) {
    	// Check for invalid characters
    	String accepted = "+-*1234567890_/ ";
    	for (String character : input.split(""))
    		if (!accepted.contains(character))
    			return "I found an invalid symbol!";
        // Check for invalid formatting
        if (input.contains("  ") || input.split("")[0].equals(" "))
            return "Don't place spaces where you don't need them.";
        
        return calculate(input);
    }
    
    // Helper Methods
    private static String calculate(String input) {
    	String[] inputs = input.split(" ");
    	if (inputs.length == 1) {
            String fractionString = parseFraction(inputs[0]);
            if (!isInt(fractionString.substring(fractionString.length()-1)))
                return fractionString;
            int[] fractionInteger = toIntArray(fractionString);
            toImproper(fractionInteger);
            reduceFraction(fractionInteger);
            toMixed(fractionInteger);
            return formatAnswer(fractionInteger);
        } else if (inputs.length > 2) {
            int[] out = new int[4];
    	    // Parses expression in blocks of 3
            String fraction1, operand, fraction2;
            int[] fraction1Int, fraction2Int;
    	    for (int index = 0; index < inputs.length / 2; index++) {
                if (index == 0) {
                    fraction1 = parseFraction(inputs[0]);
                    if (!isInt(fraction1.substring(fraction1.length()-1)))
                        return fraction1;
                    fraction1Int = toIntArray(fraction1);
                } else {
                    fraction1Int = out;
                }

                fraction2 = parseFraction(inputs[2*index + 2]);
                if (!isInt(fraction2.substring(fraction2.length()-1)))
                    return fraction2;
                fraction2Int = toIntArray(fraction2);

                operand = inputs[index*2 + 1];

                if (operand.equals("+"))
                    out = addSubtract(fraction1Int, fraction2Int, true);
                else if (operand.equals("-"))
                    out = addSubtract(fraction1Int, fraction2Int, false);
                else if (operand.equals("*"))
                    out = multiplyDivision(fraction1Int, fraction2Int, true);
                else if (operand.equals("/"))
                    out = multiplyDivision(fraction1Int, fraction2Int, false);
                reduceFraction(out);
                toMixed(out);
            }
            return formatAnswer(out);
        } else {
            return "Missing operator?";
        }
    }

    private static int[] toIntArray(String input) {
        int[] out = new int[4];
        String[] fractionProperties = input.split(",");
        for (int i = 0; i < 3; i++)
            out[i] = Integer.parseInt(fractionProperties[i]);
        return out;
    }

    private static String formatAnswer(int[] mixednumber) {
        String output = "";
        if (mixednumber[3] == -1)
            output += "-";
        if (mixednumber[0] != 0)
            output += mixednumber[0];
        if (mixednumber[1] != 0) {
            if (mixednumber[0] != 0)
                output += "_";
            output += mixednumber[1] + "/" + mixednumber[2];
        }
        if (output.equals("") || output.equals("-"))
            return "0";
        return output;
    }

    // Main program that turns input string fraction into organized formats, supporting error messages
    private static String parseFraction(String mixed) {
    	// Checking for invalid characters and if the fraction has a number
    	boolean hasNum = false;
    	String accepted = "-1234567890_/";
    	for (String character : mixed.split(""))
    		if (!accepted.contains(character))
    			return "I found an invalid symbol!";
    	
    	for (String character : mixed.split(""))
    		if (isInt(character))
    			hasNum = true;
    	if (!hasNum || mixed.charAt(mixed.length()-1) == '_') return "Oh no! Make sure to have numbers";
    	
    	// Default values of fraction properties
        int whole       = 0,
        	numerator   = 0,
        	denominator = 1;
        
        String[] mixArray = mixed.split("_"); // Attempt to split input to whole and frac
        String[] fraction = String.join("", mixArray[mixArray.length - 1]).split("/"); // Get last index and check if fraction
        
        try {
	        if (fraction.length == 2) {// There is a fraction
	        	numerator = Integer.parseInt(fraction[0]); // Parse numerator / denominator
	        	denominator = Integer.parseInt(fraction[1]);
	        	if (denominator == 0)
	        	    return "Haha nice one! I don't want zeros in my denominator!";
	        	if (mixArray.length == 2) // If fraction has a number in front, get whole number
	            	whole = Integer.parseInt(mixArray[0]);
	        } else if (mixArray.length == 2) { // Error if not a fraction and has something random after "_"
	        	return "Make sure to have a fraction after \"_\".";
	        } else {
	        	whole = Integer.parseInt(mixArray[0]); // If no fraction and only has whole number
	        }
        } catch (NumberFormatException e) { // Checks for random symbol combinations and huge integers
        	return "Oh no! Make sure to follow rules!";
        }

        return String.format("%d,%d,%d", whole, numerator, denominator);
    }

    // Method prints the fraction out for checkpoint 2
    private static String printFrac(String fraction) {
        String[] fractionProp = parseFraction(fraction).split(",");
        // If there isn't an error message
        if (fractionProp.length != 1)
	        return String.format("whole:%s numerator:%s denominator:%s", 
	        		fractionProp[0], 
	        		fractionProp[1], 
	        		fractionProp[2]);
        else
        	return fractionProp[0];
    }
    
    // Quick absolute value method
    private static int abs(int n) {
    	return n < 0 ? n*-1 : n;
    }
    
    // Simplifies the negatives in a fraction 
    private static void fixNeg(int[] fraction) {
    	// Determine if fraction is negative by toggling bool negative
    	int sign = 1;
    	for (int i = 0; i < 4 - 1; i++)
    	    if (fraction[i] < 0)
                sign *= -1;
    	
    	// Absolute value all values
        fraction[0] = abs(fraction[0]);
        fraction[1] = abs(fraction[1]);
        fraction[2] = abs(fraction[2]);
		
		// If negative, the whole number is negative
        if (fraction[3] == 0)
    	    fraction[3] = sign;
        else fraction[3] *= sign;
    }

    private static void toMixed(int[] prop) {
    	int whole = abs(prop[1]) / abs(prop[2]);
    	int numerator = prop[1] - whole*prop[2];
    	prop[0] = whole;
    	prop[1] = numerator;
    }
    
    private static void toImproper(int[] mixed) {
        fixNeg(mixed);
    	int numerator = mixed[2]*mixed[0] + mixed[1];
    	mixed[0] = 0;
    	mixed[1] = numerator;
    }
    
    private static void reduceFraction(int[] improper) {
    	int gcf = getGCF(abs(improper[1]), abs(improper[2]));
    	improper[1] /= gcf;
    	improper[2] /= gcf;
    }
    
    private static int[] addSubtract(int[] a, int[] b, boolean add) {
    	int[] out = new int[4];
    	fixNeg(a); // Simplify negatives
    	fixNeg(b);
    	toImproper(a); // Change to improper fraction
    	toImproper(b);
    	int denominator = a[2] * b[2]; // Get denominator with LCM
    	// If add is true add numerators if not, subtract
    	if (add)
    	    out[1] = denominator/a[2] * a[1] * a[3] + denominator/b[2] * b[1] * b[3];
    	else
    	    out[1] = denominator/a[2] * a[1] * a[3] - denominator/b[2] * b[1] * b[3] ;
    	out[2] = denominator; // Denominator is LCM

    	fixNeg(out);
    	return out;	
	}
    
    private static int[] multiplyDivision(int[] a, int[] b, boolean multiply) {
    	int[] out = new int[4];
    	fixNeg(a); // Simplify negatives
    	fixNeg(b);
    	toImproper(a); // Change to improper fraction
    	toImproper(b);
    	
    	if (multiply) {
	    	out[1] = a[1] * b[1];
	    	out[2] = a[2] * b[2]; 
    	} else {
    		out[1] = a[1] * b[2];
    		out[2] = a[2] * b[1];
    	}

    	out[3] = a[3] * b[3];
    	return out;
    	
    }
    
    private static int getGCF(int a, int b) {
    	int gcf = b;
    	int divisor = a;
    	while (divisor != 0) {
    		int old_a = divisor;
    		divisor = gcf%divisor;
    		gcf = old_a;
    	}
    	return gcf;
    }
    
    // LCM Calculator
    private static int getLCM(int a, int b) {
    	// LCM is ( a x b )/gcd 
    	return (a*b)/getGCF(a, b);
    }
    
    // Check if input String is a number
    private static boolean isInt(String string) {
        if(string.contains("-")){
            if(string.indexOf("-") != 0){
                return false;
            } else {
                string = string.substring(1);
            }
        }
        char[] list = string.toCharArray();
        for(char c : list){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }
}
