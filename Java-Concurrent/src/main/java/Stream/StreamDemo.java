package Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamDemo<T, R> {


    public static void main(String[] args) {
        StreamDemo<String, String> streamDemo = new StreamDemo<>();
        String result = streamDemo.compute("a", a -> a.toUpperCase());
        System.out.println(result);
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.stream().filter(a->a.equals("a")).collect(Collectors.toList());
    }



    @FunctionalInterface
    interface Lamda<T,R> {
        R apply(T t);
    }

    private R compute(T t, Lamda<? super T, ? extends R> mappingFunction){
        return  mappingFunction.apply(t);
    }
}
