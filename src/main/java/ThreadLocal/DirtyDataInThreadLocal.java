package ThreadLocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DirtyDataInThreadLocal {
    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);

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
            if (flag) {
                threadLocal.set(this.getName() + " session info");
                flag = false;
            }
            System.out.println(this.getName() + " 线程是： " + threadLocal.get());
            //  threadLocal.remove();
        }
    }

}
