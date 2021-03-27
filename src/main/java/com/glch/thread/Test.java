package com.glch.thread;

public class Test {
    public static void main(String[]args){
        Thread myThread = new MyTread();
        // 设置线程名
        myThread.setName("继承了Thread类");
        myThread.start();
        Thread myRunnable=new Thread(new MyRunnable(),"实现了Runnale类");
        myRunnable.start();
    }
}
