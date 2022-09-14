package netty.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class String2IntegerEncoderTest {

    @Test
    public void testString2IntegerEncoder(){
        ChannelInitializer i =new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline()
                        .addLast(new Integer2ByteEncoder())
                        .addLast(new String2IntegerEncoder());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        for(int j=0; j<=100;j++){
            String s = "我是" + j;
            channel.write(s);
        }
        channel.flush();
        ByteBuf buf = channel.readOutbound();
        while (buf != null) {
            System.out.println("o = " + buf.readInt());
            buf = channel.readOutbound();
        }
    }
}
