package Algrothim;

public class RateLimiter {
    private static final  int THREASHOLD = 1000;

    private long timestamp;

    private int count;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean canInvoke(){
        long current = System.currentTimeMillis();
        //当前时间超过窗口时，更新窗口左边界为当前时间
        if(getTimestamp() + 60 * 10 * 1000L < current){
            setTimestamp(current);
            setCount(0);
            return true;
        }
        //在窗口内
        //判断 计算器+1是否超过1000
        if(this.count + 1 > 1000){
            return  false;
        }else {
            this.count++;
            return  true;
        }
    }
}
