package CalculateLibrary;

public class Calculate {
	public double pi = 3.14159;

	// returns the input to the power of 2
	public static int square(int number) {
		return number*number;
	}

	// returns the input to the power of 3
	public static int cube(int number) {
		return number*number*number;
	}

	// returns the average of two doubles
	public static double average(double one, double two) {
		return (one + two)/2;
	}

	// returns the average of three doubles
	public static double average(double one, double two, double three) {
		return (one + two + three)/3;
	}

	// returns the equivalent degrees given radians
	public static double toDegrees(double radians) {
		final double pi = 3.14159;
		return (radians/pi)*180;
	}

	// returns the equivalent radians given degrees
	public static double toRadians(double degrees) {
		final double pi = 3.14159;
		return (degrees/180)*pi;
	}

	// returns the discriminant 
	public static double discriminant(double a, double b, double c) {
		return -b*b - 4*a*c;
	}

	// returns an equivalent improper fraction given mixed fraction
	public static String toImproperFrac(int co, int num, int den) {
		return den*co+num + "/" + den;
	}

	// returns an equivalent mixed fraction given improper fracion
	public static String toMixedNum(int num, int den) {
		return num/den + "_" + num%den + "/" + den;
	}

	// returns a tri-nomial given a fully factored single variable expression
	public static String foil(int a, int b, int c, int d, String var) {
		String firstTerm  = a * c + var + "^2 + ";
		String secondTerm = a*d + c*b + var;
		String thirdTerm  = " + " + b*d;

		return firstTerm + secondTerm + thirdTerm;
	}

	// Returns a boolean to verify if one integer is divisible by the other
	public static boolean isDivisibleBy(int a, int b) {
		return a % b == 0;
	}

	// Returns the absValue of the input
	public static double absValue(double number) {
		if (number < 0.0) return 0.0 - number;
		return number;
	}

	// Returns the larger value of two doubles
	public static double max(double a, double b) {
		return a >= b ? a : b;
	}

	// Returns the larger value of three doubles
	public static double max(double a, double b, double c) {
		if (a >= b && b >= c) return a;
		if (b >= a && a >= c) return b;
		return c;
	}

	// Returns the smaller value of two doubles
	public static double max(double a, double b) {
		return a <= b ? a : b;
	}

	// Returns the input rounded to the nearest 2 decimal points
	public static double round2(double number) {
		
	}

}