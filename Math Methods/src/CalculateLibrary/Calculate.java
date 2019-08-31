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

	public static String toImproperFrac(int co, int num, int den) {
		return den*co+num + "/" + den;
	}
}