package com.example.asyncprogramming.sync;

public class BasicSynchronization {
    private String mMessage;

    public static void main(String[] args) {
        BasicSynchronization temp = new BasicSynchronization();

        System.out.println("Test start!");
        new Thread(() -> {
            for (int i=0; i<1000; i++) {
                temp.callMe("Thread1");
            }
        }).start();

        new Thread(() -> {
            for (int i=0; i<1000; i++) {
                temp.callMe("Thread2");
            }
        }).start();
        System.out.println("Test end!");
    }

    // synchronized 를 걸면 그 함수가 포함된 해당 객체(this)에 lock을 거는 것과 같음.
    public synchronized void callMe(String whoCallMe) {
        mMessage = whoCallMe; // parameter 값을 멤버 변수에 저장

        try {
            long sleep = (long) (Math.random() * 100); // 랜덤하게 sleep
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!mMessage.equals(whoCallMe)) { // 멤버변수와 parameter와 값이 같지 않으면 로깅
            System.out.println(whoCallMe + " | " + mMessage);
        }
    }
}
