package ForkJoinExample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Integer> {
    private int limit;
    private List<Integer> numubers;

    public MyTask(int limit, List<Integer> numubers) {
        this.limit = limit;
        this.numubers = numubers;
    }

    protected Integer compute() {
        boolean canCompute = numubers.size() <= limit;
        int result;

        if (canCompute) {
            result = add(numubers);
        } else {
            int middle = numubers.size() / 2;
            MyTask task1 = new MyTask(limit, numubers.subList(0, middle));
            MyTask task2 = new MyTask(limit, numubers.subList(middle, numubers.size()));

            //执行子任务
            task1.fork();
            task2.fork();

            //获取结果
            int task1Result = task1.join();
            int task2Result = task2.join();
            result = task1Result + task2Result;
        }
        return result;
    }

    private int add(List<Integer> numubers) {
        int sum = 0;

        for (Integer numuber : numubers) {
            sum += numuber;
        }
        return sum;
    }

    public static void main(String[] args) {
        List<Integer> nunmbers = new ArrayList<>();
        nunmbers.add(1);
        nunmbers.add(2);
        nunmbers.add(3);
        nunmbers.add(4);
        nunmbers.add(5);
        nunmbers.add(6);
        nunmbers.add(7);
        nunmbers.add(8);
        nunmbers.add(9);
        nunmbers.add(10);

        MyTask task = new MyTask(6, nunmbers);
        System.out.println(task.compute());
    }
}
