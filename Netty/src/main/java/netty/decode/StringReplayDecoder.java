package netty.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *  使用ReplayingDecoder来处理字符串，第一步是读取字符串长度length，第二部是读取对应length的byte并解码成字符串
 *  ReplayingDecoder适用于简单的场景，对于复杂的不适合，解析的速度不够快且其中的装饰类ReplayingDecoderByteBuf
 *  不支持一些ByteBuf的操作，会直接报错
 */
public class StringReplayDecoder extends ReplayingDecoder<StringReplayDecoder.Status> {
    enum Status {
        Step1, Step2
    }

    private int length;
    private byte[] data;

    public StringReplayDecoder() {
        super(Status.Step1);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()) {
            case Step1:
                length = in.readInt();
                data = new byte[length];
                checkpoint(Status.Step2);
                break;
            case Step2:
                in.readBytes(data, 0, length);
                out.add(new String(data, StandardCharsets.UTF_8));
                checkpoint(Status.Step1);
                break;
            default:
                break;
        }
    }
}
