/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cplab6;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Leanne
 */
public class LamdaClass {

    /**
     * @param args the command line arguments
     */
    //utility method
    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }

    private void evaluate(List<Integer> listOfInt, Predicate<Integer> predicate) {
        listOfInt.forEach((num) -> {
            if (predicate.test(num)) {
                System.out.print(num + " ");
            }
        });
        System.out.println("");
    }

    public static void main(String[] args) {
        System.out.println("Question 1");
//        Q1
//        First way
//        MathOperation addition = (int a, int b) -> a + b;
//        MathOperation subtraction = (int a, int b) -> a - b;
//        MathOperation multiplication = (int a, int b) -> a * b;
//        MathOperation division = (int a, int b) -> (b != 0) ? a / b : b / a;
//        Second way 
        MathOperation addition = (int a, int b) -> {
            return a + b;
        };
        MathOperation subtraction = (int a, int b) -> {
            return a - b;
        };
        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };
        MathOperation division = (int a, int b) -> {
            return b != 0 ? a / b : b / a;
        };

//        First way of calling
        int a = 10, b = 5;
        int sum = addition.operation(a, b);
        System.out.println(sum);

//        Second way of calling
//        %n line separater
        LamdaClass lamda = new LamdaClass();
        System.out.printf("%d - %d = %s %n", a, b, lamda.operate(a, b, subtraction));
        System.out.printf("%d * %d = %s %n", a, b, lamda.operate(a, b, multiplication));
        System.out.printf("%d / %d = %s %n", a, b, lamda.operate(a, b, division));
        
        
        
        System.out.println("\nQuestion2\n---------");
//        Q2
        Integer[] listArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        List<Integer> list = Arrays.asList(listArray);
        Predicate<Integer> allElement = (num) -> true;
        Predicate<Integer> oddNumber = (num) -> num % 2 != 0;
        Predicate<Integer> evenNumber = (num) -> num % 2 == 0;
        Predicate<Integer> greatherThanFive = (num) -> num > 5;
        
        System.out.println("All Element:");
        lamda.evaluate(list, allElement);
        System.out.println("Even Number");
        lamda.evaluate(list, evenNumber);
        System.out.println("Odd Number:");
        lamda.evaluate(list, oddNumber);
        System.out.println("Greather Than Five:");
        lamda.evaluate(list, greatherThanFive);

    }

}
