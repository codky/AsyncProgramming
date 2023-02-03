package com.example.asyncprogramming.callbackExample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class CallbackExample2 {
    private static ExecutorService executorService;

    public static void main(String[] args) {

        executorService = Executors.newCachedThreadPool();

        // execute 함수의 인자로 callback의 구현체를 넣는다.
        execute(parameter -> {
            log("작업 2 시작 (작업 1의 결과: " + parameter + ")");
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            log("작업 2 종료");
        });

        // 별개로 돌아가는 작업 3
        log("작업 3 시작");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log("작업 3 종료");
    }

    /**
     * 함수형 인터페이스
     * Runnable : 인자와 리턴값이 모두 없다.
     * Supplier<R>, Callable<R> : 인자는 없고, R타입의 객체를 리턴한다.
     * Consumer<T> : T타입의 인자를 받고, 아무것도 리턴하지 않는다.
     * Function<T,R> : T타입의 인자를 받고, R 타입의 객체를 리턴한다.
     * @FunctionalInterface 를 통해 커스텀 함수형 인터페이스를 만들 수도 있다.
     * @param callback
     */
    public static void execute(Consumer<String> callback) { //
        executorService.submit(() -> {
            log("작업 1 시작");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String result = "Alice";
            log("작업 1 종료");

            // 작업을 마친 후 인자로 받아온 callback의 구현체를 비동기로 실행한다.
            callback.accept(result);
        });
    }

    private static void log(String content) {
        System.out.println(Thread.currentThread().getName() + "> " + content);
    }
}
