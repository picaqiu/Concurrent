package ConcurrentUtilsExample;


import java.util.concurrent.Semaphore;

/**
 * use the semaphore
 * to make the two threads
 * print the first, second, third alternately.
 */
public class SemaphoreExample2 {
    private static Semaphore semaphore1 = new Semaphore(0);
    private static Semaphore semaphore2 = new Semaphore(0);

    public static void main(String[] args) {
        Thread firstThread = new Thread(() -> {
            //first thread print First
            System.out.println("First");
            //semaphore1's permit changed to 1
            semaphore1.release();
        });

        Thread secondThread = new Thread(() -> {
            try {
                //if the semaphore1's permit is not one,the thread will be put into a blocked queue
                semaphore1.acquire(1);
                //second thread print Second
                System.out.println("Second");
                //semaphore2's permit changed to 1
                semaphore2.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thirdThread = new Thread(() -> {
            try {
                semaphore2.acquire(1);
                //third thread print Third
                System.out.println("Third");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        firstThread.start();
        secondThread.start();
        thirdThread.start();
    }
}
