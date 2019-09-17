
// Nathan Choi
// August 2019
// APCS3
// DoMath.java --> Call the Calculate class
import CalculateLibrary.Calculate;
import java.util.Scanner;

public class DoMath {
    public static void main(String[] args) {
        System.out.println(Calculate.square(12.1));
        System.out.println(Calculate.cube(3.1));
        System.out.println(Calculate.average(3, 2));
        System.out.println(Calculate.average(5, 5, 4));
        System.out.println(Calculate.toDegrees(1.512));
        System.out.println(Calculate.toRadians(Calculate.toDegrees(1.512)));
        System.out.println(Calculate.discriminant(64, -1388, 7208));
        System.out.println(Calculate.toImproperFrac(-5, 3, 8));
        System.out.println(Calculate.toMixedNum(9, -7));
        System.out.println(Calculate.foil(-1, 3, -9, -3, "x"));
        System.out.println("Hello World!");
    }
}
