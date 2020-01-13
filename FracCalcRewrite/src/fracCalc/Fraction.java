package fracCalc;

public class Fraction {
    private int whole;
    private int numerator;
    private int denominator;
    private int negative;

    public Fraction() {
        numerator = 0;
        whole = 0;
        denominator = 1;
        negative = 1;
    }

    public Fraction (int whole, int numerator, int denominator) {
        this.whole = whole;
        this.numerator = numerator;
        this.denominator = denominator;
        this.negative = 1;
    }

    private String parseFraction(String mixed) {
        return "";

    }

    // Convert Fraction object to mixed number
    public void toMixed() {
        whole = numerator / denominator;
        numerator = numerator % denominator;
    }

    // Convert Fraction object to Improper
    private void toImproper() {
        fixNeg();
        numerator = whole*denominator + numerator;
        whole = 0;
    }

    // Move all negatives to the negatives variable
    private void fixNeg() {
        int[] list = {whole, numerator, denominator};
        for (int property : list)
            negative *= property / abs(property);
    }

    // Absolute value
    private int abs(int n) {
        if (n < 0) return -n;
        return n;
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