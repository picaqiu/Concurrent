package Netty.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class String2IntegerEncoder extends MessageToByteEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        char[] array = msg.toCharArray();
        for(char c : array){
            if (c >= '0' && c <= '9'){
                out.writeInt(new Integer(c));
            }
        }
    }
}
