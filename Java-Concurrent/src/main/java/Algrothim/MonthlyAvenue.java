package Algrothim;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MonthlyAvenue {

    private static final Map<Integer, Integer> levelCache = new HashMap<>();

    private static final Map<Integer, Integer> priceCache = new HashMap<>();

    static {
        //缓存每一层的总金额
        levelCache.put(1, 150);
        //缓存层数对应的单价
        priceCache.put(1, 30);
    }

    /**
     * 计算费用
     * @param
     * @param
     * @return
     */
    public int calculateFee(int count){
        if(count == 0){
            return  0;
        }
        int level = 0;
        if (count >= 1 && count <= 5){
            level = 0;
        }else if (count >= 1 && count <= 5){
            //获取到层数
            level = 1;
            //下一层需要计算的笔数
            count -= 5;
        }else{
            level = 10;
        }
        return  calculateFeeByLevel(level, count);
    }

    private int calculateFeeByLevel(int level, int count) {
        //获取上一层的总和
        int preLevelValue = levelCache.get(level);
        return  preLevelValue + priceCache.get(level) * count;
    }
}
