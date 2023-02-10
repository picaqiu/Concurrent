package netty.messages;

import constant.ConfigConstants;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProtobufClient {
    private final String host;
    private final int port;

    public ProtobufClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void sendMessage() {
        Bootstrap b = new Bootstrap();
        EventLoopGroup workers = new NioEventLoopGroup();
        try {
            b.group(workers);
            b.channel(NioSocketChannel.class)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender())
                                    .addLast(new ProtobufEncoder());
                        }
                    });
            b.remoteAddress(host, port);
            ChannelFuture f = b.connect();
            f.addListener((ChannelFuture channelFuture) -> {
                if (channelFuture.isSuccess()) {
                    log.info("客户端连接成功！");
                } else {
                    log.info("客户端连接失败！");
                }
            });
            f.sync();
            Channel channel = f.channel();
            for (int i = 0; i < 1000; i++) {
                ProtoMessage.Message.Builder build = ProtoMessage.Message.newBuilder();
                build.setId(i);
                build.setContent("第" + i + "发送=》" + "Protobuf序列化消息");
                channel.writeAndFlush(build.build());
            }
            //阻塞至连接断开
            ChannelFuture close = f.channel().closeFuture();
            close.sync();
        } catch (Exception e) {

        } finally {
            workers.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new ProtobufClient(ConfigConstants.SOCKET_SERVER_IP, ConfigConstants.SOCKET_SERVER_PORT).sendMessage();
    }
}
