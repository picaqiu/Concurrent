package netty.basic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class ByteBufDemos {
    public static void main(String[] args) {
        //默认为PooledByteBufAllocator, 默认构造函数中，设置的缓存类型为直接内存，所以不能直接调用array()方法读取数组内部信息
        //因为直接内存的创建与开销的成本很高，所以一般与池化分配器联用，已提高利用率
        /* public static final PooledByteBufAllocator DEFAULT =
              new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
        */
//        ByteBuf pooledBuffer = ByteBufAllocator.DEFAULT.buffer(10);
//        System.out.println(Arrays.toString(pooledBuffer.array()));
        ByteBuf unpooledBuffer = Unpooled.buffer(8);
        System.out.println("初始化: " + Arrays.toString(unpooledBuffer.array()));
        System.out.println("initial read index is : " + unpooledBuffer.readerIndex());
        System.out.println("initial write index is : " + unpooledBuffer.writerIndex());
        System.out.println("initial capacity is : " + unpooledBuffer.capacity());
        unpooledBuffer.writeBytes(new byte[]{1, 2, 1});
        System.out.println("写入后: " + Arrays.toString(unpooledBuffer.array()));
        System.out.println("after writing read index is : " + unpooledBuffer.readerIndex());
        System.out.println("after writing capacity is : " + unpooledBuffer.capacity());
        //getBytes()方法不会改变read index
        unpooledBuffer.getByte(0);
        System.out.println("after getByte read index is : " + unpooledBuffer.readerIndex());
        //readByte()方法会改变read index
        unpooledBuffer.readByte();
        System.out.println("after readByte read index is : " + unpooledBuffer.readerIndex());
        System.out.println("after writing index is : " + unpooledBuffer.writerIndex());
        //动态扩容 x 2
        //继续写入超过8字节，查看buffer的大小
        unpooledBuffer.writeBytes(new byte[]{1, 2, 1, 1, 2, 1});
        System.out.println("after writing capacity is : " + unpooledBuffer.capacity());
        // 当capacity小于64字节时，扩容时，扩容为64字节，大于64字节则扩容两倍，最大不能超过4MB
        /* int newCapacity = 64;
        while (newCapacity < minNewCapacity) {
            newCapacity <<= 1;
        }*/

        //clear()方法会将读写指针都置为0
        unpooledBuffer.clear();
        System.out.println("after clearing read index is : " + unpooledBuffer.readerIndex());
        System.out.println("after clearing index is : " + unpooledBuffer.writerIndex());
        //切片操作指滑，读指针为0，写指针为源ByteBuf可读字节数，maxCapacity为源ByteBuf可读字节数，不可修改
        //new了一个UnpooledSlicedByteBuf，其父类AbstractUnpooledSlicedByteBuf中的成员变量中的ByteBuf为源ByteBuf
        //实际就是对源的切片浅层复制
        ByteBuf slice = unpooledBuffer.slice();
        //duplicate 对应的为整体的浅层复制
        ByteBuf duplicate = unpooledBuffer.duplicate();
    }
}
