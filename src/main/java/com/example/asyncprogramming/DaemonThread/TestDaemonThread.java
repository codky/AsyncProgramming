package com.example.asyncprogramming.DaemonThread;

public class TestDaemonThread extends Thread{

    public void run() {

        // 데몬 스레드인지 확인
        if (Thread.currentThread().isDaemon()) {
            while (true) {
                try {
                    Thread.sleep(100L); // 100 ms 지연되고 실행
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Daemon thread running");
            }
        } else {
            try {
                Thread.sleep(101L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Normal thread executing");
        }
    }


    public static void main(String[] args) {
        TestDaemonThread t1 = new TestDaemonThread();
        TestDaemonThread t2 = new TestDaemonThread();

        // t1을 데몬으로 설정
        t1.setDaemon(true); // JVM은 데몬 스레드의 종료를 기다려주지않는다.

        // 스레드 시작
        t1.start();
        t2.start();
    }
}
