package com.ze.thread;

import javax.sound.midi.SoundbankResource;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int i = 0;
                    for(i=0;i<200418;i++){

                        System.out.println(i);
                        System.out.println(Thread.currentThread().isInterrupted());
                    }
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        System.out.println("started");
        TimeUnit.SECONDS.sleep(1);
        t.interrupt();
    }
}
