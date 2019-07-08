/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cplab1;

//import java.util.logging.Logger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tan Lay Yan WIF160058
 */

/* 1(a)
 * Create a class called PrintChar that prints a given character for a given time
 */
class PrintChar implements Runnable{
    private int times;
    private char c;

    public PrintChar(int times, char c) {
        this.times = times;
        this.c = c;
    }
    
    public void run(){
        for (int i = 0; i < times; i++) {
            System.out.println(c +" ");
        }
    }  
}

/* 1(b)	
 * Create a class called PrintNum that prints numbers from 1 to the given number.
 */
class PrintNum implements Runnable{
    private int num;
    
    public PrintNum(int num) {
        this.num = num;
    }

    
    public void run() {
        for (int i = 1; i <= num; i++) {
            System.out.println(i);
        }
    }    
    
}


/* 1(c)
 * Create a driver class that runs 2 instances of PrintChar and 1 instance of PrintNum as theads
 */
public class CPLab1Q1Q2 {

    /**
     * @param args Runs 2 instances of PrintChar and 1 instance of PrintNum as threads.
     */
    public static void main(String[] args) {
        // 1(c)
        Runnable a = new PrintChar(200,'a');
        Runnable b = new PrintChar(80,'b');
        Runnable c = new PrintNum(100);
        
        Thread t1 = new Thread(a);
        Thread t2 = new Thread(b);
        Thread t3 = new Thread(c);
        
        t1.start();
        t2.start();
        t3.start();
        
        // 2
        Runnable d = new PrintCharSleep(80,'x');
        Runnable e = new PrintNumSleep(345);
        
        Thread t4 = new Thread(d);
        Thread t5 = new Thread(e);
        
        t4.start();
        t5.start();
    }   
}

/* 2
 * Create 2 threads and make them run interleaving each other using sleep()
 */
class PrintCharSleep implements Runnable {

    private int times;
    private char c;

    public PrintCharSleep(int times, char c) {
        this.times = times;
        this.c = c;
    }

    public void run() {
        for (int i = 0; i < times; i++) {
            try {
                if (i == 5) {
                    Thread.sleep(100);
                }

            } catch (Exception e) {

            }

            System.out.println(c + " ");
        }
    }
}

class PrintNumSleep implements Runnable {

    private int num;

    public PrintNumSleep(int num) {
        this.num = num;
    }

    public void run() {
        for (int i = 1; i <= num; i++) {
            try {
                if (i == 2) {
                    Thread.sleep(5000);
                }
            } catch (Exception e) {

            }
            System.out.println(i);
        }
    }
}

//Dr Chiew Answer for Q2
//class Interleave implements Runnable {
//
//    private Thread t;
//    private String threadName;
//
//    public Interleave(String name) {
//        threadName = name;
//    }
//
//    public void run() {
//        for (int i = 0; i < 3; i++) {
//            System.out.println("Running " + threadName);
//
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Interleave.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        Runnable R1 = new Interleave("Thread-1");
//        Runnable R2 = new Interleave("Thread-2");
//
//        Thread t1 = new Thread(R1);
//        Thread t2 = new Thread(R2);
//
//        t1.start();
//        t2.start();
//    }
//}
