package Algrothim;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Meogo3 {
    class AsyncWorker {
        private int capacity;
        private ArrayBlockingQueue<FutureTask> queue;

        /**
         * 构造函数
         *
         * @param capacity 最大并发数量
         */
        public AsyncWorker(int capacity) {
            // show me your code
            capacity = capacity;
            queue = new ArrayBlockingQueue<FutureTask>(capacity);
        }

        /**
         * 任务提交函数: 当前正在执行的任务数小于 capacity 时, 立即异步执行, 否则
         * 等到任意一个任务执行完成后立即执行.
         *
         * @param task 任务函数
         * @param <T>  返回值类型
         * @return 返回由 Future 包装的任务函数的返回值, 其状态应该和 task 的执行结果一致
         */
        public <T> Future<T> submit(Callable<T> task) throws InterruptedException, ExecutionException, TimeoutException {
            // show me your code
            if (queue.size() < capacity) {
                FutureTask<T> futureTask = new FutureTask(task);
                queue.offer(futureTask);
                new Thread(futureTask).start();
                return futureTask;
            } else {
                boolean state = false;
                for (; ; ) {
                    if (state) {
                        break;
                    }
                    for (FutureTask futureTask : queue) {
                        Object result = futureTask.get(1000L, TimeUnit.MILLISECONDS);
                        if (result != null) {
                            state = true;
                            queue.remove(futureTask);
                        }
                    }
                }
                FutureTask<T> futureTask = new FutureTask(task);
                queue.offer(futureTask);
                new Thread(futureTask).start();
                return futureTask;
            }
        }
    }

}
