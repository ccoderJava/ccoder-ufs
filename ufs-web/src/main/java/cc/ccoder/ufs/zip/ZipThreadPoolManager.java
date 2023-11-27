package cc.ccoder.ufs.zip;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ZipThreadPoolManager {

    private static volatile ThreadPoolExecutor executor;

    private ZipThreadPoolManager() {

    }

    public static ThreadPoolExecutor getPool() {
        if (null != executor) {
            return executor;
        }
        synchronized (ZipThreadPoolManager.class) {
            if (null == executor) {
                executor = new ThreadPoolExecutor(10, 20, 2000, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20000), r -> {
                    Thread thread = new Thread(r);
                    thread.setName("zip-download-thread-" + thread.getId());
                    return thread;
                });
            }
        }
        return executor;
    }
}
