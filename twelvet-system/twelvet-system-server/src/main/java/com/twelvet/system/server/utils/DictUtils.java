package com.twelvet.system.server.utils;

import com.twelvet.framework.RedisService;
import com.twelvet.framework.constants.CacheConstants;
import com.twelvet.framework.utils.SpringUtils;

import java.util.Collection;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 字典工具类
 */
public class DictUtils {

	/**
	 * 清空字典缓存
	 */
	public static void clearDictCache() {
		Collection<String> keys = SpringUtils.getBean(RedisService.class).keys(CacheConstants.SYS_DICT_KEY + "*");
		SpringUtils.getBean(RedisService.class).deleteObject(keys);
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
