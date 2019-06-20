package com.caetp.digiex.utli.common;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Java 线程工具包
 */
public class ThreadUtil {

	private static ExecutorService sharedES; // 全局异步服务

	private static int maxAsyncThreads = 30; // 最大异步线程池
	private static int maxAsyncQueueSize = 300; // 最大异步执行队列

	public static ExecutorService getSharedES() {
		synchronized (ThreadUtil.class) {
			if (sharedES != null) {
				return sharedES;
			}
			sharedES = new ThreadPoolExecutor(maxAsyncThreads / 10, maxAsyncThreads, 60L, TimeUnit.SECONDS,
					new ArrayBlockingQueue<Runnable>(maxAsyncQueueSize), new ThreadFactory() {
						private final AtomicInteger threadNumber = new AtomicInteger(1);
						@Override
						public Thread newThread(Runnable task) {
							Thread thread = new Thread(task, "ThreadUtil-" + threadNumber.getAndIncrement());
							thread.setDaemon(true);
							thread.setPriority(Thread.NORM_PRIORITY);
							return thread;
						}
					}, new RejectedExecutionHandler() {
						@Override
						public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
							if (!executor.isShutdown()) {
								task.run();
							} else {
								throw new RejectedExecutionException();
							}
						}
					});
			return sharedES;
		}
	}

}
