package WaitNotify;

public class test {
    public static void main(String[] args) {
//        Object lock = new Object();
//        Thread1 t1 = new Thread1(lock);
//        Thread2 t2 = new Thread2(lock);
//
//        t1.start();
//        t2.start();
//        System.out.println(isPowerOfTwo(2));
//        System.out.println(isPowerOfTwo(3));
        String str1 = "a" + "b" + "c";
        String str2 = "abc";
        String a = "a";
        String str3 = a + "b" + "c";
        System.out.println(str1 == str2);
        System.out.println(str1 == str3);
    }

    private static boolean isPowerOfTwo(int val) {
        return (val & -val) == val;
    }

    private static class Thread1 extends  Thread{
        private Object lock;

        public Thread1(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                for (int i = 0; i <= 100; i++) {
                    if (i % 2 == 1) {
                        System.out.println("Thread1 : " + i);
                        lock.notifyAll();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private static class Thread2 extends  Thread{
        private Object lock;

        public Thread2(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                for (int i = 0; i <= 100; i++) {
                    if (i % 2 == 0) {
                        System.out.println("Thread2 : " + i);
                        lock.notifyAll();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
