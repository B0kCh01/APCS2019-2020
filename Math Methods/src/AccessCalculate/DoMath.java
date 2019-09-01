// Nathan Choi
// August 2019
// APCS3
// DoMath.java --> Call the Calculate class
package AccessCalculate;
import CalculateLibrary.Calculate;
import java.util.Scanner;

public class DoMath {
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
    	System.out.print("Enter number: ");
    	double num = sc.nextDouble();
    	System.out.print("Raw number: " + num + "\nRounded number: ");
    	System.out.println(Calculate.round2(num));
    }
}