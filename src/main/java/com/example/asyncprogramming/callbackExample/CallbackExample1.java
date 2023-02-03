package com.example.asyncprogramming.callbackExample;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 비동기 처리하는 방법
 * 1. Future 객체 : 다른 주체에게 작업을 맡긴 상태에서 본 주체 쪽으로 작업이 끝났는지 직접 확인하는 방식
 * 2. Callback 을 구현하는 방식 : 다른 주체에게 맡긴 작업이 끝나면 다른 주체쪽에서 본 주체가 전해준 콜백 함수를 실행하는 방식
 *
 * Callback을 구현하는 방식에 여러가지가 있다.
 * CompletionHandler 또는 함수형 인터페이스를 이용한 구현방법이 있다.
 */
public class CallbackExample1 {
    private static ExecutorService executorService;
    // CompletionHandler를 구현한다.
    // CompletionHandler는 비동기 I/O 작업의 결과를 처리하기 위한 목적으로 만들어졌으며, 콜백 객체를 만드는 데 사용된다.
    // completed() 메소드를 오버라이드해서 콜백을 구현하고, failed() 메소드를 오버라이드해서 작업이 실패했을 때의 처리를 구현하면 된다.
    // try-catch나 if문을 이용해서 작업이 성공했는지 판단한 뒤 작업이 성공했으면 콜백 객체의 completed() 를 호출하고,
    // 실패했거나 예외가 발생했으면 failed()를 호출하는 식으로 사용하면 된다.
    // 또, 위 코드의 실행 결과에서 스레드 pool-1-thread-1 쪽에서 콜백을 호출해서 콜백도 계속해서 main 스레드와 별개로 비동기적으로 실행하는 것을 볼 수 있다.
    private static final CompletionHandler<String, Void> completionHandler = new CompletionHandler<String, Void>() {

        // 작업 1 이 성공적으로 종료된 경우 불리는 콜백 (작업 2)
        @Override
        public void completed(String result, Void attachment) {
            log("작업 2 시작 (작업 1의 결과: " + result + ")");
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            log("작업 2 종료");
        }

        // 작업 1이 실패했을 경우 불리는 콜백
        @Override
        public void failed(Throwable exc, Void attachment) {
            log("작업 1 실패 " + exc.toString());
        }
    };

    public static void main(String[] args) {
        executorService = Executors.newCachedThreadPool();

        // 작업 1
        executorService.submit(() -> {
            log("작업 1 시작");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log("작업 1 종료");

            String result = "Alice";
            if (result.equals("Alice")) { // 작업 성공
               completionHandler.completed(result, null);
            } else { // 작업 실패
                completionHandler.failed(new IllegalStateException(), null);
            }
        });

        // 별개로 돌아가는 작업 3
        log("작업 3 시작");
        try {
            Thread.sleep(1500);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        log("작업 3 종료");
    }

    private static void log(String content) {
        System.out.println(Thread.currentThread().getName() + "> " + content);
    }
}
