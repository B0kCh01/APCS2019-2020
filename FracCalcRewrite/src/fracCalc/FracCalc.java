// Nathan Choi
// APCS 3rd
// January 13, 2020
// Excepts expression and outputs answer (support fraction computation)

package fracCalc;
import java.util.*;

public class FracCalc {
    // Main Class (Client Code Front-End)
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("FracCalc by Nathan Choi (b0kch01)\nEnter \"quit\" to quit!\n\nInput: ");
        for (String input = sc.nextLine();
             !input.equalsIgnoreCase("quit");
             System.out.print("\nInput: "), input = sc.nextLine())
        {
            System.out.println(produceAnswer(input));
        }
    }

    // Produce Answer (Client Code Back-End)
    public static String produceAnswer(String input) {
        // Split the first input into the fractions and operand with the space.
        String[] inputs = input.split(" ");

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
        } else {
            fraction1.divide(fraction2);
        }
        // Reduce the answer after calculation
        fraction1.reduceFraction();
        // Convert the fraction to Mixed and return it
        fraction1.toMixed();
        return fraction1.toString();
    }
}