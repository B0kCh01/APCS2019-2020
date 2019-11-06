// Nathan Choi
// August 2019
// APCS3
// DoMath.java --> Call the Calculate class

public class DoMath {
    public static void main(String[] args) {
        testFor(Calculate.square(5), 25);
        testFor(Calculate.cube(4),  64);
        testFor(Calculate.average(10, 34.5, -634), (10+34.5-634)/3);
        testFor(Calculate.discriminant(3, 5, 2), 1);
        testFor(Calculate.isDivisibleBy(34, 17), true);
        testFor(Calculate.round2(23.35734), 23.36);
        testFor(Calculate.exponent(23, 2), 529);
        testFor(Calculate.exponent(4536.54, 0), 1);
        testFor(Calculate.factorial(7), 5040);
        testFor(Calculate.isPrime(13459), false);
        testFor(Calculate.gcf(5544564,4657455), 3);
        testFor(Calculate.sqrt(9), 3);
        testFor(Calculate.toMixedNum(7, 2), "3_1/2");
    }

    private static void testFor(double a, double b){
        if (a != b) throw new ArithmeticException("Oops");
    }

    private static void testFor(double a, int b) {
        if (a != b + 0.0) throw new ArithmeticException("Oops");
    }

    private static void testFor(boolean a, boolean b) {
        if (a != b) throw new ArithmeticException("Oops");
    }

    public static void testFor(String a, String b){
        if (!a.equals(b)) throw new ArithmeticException("RIpp,");
    }
}
