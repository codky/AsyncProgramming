package com.example.asyncprogramming.sync;

public class MyHero {
    private String mHero;

    public static void main(String[] args) {
        MyHero tmain = new MyHero();
        System.out.println("Test start!");
        new Thread(() -> {
            for (int i=0; i<100000; i++) {
                tmain.batman();
            }
        }).start();

        new Thread(() -> {
            for (int i=0; i<100000; i++) {
                tmain.superman();
            }
        }).start();
        System.out.println("Test end!");
    }

    public synchronized void batman() {
        mHero = "batman";

        try {
            long sleep = (long) (Math.random()*100);
            Thread.sleep(sleep);
            if (!"batman".equals(mHero)) {
                System.out.println("synchronization broken");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void superman() {
        mHero = "superman";

        try {
            long sleep = (long) (Math.random()*100);
            Thread.sleep(sleep);
            if (!"superman".equals(mHero)) {
                System.out.println("synchronization broken");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
