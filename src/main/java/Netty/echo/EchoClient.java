package Netty.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Slf4j
public class EchoClient {
    private final String host;
    private final int port;
    private Bootstrap b = new Bootstrap();

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    void sendMessage() {
        EventLoopGroup workers = new NioEventLoopGroup();
        try {
            b.group(workers)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            b.remoteAddress(host, port);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            ByteBuf buf = (ByteBuf) msg;
                            System.out.println("收到了服务端的消息：" + buf.toString(StandardCharsets.UTF_8));
                            super.channelRead(ctx, msg);
                        }
                    });
                }
            });
            ChannelFuture f = b.connect();
            f.addListener((ChannelFuture future) -> {
                if (future.isSuccess()) {
                    log.info("客户端连接成功！");
                } else {
                    log.info("客户端连接失败！");
                }
            });
            f.sync();
            Channel channel = f.channel();
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入想输入的内容: ");
            while (scanner.hasNext()) {
                //获取输入的内容
                String next = scanner.next();
                //发送ByteBuf
                ByteBuf buffer = channel.alloc().buffer();
                buffer.writeBytes(next.getBytes());
                channel.writeAndFlush(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workers.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        EchoClient client = new EchoClient("localhost", 18899);
        client.sendMessage();
    }
}
