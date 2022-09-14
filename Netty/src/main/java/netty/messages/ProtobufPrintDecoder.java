package netty.messages;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProtobufPrintDecoder extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ProtoMessage.Message message = (ProtoMessage.Message) msg;
        log.info("收到一个ProtoBuf数据包====》");
        log.info("the id is {}", message.getId());
        log.info("the content is {}", message.getContent());
    }
}
