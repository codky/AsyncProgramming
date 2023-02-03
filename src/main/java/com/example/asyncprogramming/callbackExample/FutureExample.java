package com.example.asyncprogramming.callbackExample;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 작업 1 Callble 이 리턴한 값을 future에 담는다.
        Future<String> future = executorService.submit(() -> {
            log("작업 1 시작"); // 2
            Thread.sleep(1000);
            log("작업 1 종료"); // 4
            return "Alice";
        });

        log("작업 2 시작 (작업 1 종료 대기)"); // 1
        String result = "";
        try {
            // 논블로킹으로 작업 1이 종료되었는지 확인한다.
            log("작업 1 종료 여부: " + future.isDone()); // 3
            // 블로킹 상태에서 작업 1이 끝날 때까지 대기한다.
            result = future.get();
            // 논블로킹으로 작업 1이 종료되었는지 확인한다.
            log("작업 1 종료 여부: " + future.isDone()); // 5
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        log("작업 1의 결과: " + result); // 6
        log("작업 2 종료"); // 7
    }

    private static void log(String content) {
        System.out.println(Thread.currentThread().getName() + "> " + content);
    }
}
