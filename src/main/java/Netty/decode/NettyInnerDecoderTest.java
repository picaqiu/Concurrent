package Netty.decode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class NettyInnerDecoderTest {
    static String spliter = "\r\n";
    static String content = "Netty内嵌解码器，测试";
    static final int MAGIC_CODE = 9999;

    @Test
    public void testLineBasedFrameDecoder() {
        try {
            ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
                @Override
                protected void initChannel(EmbeddedChannel ch) throws Exception {
                    ch.pipeline().addLast(new LineBasedFrameDecoder(1024))
                            .addLast(new StringDecoder())
                            .addLast(new StringProcessHandler());
                }
            };
            EmbeddedChannel channel = new EmbeddedChannel(i);
            Random r = new Random();
            for (int j = 0; j < 100; j++) {
                int random = r.nextInt(3) + 1;
                ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer();
                for (int k = 0; k < random; k++) {
                    buf.writeBytes(content.getBytes(StandardCharsets.UTF_8));
                }
                buf.writeBytes(spliter.getBytes(StandardCharsets.UTF_8));
                channel.writeInbound(buf);
            }
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelimiterBasedFrameDecoder() {
        String spliter2 = "\t";
        try {
            ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
                @Override
                protected void initChannel(EmbeddedChannel ch) throws Exception {
                    ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, true, Unpooled.copiedBuffer(spliter2.getBytes())))
                            .addLast(new StringDecoder())
                            .addLast(new StringProcessHandler());
                }
            };
            EmbeddedChannel channel = new EmbeddedChannel(i);
            Random r = new Random();
            for (int j = 0; j < 100; j++) {
                int random = r.nextInt(3) + 1;
                ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer();
                for (int k = 0; k < random; k++) {
                    buf.writeBytes(content.getBytes(StandardCharsets.UTF_8));
                }
                buf.writeBytes(spliter2.getBytes(StandardCharsets.UTF_8));
                channel.writeInbound(buf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 2bytes     4bytes      4bytes       52bytes
     * | version | length | magic number | content |
     */
    @Test
    public void testLengthFieldBaedFrameTest() {
        try {
            // lengthAdjustment = 内容字段偏移量 - 长度偏移量 - 长度字段的长度
            final LengthFieldBasedFrameDecoder decoder = new LengthFieldBasedFrameDecoder(1024,
                    2, 4, 4, 10);
            ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
                @Override
                protected void initChannel(EmbeddedChannel ch) throws Exception {
                    ch.pipeline().addLast(decoder)
                            .addLast(new StringDecoder(StandardCharsets.UTF_8))
                            .addLast(new StringProcessHandler());
                }
            };
            EmbeddedChannel channel = new EmbeddedChannel(i);
            for (int j = 0; j < 100; j++) {
                ByteBuf buf = Unpooled.buffer();
                String s = "第" + j + "次发送-》》》" + content;
                byte[] data = s.getBytes();
                buf.writeChar(1);
                buf.writeInt(data.length);
                buf.writeInt(MAGIC_CODE);
                buf.writeBytes(data);
                channel.writeInbound(buf);

            }
            Thread.sleep(1000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
