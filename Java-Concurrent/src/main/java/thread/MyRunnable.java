package thread;

//2.实现Runnable接口
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("This is MyRunnable");
    }

    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();
    }
}
