package Lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 根据
 * 交替打印0奇数0偶数，如n为5，则打印0102030405
 */
public class ReentrantLockDemo {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private int n;
    private volatile int count = 0;

    public ReentrantLockDemo(int n) {
        this.n = n;
    }

    public static void main(String[] args) {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo(10);

        Thread threadA = new Thread(() -> {
            try {
                reentrantLockDemo.printZero();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                reentrantLockDemo.printOdd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                reentrantLockDemo.printEven();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
    }

    /**
     * 只打印 0
     *
     * @throws InterruptedException
     */
    public void printZero() throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                while (count != 0) {
                    condition.await();
                }
                System.out.println(0);
                count = i % 2 + 1;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 只打印奇数
     *
     * @throws InterruptedException
     */
    public void printOdd() throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            lock.lock();
            try {
                while (count != 1) {
                    condition.await();
                }
                System.out.println(i);
                count = 0;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 只打印偶数
     *
     * @throws InterruptedException
     */
    public void printEven() throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            lock.lock();
            try {
                while (count != 2) {
                    condition.await();
                }
                System.out.println(i);
                count = 0;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }
}
