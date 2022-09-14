package netty.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class StringIntegerHeaderDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //读取不到长度直接返回
        if (in.readableBytes() < 4){
            return;
        }
        //设置回滚点
        in.markReaderIndex();
        int length = in.readInt();
        if(in.readableBytes() < length){
            in.resetReaderIndex();
            return;
        }
        byte[] data =new byte[length];
        in.readBytes(data, 0, length);
        out.add(new String(data, StandardCharsets.UTF_8));
    }
}
