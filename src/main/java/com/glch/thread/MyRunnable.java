package com.glch.thread;

/**
 * @descriprion 实现Runnable
 */
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        boolean keep = true;
        int count = 0;
        while (keep) {
            System.out.println(Thread.currentThread().getName() + "执行了" + (++count));
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
