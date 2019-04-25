package com.ze.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceMain {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                return null;
            }
        });
        executor.shutdown();
        System.out.println("done");
    }
}
