package fracCalc;

public class Fraction {
    // Instance Variables
    private int whole; // Whole number
    private int numerator; // Numerator
    private int denominator; // Denominator
    private int negative = 1; // Initially always 1

    // Constructor for empty initialization
    public Fraction() {
        numerator = 0;
        whole = 0;
        denominator = 1;
    }
    // Overloaded Constructor for passing in a string
    public Fraction (String fractionString) {
        // Pass to parseFraction and split to the varibles
        int[] fractionParts = parseFraction(fractionString);
        whole = fractionParts[0];
        numerator = fractionParts[1];
        denominator = fractionParts[2];
        fixNeg();
    }
    // Convert Fraction object to mixed number only if it is improper
    public void toMixed() {
        fixNeg();
        if (whole == 0) {
            whole = numerator / denominator;
            numerator = numerator % denominator;
        }
    }
    // Convert Fraction object to Improper
    public void toImproper() {
        fixNeg();
        numerator += whole*denominator;
        whole = 0;
    }
    // Reduce fraction
    public void reduceFraction() {
        // Reduce fraction ONLY when everything is normal
        fixNeg();
        // Make sure to keep original fraction state
        boolean wasImproper = (whole == 0);
        toImproper();

        // Get gcf, divide both numerator and denominator by it
        int gcf = getGCF(denominator, numerator);
        numerator /= gcf;
        denominator /= gcf;
        // If it was mixed, convert back to mix
        if (!wasImproper)
            toMixed();
        // If it's just a whole number, convert ot swhole number
        if (denominator == 1) {
            whole += numerator;
            numerator = 0;
        }
    }
    // Format and print the fraction
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
    // Add another fraction
    public void add(Fraction fraction2) {
        fraction2.toImproper();
        toImproper();
        this.numerator = (this.numerator * fraction2.getDenominator() * negative) +
                         (fraction2.getNumerator() * this.denominator * fraction2.getNegative());
        this.denominator *= fraction2.getDenominator();
        negative = 1;
    }
    // Subtract another fraction
    public void subtract(Fraction fraction2) {
        fraction2.toImproper();
        toImproper();
        this.numerator = (this.numerator * fraction2.getDenominator() * negative) -
                         (fraction2.getNumerator() * this.denominator * fraction2.getNegative());
        this.denominator *= fraction2.getDenominator();
        negative = 1;
    }
    // Multiply another fraction
    public void multiply(Fraction fraction2) {
        fraction2.toImproper();
        toImproper();
        this.numerator *= fraction2.getNumerator();
        this.denominator *= fraction2.getDenominator();
        this.negative *= fraction2.getNegative();
    }
    // Divide another fraction
    public void divide(Fraction fraction2) {
        fraction2.toImproper();
        toImproper();
        this.numerator *= fraction2.getDenominator();
        this.denominator *= fraction2.getNumerator();
        this.negative *= fraction2.getNegative();
    }
    // | Getters |
    public int getWhole() {
        return whole;
    }
    public int getNumerator() {
        return numerator;
    }
    public int getDenominator() {
        return denominator;
    }
    public int getNegative() {
        fixNeg();
        return negative;
    }
    // | Setters |
    public void setWhole(int whole) {
        this.whole = whole;
    }
    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }
    public void setDenominator(int denominator) {
        this.denominator = numerator;
    }
    public void setNegative(int negative) {
        this.negative = (negative < 0) ? -1 : 1;
    }
    // Parse fraction will turn String into a list of fractino parts
    private int[] parseFraction(String mixed) {
        // Split the string by whole and fraction
        String[] wholeAndFrac = mixed.split("_");
        // If the string does not have a fraction return just the whole
        if (!mixed.contains("/"))
            return new int[]{Integer.parseInt(wholeAndFrac[0]), 0, 1};
        // Return mixed or improper depending if there is a whole
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
        // For each property, if negative, toggle the negative property
        int[] fractionProperties = {whole, numerator, denominator};
        for (int property : fractionProperties)
            if (property < 0)
                negative *= -1;
        // After updating negative, absolute value the others
        whole = abs(whole);
        numerator = abs(numerator);
        denominator = abs(denominator);
    }
    // Get greatest common factor
    private int getGCF(int a, int b) {
        // Uses recursion (looks neater this way) to find gcf
        return (b == 0) ? a : getGCF(b, a%b);
    }
    // Absolute value given double and return int
    private int abs(double n) {
        if (n < 0)
            return (int) -n;
        return (int) n;
    }
    //**METHOD PRINTS THE FRACTION OUT FOR CHECKPOINT 2**
    public String printFrac() {
        return String.format("whole:%s numerator:%s denominator:%s",
                    whole, numerator, denominator);
    }
}