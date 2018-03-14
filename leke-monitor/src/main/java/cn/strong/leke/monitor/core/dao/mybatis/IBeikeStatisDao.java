package cn.strong.leke.monitor.core.dao.mybatis;

import java.util.List;
import java.util.Map;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.core.model.BeikeStatisDTO;

/**
 * 全网备课数据层
 * 
 * @Description
 * @author Deo
 * @createdate 2016年11月16日 下午2:38:45
 */
public interface IBeikeStatisDao
{

	/**
	 * 获取全网学校数量
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	BeikeStatisDTO selectSchoolNums(BeikeStatisDTO query);

	/**
	 * 获取时间段内课堂数
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	BeikeStatisDTO selectLessonNums(BeikeStatisDTO query);

	/**
	 * 全网课中课前课件数据
	 * 
	 * @param query
	 * @return
	 */
	BeikeStatisDTO selectAllCoursewareData(BeikeStatisDTO query);

	/**
	 * 全网课中课前作业数据
	 * 
	 * @param params
	 * @return
	 */
	BeikeStatisDTO selectAllHomeworkData(BeikeStatisDTO query);

	/**
	 * 全网微课数据
	 * 
	 * @param params
	 * @return
	 */
	BeikeStatisDTO selectAllWkData(BeikeStatisDTO query);

	/**
	 * 全网备课包数据
	 * 
	 * @param params
	 * @return
	 */
	BeikeStatisDTO selectAllBkgData(BeikeStatisDTO query);
	
	/**
	 * 绑定课件课堂数
	 * 
	 * @param params
	 * @return
	 */
	BeikeStatisDTO selectAllCwLessonNums(BeikeStatisDTO query);
	
	/**
	 * 绑定作业课堂数
	 * 
	 * @param params
	 * @return
	 */
	BeikeStatisDTO selectAllHwLessonNums(BeikeStatisDTO query);

	/**
	 * 学校历史备课数据列表
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<BeikeStatisDTO> selectSchoolBeikeList(BeikeStatisDTO query, Page page);

	/**
	 * 学校课中课前课件数据
	 * 
	 * @param query
	 * @return
	 */
	List<BeikeStatisDTO> selectCoursewareData(Map<String, Object> params);

	/**
	 * 学校课中课前作业数据
	 * 
	 * @param params
	 * @return
	 */
	List<BeikeStatisDTO> selectHomeworkData(Map<String, Object> params);

	/**
	 * 微课数据
	 * 
	 * @param params
	 * @return
	 */
	List<BeikeStatisDTO> selectWkData(Map<String, Object> params);

	/**
	 * 备课包数据
	 * 
	 * @param params
	 * @return
	 */
	List<BeikeStatisDTO> selectBkgData(Map<String, Object> params);

	/**
	 * 学校绑定课件课堂数
	 * 
	 * @param params
	 * @return
	 */
	List<BeikeStatisDTO>  selectSchoolCwLessonNums(Map<String, Object> params);
	
	/**
	 * 学校绑定作业课堂数
	 * 
	 * @param params
	 * @return
	 */
	List<BeikeStatisDTO>  selectSchoolHwLessonNums(Map<String, Object> params);

	
	/**
	 * 年级学科备课数据列表
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<BeikeStatisDTO> selectGradeBeikeList(BeikeStatisDTO query, Page page);
	
	/**
	 * 年级课中课前课件数据
	 * 
	 * @param query
	 * @return
	 */
	List<BeikeStatisDTO> selectGradeCoursewareData(Map<String, Object> params);

	/**
	 * 年级课中课前作业数据
	 * 
	 * @param params
	 * @return
	 */
	List<BeikeStatisDTO> selectGradeHomeworkData(Map<String, Object> params);

	/**
	 * 年级微课数据
	 * 
	 * @param params
	 * @return
	 */
	List<BeikeStatisDTO> selectGradeWkData(Map<String, Object> params);

	/**
	 * 年级备课包数据
	 * 
	 * @param params
	 * @return
	 */
	List<BeikeStatisDTO> selectGradeBkgData(Map<String, Object> params);
	
	/**
	 * 年级绑定课件课堂数
	 * 
	 * @param params
	 * @return
	 */
	List<BeikeStatisDTO>  selectGradeCwLessonNums(Map<String, Object> params);
	
	/**
	 * 年级绑定作业课堂数
	 * 
	 * @param params
	 * @return
	 */
	List<BeikeStatisDTO>  selectGradeHwLessonNums(Map<String, Object> params);
}
