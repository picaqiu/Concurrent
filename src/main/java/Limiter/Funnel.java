package Limiter;

/**
 * 单机版漏桶
 */
public class Funnel {
    private int capacity;//容量
    private int leakRate;//流速
    private float leftSpace;//剩余空间
    private long leakTime;//上次漏水时间

    public Funnel(int capacity, int leakRate, int leftSpace, long leakTime) {
        this.capacity = capacity;
        this.leakRate = leakRate;
        this.leftSpace = leftSpace;
        this.leakTime = leakTime;
    }

    void makeSpace() {
        long now = System.currentTimeMillis();
        long timeGap = now - this.leakTime;
        int space = (int) (leakRate * (timeGap));
        if (space < 0) {
            return;
        }
        if (space < 1) {
            return;
        }
        this.leftSpace += space;
        this.leakTime = now;
        this.leftSpace = this.leftSpace > capacity ? capacity : this.leftSpace;
    }
}
