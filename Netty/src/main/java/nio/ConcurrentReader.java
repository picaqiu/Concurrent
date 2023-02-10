package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class ConcurrentReader {
    private int threadNum = Runtime.getRuntime().availableProcessors();
    static List<Thread> activeThreads = new ArrayList();

    public void streamRead(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("输入的文件路径不存在");
        }
        Stream<String> stream = Files.lines(file.toPath());
        AtomicInteger totalLines = new AtomicInteger();
        stream.forEach(e -> {
            totalLines.getAndIncrement();
        });
        int linesPerThread = totalLines.get() % threadNum == 0
                ? totalLines.get() / threadNum :
                (totalLines.get() / threadNum) + 1;
        int start = 0;
        for (int i = 0; i < threadNum; i++) {
            start = i * linesPerThread;
            ReadThread readThread = new ReadThread(file.getPath(), start, linesPerThread);
            readThread.start();
            activeThreads.add(readThread);
        }

    }

    public void read(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        FileChannel channel = fileInputStream.getChannel();
        long fileSize = channel.size(); //获取文件大小
        System.out.println("file size is : " + fileSize);
        //获取空闲内存大小
        long memory = Runtime.getRuntime().freeMemory();
        System.out.println("free memory is : " + memory);
        int chunkNumber = calculateChunkNumber(fileSize);
        int chunkSize = (int) (fileSize / chunkNumber);
//        //最大分片大小设置约为2GB
//        if (chunkSize > (Integer.MAX_VALUE - 5)) {
//            chunkSize = (Integer.MAX_VALUE - 5);
//        }
        //初始化线程池
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);
        int sizeOfPerThread = chunkNumber > 1 ? chunkSize / threadNum : (int) fileSize;
        for (int i = 0; i < chunkNumber; i++) {
            long startOffset = i*chunkSize;//偏移量
            long remainingSize = i == chunkNumber -1 ? fileSize - chunkSize*(i-1) : chunkSize;
            while (remainingSize >= sizeOfPerThread) {
                executor.execute(new FileReader(channel, startOffset, sizeOfPerThread, i));
                remainingSize = remainingSize - sizeOfPerThread;
                startOffset += sizeOfPerThread;
            }
            //读取最后的数据
            executor.execute(new FileReader(channel, startOffset, chunkSize, i));
        }

        //关闭线程池
        executor.shutdown();
        //等待所有现场运行结束
        while (!executor.isTerminated()) {
            System.out.println("waiting");
        }
        fileInputStream.close();
    }

    /**
     * 计算分片数
     * @param fileSize
     * @return
     */
    private int calculateChunkNumber(long fileSize) {
        //获取空闲内存大小
        //如果文件过大，剩余10%的jvm内存空间
        long memory = Runtime.getRuntime().freeMemory();
        int chunkNum = (int) (fileSize % (memory * 0.9)
                == 0 ? fileSize / (memory * 0.9)
                : (fileSize / (memory * 0.9)) + 1); //分片个数
        if (fileSize  >= (memory * 0.9)) {
            return chunkNum;
        }
        return 1;
    }

    class ReadThread extends Thread {
        private String filePath;
        private int start;
        private int linesPerThread;

        ReadThread(String filePath, int start, int linesPerThread) {
            this.filePath = filePath;
            this.start = start;
            this.linesPerThread = linesPerThread;
        }

        @Override
        public void run() {
            try {
                Stream<String> stream = Files.lines(new File(filePath).toPath())
                        .skip(start)
                        .limit(linesPerThread);
                stream.forEach(System.out::println);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ConcurrentReader concurrentReader = new ConcurrentReader();
        String filePath = "D:\\a.txt";
        long start = System.currentTimeMillis();
        concurrentReader.streamRead(filePath);
        activeThreads.forEach(e -> {
            try {
                e.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        //concurrentReader.read(filePath);
        long end = System.currentTimeMillis();
        System.out.println("time cost : " + (end - start));
    }
}
