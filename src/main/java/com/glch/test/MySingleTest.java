package com.glch.test;


/**
 * @author zhongzhilong
 * @date 2021-03-26
 * #description 单例设计模式-饿汉模式
 */
public class MySingleTest {
    // 1、定义一个全局唯一的对象，并且私有化
    private static MySingleTest mySingleTest = new MySingleTest();

    // 2、对本类构造方法私有化，防止外部对象调用构造方法创建对象
    private MySingleTest() {

    }

    // 3、通过自定义的公共方法将创建好的对象返回
    public static MySingleTest getInstance() {
        return mySingleTest;
    }


}
