/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cplab7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author Leanne
 */
class ForkJoinRecursiveSum extends RecursiveTask<Integer> {

    private static final int SEQEUNCIAL_THRESHOLD = 100;

    private int lowIndex, highIndex;
    private int[] array;
    
    
    
    ForkJoinRecursiveSum(int[] array, int lowIndex, int highIndex) {
        this.array = array;
        this.lowIndex = lowIndex;
        this.highIndex = highIndex;
    }

    @Override
    protected Integer compute() {
        if ((this.highIndex - this.lowIndex) <= SEQEUNCIAL_THRESHOLD) {
            return addSequencially(this.array, this.lowIndex, this.highIndex);
        } else {
            int mid = (this.highIndex + this.lowIndex) / 2;
            ForkJoinRecursiveSum leftPart = new ForkJoinRecursiveSum(this.array, this.lowIndex, mid);
            ForkJoinRecursiveSum rightPart = new ForkJoinRecursiveSum(this.array, mid + 1, this.highIndex);
            //Way1
//            leftPart.fork();
//            rightPart.fork();
//            return leftPart.join() + rightPart.join();
            //Way2
            invokeAll(leftPart,rightPart);
            return leftPart.join() + rightPart.join();
            //Way3
//            return (int) leftPart.invoke() + (int) rightPart.invoke();
        }
    }

    protected int addSequencially(int[] arr, int lowIndex, int highIndex) {
        int sum = 0;
        for (int i = lowIndex; i <= highIndex; i++) {
            sum += arr[i];
        }
        return sum;
    }
    
    public static int intermediateSum (int[] arr){
        ForkJoinPool poolSum = new ForkJoinPool();
        return poolSum.invoke(new ForkJoinRecursiveSum(arr, 0, arr.length-1));
    }

}

public class CPLab7 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int[] arr = new int[50000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        //Way1
        ForkJoinRecursiveSum add = new ForkJoinRecursiveSum(arr, 0, arr.length-1);
        long pStart = System.nanoTime();
        System.out.println(add.invoke());
        long pEnd = System.nanoTime();
        System.out.println("Parallel: "+ (pEnd - pStart));
        long sStart = System.nanoTime();
        System.out.println(add.addSequencially(arr, 0,arr.length-1));
        long sEnd = System.nanoTime();
        System.out.println("Sequencial: "+ (sEnd - sStart));
        //Way2
        System.out.println(ForkJoinRecursiveSum.intermediateSum(arr));
        
    }

}


//Home work
//F(0) = 1
//F(1) = 2
//F(n) = F(n-1) + F(n-2)
//do sub task for F(n-1), F(n-2)

