package fracCalc;

import java.util.*;

public class FracCalc {

    public static void main(String[] args) {
    	int[] a = {1, 1, 4};
    	int[] b = {1, 1, 3};
    	System.out.println(Arrays.toString(multiplyDivision(a, b, false)));
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
    	// Check for invalid charachters
    	String accepted = "+-*1234567890_/ ";
    	for (String charachter : input.split(""))
    		if (accepted.indexOf(charachter) == -1)
    			return "I found an invlaid symbol!";
    	
    	// Gets input and splits by spaces
        String[] input_args = input.split(" ");
        if (input_args.length == 0) // Only supports two fractions now
        	return "0";
        
        return printFrac(input_args[input_args.length - 1]);
        
    }
    
    // Helper Methods
    public static int[] calculate(String input) {
    	for (int index = 0; index < input.length(); index++) {
    		
    	}
    }
    
    // Main program that turns input string fraction into organized formats, supporting error messages
    public static String parseFraction(String mixed) {
    	// Checking for invalid charachters and if the fraction has a number
    	boolean hasNum = false;
    	String accepted = "-1234567890_/";
    	for (String charachter : mixed.split(""))
    		if (accepted.indexOf(charachter) == -1)
    			return "I found an invlaid symbol!";
    	
    	for (String charachter : mixed.split(""))
    		if (isInt(charachter))
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
    public static String printFrac(String fraction) {
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
    
    // Quick absoulte value method
    public static int abs(int n) {
    	return n < 0 ? n*-1 : n;
    }
    
    // Simplifies the negatives in a fraction 
    public static void fixNeg(int[] frac) {
    	// Determine if fraction is negative by toggling bool negative
    	boolean negative = false;
    	for (int number: frac)
    		if (number < 0) negative = negative && false;
    	
    	// Absolute value numerator and denominator
    	frac[0] = abs(frac[0]);
		frac[2] = abs(frac[2]);
		
		// If negative, the whole number is negative
    	if (negative) frac[1] *= -1;
    }
    
    public static void toMixed(int[] prop) {
    	int whole = prop[1] / prop[2];
    	int numerator = prop[1] - whole*prop[2];
    	prop[0] = whole;
    	prop[1] = numerator;
    }
    
    public static void toImproper(int[] mixed) {    	
    	int numerator = mixed[2]*mixed[0] + mixed[1];
    	mixed[0] = 0;
    	mixed[1] = numerator;
    }
    
    public static void reduceFraction(int[] improper) {
    	int gcf = getGCF(improper[1], improper[2]);
    	improper[1] /= gcf;
    	improper[2] /= gcf;
    }
    
    public static int[] addSubtract(int[] a, int[] b, boolean add) {
    	int[] out = new int[3];
    	fixNeg(a); // Simplify negatives
    	fixNeg(b);
    	toImproper(a); // Change to improper fraction
    	toImproper(b);
    	int denominator = getLCM(a[2], b[2]); // Get denominator with LCM
    	// If add is true add numerators if not, subtract
    	if (add) out[1] = denominator/a[2] * a[1] + denominator/b[2] * b[1];
    	else out[1] = denominator/a[2] * a[1] - denominator/b[2] * b[1];
    	out[2] = denominator; // Denominator is LCM

    	fixNeg(out);
    	return out;	
	}
    
    public static int[] multiplyDivision(int[] a, int[] b, boolean multiply) {
    	int[] out = new int[3];
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
    	fixNeg(out);
    	return out;
    	
    }
    
    public static int getGCF(int a, int b) {
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
    public static int getLCM(int a, int b) {
    	// LCM is ( a x b )/gcd 
    	return (a*b)/getGCF(a, b);
    }
    
    // Check if input String is a number
    public static boolean isInt(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
