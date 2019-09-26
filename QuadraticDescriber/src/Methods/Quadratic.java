// Nathan Choi
// September 24, 2019
// APCS 3rd
// Quadratic.java -> Quadratic Class File

package Methods;

public class Quadratic {
    // Returns formatted quadratic equation from a, b, and c
    public static String quadrDescriber(double a, double b, double c) {
        // Initialize all of the variables that will be used
        String a_str = "";
        String b_str = "";
        String c_str = "";
        int a_int = (int) a;
        int b_int = (int) b;
        int c_int = (int) c;

        /*
            Manage the space between negatives / positives
            Used ternary operators as a quick way to do it
        */
        a_str += (a < 0) ? "-" : "";
        b_str += (b < 0) ? " - " : " + ";
        c_str += (c < 0) ? " - " : " + ";

        // Code below eliminates the ugly trailing zeros shown in doubles
        if (absValue(a) == 1.0)
            a_str += "";
        else if (((int) a) / a == 1.0) // if a is an integer
            a_str += absValue(a_int);
        else
            a_str += absValue(a);

        if (((int) b) / b == 1.0) // if b is an integer
            b_str += absValue(b_int);
        else
            b_str += absValue(b);

        if (((int) c) / c == 1.0) // if c is an integer
            c_str += absValue(c_int);
        else
            c_str += absValue(c);

        // Constructs and concatenates the Strings altogether
        return a_str + "x^2" + b_str + "x" + c_str;
    }

    // Returns details about a quadratic equation from a, b, and c
    public static String quadrDetails(double a, double b, double c) {
        String output = "";
        double h = -b / (2 * a);
        double k = h * h * a + h * b + c;

        output += (a > 0) ? "\nOpens: Upward\n" : "\nOpens: Downward\n"; // Positive / Negative
        output += "Axis of symmetry: " + round2(h); // Symmetry
        output += "\nVertex: (" + round2(h) + ", " + round2(k) + ")";
        output += "\nx-intercepts: " + getRoots(a, b, c);
        output += "\ny-intercept: " + c;
        return output;
    }

    // ~DEPENDENCIES

    private static String getRoots(double a, double b, double c) {
        if (discriminant(a, b, c) < 0)
            return "no real roots";

        double root1 = round2((-b + sqrt(discriminant(a, b, c))) / (2 * a));
        double root2 = round2((-b - sqrt(discriminant(a, b, c))) / (2 * a));

        if (root1 == root2)
            return "" + root1;
        else
            return min(root1, root2) + " and " + max(root1, root2);
    }

    // Returns the absolute value of the input
    private static int absValue(int number) {
        // Ternary operator to return abs value
        return (number < 0) ? -number : number;
    }

    // Overloaded for doubles
    private static double absValue(double number) {
        // Ternary operator to return abs value
        return (number < 0) ? -number : number;
    }

    // Rounds input to the nearest hundredths
    private static double round2(double number) {
        if (number < 0)
            return ((int) (number * 100 - 0.5)) / 100.0;
        return ((int) (number * 100 + 0.5)) / 100.0;
    }

    // Check for real roots and root calculation
    private static double discriminant(double a, double b, double c) {
        return b * b - 4 * a * c;
    }

    // Square root of a number up to a certain precision
    private static double sqrt(double number) {
        if (number < 0)
            throw new IllegalArgumentException(number + " - cannot find square root of a negative number");

        double root = number / 2;
        do {
            root = 0.5 * (number / root + root);
        } while (absValue(number - root * root) > 0.25);
        return round2(root);
    }

    // Returns the lesser of two values
    private static double min(double a, double b) {
        return a <= b ? a : b;
    }

    // Returns the greater of two values
    private static double max(double a, double b) {
        return a >= b ? a : b;
    }
}
