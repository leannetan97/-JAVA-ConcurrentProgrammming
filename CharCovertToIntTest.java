/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cplab7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author Leanne
 */
// Question:create a recursiveTask class that takes a string as input then return a string contained the numerical value of the each of the char in the input string
public class CharCovertToIntTest {

    public static void main(String[] args) {
        CharCovertToInt input = new CharCovertToInt("abcdefgh");
        System.out.println(input.invoke());
    }
}

class CharCovertToInt extends RecursiveTask<String> {

    private final int THRESHOLD = 4;
    private String inputString;

    public CharCovertToInt(String inputString) {
        this.inputString = inputString;
    }

    @Override
    protected String compute() {
        if (inputString.length() > THRESHOLD) {
            //Way 1
//            int mid = (0 + inputString.length()) / 2;
//            CharCovertToInt part1 = new CharCovertToInt( inputString.substring(0, mid));
//            CharCovertToInt part2 = new CharCovertToInt( inputString.substring(mid,  inputString.length()));
//            return part1.invoke() + part2.invoke();
            // Way 2
//            return ForkJoinTask.invokeAll(createSubTask(inputString)).stream().map(ForkJoinTask::join).reduce("",(p1,p2)-> p1+ p2);
            //Way 3
            return ForkJoinTask.invokeAll(split(inputString)).stream().map(ForkJoinTask::join).reduce("",String::concat);

        } else {
            return convertSequencially();
        }
    }

    private List<CharCovertToInt> createSubTask(String inputString) {
        int mid = (0 + inputString.length()) / 2;
        List<CharCovertToInt> partList = new ArrayList<>();
        CharCovertToInt part1 = new CharCovertToInt(inputString.substring(0, mid));
        CharCovertToInt part2 = new CharCovertToInt(inputString.substring(mid, inputString.length()));
        partList.add(part1);
        partList.add(part2);
        return partList;
    }
    private List<CharCovertToInt> split(String inputString) {
        String left = inputString.substring(0, inputString.length() / 2);
        String right = inputString.substring(inputString.length() / 2, inputString.length());

        List<CharCovertToInt> list = new ArrayList<>();
        list.add(new CharCovertToInt(left));
        list.add(new CharCovertToInt(right));

        return list;
    }

    private String convertSequencially() {
        String valueString = "";
        for (int i = 0; i < inputString.length(); i++) {
            valueString += Character.getNumericValue(inputString.charAt(i));
//            valueString += (int) inputString.charAt(i);
        }
        return valueString;
    }
}
