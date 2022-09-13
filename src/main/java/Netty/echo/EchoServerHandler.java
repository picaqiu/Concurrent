package Netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@ChannelHandler.Sharable
@Slf4j
public class EchoServerHandler  extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        log.info("回写的消息为: {}", buf.toString(StandardCharsets.UTF_8));
        ctx.writeAndFlush(buf.toString(StandardCharsets.UTF_8));
     }
}
