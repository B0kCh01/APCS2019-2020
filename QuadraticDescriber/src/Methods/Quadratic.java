// Nathan Choi
// September 24, 2019
// APCS 3rd
// Quadratic.java -> Quadratic Class File

package Methods;

public class Quadratic {
    public static String quadrDescriber(double a, double b, double c) {
        // Initalize all of the variables that will be used
    	String a_str = "";
    	String b_str = "";
    	String c_str = "";
    	int a_int = (int) a;
    	int b_int = (int) b;
    	int c_int = (int) c;

        /** 
            Manage the space between negatives / positives
            Used turnary operators as a quick way to do it
        **/
    	a_str += (a < 0) ? "-"   : "";
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

        // Constructs and conacatenates the Strings altogther
    	return a_str + "x^2" + b_str + "x" + c_str;
    }

    private static int absValue(int number) {
        // Turnary operator to return abs value
        return (number < 0) ? -number : number;
    }

    // Overloaded for doubles
	private static double absValue(double number) {
        // Turnary operator to return abs value
        return (number < 0) ? -number : number;
	}
}
