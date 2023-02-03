package com.example.asyncprogramming.DaemonThread;

public class TestIllegalSetDaemon  extends Thread {

    public void run() {
        System.out.println("Thread is running");
    }

    public static void main(String[] args) {
        TestIllegalSetDaemon t1 = new TestIllegalSetDaemon();
        t1.start();

        // 발생 IllegalThreadStateException
        t1.setDaemon(true);
    }
}
