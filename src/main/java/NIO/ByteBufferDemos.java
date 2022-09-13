package NIO;

import java.nio.ByteBuffer;

public class ByteBufferDemos {
    public static void main(String[] args) {
        ByteBuffer buffer  = ByteBuffer.allocate(15);
        //写模式
        System.out.println("buffer limit is :"+ buffer.limit());
        System.out.println("buffer capacity is :"+ buffer.capacity());
        System.out.println("buffer position is :"+ buffer.position());
        System.out.println("-----------------------------------------");
        //写入时 当postion < limit时才能写入
        //所谓的读写模式切换，是通过limit和position的关系来实现的
        //flip()时，
        buffer.put((byte) 1);
        buffer.put((byte) 12);
        buffer.put((byte) 11);
        System.out.println("buffer limit is :"+ buffer.limit());
        System.out.println("buffer capacity is :"+ buffer.capacity());
        System.out.println("buffer position is :"+ buffer.position());
        System.out.println("-----------------------------------------");
        System.out.println("buffer position is :"+ buffer.position());
        // limit = position
        //所以只能读
        buffer.flip();
        System.out.println("buffer limit is :"+ buffer.limit());
        System.out.println("buffer capacity is :"+ buffer.capacity());
        System.out.println("buffer position is :"+ buffer.position());
        System.out.println("-----------------------------------------");
        System.out.println(buffer.get());
        System.out.println("buffer li0mit is :"+ buffer.limit());
        System.out.println("buffer capacity is :"+ buffer.capacity());
        System.out.println("buffer position is :"+ buffer.position());
        System.out.println("-----------------------------------------");

        ByteBuffer test = ByteBuffer.wrap("abc".getBytes());
        System.out.println("----------------wrap -------------------");
        test.clear();
        test.put("abacdefg".getBytes());

    }
}
