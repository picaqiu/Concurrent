package netty.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class Integer2ByteEncoderTest {

    @Test
    public void testIntegerToByteDecoder() {
        ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new Integer2ByteEncoder());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        for (int j = 1; j <= 100; j++) {
            channel.write(j);
        }
        channel.flush();
        ByteBuf buf = channel.readOutbound();
        while (buf != null) {
            log.info("o = {}", buf.readInt());
            buf = channel.readOutbound();
        }
    }
}