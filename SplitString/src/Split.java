// Nathan Choi
// APCS 3rd

import java.util.*;

public class Split {
    public static void main(String[] args) {
        System.out.println();
        // Your task Part 0
        //String.split();
    
        //It's a method that acts on a string, <StringName>.split(<sp>);
        //Where sp is the string where the string splits
        //And it returns an array
    
        // Example: "I like apples!".split(" ");
        // it will split at spaces and return an array of ["I","like","apples!"]
        System.out.println("Example 1:");
        String[] example1 = "I like apples!".split("");
        System.out.println(Arrays.toString(example1));
    
        // Example 2: "I really like really red apples!".split("really")
        // it will split at the word "really" and return an array of ["I "," like ","red apples!"]
        System.out.println("Example 2:");
        String[] example2 = "I really like really red apples!".split("really");
        System.out.println(Arrays.toString(example2));
    
        //play around with String.split!
        //What happens if you "I reallyreally likeapples".split("really") ?
        //Your task Part 1:
        System.out.println("Example 3:");
        String[] task1 = "I reallyreally likeapples".split("really");
        System.out.println(Arrays.toString(task1));
        
        // Testing the methods below
        String[] meals = {
            "applespineapplesbreadbreadlettucebreadtomatobaconmayohambreadcheese",
            "apples pineapples bread lettuce tomato bacon mayo ham bread cheese"
        };
        
        System.out.println("\nTesting Methods:");
        System.out.println(task1("applespineapplesbreadbreadlettucebreadtomatobaconmayohambreadcheese"));
        System.out.println(task2("apples pineapples bread lettuce tomato bacon mayo ham bread cheese"));
    }
    
    /*Write a method that take in a string like
        * "applespineapplesbreadbreadlettucebreadtomatobaconmayohambreadcheese"
        * describing a sandwich.
        * Use String.split to split up the sandwich by the word "bread" and return what's in the middle of
        * the sandwich and ignores what's on the outside
        * What if it's a fancy sandwich with multiple pieces of bread?
    */
    
    private static String task1(String meal) {
        String[] splitMeal = meal.split("bread");
        String inside = "";
        // Iterate from second index to the second to last
        if (splitMeal.length < 3)
            return "Not a sandwich";
        for (int index = 1; index < splitMeal.length - 1; index++)
            inside += splitMeal[index];
        return "You have a " + inside + " sandwich.";
    }
    
    //Your task pt 2:
    /*Write a method that take in a string like
        * "apples pineapples bread lettuce tomato bacon mayo ham bread cheese"
        * describing a sandwich
        * use String.split to split up the sandwich at the spaces, " ", and return what's in the middle of
        * the sandwich and ignores what's on the outside.
        * Again, what if it's a fancy sandwich with multiple pieces of bread?
    */
    
    private static String task2(String meal) {
        String processedMeal = "", inside = "";
        String[] mealNoSpace = meal.split(" ");
        for (String ingredient : mealNoSpace)
            processedMeal += ingredient;
        String[] splitMeal = processedMeal.split("bread");
        for (int index = 1; index < splitMeal.length - 1; index++)
            inside += splitMeal[index] + " ";
        return "You have a " + inside + "sandwich.";
    }
}