package Future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<String>(()->{
            System.out.println("future task run");
            return "this is a future task";
        });
        Thread t = new Thread(futureTask, "future_task");
        t.start();
        String mainThreadResult = mainTask();
        //这步会阻塞，内部实现是通过死循环获取结果
        String futureTaskResult = futureTask.get();
        System.out.println("mainThreadResult: " + mainThreadResult + " futureTaskResult: " + futureTaskResult);

        /*
        *  output
        * this is the main thread
          future task run
          mainThreadResult: main task futureTaskResult: this is a future task
        * */
    }

    private static String mainTask(){
        System.out.println("this is the main thread");
        return "main task";
    }
}
