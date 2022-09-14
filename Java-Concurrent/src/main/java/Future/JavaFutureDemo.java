package Future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

@Slf4j
public class JavaFutureDemo {
    public static String getCurrentThread(){
        return  Thread.currentThread().getName();
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
            }catch (Exception e){
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
            }catch (Exception e){
                log.error("发生了异常");
                return false;
            }
            return true;
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


    public static void main(String[] args) {
        Callable<Boolean> boiling = new BoilingWaterJob();
        FutureTask<Boolean> boilingTask = new FutureTask(boiling);
        Thread boilingThread = new Thread(boilingTask, "** 烧水-线程");

        Callable<Boolean> washingJob = new WashingJob();
        FutureTask<Boolean> washingTask = new FutureTask(washingJob);
        Thread washingThead = new Thread(washingTask, "$$$ 清洗-线程");
        boilingThread.start();
        washingThead.start();
        Thread.currentThread().setName("主线程");
        try {
            //获取结果为阻塞
            Boolean cupReady = washingTask.get();
            Boolean waterReady = boilingTask.get();
            drinkTea(waterReady, cupReady);
        }catch (Exception e){
            log.info(getCurrentThread() + "发生异常");
        }
    }
}
