package com.twelvet.system.server.utils;

import com.twelvet.framework.RedisUtils;
import com.twelvet.framework.constants.CacheConstants;

import java.util.Collection;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 字典工具类
 */
public class DictUtils {

	/**
	 * 清空字典缓存
	 */
	public static void clearDictCache() {
		Collection<String> keys = RedisUtils.keys(CacheConstants.SYS_DICT_KEY + "*");
		RedisUtils.deleteObject(keys);
	}

	/**
	 * 设置cache key
	 * @param configKey 参数键
	 * @return 缓存键key
	 */
	public static String getCacheKey(String configKey) {
		return CacheConstants.SYS_DICT_KEY + configKey;
	}

}
