package com.example.mq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class MqApplicationTests {
    public static void main(String[] args) {
//        MqApplicationTests app = new MqApplicationTests();
//        app.generate(User.class, "UserResponse");
        ObjectMapper mapper = new ObjectMapper();
        User user = new User();
        user.setName("a");
        user.setAge(11);
        user.setGender("Male");
        user.setComments(Arrays.asList("辣鸡"));
        ObjectNode jsonNode = mapper.valueToTree(user);

        jsonNode.remove("testComments");
        System.out.println(jsonNode);
    }


    public void generate(Class<?> clazz, String name){
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {

            System.out.println(field.getName());
            System.out.println(field.getType().isEnum());
            System.out.println(field.getType().isArray());
            if (field.getType().isArray()){
                System.out.println(field.getType().getComponentType().getSimpleName());
            }
            if (field.getType().equals(List.class)){
                Type geneticType = field.getGenericType();
                if (geneticType != null){
                    ParameterizedType parameterizedType = (ParameterizedType) geneticType;
                    Class<?> integerListClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                    System.out.println("list type length : " + parameterizedType.getActualTypeArguments().length);
                    System.out.println("list type is : " + integerListClass.getSimpleName());
                }
            }else {
                Class<?> clazz2 = field.getType();
                clazz2.getDeclaredFields();
                System.out.println("clazz2: " + clazz2.getSimpleName());
            }
        }
    }

    public static void test(){

    }
}
