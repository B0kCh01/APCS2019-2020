package Methods;

public class Quadratic {
    public static String quadrDescriber(double a, double b, double c) {
    	String a_str = "";
    	String b_str = "";
    	String c_str = "";
    	String var_name = "x";

    	if (a < 0) a_str += "-";
    	if (b < 0) b_str += "-";
    	if (c < 0) c_str += "-";

    	return a + var_name + "^2" + b + var_name + c;
    }

	public static double absValue(double number) {
		if (number < 0)
			return -number;
		return number;
	}
}
