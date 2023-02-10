package Base;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三个线程书顺序打印ABC
 */
public class PrintDemo {
    //    public static void main(String[] args) {
//        AtomicInteger i = new AtomicInteger(0);
//
//        Thread a = new Thread(()->{
//            for ( ; i.get() < 100;){
//                try {
//                    if (i.get()%3 == 0){
//                        System.out.println("A");
//                        i.incrementAndGet();
//                    }
//                }catch (Exception e){
//
//                }
//            }
//        });
//
//        Thread b = new Thread(()->{
//            for ( ; i.get() < 100; ){
//                try {
//                    if (i.get()%3 == 1){
//                        System.out.println("B");
//                        i.incrementAndGet();
//                    }
//                }catch (Exception e){
//
//                }
//            }
//        });
//
//        Thread c = new Thread(()->{
//            for ( ; i.get() < 100; ){
//                try {
//                    if (i.get()%3 == 2){
//                        System.out.println("C");
//                        i.incrementAndGet();
//                    }
//                }catch (Exception e){
//
//                }
//            }
//        });
//
//        a.start();
//        b.start();
//        c.start();
//     }
//    public static void main(String[] args) {
//        ReentrantLock lock = new ReentrantLock();
//         int[] state = {0};// 用state来判断轮到谁执行
//
//        Thread a = new Thread(() ->{
//            for (int i=0; i < 100; i++){
//                lock.lock();
//                if (state[0] %3 == 0){
//                    System.out.println("A");
//                    state[0] = state[0] +1;
//                }
//                lock.unlock();
//            }
//        });
//
//        Thread b = new Thread(() ->{
//            for (int i=0; i < 100; i++){
//                lock.lock();
//                if (state[0] %3 == 1){
//                    System.out.println("B");
//                    state[0] = state[0] +1;
//                }
//                lock.unlock();
//            }
//        });
//
//        Thread c = new Thread(() ->{
//            for (int i=0; i < 100; i++){
//                lock.lock();
//                if (state[0] %3 == 2){
//                    System.out.println("C");
//                    state[0] = state[0] +1;
//                }
//                lock.unlock();
//            }
//        });
//        a.start();
//        b.start();
//        c.start();
//    }

//    public static void main(String[] args) {
//        Object lock = new Object();
//        int[] state = {0};// 用state来判断轮到谁执行
//        Thread a = new Thread(() -> {
//            for (int i = 0; i < 100; ) {
//                synchronized (lock) {
//                    if (state[0] % 3 == 0) {
//                        System.out.println("A");
//                        state[0] = state[0] + 1;
//                        i++;
//                    }
//                }
//            }
//        });
//
//        Thread b = new Thread(() -> {
//            for (int i = 0; i < 100; ) {
//                synchronized (lock) {
//                    if (state[0] % 3 == 1) {
//                        System.out.println("B");
//                        state[0] = state[0] + 1;
//                        i++;
//                    }
//                }
//            }
//        });
//
//        Thread c = new Thread(() -> {
//            for (int i = 0; i < 100; ) {
//                synchronized (lock) {
//                    if (state[0] % 3 == 2) {
//                        System.out.println("C");
//                        state[0] = state[0] + 1;
//                        i++;
//                    }
//                }
//            }
//        });
//        a.start();
//        b.start();
//        c.start();
//    }
}
