package Future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CompletableFutureDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("start to handle task");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("the task is finished");
            return "done";
        });
        System.out.println("this is the main thread");
        //休眠让主线程不要太快结束，要不然异步结果还没执行完成
        TimeUnit.SECONDS.sleep(10);

        //合并两个CompletableFuture的结果
        CompletableFuture<String> result = doSomethingA("name").thenCombine(doSomethingB("henry"), (key, value) -> key + value);
        System.out.println(result.get());

        //Stream与CompletableFuture
        List<String> ipList = new ArrayList<>();
        for(int i=0;i<3;i++){
            ipList.add("192.168.1"+i);
        }
        List<CompletableFuture<String>> futureList = ipList.stream().map(ip-> CompletableFuture.supplyAsync(()->{
            return rpcCall(ip);
        })).collect(Collectors.toList());
        //等待所有的rpc调用结果返回
        List<String> resultList = futureList.stream().map(future -> future.join()).collect(Collectors.toList());
        resultList.forEach(e-> System.out.println(e));
    }
    private static String rpcCall(String ip){
        try {
            TimeUnit.MICROSECONDS.sleep(100);
        } catch (InterruptedException e) {
            return "远程调用服务失败,ip为: "+ip;
        }
        System.out.println("远程调用服务ip为: "+ip);
        return "远程调用服务成功,ip为: "+ip;
    }
    /**
     * 烧热水
     *
     * @return
     */
    private static CompletableFuture<String> doSomethingA(String str) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("start to doSomethingA");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
            }
            return str.toUpperCase();
        });
    }

    private static CompletableFuture<String> doSomethingB(String str) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("start to doSomethingB");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
            }
            return " : " + str.toUpperCase();
        });
    }
}
