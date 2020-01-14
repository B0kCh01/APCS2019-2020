// Nathan Choi
// APCS 3rd
// January 13, 2020
// Excepts expression and outputs answer (support fraction computation)

package fracCalc;
import java.util.*;

public class FracCalc {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("FracCalc by Nathan Choi (b0kch01)\nEnter \"quit\" to quit!\n\nInput: ");
        for (String input = sc.nextLine();
             !input.equalsIgnoreCase("quit");
             System.out.print("\nInput: "), input = sc.nextLine())
        {
            System.out.println("Answer: " + produceAnswer(input));
        }
    }

    public static String produceAnswer(String input) {
        Fraction fraction = new Fraction(input);
        fraction.reduceFraction();
        fraction.toMixed();
        return "Your fraction is: " + fraction;
    }
}