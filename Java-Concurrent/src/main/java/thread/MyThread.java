package thread;

//1.继承Thread类
public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("this is MyThread");
    }
}
