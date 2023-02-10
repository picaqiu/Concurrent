package nio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

public class FileStream {
    public static void main(String[] args) throws IOException {
        File file = new File("D:\\a.txt");
//        BufferedWriter bufferedWriter = Files.newBufferedWriter(file.toPath());
//        int bufferCount = 0;
//        for (int i = 0; i < 10000000; i++) {
//            bufferedWriter.write(String.valueOf(i + 1));
//            bufferedWriter.newLine();
//            bufferCount++;
//            if (bufferCount == 1024) {
//                bufferedWriter.flush();
//                bufferCount = 0;
//            }
//
//        }
//        bufferedWriter.flush();
//        bufferedWriter.close();
//        System.out.println("end");
        long start = System.currentTimeMillis();
        try {
            Stream<String> stream = Files.lines(file.toPath());
            stream.forEach(System.out::println);
            long end = System.currentTimeMillis();
            System.out.println("time cost : " + (end-start));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
