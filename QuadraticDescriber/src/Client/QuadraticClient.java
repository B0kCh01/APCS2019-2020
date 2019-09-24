package Client;

import Methods.*;
import java.util.Scanner;

public class QuadraticClient {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to QuadraticDescriber");
        System.out.println("Provide coefficents for a b c");

        double a = sc.nextDouble();
        double b = sc.nextDouble();
        double c = sc.nextDouble();

        System.out.println("Description of the graph:");
        System.out.println(Quadratic.quadrDescriber(a, b, c));

    }
}
