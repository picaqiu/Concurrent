package Limiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 计数器java单机版
 */
public class Counter {
    private long interval;//时间间隔值
    private long threshold;//单位时间内计数器的阈值
    private long startTime;//初始化起始时间
    private AtomicLong value = new AtomicLong(0);//计数器
    private static final ConcurrentHashMap<Long, Counter> cache = new ConcurrentHashMap<>();
    private Lock lock = new ReentrantLock();//在多线程访问下对counter修改会存在变量的可见性问题，所以内置化一把锁

    /**
     * 默认构造器
     */
    public Counter() {
        this.interval = 1000;
        this.threshold = 3;
        this.startTime = System.currentTimeMillis();
    }

    public Counter(long interval, long threshold, long startTime) {
        this.interval = interval;
        this.threshold = threshold;
        this.startTime = startTime;
    }

    private boolean isActionAllowed(long taskId) {
        try {
            lock.lock();
            //缓存未命中，初始化Counter
            if (!cache.contains(taskId)) {
                cache.put(taskId, new Counter());
                System.out.println("你可以进行此操作");
                return true;
            } else {
                Counter counter = cache.get(taskId);
                AtomicLong counterValue = counter.getValue();
                long now = System.currentTimeMillis();
                //在时间窗口内
                if (counter.getStartTime() + counter.getIntervalValue() >= now) {
                    long count = counterValue.incrementAndGet();
                    if (count > counter.getThreshold()) {
                        System.out.println("你的操作太频繁，请稍后再试");
                        return false;
                    } else {
                        System.out.println("你可以进行此操作");
                        return true;
                    }
                } else {
                    //更新起始时间
                    counter.setStartTime(System.currentTimeMillis());
                    //计数器的值清0
                    counterValue.set(0);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return false;
    }

    public boolean isActionAllowed(long taskId, Counter counter) {
        //缓存的计数器为空
        if (counter != null) {
            cache.put(taskId, counter);
        }
        return isActionAllowed(taskId);
    }

    public long getIntervalValue() {
        return interval;
    }

    public void setIntervalValue(long interval) {
        this.interval = interval;
    }























    public long getThreshold() {
        return threshold;
    }

    public void setThreshold(long threshold) {
        this.threshold = threshold;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public AtomicLong getValue() {
        return value;
    }

    public void setValue(AtomicLong value) {
        this.value = value;
    }
}
