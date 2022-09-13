package Future;

import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.concurrent.*;

@Slf4j
public class GuavaFutureDemo {
    public static String getCurrentThread() {
        return Thread.currentThread().getName();
    }

    static class BoilingWaterJob implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            try {
                log.info("洗好水壶");
                log.info("灌上凉水");
                log.info("放在火上");

                //线程睡眠一段时间，代表烧水中
                Thread.sleep(500);
                log.info("水开了");
            } catch (Exception e) {
                log.error("发生了异常");
                return false;
            }
            return true;
        }
    }

    static class WashingJob implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            try {
                log.info("洗茶壶");
                log.info("洗茶杯");
                log.info("拿茶叶");

                //线程睡眠一段时间，代表烧水中
                Thread.sleep(500);
                log.info("洗完了");
            } catch (Exception e) {
                log.error("发生了异常");
                return false;
            }
            return true;
        }
    }

    //新创建一个异步业务类型，作为泡茶喝主线程类
    static class MainJob implements Runnable {
        Boolean cupReady = false;
        Boolean waterReady = false;

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(500);
                    log.info("读取中.....");
                } catch (Exception e) {
                    log.info(getCurrentThread() + "发生");
                }
                if(cupReady && waterReady){
                    drinkTea(waterReady, cupReady);
                    break;
                }
            }
        }

        public static void drinkTea(Boolean waterReady, Boolean cupReady){
            if (waterReady && cupReady){
                log.info("泡茶喝");
            }else if(!waterReady){
                log.info("热水未准备好，泡不了茶喝！");
            }else {
                log.info("杯具未洗干净，泡不了茶喝！");
            }
        }
    }

    public static void main(String[] args) {
        //新起一个线程，作为泡茶主线程
        MainJob mainJob = new MainJob();
        Thread mainThread = new Thread(mainJob);
        mainThread.setName("主线程");
        mainThread.start();
        //烧水Job
        Callable<Boolean> boilingJob = new JavaFutureDemo.BoilingWaterJob();
        //洗茶Job
        Callable<Boolean> washingJob = new JavaFutureDemo.WashingJob();
        //创建Java线程池
        ExecutorService executorService = new ThreadPoolExecutor(4, 10, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(60));
        //包装Java线程池，构造Guava线程池
        ListeningExecutorService pool = MoreExecutors.listeningDecorator(executorService);
        //提交烧水的业务逻辑，到Guava线程池获取异步任务
        ListenableFuture<Boolean> boilingFuture = pool.submit(boilingJob);
        //绑定异步回调，烧水完成后，把喝水任务的waterReady设置为True
        Futures.addCallback(boilingFuture, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                if (result){
                    mainJob.waterReady = true;
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                log.info("烧水失败，没有茶喝了！");
            }
        });
        //提交清洗的业务逻辑，到Guava线程池获取异步任务
        ListenableFuture<Boolean> washFuture = pool.submit(washingJob);
        //绑定异步回调，清洗完成后，清洗水任务的washReady设置为True
        Futures.addCallback(washFuture, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                if (result){
                    mainJob.cupReady = true;
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                log.info("清洗失败，没有茶喝了！");
            }
        });
    }
}
