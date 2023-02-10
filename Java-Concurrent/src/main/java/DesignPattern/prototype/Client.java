package DesignPattern.prototype;

import java.util.List;

/**
 * 原型模式使用内存二进制流拷贝，比直接new的效率也高，同时也逃避了构造函数，减少了约束
 */
public class Client {
    public static void main(String[] args) {
        Prototype p = new Prototype("p-0");
        for (int i = 1; i <= 10; i++) {
            Prototype clonedObj = p.clone();
            clonedObj.setValue("p-" + i);
        }
        if(true){

        }else if (true){

        }else if(true){

        }
        else {

        }
    }
}
