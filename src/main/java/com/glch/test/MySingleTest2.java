package com.glch.test;


/**
 * @author zhongzhilong
 * @date 2021-03-26
 * #description 单例设计模式-懒汉模式
 */
public class MySingleTest2 {
    // 1、
    private static MySingleTest2 mySingleTest;

    // 2、对本类构造方法私有化，防止外部对象调用构造方法创建对象
    private MySingleTest2() {

    }

    // 3、通过自定义的公共方法将创建好的对象返回
    public synchronized static MySingleTest2 getInstance() {
        if (mySingleTest == null) {
            return new MySingleTest2();
        }
        return mySingleTest;
    }


}
