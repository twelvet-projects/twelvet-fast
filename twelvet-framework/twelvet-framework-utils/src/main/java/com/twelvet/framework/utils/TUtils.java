package com.twelvet.framework.utils;

import com.twelvet.framework.utils.exception.TWTUtilsException;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: twelvet工具类
 */
public class TUtils {

	public TUtils() {
		throw new TWTUtilsException("This is a utility class and cannot be instantiated");
	}

	/**
	 * 线程池
	 */
	public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			// 核心数量
			3,
			// 最大线程数量
			// 最大核心*2,获取cpu数量
			Runtime.getRuntime().availableProcessors() * 2,
			// 保持一定连接时间
			10,
			// 以分钟计算
			TimeUnit.MINUTES,
			// 队列
			new LinkedBlockingQueue<>(30),
			// 线程工厂
			Executors.defaultThreadFactory(),
			// 拒绝策略
			new ThreadPoolExecutor.CallerRunsPolicy());

}
