public class Calculate {
	public final double pi = 3.14159;

	// returns the input to the power of 2
	public static int square(int number) {
		return number * number;
	}

	// returns the input to the power of 2
	public static double square(double number) {
		return number * number;
	}

	// returns the input to the power of 3
	public static int cube(int number) {
		return number * number * number;
	}

	// returns the input to the power of 3
	public static double cube(double number) {
		return number * number * number;
	}

	// returns the mean average of two doubles
	public static double average(double one, double two) {
		return (one + two) / 2.0; // Adds the two numbers and divides by 2
	}

	// returns the average of three doubles
	public static double average(double one, double two, double three) {
		return (one + two + three) / 3.0; // Ads the three numbers and divides by 3
	}

	// returns the equivalent degrees given radians
	public static double toDegrees(double radians) {
		final double pi = 3.14159;
		return (radians / pi) * 180;
	}

	// returns the equivalent radians given degrees
	public static double toRadians(double degrees) {
		final double pi = 3.14159;
		return (degrees / 180) * pi;
	}

	// returns the discriminant given constants a, b, c
	public static double discriminant(double a, double b, double c) {
		return b*b - 4*a*c;
	}

	// returns an equivalent improper fraction given mixed fraction
	public static String toImproperFrac(int whole, int num, int den) {
		// If numerator is 0, return co
		if (num == 0)
			return whole + "";
		// If denominator is 0, throw an error
		if (den < 1 || num < 0)
			throw new IllegalArgumentException("The denominator has to be greater than 0 and the numerator positive.");
		// If negative, then add a negative sign
		return (whole > 0 ? "-" : "") + absValue(den * whole) + num + "/" + den;
	}

	// returns an equivalent mixed fraction given improper fracion
	public static String toMixedNum(int num, int den) {
		// if the numerator is 0, return 0
		if (num == 0)
			return 0 + "";
		// throw error if denominator is 0
		if (den == 0)
			throw new IllegalArgumentException("The denominator cannot be 0!");
		// If fraction is negative, add "-"    // if the fraction can be mixed      // construct the rest
		return (num * den < 0 ? "-" : "") + (num / den > 0 ? num / den + "_" : "") + num % den + "/" + den;
	}

	// returns a polynomial given a fully factored single variable expression
	public static String foil(int a, int b, int c, int d, String var) {
		// Initialize new variables
		String term1_str = "";
		String term2_str = "";
		String term3_str = "";
		int term1_int = a * c;
		int term2_int = a * d + c * b;
		int term3_int = b * d;

		// Calculation of terms (if zero, don't add anything)
		// This will check for a value and negative
		if (term1_int != 0) {
			if (term1_int < 0) {
				if (term1_int != -1)
					term1_str += term1_int;
				term1_str += "-";
			}
			term1_str += var + "^2";
		}
		// This will check for the next operator and b constant
		if (term2_int != 0) {
			if (term2_int < 0) // Check if b is first term
				term2_str += term1_str.equals("") ? " -" : " - ";
			else
				term2_str += term1_str.equals("") ? "" : " + ";
			term2_int = absValue(term2_int);
			term2_str += term2_int == 1 ? var : term2_int + var;
		}

		// This will check for the last operator and c constant
		if (b * d != 0) {
			if (term3_int < 0) // Check if c is first term
				term3_str += (term1_str + term2_str).equals("") ? " -" : " - ";
			else
				term3_str += (term1_str + term2_str).equals("") ? "" : " + ";
			term3_str += absValue(term3_int);
		}

		// Construct and return result. If polynomial turns out to be nothing, return "0"
		String result = term1_str + term2_str + term3_str;
		return result.equals("") ? "0" : result;
	}

	// Returns a boolean to verify if one integer is divisible by the other
	public static boolean isDivisibleBy(int a, int b) {
		if (b == 0) throw new IllegalArgumentException("Cannot divide by zero");
		return a % b == 0;
	}

	// Returns the absValue of the input
	public static int absValue(int number) {
		if (number < 0) // If the number is less than 0, make it positive
			return number*-1;
		return number;
	}

	// Returns the absValue of the input
	public static double absValue(double number) {
		if (number < 0.0) // If the number is less than 0, make it positive
			return 0.0 - number;
		return number;
	}

	// Returns the larger value of two doubles
	public static double max(double a, double b) {
		// If a is greater than or equal to b, return a
		// else, b
		return a >= b ? a : b;
	}

	// Returns the larger value of two integers
	public static int max(int a, int b) {
		// If a is greater than or equal to b, return a
		// else, b
		return a >= b ? a : b;
	}

	// Returns the larger value of three doubles
	public static double max(double a, double b, double c) {
		if (a >= b && b >= c) // if a > b > c, a is the biggest
			return a;
		if (b >= a && a >= c) // if b > a > x, b is the biggest
			return b;
		return c; // If neither are the biggest, c has to be the biggest
	}

	// Returns the smaller value of two doubles
	public static double min(double a, double b) {
		// Return a if a is less than or equal to b else return b
		return a <= b ? a : b;
	}

	// Returns the smaller value of two integers
	public static int min(int a, int b) {
		// Return a if a is less than or equal to b else return b
		return a <= b ? a : b;
	}

	// Returns the input rounded to the nearest 2 decimal points
	public static double round2(double number) {
		double output = 0;
		// Cutting of numbers past two decimals
		// subtract or add 0.5 to round the next digit up
		if (number < 0)
			output = ((int) (number * 100 - 0.5)) / 100.0;
		else
			output = ((int) (number * 100 + 0.5)) / 100.0;
		return output;
	}

	// Returns the input to the power of a positive integer
	public static double exponent(double number, int power) {
		// Check for special edge cases and math rules
		if (power < 0)
			throw new IllegalArgumentException("Currently does not support a power less than 0.");
		if (number == 0 && power == 0)
			throw new IllegalArgumentException("Cannot compute the power of 0 to 0.");
		if (power == 0)
			return 1;
		if (number == 0)
			return 0;

		// Looping to multiply number by it's original number
		int iteration = 1;
		double base = number;
		while (iteration < power) {
			number *= base;
			iteration++;
		}
		return number;
	}

	// Returns the factorial of the input number
	public static int factorial(int number) {
		// Check for edge cases
		if (number == 0)
			return 1;
		if (number < 0)
			throw new IllegalArgumentException("Hey! You can't compute the factorial of a negative number.");
		// Iterates backwards until it reaches 1 multiplying each value
		for (int iteration = number - 1; iteration >= 1; iteration--) {
			number *= iteration;
		}
		return number;
	}

	// Returns a boolean. (True means the input is a prime number)
	public static boolean isPrime(int number) {
		// Loop from 2 to half of the number, checking for factors
		for (int iteration = 2; iteration < number / 2; iteration++)
			if (isDivisibleBy(number, iteration))
				return false;
		return true;
	}

	// Returns the greatest common factor of the input
	public static int gcf(int a, int b) {
		int maxFactor = min(a, b);
		// Using the for loop to iterate backwards, trying to find the gcf
		for (int gcf = maxFactor; gcf > 1; gcf--)
			if (a % gcf == 0 && b % gcf == 0)
				return gcf;
		return 1;
	}

	// Returns the estimated square root of an input type double
	public static double sqrt(double number) {
		// Check for edge case
		if (number < 0)
			throw  new IllegalArgumentException(number + " - cannot find square root of a negative number");
		// Using a method from some old guy
		double root = number / 2;
		do {
			root = 0.5 * (number / root + root);
		} while (absValue(number - square(root)) > 0.005); // Loop until result is accurate enough
		return round2(root);
	}

	// Returns roots of the  quadratic equation
	public static String quadForm(int a, int b, int c) {
		// Check if there are roots with discriminant
		if (discriminant(a, b, c) < 0)
			return "no real roots";

		// Using quadratic formula to find roots (round the roots too)
		String output = "";
		double root1 = round2((-b + sqrt(discriminant(a, b, c)))/(2*a));
		double root2 = round2((-b - sqrt(discriminant(a, b, c)))/(2*a));

		// If there is a double root, return just one of them.
		if (root1 == root2)
			return "" + root1;
		else
			return min(root1, root2) + " and " + max(root1, root2);
	}
}
