package com.ze.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ZComplateFuture {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync( ()-> {

            try {
                System.out.println("future sleep start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("future sleep end");
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
                //e.printStackTrace();
            }

            return null;
        } );
        future.thenAccept((v)-> {
            System.out.println("future 2");
            System.out.println(v);
        });
        System.out.println("main sleep start");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("main sleep end");
        future.cancel(true);
        System.out.println("canceled");
        //future.get();
        //future.isDone();
        System.out.println();
        TimeUnit.SECONDS.sleep(5);
    }
}
