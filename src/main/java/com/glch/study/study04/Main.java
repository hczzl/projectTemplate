package com.glch.study.study04;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author zzl
 * @Date 2022/6/28
 * @description
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("主线程开始运行："+format.format(new Date()));
        new Thread(()->{
            System.out.println("子线程开始运行："+format.format(new Date()));
            try {
                // 单位是毫秒，sleep是Thread类中的方法
                Thread.sleep(2000);
                TimeUnit.SECONDS.sleep(1);
                TimeUnit.MINUTES.sleep(1);
                TimeUnit.HOURS.sleep(1);
                TimeUnit.DAYS.sleep(1);
                TimeUnit.MILLISECONDS.sleep(1);
                // Object类中的方法
                // this.wait(10);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程运行结束："+format.format(new Date()));

        }).start();
        Thread thread =new Thread("a");
        thread.start();
        thread.join();
        System.out.println("主线程运行结束："+format.format(new Date()));
    }
}
