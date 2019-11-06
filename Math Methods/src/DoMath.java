
// Nathan Choi
// August 2019
// APCS3
// DoMath.java --> Call the Calculate class
package CalculatorTest;

import CalculateLibrary.Calculate.*;

import static CalculateLibrary.Calculate.*;

public class DoMath {
    public static void main(String[] args) {
         testFor(square(5), 25);
         testFor(cube(4),  64);
         testFor(average(10, 34.5, -634), (10+34.5-634)/3);
         testFor(discriminant(3, 5, 2), 1);
         testFor(isDivisibleBy(34, 17), true);
         testFor(round2(23.35734), 23.36);
         testFor(exponent(23, 2), 529);
         testFor(exponent(4536.54, 0), 1);
         testFor(factorial(7), 5040);
         testFor(isPrime(13459), false);
         testFor(gcf(5544564,4657455), 3);
         testFor(sqrt(9), 3);
    }

    public static void testFor(double a, double b){
        if (a != b) throw new ArithmeticException("Oops");
    }

    public static void testFor(double a, int b) {
        if (a != b + 0.0) throw new ArithmeticException("Oops");
    }

    public static void testFor(boolean a, boolean b) {
        if (a != b) throw new ArithmeticException("Oops");
    }

    public static void testFor(String a, String b){
        if (!a.equals(b)) throw new ArithmeticException("RIpp,");
    }
}
