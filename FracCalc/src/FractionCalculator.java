import java.util.Scanner;

public class FractionCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Number: ");
        String input = sc.nextLine();
        String decimals_str = input.substring(input.indexOf('.') + 1);

        System.out.println("Here are the decimals: " + decimals_str);
        int firstBracket = decimals_str.indexOf("[");
        int endingBracket = decimals_str.length() - 1;

        if (input.charAt(input.length() - 1) != ']')
            throw new IllegalArgumentException("Make sure to give correct input!");

        int repeating = Integer.parseInt(decimals_str.substring(firstBracket + 1, endingBracket));
        System.out.println(repeating);

        String nine = "";
        for (int i = 0; i < (repeating + "").length() + firstBracket; i ++)
            nine += "9";

        System.out.printf("Repeating decimal fraction: %s/%s", repeating, nine);

    }
}
