package com.example.asyncprogramming.threadExample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 자바에서 비동기 코드를 작성해보기
 */
public class ThreadExampleAfter {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 작업 1 (스레드)
        executorService.submit(() -> {
            log("작업 1 시작");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log("작업 1 종료");
        });

        // 작업 2
        log("작업 2 시작");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log("작업 2 종료");

        executorService.shutdown();
    }

    // 출력을 어떤 스레드에서 하고 있는지 확인
    private static void log(String content) {
        System.out.println(Thread.currentThread().getName() + "> " + content);
    }
}
/**
 * 이 경우에는 실행시간이 긴 작업 1 을 다른 스레드에게 맡기고, 메인 스레드는 그대로 밑으로 내려가 작업 2를 시작한다.
 * 실행 결과를 보면, main 과 pool-1-thread-1의 두 주체(스레드)가 서로 다른 작업을 동시에 진행하는 것을 알 수 있다.
 *
 */
