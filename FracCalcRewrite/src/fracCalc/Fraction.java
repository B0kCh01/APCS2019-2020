package fracCalc;

public class Fraction {
    private int whole;
    private int numerator;
    private int denominator;
    private int negative = 1; // Initially always 1

    public Fraction() {
        numerator = 0;
        whole = 0;
        denominator = 1;
    }

    public Fraction (String fractionString) {
        int[] fractionParts = parseFraction(fractionString);
        whole = fractionParts[0];
        numerator = fractionParts[1];
        denominator = fractionParts[2];
        fixNeg();
    }
    // Convert Fraction object to mixed number
    public void toMixed() {
        if (whole == 0) {
            whole = numerator / denominator;
            numerator = numerator % denominator;
        }
    }
    // Convert Fraction object to Improper
    public void toImproper() {
        numerator += whole*denominator;
        whole = 0;
    }
    // Reduce fraction
    public void reduceFraction() {
        boolean wasImproper = (whole == 0);
        toImproper();

        int gcf = getGCF(denominator, numerator);
        numerator /= gcf;
        denominator /= gcf;

        if (!wasImproper)
            toMixed();
        if (denominator == 1) {
            whole += numerator;
            numerator = 0;
        }
    }
    // Print the fraction
    @Override
    public String toString() {
        String output = (negative == -1) ?  "-" : ""; // If negative, add negative sign
        if (whole != 0) // If there is a whole number, add it
            output += whole;
        if (numerator != 0) { // If there a fraction, add it
            if (whole != 0)
                output += "_"; // Add underscore if whole number is also present
            output += numerator + "/" + denominator;
        }
        return (output.equals("") || output.equals("-")) ? "0" : output;
    }
    // From string, split the fraction into it's properties
    private int[] parseFraction(String mixed) {
        String[] wholeAndFrac = mixed.split("_");
        if (!mixed.contains("/"))
            return new int[]{Integer.parseInt(wholeAndFrac[0]), 0, 1};

        String[] fraction = wholeAndFrac[wholeAndFrac.length-1].split("/");
        if (wholeAndFrac.length == 1)
            return new int[]{
                    0,
                    Integer.parseInt(fraction[0]),
                    Integer.parseInt(fraction[1])};
        else
            return new int[]{
                    Integer.parseInt(wholeAndFrac[0]),
                    Integer.parseInt(fraction[0]),
                    Integer.parseInt(fraction[1])
            };

    }
    // Move all negatives to the negatives variable
    private void fixNeg() {
        int[] fractionProperties = {whole, numerator, denominator};
        for (int property : fractionProperties)
            if (property < 0)
                negative *= -1;
        whole = abs(whole);
        numerator = abs(numerator);
        denominator = abs(denominator);
    }
    // Get greatest common factor
    private int getGCF(int a, int b) {
        // Uses recursion (looks neater this way) to find gcf
        return (b == 0) ? a : getGCF(b, a%b);
    }
    // Absolute value
    private int abs(double n) {
        if (n < 0)
            return (int) -n;
        return (int) n;
    }
    // Check if input String is a number (Thanks Nathan and Sebastian)
    private boolean isInt(String string) {
        if(string.contains("-"))
            if (string.indexOf("-") != 0)
                return false;
            else
                string = string.substring(1);
        for (char c : string.toCharArray())
            if (!Character.isDigit(c)) return false;
        return true;
    }

}