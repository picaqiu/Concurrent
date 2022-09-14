package netty.messages;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class JsonSendClient {
    private final String host;
    private final int port;

    public JsonSendClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void runClient() {
        Bootstrap b = new Bootstrap();
        EventLoopGroup workers = new NioEventLoopGroup();
        try {
            b.group(workers);
            b.channel(NioSocketChannel.class)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                            //Netty内置编码器，将二进制字节数组编码成head-content格式的数据包
                            .addLast(new LengthFieldPrepender(4))
                            //Netty内置编码器，将String编码成二进制字节数组
                            //出站队列是反的，所以刚好和server端解码器匹配
                            .addLast(new StringEncoder(StandardCharsets.UTF_8));
                }
            });
            ChannelFuture f = b.connect(host, port);
            f.addListener((ChannelFuture channelFuture) -> {
                if (channelFuture.isSuccess()) {
                    log.info("客户端连接成功! ");
                } else {
                    log.info("客户端连接失败! ");
                }
            });
            //阻塞直到连接完成
            f.sync();
            Channel channel = f.channel();
            for (int i = 0; i < 100; i++) {
                JsonMessage message = new JsonMessage();
                message.setId(i).setContent("Json数据传输！！！！");
                channel.writeAndFlush(message.convertToJson());
            }
            channel.flush();
            // 等待通道关闭的异步任务结束
            // 服务监听通道会一直等待通道关闭的异步任务结束
            ChannelFuture closeFuture = channel.closeFuture();
            closeFuture.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workers.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = ConfigConstants.SOCKET_SERVER_PORT;
        String ip = ConfigConstants.SOCKET_SERVER_IP;
        new JsonSendClient(ip, port).runClient();
    }
}
