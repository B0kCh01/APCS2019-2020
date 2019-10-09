import java.util.Scanner;

public class ProcessingNumbers {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("How many numbers? ");
        int numTotal = input.nextInt();

        // First Iteration
        System.out.print("1: ");
        double first = input.nextDouble();
        double sum = 0;
        double max = first;
        double min = first;
        double maxEven = first;
        boolean foundEven = false;

        if (first % 2 == 0) {
            foundEven = true;
            sum += first;
        }

        for (int i = 2; i <= numTotal; i++) {
            // Get next number
            System.out.print(i + ": ");
            double number = input.nextDouble();
            // If number is greater than the max, replace it
            if (number > max) {
                max = number;
                if (number % 2 == 0)
                    maxEven = number;
            } else if (number < min) { // If the number is less then the min, replace it
                min = number;
            }
            // If the number is even, add it to the sum
            if (number % 2 == 0)
                sum += number;
        }
        // Print the output
        System.out.printf("\nResults:\n-----\nMax Even Number: %s\nSum of Evens: %s\nGlobal Maximum: %s\nGlobal Minimum: %s\n",
                foundEven ? maxEven : "None", sum, max, min);
    }
}
