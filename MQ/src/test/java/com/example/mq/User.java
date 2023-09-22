package com.example.mq;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private String name;
    private String sex;
    private int age;
    private int[] tels;
    private Address address;
    private List<String> comments;
    private List<String> testComments;
    private List<Integer> ids;
    private String gender;
}
