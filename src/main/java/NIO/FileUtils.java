package NIO;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class  FileUtils {
    public static void copyFile(String src, String des) throws IOException {
        File srcFile = new File(src);
        File desFile = new File(des);
        if (!srcFile.exists()) {
            throw new FileNotFoundException("拷贝的文件不存在");
        }
        if (!desFile.exists()) {
            desFile.createNewFile();
        }
        FileInputStream srcFileInputStream = null;
        FileOutputStream desFileInputStream = null;
        FileChannel srcFileChannel = null;
        FileChannel desFileChannel = null;
        try {
            srcFileInputStream = new FileInputStream(srcFile);
            desFileInputStream = new FileOutputStream(desFile);
            srcFileChannel = srcFileInputStream.getChannel();
            desFileChannel = desFileInputStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (srcFileChannel.read(buffer) != -1) {
                //变成读模式
                buffer.flip();
                int outlength = 0;
                while ((outlength = desFileChannel.write(buffer)) != 0) {
                    System.out.println("写入数据的长度为: " + outlength);
                }
                //变为写模式
                buffer.clear();
            }
            desFileChannel.force(true);
        } finally {
            srcFileInputStream.close();
            desFileInputStream.close();
            srcFileChannel.close();
            desFileChannel.close();
        }
    }

    public static void fastCopyFile(String src, String des) throws IOException {
        File srcFile = new File(src);
        File desFile = new File(des);
        if (!srcFile.exists()) {
            throw new FileNotFoundException("拷贝的文件不存在");
        }
        if (!desFile.exists()) {
            desFile.createNewFile();
        }
        FileInputStream srcFileInputStream = null;
        FileOutputStream desFileInputStream = null;
        FileChannel srcFileChannel = null;
        FileChannel desFileChannel = null;
        try {
            srcFileInputStream = new FileInputStream(srcFile);
            desFileInputStream = new FileOutputStream(desFile);
            srcFileChannel = srcFileInputStream.getChannel();
            desFileChannel = desFileInputStream.getChannel();
            long size= srcFileChannel.size();
            long index = 0L;
            long count = 0L;
            while(index < size){
                //每次拷贝1024个字节
                count = size - index > 1024 ? 1024 : size - index;
                index += desFileChannel.transferFrom(srcFileChannel, index, count);
            }
            //强制刷新到磁盘
            desFileChannel.force(true);
        } finally {
            srcFileInputStream.close();
            desFileInputStream.close();
            srcFileChannel.close();
            desFileChannel.close();
        }
    }

    public static void main(String[] args) throws IOException {
        String a = "E:\\github\\code\\Concurrent\\src\\main\\resources\\a.txt";
        String b = "E:\\github\\code\\Concurrent\\src\\main\\resources\\b.txt";
        String c = "E:\\github\\code\\Concurrent\\src\\main\\resources\\c.txt";
        File file = new File(a);
        long start1 = System.currentTimeMillis();
        System.out.println(start1);
        copyFile(a, b);
        System.out.println("copy cost :"+ (System.currentTimeMillis()-start1));
        System.out.println(file.exists());
        long start2 = System.currentTimeMillis();
        fastCopyFile(a, c);
        System.out.println("fast copy cost :"+ (System.currentTimeMillis()-start2));
    }
}
