/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cplab1;

/**
 *
 * @author Tan Lay Yan WIF160058
 */
class BankAccount {

    private double balance;
    private int flag = 0;

    public BankAccount() {
        balance = 0.0;
    }

    public synchronized double withdraw(double amount) {
        System.out.println(Thread.currentThread().getName() + " is going to withdraw");
        //Avoid withdraw when first time doing transaction
        if (this.flag == 0) {
            try {
                System.out.println("Waiting…");
                wait();
            } catch (Exception e) {
            }
        }
        this.balance -= amount;
        System.out.println("Withdraw Done");
        System.out.println("Current Balance : " + this.balance);
        return balance;
    }

    public synchronized double deposit(double amount) {
        System.out.println(Thread.currentThread().getName() + " is going to deposit");
        this.balance += amount;
        notifyAll();
        this.flag = 1;
        System.out.println("Deposite Done");
        System.out.println("Current Balance : " + this.balance);
        return balance;
    }
}

class Deposit implements Runnable {

    private double depositeAmount;
    BankAccount bankAcc;

    public Deposit(double depositeAmount, BankAccount bankAcc) {
        this.depositeAmount = depositeAmount;
        this.bankAcc = bankAcc;
    }

    @Override
    public void run() {
        bankAcc.deposit(this.depositeAmount);
    }
}

class Withdraw implements Runnable {

    private double withdrawAmount;
    BankAccount bankAcc;

    public Withdraw(double withdrawAmount, BankAccount bankAcc) {
        this.withdrawAmount = withdrawAmount;
        this.bankAcc = bankAcc;
    }

    @Override
    public void run() {
        bankAcc.withdraw(this.withdrawAmount);
    }
}

public class CPLab1Q3 {

    public static void main(String[] args) {

        BankAccount bankAcc = new BankAccount();

        Runnable run1 = new Deposit(10.00, bankAcc);
        Runnable run2 = new Withdraw(2.00, bankAcc);
        Runnable run3 = new Deposit(5.00, bankAcc);

        Thread t0 = new Thread(run1);
        Thread t1 = new Thread(run2);
        Thread t3 = new Thread(run1);
        Thread t4 = new Thread(run2);
        Thread t5 = new Thread(run3);

        t0.start();
        t1.start();
        t3.start();
        t4.start();
        t5.start();
       
    }

    public static int oddOrPos(int[] x) {
        int count = 0;
        for (int i = 0; i < x.length; i++) {
            if (Math.abs(x[i]) % 2 == 1 || x[i] > 0) {
                count++;
            }
        }
        System.out.println(count);
        return count;
    }

}
