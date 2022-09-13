package Netty.message;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

import static Constant.ConfigConstants.SOCKET_SERVER_PORT;

@Slf4j
public class JsonServer {
    private final int port;

    public JsonServer(int port) {
        this.port = port;
    }

    public void runServer() {
        ServerBootstrap b = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup workers = new NioEventLoopGroup();
        try {
            b.group(boss, workers);
            b.channel(NioServerSocketChannel.class)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4))
                                    .addLast(new StringDecoder(StandardCharsets.UTF_8))
                                    .addLast(new JsonMessageHandler());
                        }
                    });
            ChannelFuture f = b.bind(SOCKET_SERVER_PORT);
            f.addListener((ChannelFuture future) -> {
                if (future.isSuccess()) {
                    log.info("服务器启动成功");
                } else {
                    log.info("该端口已被占用");
                }
            });
            f.sync();
            // 服务监听通道会一直等待通道关闭的异步任务结束
            ChannelFuture closeFuture = f.channel().closeFuture();
            closeFuture.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workers.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = SOCKET_SERVER_PORT;
        new JsonServer(port).runServer();
    }
}
