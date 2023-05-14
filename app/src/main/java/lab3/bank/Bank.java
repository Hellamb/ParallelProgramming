package lab3.bank;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {

    private final Lock lock = new ReentrantLock();

    public static final int NTEST = 10000;

    /**
     * Solution 1,2 and without solution
     */
    private final int[] accounts;
    private long ntransacts = 0;

    public Bank(int n, int initialBalance) {
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++)
            accounts[i] = initialBalance;
        ntransacts = 0;
    }

    public void test() {
        int sum = 0;
        for (int account : accounts) sum += account;
        System.out.println("Transactions:" + ntransacts
                + " Sum: " + sum);
    }

    public int size() {
        return accounts.length;
    }

    /**
     * Without solution
     */
//    public void transfer(int from, int to, int amount) {
//        accounts[from] -= amount;
//        accounts[to] += amount;
//        ntransacts++;
//        if (ntransacts % NTEST == 0)
//            test();
//    }

    /**
     * Solution 1
     */
    synchronized public void transfer(int from, int to, int amount) {
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0)
            test();
    }

    /**
     * Solution 2
     */
//    public void transfer(int from, int to, int amount) {
//        try {
//            lock.lock();
//            accounts[from] -= amount;
//            accounts[to] += amount;
//            ntransacts++;
//            if (ntransacts % NTEST == 0) {
//                test();
//                Thread.currentThread().interrupt();
//            }
//        } finally {
//            lock.unlock();
//        }
//    }

    /**
     * Solution 3
     */
//    synchronized public void transfer(int from, int to, int amount) {
//        synchronized (accounts) {
//            accounts[from] -= amount;
//            accounts[to] += amount;
//            ntransacts++;
//            if (ntransacts % NTEST == 0)
//                test();
//        }
//    }

}
