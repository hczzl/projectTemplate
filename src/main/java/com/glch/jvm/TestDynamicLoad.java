package com.glch.jvm;

/**
 * @Date 2022/11/19
 * @description 主类在运行过程中如果使用到其他类，会逐步加载这些类的。
 * jar包或者war包里的类不是一次性全部加载过来的，使用到的时候才会逐步加载
 */
public class TestDynamicLoad {
    static {
        System.out.println("*************load TestDynamicLoad************");
    }

    public static void main(String[] args) {
        new A();
        System.out.println("*************load test************");
        B b = null; // B不会加载，除非这里执行 new B()
//        new B();
    }
}

class A {
    static {
        System.out.println("*************load A************");

    }

    public A() {
        System.out.println("*************initial A************");
    }
}

class B {
    static {
        System.out.println("*************load B************");
    }

    public B() {
        System.out.println("*************initial B************");
    }
}
