// Nathan Choi
// APCS 3rd
// January 13, 2020
// Excepts expression and outputs answer (support fraction computation)

package fracCalc;
import java.util.*;

public class FracCalc {
    // Main Class (Client Code Front-End)
    public static void main(String[] args) {
        // Main scanner object
        Scanner sc = new Scanner(System.in);
        // Title text && First iteration
        System.out.println("FracCalc by Nathan Choi (b0kch01)");
        System.out.println("Enter \"quit\" to quit!");
        System.out.print("\nInput: ");

        for (String input = sc.nextLine(); // Runs first
             !input.equalsIgnoreCase("quit"); // Checks for exit condition every iteration
             System.out.print("\nInput: "), input = sc.nextLine()) // Asks for new input after each iteration
        {
            System.out.println("Answer: " + produceAnswer(input));
        }
    }

    // Produce Answer (Client Code Back-End)
    public static String produceAnswer(String input) {
        // Split the first input into the fractions and operand with the space.
        String[] inputs = input.split(" ");

        // Simple error checking
        if (!input.contains("123456789 ") || !input.contains("+-/%") || inputs.length != 3)
            return "Oh no! Make sure to follow the guide: [fraction] [+,-,/,%,*] [fraction]";

        // Store the inputs. Operand = String, fractions = Fraction object
        String operand = inputs[1];
        Fraction fraction1 = new Fraction(inputs[0]);
        Fraction fraction2 = new Fraction(inputs[2]);

        // Undergo calculation depending on the operand.
        if (operand.equals("+")) {
            fraction1.add(fraction2);
        } else if (operand.equals("-")) {
            fraction1.subtract(fraction2);
        } else if (operand.equals("*")) {
            fraction1.multiply(fraction2);
        } else if ("%/".contains(operand)){
            fraction1.divide(fraction2);
        }
        // Reduce the answer after calculation
        fraction1.reduceFraction();
        // Convert the fraction to Mixed and return it
        fraction1.toMixed();
        return fraction1.toString();
    }
}