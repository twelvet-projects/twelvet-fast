package com.twelvet.system.server.service.impl;

import java.util.List;

import com.twelvet.framework.RedisUtils;
import com.twelvet.framework.core.locale.constants.LocaleCacheConstants;
import com.twelvet.framework.core.locale.constants.LocaleSystemConstants;
import com.twelvet.framework.utils.DateUtils;
import com.twelvet.system.api.domain.I18n;
import com.twelvet.system.api.domain.SysDictData;
import com.twelvet.system.server.mapper.I18nMapper;
import com.twelvet.system.server.mapper.SysDictDataMapper;
import com.twelvet.system.server.service.II18nService;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.twelvet.framework.security.utils.SecurityUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * 国际化Service业务层处理
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-04-01
 */
@Service
public class I18nServiceImpl implements II18nService, ApplicationRunner {

	private final static Logger log = LoggerFactory.getLogger(I18nServiceImpl.class);

	@Autowired
	private I18nMapper i18nMapper;

	@Autowired
	private SysDictDataMapper dictDataMapper;

	/**
	 * 判断是否需要进行国际化初始化
	 * @param args ApplicationArguments
	 * @throws Exception Exception
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		String hashFormat = String.format("%s::%s:*", LocaleCacheConstants.LOCALE, LocaleCacheConstants.ZH_CN);
		if (!RedisUtils.getClient().getBucket(hashFormat).isExists()) {
			log.info("Detected i18n deficiency, initializing");
			List<I18n> i18ns = i18nMapper.selectI18nList(new I18n());
			for (I18n i18n : i18ns) {
				String format = String.format("%s::%s:%s", LocaleCacheConstants.LOCALE, i18n.getType(), i18n.getCode());
				RedisUtils.setCacheObject(format, i18n.getValue());
			}
			// 完成初始化标志
			RedisUtils.setCacheObject(hashFormat, true);
			log.info("i18n initialization complete");
		}

		// 处理i18n支持语言缓存
		if (!RedisUtils.getClient().getBucket(LocaleCacheConstants.CACHE_DICT_CODE).isExists()) {
			List<SysDictData> sysDictData = dictDataMapper.selectDictDataByType(LocaleCacheConstants.DICT_CODE);
			RedisUtils.setCacheObject(LocaleCacheConstants.CACHE_DICT_CODE, sysDictData);
		}

	}

	/**
	 * 查询国际化
	 * @param i18nId 国际化主键
	 * @return 国际化
	 */
	@Override
	public I18n selectI18nByI18nId(Long i18nId) {
		return i18nMapper.selectI18nByI18nId(i18nId);
	}

	/**
	 * 查询国际化列表
	 * @param i18n 国际化
	 * @return 国际化
	 */
	@Override
	public List<I18n> selectI18nList(I18n i18n) {
		return i18nMapper.selectI18nList(i18n);
	}

	/**
	 * 新增国际化
	 * @param i18n 国际化
	 * @return 结果
	 */
	@Override
	public int insertI18n(I18n i18n) {
		i18n.setCreateTime(DateUtils.getNowDate());
		String loginUsername = SecurityUtils.getUsername();
		i18n.setCreateBy(loginUsername);
		i18n.setUpdateBy(loginUsername);
		return i18nMapper.insertI18n(i18n);
	}

	/**
	 * 修改国际化
	 * @param i18n 国际化
	 * @return 结果
	 */
	@Override
	public int updateI18n(I18n i18n) {
		i18n.setUpdateTime(DateUtils.getNowDate());
		String loginUsername = SecurityUtils.getUsername();
		i18n.setCreateBy(loginUsername);
		i18n.setUpdateBy(loginUsername);
		return i18nMapper.updateI18n(i18n);
	}

	/**
	 * 批量删除国际化
	 * @param i18nIds 需要删除的国际化主键
	 * @return 结果
	 */
	@Override
	public int deleteI18nByI18nIds(Long[] i18nIds) {
		return i18nMapper.deleteI18nByI18nIds(i18nIds);
	}

	/**
	 * 删除国际化信息
	 * @param i18nId 国际化主键
	 * @return 结果
	 */
	@Override
	public int deleteI18nByI18nId(Long i18nId) {
		return i18nMapper.deleteI18nByI18nId(i18nId);
	}

}
