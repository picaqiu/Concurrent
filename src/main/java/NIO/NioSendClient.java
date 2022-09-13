package NIO;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class NioSendClient {
    private Charset charset = Charset.forName("UTF-8");

    public void sendFile(String filePath) throws Exception{
        try {
            File file = new File(filePath);
            if (!file.exists()){
                throw new Exception("该文件不存在");
            }
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);
            socketChannel.configureBlocking(false);
            socketChannel.socket().connect(new InetSocketAddress("localhost", 80));
            System.out.println("连接成功！");
            //轮询知道完成建立连接
            while (!socketChannel.finishConnect()) {
                //不断的自旋、等待，或者做一些其他的事情
            }
            FileChannel fileChannel = new FileInputStream(file).getChannel();
            //发送文件名称
            ByteBuffer fileNameByteBuffer = charset.encode(file.getName());
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //
            int fileNameLen = fileNameByteBuffer.remaining();
            buffer.clear();
            buffer.putInt(fileNameLen);
            //切换到读模式
            buffer.flip();
            socketChannel.write(buffer);
            System.out.println("Client 文件名称长度发送完成: "+fileNameLen);
            // 发送文件名称
            socketChannel.write(fileNameByteBuffer);
            System.out.println("Client 文件名称发送完成:"+ file.getName());
            //发送文件长度
            //清空
            buffer.clear();
            buffer.putInt((int) file.length());
            //切换到读模式
            buffer.flip();
            //写入文件长度
            socketChannel.write(buffer);
            System.out.println("Client 文件长度发送完成: "+ file.length());
            int length = 0;
            long index = 0;
            while ((length = fileChannel.read(buffer)) > 0){
                //改为读模式
                buffer.flip();
                socketChannel.write(buffer);
                index += length;
                System.out.println("上传进度为: | " + (100 * index / file.length()) + "% |");
                //改为写模式
                buffer.clear();
            }
            System.out.println("上传完成》》》》");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
