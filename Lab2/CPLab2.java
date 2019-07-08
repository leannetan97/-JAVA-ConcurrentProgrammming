/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cplab2;

import java.util.Arrays;
import java.util.Random;
//import java.util.concurrent.ExecutorService;

/**
 *
 * @author Tan Lay Yan WIF160058
 */
class Sequentially {

    private int[] arr;

    public Sequentially(int[] arr) {
        this.arr = arr;
    }

    public int FindLargest() {
        int largest = -1;
        for (int i = 0; i < this.arr.length; i++) {
            if (this.arr[i] > largest) {
                largest = this.arr[i];
            }
        }
        return largest;
    }
}

class Concurrently implements Runnable {

    private int[] arr;
    private int maximumNum = Integer.MIN_VALUE;
    private int startIndex;
    private int endIndex;

    public Concurrently(int[] arr, int startIndex, int endIndex) {
        this.arr = arr;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public void findLargest() {
        for (int i = startIndex; i < endIndex; i++) {
            if (arr[i] > maximumNum) {
                maximumNum = arr[i];
            }
        }
    }

    public int getMaximum() {
        return this.maximumNum;
    }

    @Override
    public void run() {
        findLargest();
    }

}

class Timer {

    private long startTime;
    private long endTime;
    private long timeDiff;

    public void startTime() {
        this.startTime = System.nanoTime();
    }

    public void endTime() {
        this.endTime = System.nanoTime();
    }

    public long timeDiff() {
        this.timeDiff = this.endTime - this.startTime;
        return this.timeDiff;
    }

}

public class CPLab2 {

    /**
     * @param args the command line arguments
     */
    public static void Sequencial(int[] arr) {
        Timer time1 = new Timer();
        Sequentially s = new Sequentially(arr);
        time1.startTime();
        System.out.println("Squential Largest: " + s.FindLargest());
        time1.endTime();
        System.out.println("Sequential Time Diff: " + time1.timeDiff());
    }

    public static void twoThread(int[] arr) {
        Timer time2 = new Timer();
//        int[] a1 = Arrays.copyOfRange(arr, 0, arr.length / 2);
//        int[] a2 = Arrays.copyOfRange(arr, arr.length / 2, arr.length);
        Concurrently c1 = new Concurrently(arr, 0, arr.length / 2);
        Concurrently c2 = new Concurrently(arr, arr.length / 2, arr.length);

        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);
        time2.startTime();
        t1.start();
        t2.start();
//        t1.run();
//        t2.run();
        try {
            t1.join();
            t2.join();
        } catch (Exception e) {

        }
        time2.endTime();
        System.out.println("2 Concurrent Largest: " + Math.max(c1.getMaximum(), c2.getMaximum()));
        System.out.println("2 Concurrent Time Diff: " + time2.timeDiff());
    }

    public static void fourThread(int[] arr) {
        Timer time3 = new Timer();
//        int[] b1 = Arrays.copyOfRange(arr, 0, arr.length / 4);
//        int[] b2 = Arrays.copyOfRange(arr, arr.length / 4, arr.length / 2);
//        int[] b3 = Arrays.copyOfRange(arr, arr.length / 2, 3 * arr.length / 4);
//        int[] b4 = Arrays.copyOfRange(arr, 3 * arr.length / 4 / 2, arr.length);
        Concurrently c_1 = new Concurrently(arr, 0, arr.length / 4);
        Concurrently c_2 = new Concurrently(arr, arr.length / 4, arr.length / 2);
        Concurrently c_3 = new Concurrently(arr, arr.length / 2, 3 * arr.length / 4);
        Concurrently c_4 = new Concurrently(arr, 3 * arr.length / 4 / 2, arr.length);

        Thread t_1 = new Thread(c_1);
        Thread t_2 = new Thread(c_2);
        Thread t_3 = new Thread(c_3);
        Thread t_4 = new Thread(c_4);

        time3.startTime();
        t_1.start();
        t_2.start();
        t_3.start();
        t_4.start();
//        t_1.run();
//        t_2.run();
//        t_3.run();
//        t_4.run();

        try {
            t_1.join();
            t_2.join();
            t_3.join();
            t_4.join();
        } catch (Exception e) {

        }
        int largest1 = Math.max(c_1.getMaximum(), c_2.getMaximum());
        int largest2 = Math.max(c_3.getMaximum(), c_4.getMaximum());
        System.out.println("4 Concurrent Largest: " + Math.max(largest1, largest2));
        time3.endTime();
        System.out.println("4 Concurrent Time Diff: " + time3.timeDiff());
    }

    public static void main(String[] args) {
        // TODO code application logic here
        int elementNum = 60000;
        int[] arr = new int[elementNum];
        for (int i = 0; i < arr.length; i++) {
            Random rdm = new Random();
            arr[i] = rdm.nextInt(elementNum) + 1;
        }
        System.out.println("Array : " + Arrays.toString(arr));

        
        //Create A Pool of threads
//        ExecutorService ex = Excecutors.newCachedThreadPool();
//        IntsStream 
        //Q1
        Sequencial(arr);
        //Q2
        twoThread(arr);
        //Q3
        fourThread(arr);
    }

}
