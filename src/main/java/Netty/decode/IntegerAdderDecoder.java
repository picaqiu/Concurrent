package Netty.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 *  整数解码器并使整数两两相加
 *  利用ReplayingDecoder的特性：其内部的对ByteBuf的装饰类ReplayingDecoderByteBuf
 *  以及state属性
 */
public class IntegerAdderDecoder extends ReplayingDecoder<IntegerAdderDecoder.State> {
    enum State {
        FIRST, SECOND
    }

    private int firstNum;
    private int secondNum;

    public IntegerAdderDecoder() {
        super(State.FIRST);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()) {
            case FIRST:
                firstNum = in.readInt();
                //更新state为second
                checkpoint(State.SECOND);
                break;
            case SECOND:
                secondNum = in.readInt();
                int sum = firstNum + secondNum;
                out.add(sum);
                //更新state为first
                checkpoint(State.FIRST);
            default:
                break;
        }
    }

}
