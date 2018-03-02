package com.teethen.sdk.xutil;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xingq on 2018/3/1.
 * 线程管理工具类
 */

public class ThreadUtil {

    //private static int CORE_POOL_SIZE = 5;//线程池核心线程数
    //private static int MAX_POOL_SIZE = 100;//线程池最大线程数
    //private static int KEEP_ALIVE_TIME = 10000;//额外线程空状态生存时间
    //private static BlockingQueue workQueue = new ArrayBlockingQueue(10);//阻塞队列。当核心线程都被占用，且阻塞队列已满的情况下，才会开启额外线程。

    private static int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static int MAX_POOL_SIZE = CORE_POOL_SIZE * 10;
    private static int KEEP_ALIVE_TIME = 1;
    private static TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private static BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();
    private static ExecutorService executorService = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT,
            taskQueue,
            new BackgroundThreadFactory());

    public static void execute(Runnable runnable){
        executorService.execute(runnable);
    }

    public static void execute(FutureTask futureTask){
        executorService.execute(futureTask);
    }

    public static void cancel(FutureTask futureTask){
        futureTask.cancel(true);
    }

    static class BackgroundThreadFactory implements ThreadFactory {
        private final AtomicInteger integer = new AtomicInteger();
        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, String.valueOf(integer.getAndIncrement()));
        }
    }

    /**## 华丽的分割线 ##**/
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static Executor sExecutor = Executors.newSingleThreadExecutor();
    public static void runOnBackThread(Runnable runnable){
        sExecutor.execute(runnable);
    }
    public static void runOnUiThread(Runnable runnable){
        sHandler.post(runnable);
    }
}
