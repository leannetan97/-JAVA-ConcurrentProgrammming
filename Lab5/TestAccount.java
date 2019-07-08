/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cplab5;

/**
 *
 * @author Tan Lay Yan WIF160058
 */
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class Account {
//    private int balance = 0;

    private AtomicInteger balance = new AtomicInteger(0);

    public int getBalance() {
//        return balance;
        return balance.get();
    }

    public void deposit(int amount) {
        //Dr.Chiew ans:
//    balance.set(balance.addAndGet(amount));
//    System.out.println("The new balance is " + balance.get()); //perform 3 steps together

//     int newBalance = balance + amount;
        boolean update = false;
        while (!update) {
            int currentBalance = balance.get();
            System.out.println("Print currentBalance :"+ currentBalance);
            int newBalance = balance.get() + amount;

            System.out.println("The new balance is " + newBalance);
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
            }
//        balance = newBalance;
//        balance.getAndSet(newBalance);
            update = this.balance.compareAndSet(currentBalance, currentBalance + 1);
            
            // it will compare the currentBalance of the thread u get with the latest atomic one, if correct thn only it will set the value to the +1
        }
    }
}

    class AddToAccount implements Runnable {

        private Account account = new Account();

        public AddToAccount(Account acc) {
            account = acc;
        }

        public void run() {
            account.deposit(1);
            System.out.println("Added 1 ringgit.");
        }
    }

    public class TestAccount {

        public static void main(String[] args) {
            Account myAccount = new Account();

            ExecutorService executor = Executors.newCachedThreadPool();
            for (int i = 0; i < 10; i++) {
                executor.execute(new AddToAccount(myAccount));
            }

            executor.shutdown();
            while (!executor.isTerminated()) {
            }

            System.out.println("The final balance is RM" + myAccount.getBalance());
        }
    }
