package com.nowcoder.dao;


import org.springframework.core.task.support.ExecutorServiceAdapter;

import java.util.concurrent.*;

class MyThread extends Thread{
    public int tid;
    MyThread(int tid){
        this.tid = tid;
    }

    public void run(){
        try{
            for(int i = 0;i < 10;i++){
                Thread.sleep(1000);
                System.out.println(String.format("%d : %d",tid,i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
class  Consumer implements Runnable{
    private BlockingQueue<String> q;
    Consumer(BlockingQueue<String>q){
        this.q = q;
    }
    @Override
    public void run() {
        try{
            while(true){
                System.out.println(Thread.currentThread().getName() + " : " + q.take());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class Producer implements Runnable{
    private BlockingQueue<String>q;
    Producer(BlockingQueue<String>q){
        this.q = q;
    }
    @Override
    public void run() {
        try{
            for(int i = 0;i < 100;i++){
                Thread.sleep(1000);
                q.put(String.valueOf(i));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
public class MultiThreadTests  extends Thread{
    public static void testThread(){
        for(int i = 0;i < 10;i++){
            final int finalI = i;
            //new MyThread(i).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        for(int j = 0;j < 10;j++){
                            Thread.sleep(1000);
                            System.out.println(String.format("T2 %d : %d",finalI,j));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static Object obj = new Object();
    public static void testSynchronized1(){
        synchronized (obj){
            try{
                for(int j = 0;j < 10;j++){
                    Thread.sleep(1000);
                    System.out.println(String.format("T3 %d ",j));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public static void testSynchronized2(){
        synchronized (obj){
            try{
                for(int j = 0;j < 10;j++){
                    Thread.sleep(1000);
                    System.out.println(String.format("T4 %d ",j));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void testSynchronized(){
        for(int i = 0;i < 10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    testSynchronized1();
                    testSynchronized2();
                }
            }).start();
        }
    }



    public static  void testBlockingQueue(){
        BlockingQueue<String>q = new ArrayBlockingQueue<String>(10);
        new Thread(new Producer(q)).start();
        new Thread(new Consumer(q),"Consumer1").start();
        new Thread(new Consumer(q),"Consumer2").start();
    }

    public static ThreadLocal<Integer> threadLocalUserIds = new ThreadLocal<>();
    public static int userId = 0;
    public static void testThreadLocal(){
        for(int i = 0;i < 10;i++){
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        threadLocalUserIds.set(finalI);
                        Thread.sleep(1000);
                        System.out.println("ThreadLocal: " + threadLocalUserIds.get());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        for(int i = 0;i < 10;i++){
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        userId = finalI;
                        Thread.sleep(1000);
                        System.out.println("userId " + userId);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static void testExecutor(){
        //ExecutorService service = Executors.newSingleThreadExecutor();
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i< 10;i++){
                    try{
                        Thread.sleep(1000);
                        System.out.println("Executors1: " + i);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        service.submit(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i< 10;i++){
                    try{
                        Thread.sleep(1000);
                        System.out.println("Executors2: " + i);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        service.shutdown();//新的任务不会被接受，但是原有的任务结束之后才会真正关闭

        while(!service.isTerminated()){
            try{
                Thread.sleep(1000);
                System.out.println("Wait for tremination.");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void testFuture(){
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<Integer>future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                sleep(1000);
                return 1;
            }
        });
        service.shutdownNow();
        try{
            System.out.println(future.get());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
