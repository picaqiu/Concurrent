package netty.messages;

import java.nio.charset.StandardCharsets;

public class ProtoBufDemo {
    public static ProtoMessage.Message build(){
        ProtoMessage.Message.Builder builder = ProtoMessage.Message.newBuilder();
        builder.setId(1);
        builder.setContent("Protobuf 测试");
        ProtoMessage.Message message = builder.build();
        return message;
    }

    public static void main(String[] args) {
        JsonMessage jsonMessage = new JsonMessage();
        jsonMessage.setId(1);
        jsonMessage.setContent("Protobuf 测试");
        System.out.println("使用json序列化的长度为: " + jsonMessage.convertToJson().getBytes(StandardCharsets.UTF_8).length);

        System.out.println("使用Protobuf序列化长度为: " + build().toByteArray().length);
        System.out.println();
    }
}
