package Base;

/**
 * 表示可以比较，一般是自己与自己比较，需要实现compareTo()接口
 * Comperator则为第三方的比较器
 */
public class ComparableDemo implements  Comparable<ComparableDemo>{
    private Integer value;

    public ComparableDemo(Integer value) {
        this.value = value;
    }
    // bin/hadoop -jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.0.jar wordcount /input/a.txt /output
    // hadoop fs -mkdir -p /output
    @Override
    public int compareTo(ComparableDemo o) {
       if (this.value.intValue() != o.value.intValue()){
           return this.value.intValue() > o.value.intValue() ? 1 : -1;
       }
       return 0;
    }

    public static void main(String[] args) {
        ComparableDemo one = new ComparableDemo(1);
        ComparableDemo two = new ComparableDemo(2);
        System.out.println(one.compareTo(two));
        Integer b = Integer.valueOf(89);
        Integer a = new Integer(89);

        System.out.println(a == b);
        System.out.println(test());


    }

    public static String test(){
        try{
            throw  new RuntimeException();
        }catch (RuntimeException e){
            return "e";
        }finally {
            return "f";
        }
    }
}
