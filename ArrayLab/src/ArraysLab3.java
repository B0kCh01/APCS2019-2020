// Nathan Choi
// APCS 3rd
// October 28, 2019
// ArraysLab3.java

/*
    1) Contains multiple list manipulating methods.
    2) Has a runnable main method that utilizes them.
*/

import java.util.Arrays;

public class ArraysLab3 {
    public static void main(String[] args) {
        // integer arrays a1, a2, sumArr, appendArr, removeArr
        int[] sumArr, appendArr, removeArr;

        // int variables appendNum, removeIdx, sumOfEvens
        int appendNum, removeIdx, sumOfEvens;

        // Declare array a1 containing the values (5, 10, 15, 20, 25, 30, 35, 40)
        // ------- array a2 containing the values (7, 14, 21, 28, 35, 42, 49, 56)
        int[] a1 = { 5, 10, 15, 20, 25, 30, 35, 40 };
        int[] a2 = { 7, 14, 21, 28, 35, 42, 49, 56 };

        // Form sumArr by calling the sum method with a1 and a2 as inputs
        sumArr = sum(a1, a2);

        // Declare int appendNum and set it to 200.
        appendNum = 200;

        // Form appendArr by calling the append method with a1 and appendNum as inputs
        appendArr = append(a1, appendNum);

        // Declare int removeIdx and set it to 5.
        removeIdx = 5;

        // Form removeArr by calling remove with a2 and removeIdx
        removeArr = remove(a2, removeIdx);

        // Form sumOfEvens by calling sumEven with appendArr as the input
        sumOfEvens = sumEven(appendArr);

        // Call rotateRight with a1 as the input
        rotateRight(a1);

        // On their own lines print out (use Arrays.toString to transform arrays into a
        // printable strings) :
        System.out.println(" # |   VARIABLE | VALUE");
        System.out.println("-------------------------------------------------------");
        // 1) a1
        System.out.println(" 1 |         a1 | " + Arrays.toString(a1));
        // 2) sumArr
        System.out.println(" 2 |     sumArr | " + Arrays.toString(sumArr));
        // 3) appendArr
        System.out.println(" 3 |  appendArr | " + Arrays.toString(appendArr));
        // 4) removeArr
        System.out.println(" 4 |  removeArr | " + Arrays.toString(removeArr));
        // 5) sumOfEvens (an int you don’t need Arrays.toString)
        System.out.println(" 5 | sumOfEvens | " + sumOfEvens);

    }

    // 1) Write a method sum that accepts two arrays of integers arr1 and arr2 and
    // returns an array of integers,
    // in which every element is the sum of the elements at that index for the
    // arrays arr1 and arr2.
    // You can assume arrays arr1 and arr2 have at least one element each and are
    // the same length.
    private static int[] sum(int[] arr1, int[] arr2) {
        int[] newArr = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++)
            newArr[i] += arr1[i];
        return newArr;
    }

    // 2) Write a method append that accepts an array of integers arr of length n
    // and an integer num,
    // and returns an array of integers of length n+1 that consists of the elements
    // of arr with num appended to the end.
    // You can assume array arr has at least one element.

    private static int[] append(int[] arr, int num) {
        int[] newArr = new int[arr.length + 1];
        // System.arraycopy(arr, 0, newArr, 0, arr.length);
        for (int i = 0; i < arr.length; i++)
            newArr[i] = arr[i];
        newArr[newArr.length - 1] = num;
        return newArr;
    }

    // 3) Write a method remove that accepts an array of integers arr
    // and an integer idx
    // and returns an array of integers consisting of all of the elements of arr
    // except for the element at index idx (thus, the returned array has a length of
    // arr.length – 1).
    // You can assume arr has at least two elements.

    private static int[] remove(int[] arr, int idx) {
        int[] newArr = {};
        for (int n = 0; n < arr.length; n++)
            if (n != idx)
                newArr = append(newArr, arr[n]);
        return newArr;
    }

    // 4) Write a method sumEven that accepts an array of integers arr
    // and returns an integer containing the sum of the elements at the even indices
    // of arr.
    // (That means elements at indices 0,2,4,6,8.)
    // You can assume arr has at least one element.

    private static int sumEven(int[] arr) {
        int sum = 0;
        for (int num : arr)
            if (num % 2 == 0)
                sum += num;
        return sum;
    }

    // 5) Write a method rotateRight that accepts an array of integers arr and does
    // not return a value.
    // The rotateRight method moves each element of arr one index to the right
    // (element 0 goes to element 1, element 1 goes to element 2, …, element n-1
    // goes to element 0).
    // You can assume arr has at least one element.

    private static void rotateRight(int[] arr) {
        int last = arr[arr.length - 1];
        for (int i = arr.length - 1; i > 0; i--)
            arr[i] = arr[i - 1];
        arr[0] = last;
    }
}
