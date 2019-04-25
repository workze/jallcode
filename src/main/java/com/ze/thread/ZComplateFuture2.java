package com.ze.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ZComplateFuture2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<Void> future = CompletableFuture.runAsync( ()-> {

            try {

                System.out.println("future sleep start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("future sleep end");

                Integer i = null;
                int a = i;

            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
                //e.printStackTrace();
            }

        } );
        System.out.println("main out");
        future.cancel(false);
        future.get();
    }
}
