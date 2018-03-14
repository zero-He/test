package cn.strong.leke.monitor.core.service;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.core.model.BeikeStatisDTO;

/**
 * 全网备课统计业务层
 * 
 * @Description
 * @author Deo
 * @createdate 2016年11月16日 下午2:29:26
 */
public interface IBeikeStatisService
{
	/**
	 * 全网备课统计
	 * @param query
	 * @param page
	 * @return
	 */
	BeikeStatisDTO getAllbeikeData(BeikeStatisDTO query);
	
	/**
	 * 学校历史备课数据统计
	 * @param query
	 * @param page
	 * @return
	 */
	List<BeikeStatisDTO> getSchoolbeikeList(BeikeStatisDTO query, Page page);
	
	/**
	 * 年级学科备课数据
	 * @param query
	 * @param page
	 * @return
	 */
	List<BeikeStatisDTO> getGradebeikeList(BeikeStatisDTO query, Page page);
}
