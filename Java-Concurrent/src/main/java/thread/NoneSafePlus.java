package thread;

public class NoneSafePlus implements Runnable {
    private static int num=0;
    private static NoneSafePlus noneSafePlus = new NoneSafePlus();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(noneSafePlus);
        Thread thread2 = new Thread(noneSafePlus);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("运行结束");
        System.out.println("实际值为: " + num);
    }

    @Override
    public void run() {
        for(int i=0;i<10000;i++){
            num++;
        }
    }
}
