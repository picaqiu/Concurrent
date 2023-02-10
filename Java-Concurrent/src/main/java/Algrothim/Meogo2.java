package Algrothim;

public class Meogo2 {
    /**
     * Q2: 字符跳动
     * <p>
     * 给定一个由不重复的小写字母组成的字符串，通过一系列跳动指令变换成一个新的字符串。
     * 跳动指令有：
     * * Move(移动)，简写：M，例如：M:7 表示将字符串向右移动7位。
     * * eXchange(交换)，简写：X，例如：X:a/c 表示将字符串中的 a c 位置交换。
     * * IndexChange(按位置交换)，简写：I，例如：I:2/4 表示将位置2和位置4的字符交换，位置的索引从0开始计数。
     * 示例：
     * 给定初始字符串为：wosjgncxyakdbefh
     * 给定如下指令：
     * M:3   则变换后的新字符串为：efhwosjgncxyakdb
     * I:7/2  则变换后的新字符串为：efgwosjhncxyakdb
     * X:o/h  则变换后的新字符串为：efgwhsjoncxyakdb
     * 因此给定初始字符串：wosjgncxyakdbefh，在经过指令 M:3,I:7/2,X:o/h 的变换后得到新的字符串：efgwhsjoncxyakdb。
     */
   static class CharDance {

        /**
         * 题目1：
         * 给定一个随机的初始字符串: src，给定一组随机的指令: ops，(src 和 ops 一定是合法的)，求经过转换后得到的新字符串。
         */
        public String transfer(String src, String ops) {
            // show me your code
            String[] opsStr = ops.split(",");
            for (String s : opsStr) {
                if (s.contains("M")) {
                    String[] params = s.split(":");
                    int number = Integer.parseInt(params[1]);
                    StringBuilder buidler = new StringBuilder();
                    src = buidler.append(src.substring(src.length() - number, src.length()))
                            .append(src.substring(0, src.length() - number)).toString();
                } else if (s.contains("I")) {
                    String[] params = s.split(":");
                    String[] srcStr = params[1].split("/");
                    int srcIndex = Integer.parseInt(srcStr[0]);
                    int destIndex = Integer.parseInt(srcStr[1]);
                    src = exchange(src, srcIndex, destIndex);
                } else if (s.contains("X")) {
                    String[] params = s.split(":");
                    String[] srcStr = params[1].split("/");
                    String srcChar = srcStr[0];
                    String destChar = srcStr[1];
                    StringBuilder builder = new StringBuilder(src);
                    int srcIndex = builder.indexOf(srcChar);
                    int destIndex = builder.indexOf(destChar);
                    src = exchange(src, srcIndex, destIndex);
                } else {
                    continue;
                }
            }
            return src;
        }

        private String exchange(String src, int srcIndex, int destIndex){
            char[] data = src.toCharArray();
            char temp = data[srcIndex];
            data[srcIndex] = data[destIndex];
            data[destIndex] = temp;
            return new String(data);
        }
        /**
         * 题目2：
         * 将上一次转换后得到的新字符串作为初始字符串，使用相同的跳动指令集再进行转换，如此重复执行 count 次，求得到的最终字符串是什么？
         * 注意: count 足够大, 比如可能超过 2^32.
         */
        public String transferMultipleTimes(String src, String ops, long count) {
            // show me your code
            for (int i=0;i<count;i++){
                src = transfer(src, ops);
            }
            return src;
        }
    }

    public static void main(String[] args) {
        CharDance charDance = new CharDance();
        String src = "wosjgncxyakdbefh";
        String ops = "M:3,I:7/2,X:o/h";
        String dst = "efgwhsjoncxyakdb";
        String realDst = charDance.transfer(src, ops);
        if (!dst.equals(realDst)) {
            throw new RuntimeException("invalid transfer result " + realDst + ", expected is " + dst);
        }
        String dst100 = src;
        for (int i = 0; i < 100; i++) {
            dst100 = charDance.transfer(dst100, ops);
        }
        String realDst100 = charDance.transferMultipleTimes(src, ops, 100);
        if (!dst100.equals(realDst100)) {
            throw new RuntimeException("invalid transfer result " + realDst100 + " after 100 times, expected is " + dst100);
        }
    }
}
