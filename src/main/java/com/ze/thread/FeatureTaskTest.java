package com.ze.thread;

import javax.sound.midi.SoundbankResource;
import java.util.concurrent.*;

public class FeatureTaskTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executor = Executors.newFixedThreadPool(1);


        FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                for(int i=0; i<200000; i++){
                    //System.out.println(i);
                    //TimeUnit.SECONDS.sleep(1);
                }
                return 1;
            }
        });
        executor.submit(futureTask);
        //executor.shutdown();
        //futureTask.get(1, TimeUnit.SECONDS);
        //TimeUnit.SECONDS.sleep(1);
        System.out.println("cancel");
        //futureTask.cancel(true);
        System.out.println(futureTask.isDone());

        //System.out.println("canceled");
    }
}
