/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cplab4;

import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Tan Lay Yan WIF160058
 */

/* Using Synchronized */
class ManageStackAccessSynchronized {
    
    private Stack<Integer> s = new Stack<>();
    final int MAXCAPACITY = 6;
    
    synchronized public void pushToStack(int num) {
        while (s.size() >= MAXCAPACITY) {
            try {
                System.out.println("[Synchronized] " + Thread.currentThread().getName() + " : Stack reach Max, Waiting to push... ");
                wait();
            } catch (InterruptedException e) {
                
            }
        }
        System.out.println("[Synchronized] " + Thread.currentThread().getName() + " : Push " + num + " into Stack");
        s.push(num);
        System.out.println("Stack: " + s.toString());
        if (s.size() == 1) {
            notifyAll();
        }
        
    }
    
    synchronized public void popFromStack() {
        while (s.isEmpty()) {
            try {
                System.out.println("[Synchronized] " + Thread.currentThread().getName() + " : Stack is empty, Waiting to pop... ");
                wait();
            } catch (InterruptedException e) {
                
            }
        }
        System.out.println("[Synchronized] " + Thread.currentThread().getName() + " : Pop " + s.peek() + " from Stack");
        s.pop();
        System.out.println("Stack: " + s.toString());
        notifyAll();
    }
}

class PutElementSynchronized implements Runnable {
    
    private final ManageStackAccessSynchronized object;
    
    public PutElementSynchronized(ManageStackAccessSynchronized object) {
        this.object = object;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                Integer num = new Random().nextInt(100);
                object.pushToStack(num);
                if (i < 9) {
                    Thread.sleep(20);
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
    }
}

class RetrieveElementSynchronized implements Runnable {
    
    private final ManageStackAccessSynchronized object;
    
    public RetrieveElementSynchronized(ManageStackAccessSynchronized object) {
        this.object = object;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                object.popFromStack();
                if (i < 4) {
                    Thread.sleep(50);
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

//-------------------------------------------------------------------------------------------//

/* Using Lock */
class ManageStackAccess {
    
    private static Lock lock = new ReentrantLock();
    private final Condition isFull = lock.newCondition();
    private final Condition isEmpty = lock.newCondition();
    private Stack<Integer> s = new Stack<>();
    final int MAXCAPACITY = 6;
    
    public void pushToStack(Integer num) {
        System.out.println(Thread.currentThread().getName() + " : Acquire Lock to perform push");
        lock.lock();
        
        try {
            while (s.size() >= MAXCAPACITY) {
                System.out.println(Thread.currentThread().getName() + " : Max Capacity Reach, Waiting...");
                isFull.await();
            }
            System.out.println(Thread.currentThread().getName() + " Push " + num + " to stack");
            s.push(num);
            System.out.println("Stack : " + s.toString());
            if (s.size() == 1) {
                isEmpty.signalAll();
            }
        } catch (Exception e) {
        } finally {
            System.out.println(Thread.currentThread().getName() + " : Release Push Lock");
            lock.unlock();
            
        }
        
    }
    
    public void popFromStack() {
        System.out.println(Thread.currentThread().getName() + " : Acquire Lock to pop");
        lock.lock();
        
        try {
            while (s.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + " : Stack Empty, waiting...");
                isEmpty.await();
            }
            System.out.println(Thread.currentThread().getName() + " Pop " + s.peek() + " from stack");
            s.pop();
            System.out.println("Stack : " + s.toString());
        } catch (Exception e) {
        } finally {
            isFull.signalAll();
            System.out.println(Thread.currentThread().getName() + " : Release Pop Lock");
            lock.unlock();
        }
        
    }
}

class PutElement implements Runnable {
    
    private final ManageStackAccess object;
    
    public PutElement(ManageStackAccess object) {
        this.object = object;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                Integer num = new Random().nextInt(100);
                object.pushToStack(num);
                if (i < 9) {
                    Thread.sleep(20);
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
    }
}

class RetrieveElement implements Runnable {
    
    private final ManageStackAccess object;
    
    public RetrieveElement(ManageStackAccess object) {
        this.object = object;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                object.popFromStack();
                if (i < 4) {
                    Thread.sleep(50);
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

public class CPLab4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        ManageStackAccess stack = new ManageStackAccess();
        
        ManageStackAccessSynchronized stack2 = new ManageStackAccessSynchronized();
//        ExecutorService executor = Executors.newFixedThreadPool(3);
        ExecutorService executor = Executors.newCachedThreadPool();

        /* Using Lock */
        try {
            executor.execute(new RetrieveElement(stack));
            executor.execute(new RetrieveElement(stack));
            executor.execute(new RetrieveElement(stack));
            executor.execute(new PutElement(stack));
            executor.execute(new PutElement(stack));
            
        } catch (Exception e) {
            
        } finally {
            // shut down the executor manually
            
            executor.shutdown();
//            while (!executor.awaitTermination(1, TimeUnit.DAYS)) {
//                System.out.println("Not yet. Still waiting for termination");
//            }
            //OR
            while(!executor.isTerminated()){
                
            }
            System.out.println("Yeah");
            
        }

        /* Using Synchronize */
//        try {
//            executor.execute(new RetrieveElementSynchronized(stack2));
////            executor.execute(new PutElementSynchronized(stack2));
//            executor.execute(new RetrieveElementSynchronized(stack2));
////            executor.execute(new PutElementSynchronized(stack2));
//            executor.execute(new RetrieveElementSynchronized(stack2));
//            
//            executor.execute(new PutElementSynchronized(stack2));
//            executor.execute(new PutElementSynchronized(stack2));
//        } catch (Exception e) {
//
//        } finally {
//            // shut down the executor manually
//            executor.shutdown();
//        }
    }
}
