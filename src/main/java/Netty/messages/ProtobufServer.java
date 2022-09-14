package Netty.messages;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import lombok.extern.slf4j.Slf4j;

import static Constant.ConfigConstants.SOCKET_SERVER_PORT;

@Slf4j
public class ProtobufServer {
    private final int port;

    public ProtobufServer(int port) {
        this.port = port;
    }

    public void runServer() {
        ServerBootstrap b = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup workers = new NioEventLoopGroup();
        try {
            b.group(boss, workers);
            b.channel(NioServerSocketChannel.class);
            b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.SO_KEEPALIVE, true).childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ProtobufVarint32FrameDecoder())
                            .addLast(new ProtobufDecoder(ProtoMessage.Message.getDefaultInstance()))
                            .addLast(new ProtobufPrintDecoder());
                }
            });
            ChannelFuture f = b.bind(port);
            f.addListener((ChannelFuture channelFuture) -> {
                if (channelFuture.isSuccess()) {
                    log.info("绑定端口成功!");
                } else {
                    log.info("端口已被占用");
                }
            });
            //阻塞知道连接成功
            f.sync();
            //阻塞至连接断开
            ChannelFuture close = f.channel().closeFuture();
            close.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workers.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new ProtobufServer(SOCKET_SERVER_PORT).runServer();
    }
}
