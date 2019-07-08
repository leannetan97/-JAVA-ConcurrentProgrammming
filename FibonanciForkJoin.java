/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cplab7;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author Leanne
 */
public class FibonanciForkJoin {

    public static void main(String[] args) {
        Fibonanci f = new Fibonanci(100);
        System.out.println(f.invoke());
    }
}

class Fibonanci extends RecursiveTask<BigInteger> {

    private int n;

    private final int THRESHOLD = 100;

    public Fibonanci(int n) {
        this.n = n;
    }

    @Override
    protected BigInteger compute() {
        if (n > THRESHOLD) {
            //way 1
//             reduce(initialValue, a function that u want to do with the value that u return)
//            return ForkJoinTask.invokeAll(createSubtask(n)).stream().map(ForkJoinTask::join).reduce(BigInteger.ZERO,BigInteger::add);
            Fibonanci partN1 = new Fibonanci(n - 1);
            Fibonanci partN2 = new Fibonanci(n - 2);
            //Way 2
//            return partN1.invoke().add(partN2.invoke());
            //Way 3
//            invokeAll(partN1,partN2);
//            return partN1.join().add(partN2.join());
            //Way 4
            partN1.fork();
            partN2.fork();
            return partN1.join().add(partN2.join());
        } else {
            return fibonanciSequencially(n);
//            return fibonanciSequenciallyRecursive(n);
        }
    }

    private BigInteger fibonanciSequencially(int n) {
        if (n == 1 || n == 2) {
            return BigInteger.ONE;
        }
        BigInteger fibo1 = BigInteger.ONE;
        BigInteger fibo2 = BigInteger.ONE;
        BigInteger fiboAns = BigInteger.ZERO;
        for (int i = 3; i <= n; i++) {
            fiboAns = fibo1.add(fibo2);
            fibo1 = fibo2;
            fibo2 = fiboAns;
        }
        return fiboAns;
    }
    
    private BigInteger fibonanciSequenciallyRecursive(int n) {
        if (n <= 2) {
            return BigInteger.ONE;
        }
        return fibonanciSequenciallyRecursive(n-1).add(fibonanciSequenciallyRecursive(n-2));
        
    }

    private List<Fibonanci> createSubtask(int n) {
        List<Fibonanci> fiboList = new ArrayList<>();
        fiboList.add(new Fibonanci(n - 1));
        fiboList.add(new Fibonanci(n - 2));
        return fiboList;
    }
}
