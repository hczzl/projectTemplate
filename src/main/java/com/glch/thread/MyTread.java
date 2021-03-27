package com.glch.thread;

/**
 * @descriprion 继承Thread类
 */
public class MyTread extends Thread {
    @Override
    public void run() {
        boolean keep = true;
        int count = 0;
        while (keep) {
            System.out.println(getName() + "执行了" + (++count));
            if (count == 10) {
                keep = false;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
