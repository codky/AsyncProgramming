package com.example.asyncprogramming.threadExample;

/**
 * 스레드를 사용하지 않는 코드
 */
public class ThreadExampleBefore {
    public static void main(String[] args) {
        // 작업 1 -  1.5초 소요
        System.out.println("작업 1 시작");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("작업 1 종료");

        // 작업 2 - 0.5초 소요
        System.out.println("작업 2 시작");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("작업 2 종료");
    }
}

/**
 * 작업 1 은 오래 걸리는 작업이고, 작업 2는 오래 걸리지 않는 작업이다. 두 작업 사이에 연관성도 없는데 작업 1이 끝날 때까지 기다리고 작업 2를 시작하는 것은 비효율적이다.
 * 이런 경우에 스레드를 나누어 오래 걸리는 작업을 다른 주체에게 맡겨 다른 작업과 동시에 실행되게 만들면 시간을 아낄 수 있다.
 */
