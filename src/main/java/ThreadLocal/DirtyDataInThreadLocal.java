package ThreadLocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal脏数据：当在线程池中使用ThreadLocal时，由于线程会重用Thread对象，所以ThreadLocal也会被重用
 * 所以一定要在使用完之后，调用remove()，清理掉与线程相关的ThreadLocal信息
 */
public class  DirtyDataInThreadLocal {
    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(1);

        for (int i = 0; i < 2; i++) {
            MyThread thread = new MyThread();
            thread.setName("test" + i);
            pool.execute(thread);
        }
    }

    private static class MyThread extends Thread {
        private static boolean flag = true;

        @Override
        public void run() {
            System.out.println("the thread name is :" + this.getName() + " the flag is :" + flag);
            if (flag) {
                //第一个线程set后，并没有进行remove
                //而第二个线程由于某些原因没有进行set操作
                threadLocal.set(this.getName() + " session info");
                flag = false;
            }
            System.out.println(this.getName() + " 线程是： " + threadLocal.get());
            //  threadLocal.remove();
        }
    }

}
