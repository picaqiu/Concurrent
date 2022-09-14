package netty.basic;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 *  为了实验半包、粘包、拆包的问题
 */
@Slf4j
public class NettyDumpSendClient {
    private final String host;
    private final int port;

    public NettyDumpSendClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void runClient(){
        Bootstrap b =new Bootstrap();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            b.group(worker).channel(NioSocketChannel.class)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new NettyEchoClientHandler());
                }
            });
            ChannelFuture f = b.connect(host, port);
            f.addListener((ChannelFuture channelFuture) ->{
                if (channelFuture.isSuccess()){
                    log.info("客户端连接服务器成功!");
                }else {
                    log.info("连接服务器失败!");
                }
            });
            //阻塞直到连接完成
            f.sync();
            Channel channel = f.channel();
            for (int i=0; i<100; i++){
                ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer();
                buf.writeBytes("这是半包、粘包、拆包问题的测试!".getBytes(StandardCharsets.UTF_8));
                channel.writeAndFlush(buf);
            }
            // 等待通道关闭的异步任务结束
            // 服务监听通道会一直等待通道关闭的异步任务结束
            ChannelFuture closeFuture =channel.closeFuture();
            closeFuture.sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = ConfigConstants.SOCKET_SERVER_PORT;
        String ip = ConfigConstants.SOCKET_SERVER_IP;
        new NettyDumpSendClient(ip, port).runClient();
    }
}
