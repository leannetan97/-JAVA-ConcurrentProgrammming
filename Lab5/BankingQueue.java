package cplab5;

class CallingQueue implements Runnable {

    private BankingQueue BQ;

    public CallingQueue(BankingQueue BQ) {
        this.BQ = BQ;
    }

    @Override
    public void run() {
        while (BQ.getNextInLine() <= 10) {
            System.out.format("Calling for the customer #%d\n", BQ.getNextInLine());
            //Because time gap problem 导致print的次序不一样，可以Move line 20 去sleep后
            BQ.incrementNextInLIne();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class CustomerInLine implements Runnable {

    private BankingQueue BQ;
    private int targetNumber;

    public CustomerInLine(BankingQueue BQ, int targetNumber) {
        this.BQ = BQ;
        this.targetNumber = targetNumber;
    }

    @Override
    public void run() {
        while (true) {
            if (BQ.getNextInLine() >= targetNumber) {
                break;
            }
        }
        System.out.format("Great, finally #%d was called, now it is my turn\n", targetNumber);
    }
}

public class BankingQueue {
    
    volatile int nextInLine = 1;

    public int getNextInLine() {
        return nextInLine;
    }

    public void incrementNextInLIne() {
        nextInLine++;
    }

    public static void main(String[] args) throws Exception {
        BankingQueue BQ = new BankingQueue();
        Runnable cq = new CallingQueue(BQ);
        Runnable cil = new CustomerInLine(BQ, 4);
        Thread t1 = new Thread(cq);
        Thread t2 = new Thread(cil);
        t1.start();
        t2.start();
    }
}
