/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cplab3;

import java.util.ArrayList;

/**
 *
 * @author Tan Lay Yan WIF160058
 */
class Room {

    private final String roomName;
    private int guestNum;
    private boolean haveCleaner;

    public Room(String roomName) {
        this.roomName = roomName;
        this.guestNum = 0;
        this.haveCleaner = false;
    }

    //GETTER SETTER
    public int getGuestNum() {
        return this.guestNum;
    }

    public boolean getCleanerStatus() {
        return this.haveCleaner;
    }

    //CLEANER
    public synchronized void cleanerEntered(String cleanerName) {
        //Without Recursive
        while (getCleanerStatus() || getGuestNum() > 0) {
            try {
                System.out.println(Thread.currentThread().getName() + "  [" + cleanerName + "] Cleaner is waiting to Enter " + this.roomName);
                wait();
            } catch (Exception e) {
            }
        }
        System.out.println(Thread.currentThread().getName() + "  [" + cleanerName + "] Cleaner is going to Enter " + this.roomName);
        setCleanerStatus(true);
        
        //With Recursive
//        if (!getCleanerStatus() && getGuestNum() == 0) {
//            System.out.println(Thread.currentThread().getName() + "  [" + cleanerName + "] Cleaner is going to Enter " + this.roomName);
//            setCleanerStatus(true);
//        } else {
//            try {
//                System.out.println(Thread.currentThread().getName() + "  [" + cleanerName + "] Cleaner is waiting to Enter " + this.roomName);
//                wait();
//            } catch (Exception e) {
//            }
//            cleanerEntered(cleanerName);
//        }

    }

    public synchronized void cleanerExited(String cleanerName) {
        System.out.println(Thread.currentThread().getName() + "  [" + cleanerName + "] Cleaner is going to Exit " + this.roomName);
        setCleanerStatus(false);
        notifyAll();
    }

    public void setCleanerStatus(boolean status) {
        this.haveCleaner = status;
    }

    //GUEST
    public synchronized void guestEntered(String guestName) {
        //Without recursive
        while(getCleanerStatus() || getGuestNum() >= 6 ){
            try {
                System.out.println(Thread.currentThread().getName() + "  [" + guestName + "] Guest is waiting to Enter " + this.roomName);
                wait();
            } catch (Exception e) {
            }
        }
        System.out.println(Thread.currentThread().getName() + "  [" + guestName + "] Guest is going to Enter " + this.roomName);
        increaseGuest();
        
        
        //With recursive
//        if (!getCleanerStatus() && getGuestNum() < 6) {
//            System.out.println(Thread.currentThread().getName() + "  [" + guestName + "] Guest is going to Enter " + this.roomName);
//            increaseGuest();
//        } else {
//            try {
//                System.out.println(Thread.currentThread().getName() + "  [" + guestName + "] Guest is waiting to Enter " + this.roomName);
//                wait();
//            } catch (Exception e) {
//            }
//            guestEntered(guestName);
//        }

    }

    public synchronized void guestExited(String guestName) {
        System.out.println(Thread.currentThread().getName() + "  [" + guestName + "] Guest is going to Exit " + this.roomName);
        decreaseGuest();
        notifyAll();
    }

    public void increaseGuest() {
        this.guestNum++;
    }

    public void decreaseGuest() {
        this.guestNum--;
    }

}

class Cleaner implements Runnable {

    private final String cleanerName;
    Room room;

    public Cleaner(String cleanerName, Room room) {
        this.cleanerName = cleanerName;
        this.room = room;
    }

    public void cleanerEnter(String cleanerName) {
        room.cleanerEntered(cleanerName);
    }

    public void cleanerExit(String cleanerName) {
        room.cleanerExited(cleanerName);
    }

    @Override
    public void run() {
        cleanerEnter(this.cleanerName);
        try {
            Thread.sleep(100);
        } catch (Exception e) {
        }
        cleanerExit(this.cleanerName);
    }
}

class Guest implements Runnable {

    private final String guestName;
    Room room;

    public Guest(String guestName, Room room) {
        this.guestName = guestName;
        this.room = room;
    }

    public void guestEnter(String guestName) {
        room.guestEntered(guestName);
    }

    public void guestExit(String guestName) {
        room.guestExited(guestName);
    }

    @Override
    public void run() {
        guestEnter(this.guestName);
        try {
            Thread.sleep(100);
        } catch (Exception e) {
        }
        guestExit(this.guestName);
    }

}

public class CPLab3 {

    public static void main(String[] args) {
        // TODO code application logic here
        Room r1 = new Room("RoomA");
        String[] nameArr = {"C1", "C2", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8"};
//        String[] nameArr = {"G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8","C1", "C2"};

        ArrayList<Thread> threadObj = new ArrayList<>();
        for (int i = 0; i < nameArr.length; i++) {

            if (nameArr[i].charAt(0) == 'G') {
                threadObj.add(new Thread(new Guest(nameArr[i], r1)));
            } else if (nameArr[i].charAt(0) == 'C') {
                threadObj.add(new Thread(new Cleaner(nameArr[i], r1)));
            }
        }

        for (Thread t : threadObj) {
            t.start();
        }
        
//        Runnable g1 = new Guest("G1", r1);
//        Runnable g2 = new Guest("G2", r1);
//        Runnable g3 = new Guest("G3", r1);
//        Runnable g4 = new Guest("G4", r1);
//        Runnable g5 = new Guest("G5", r1);
//        Runnable g6 = new Guest("G6", r1);
//        Runnable c1 = new Cleaner("C1",r1);
//        Runnable c2 = new Cleaner("C2",r1);

//        Thread t1 = new Thread(g1);
//        Thread t2 = new Thread(g2);
//        Thread t3 = new Thread(g3);
//        Thread t4 = new Thread(g4);
//        Thread t5 = new Thread(g5);
//        Thread t6 = new Thread(g6);
//        Thread t7 = new Thread(c1);
//        Thread t8 = new Thread(c2);
//        t1.start();
//        t7.start();
//        t2.start();
//        t3.start();
//        t4.start();
//        t5.start();
//        t6.start();
//        t8.start();
    }

}
