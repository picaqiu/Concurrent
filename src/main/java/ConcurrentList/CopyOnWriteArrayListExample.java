package ConcurrentList;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList 与ArrayList基本差不多，
 * 但是内置了一个ReentrantLock在set操作时，
 * 只允许一个线程执行
 */
public class CopyOnWriteArrayListExample {
    public static void main(String[] args) {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        copyOnWriteArrayList.add(1);
        copyOnWriteArrayList.add(2);
        copyOnWriteArrayList.add(3);
        Thread thread1 = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " In");
                System.out.println("thread1 : " + copyOnWriteArrayList.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " In");

                //CopyOnWriteArrayList采用的复制策略，无论是add还是move，这个地方可能会造成弱一致问题
                //CopyOnWriteArrayList 中只能保证最终结果一致性，不能保证数据实时性
                //由于大量采用了System.copy()，COW会造成内存占用问题
                copyOnWriteArrayList.remove(0);
                System.out.println("thread2 : " + copyOnWriteArrayList.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread3 = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " In");
                System.out.println("thread3 : " + copyOnWriteArrayList.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
