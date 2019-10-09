public class Hourglass {
    public static void main(String[] args) {
        print("| ", "");
        printThis(10, "\" ");
        print("|", "\n");

        for (int row = 1; row <= 4; row++) {
            printThis(row, "  ");
            print("\\ ", "");
            printThis(-2*row+10, ": ");
            print("/", "\n");
        }

        printThis(9, " ");
        System.out.println("|   |");

        for (int row = 4; row >= 1; row--) {
            printThis(row, "  ");
            print("/ ", "");
            printThis(-2*row+10, ": ");
            print("\\ ", "\n");
        }

        print("| ", "");
        printThis(10, "\" ");
        print("|", "\n");
    }

    private static void print(String string, String end){
        System.out.print(string + end);
    }

    private static void printThis(int num, String character) {
        for (int i = 0; i < num; i++)
            System.out.print(character);
    }
}
