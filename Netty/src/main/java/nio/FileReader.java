package nio;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class FileReader implements Runnable {
    private FileChannel channel;
    private long startOffset;
    private int size;
    int seqNo;

    public FileReader(FileChannel channel, long startOffset, int size, int seqNo) {
        this.channel = channel;
        this.startOffset = startOffset;
        this.size = size;
        this.seqNo = seqNo;
    }

    @Override
    public void run() {
        try {
            System.out.println("Reading the channel offset: " + startOffset + ",size is :" + size);
            //分配内存
            ByteBuffer buff = ByteBuffer.allocate(size);
            //读取数据
            channel.read(buff, startOffset);
            //把分片数据转化成String
            String stringChunk = new String(buff.array(), Charset.forName("UTF-8"));
            //对数据按换行符进行切割并输出
            String[] datas = stringChunk.split(System.lineSeparator());
            for (String data : datas) {
                if (data == null || data.trim().isEmpty()) {
                    continue;
                }
                System.out.println("data is : " + data.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
