package Client;

import Methods.*;

import java.util.Scanner;

public class QuadraticClient {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to QuadraticDescriber");
        System.out.println("\nProvide coefficents for a b c");
        System.out.println("--------------------");

        System.out.print("a:");
        double a = sc.nextDouble();
        System.out.print("b:");
        double b = sc.nextDouble();
        System.out.print("c:");
        double c = sc.nextDouble();

        System.out.println("\nDescription of the graph:");
        System.out.println(Quadratic.quadrDescriber(a, b, c));

    }
}