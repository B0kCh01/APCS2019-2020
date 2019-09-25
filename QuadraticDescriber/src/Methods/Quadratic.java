package Methods;

public class Quadratic {
    public static String quadrDescriber(double a, double b, double c) {
    	String a_str = "";
    	String b_str = "";
    	String c_str = "";
    	int a_int = (int) a;
    	int b_int = (int) b;
    	int c_int = (int) c;

    	a_str += (a < 0) ? "-"  : "";
        
        if (absValue(a) == 1.0)
            a_str += "";
        else if (((int) a) / a == 1.0)
            a_str += absValue(a_int);
        else
            a_str += absValue(a);

    	b_str += (b < 0) ? " - " : " + ";
    	if (((int) b) / b == 1.0)
            b_str += absValue(b_int);
        else
            b_str += absValue(b);

    	c_str += (c < 0) ? " - " : " + ";
    	if (((int) c) / c == 1.0)
            c_str += absValue(c_int);
        else
            c_str += absValue(c);

    	return a_str + "x^2" + b_str + "x" + c_str;
    }

    public static int absValue(int number) {
        return (number < 0) ? -number : number;
    }

	public static double absValue(double number) {
        return (number < 0) ? -number : number;
	}
}
