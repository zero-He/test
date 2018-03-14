package cn.strong.leke.monitor.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.core.dao.mybatis.IBeikeStatisDao;
import cn.strong.leke.monitor.core.model.BeikeStatisDTO;
import cn.strong.leke.monitor.core.service.IBeikeStatisService;

/**
 * 全网备课业务层
 * 
 * @Description
 * @author Deo
 * @createdate 2016年11月16日 下午2:34:30
 */
@Service
public class BeikeStatisServiceImpl implements IBeikeStatisService
{
	@Resource
	private IBeikeStatisDao beikeStatisDao;

	/**
	 * 全网备课统计
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	@Override
	public BeikeStatisDTO getAllbeikeData(BeikeStatisDTO query)
	{
		BeikeStatisDTO model = new BeikeStatisDTO();
		BeikeStatisDTO schoolNum = beikeStatisDao.selectSchoolNums(query);
		BeikeStatisDTO lesson = beikeStatisDao.selectLessonNums(query);
		BeikeStatisDTO courseware = beikeStatisDao.selectAllCoursewareData(query);
		BeikeStatisDTO homework = beikeStatisDao.selectAllHomeworkData(query);
		BeikeStatisDTO wk = beikeStatisDao.selectAllWkData(query);
		BeikeStatisDTO bkg = beikeStatisDao.selectAllBkgData(query);

		if (schoolNum != null)
		{
			model.setSchoolNums(schoolNum.getSchoolNums());
		}
		if (lesson != null)
		{
			model.setMustLessons(lesson.getMustLessons());
			model.setBeikeLessons(lesson.getBeikeLessons());
		}
		if (courseware != null)
		{
			model.setBeforeCwUpNums(courseware.getBeforeCwUpNums());
			model.setBeforeCwQuoteNums(courseware.getBeforeCwQuoteNums());
			model.setBeforeCwEditNums(courseware.getBeforeCwEditNums());
			model.setInClassCwUpNums(courseware.getInClassCwUpNums());
			model.setInClassCwQuoteNums(courseware.getInClassCwQuoteNums());
			model.setInClassCwEditNums(courseware.getInClassCwEditNums());
			BeikeStatisDTO cwLessonNums = beikeStatisDao.selectAllCwLessonNums(query);
			model.setCwLessonNums(cwLessonNums.getCwLessonNums());
		}
		if (homework != null)
		{
			model.setBeforeHwUpNums(homework.getBeforeHwUpNums());
			model.setBeforeHwQuoteNums(homework.getBeforeHwQuoteNums());
			model.setBeforeHwEditNums(homework.getBeforeHwEditNums());
			model.setInClassHwUpNums(homework.getInClassHwUpNums());
			model.setInClassHwQuoteNums(homework.getInClassHwQuoteNums());
			model.setInClassHwEditNums(homework.getInClassHwEditNums());
			model.setAfterHwUpNums(homework.getAfterHwUpNums());
			model.setAfterHwQuoteNums(homework.getAfterHwQuoteNums());
			model.setAfterHwEditNums(homework.getAfterHwEditNums());
			BeikeStatisDTO hwLessonNums = beikeStatisDao.selectAllHwLessonNums(query);
			model.setHwLessonNums(hwLessonNums.getHwLessonNums());
		}
		if (bkg != null)
		{
			model.setBkgUpNums(bkg.getBkgUpNums());
			model.setBkgQuoteNums(bkg.getBkgQuoteNums());
			model.setBkgEditNums(bkg.getBkgEditNums());
		}
		if (wk != null)
		{
			model.setBeforeWkUpNums(wk.getBeforeWkUpNums());
			model.setBeforeWkQuoteNums(wk.getBeforeWkQuoteNums());
			model.setAfterWkUpNums(wk.getAfterWkUpNums());
			model.setAfterWkQuoteNums(wk.getAfterWkQuoteNums());
		}

		return model;
	}

	/**
	 * 学校历史备课数据统计
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	@Override
	public List<BeikeStatisDTO> getSchoolbeikeList(BeikeStatisDTO query, Page page)
	{
		// step1.获取时间段内潜前10条学校备课数据
		List<BeikeStatisDTO> mainList = beikeStatisDao.selectSchoolBeikeList(query, page);
		if (mainList.size() <= 0)
		{
			return null;
		}
		List<Long> schoolIds = mainList.stream().map(t -> t.getSchoolId()).collect(Collectors.toList());
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("fromDate", query.getFromDate());
		params.put("endDate", query.getEndDate());
		params.put("schoolIds", schoolIds);
		// step2.获取备课课件数据
		List<BeikeStatisDTO> coursewareList = beikeStatisDao.selectCoursewareData(params);
		// step3.获取备课作业数据
		List<BeikeStatisDTO> homeworkList = beikeStatisDao.selectHomeworkData(params);
		// step4.获取微课数据
		List<BeikeStatisDTO> wkList = beikeStatisDao.selectWkData(params);
		// step5.获取备课包数据
		List<BeikeStatisDTO> bkgList = beikeStatisDao.selectBkgData(params);
		List<BeikeStatisDTO> cwLessonNumList = beikeStatisDao.selectSchoolCwLessonNums(params);
		List<BeikeStatisDTO> hwLessonNumList = beikeStatisDao.selectSchoolHwLessonNums(params);
		// step6.填充数据
		for (BeikeStatisDTO item : mainList)
		{
			List<BeikeStatisDTO> List = new ArrayList<BeikeStatisDTO>();
			List<BeikeStatisDTO> cwlessonNum = new ArrayList<BeikeStatisDTO>();
			List<BeikeStatisDTO> hwlessonNum = new ArrayList<BeikeStatisDTO>();
			BeikeStatisDTO dto = new BeikeStatisDTO();
			// 课前课中课件数据
			List = coursewareList.stream().filter(t -> t.getSchoolId().equals(item.getSchoolId())).collect(Collectors.toList());
			if (List != null && List.size() > 0)
			{
				cwlessonNum = cwLessonNumList.stream().filter(t -> t.getSchoolId().equals(item.getSchoolId())).collect(Collectors.toList());
				dto = List.get(0);
				item.setBeforeCwUpNums(dto.getBeforeCwUpNums());
				item.setBeforeCwQuoteNums(dto.getBeforeCwQuoteNums());
				item.setBeforeCwEditNums(dto.getBeforeCwEditNums());
				item.setInClassCwUpNums(dto.getInClassCwUpNums());
				item.setInClassCwQuoteNums(dto.getInClassCwQuoteNums());
				item.setInClassCwEditNums(dto.getInClassCwEditNums());
				item.setCwLessonNums(cwlessonNum.get(0).getCwLessonNums());
			}
			// 课前课中作业数据
			List.clear();
			List = homeworkList.stream().filter(t -> t.getSchoolId().equals(item.getSchoolId())).collect(Collectors.toList());
			if (List != null && List.size() > 0)
			{
				hwlessonNum = hwLessonNumList.stream().filter(t -> t.getSchoolId().equals(item.getSchoolId())).collect(Collectors.toList());
				dto = List.get(0);
				item.setBeforeHwUpNums(dto.getBeforeHwUpNums());
				item.setBeforeHwQuoteNums(dto.getBeforeHwQuoteNums());
				item.setBeforeHwEditNums(dto.getBeforeHwEditNums());
				item.setInClassHwUpNums(dto.getInClassHwUpNums());
				item.setInClassHwQuoteNums(dto.getInClassHwQuoteNums());
				item.setInClassHwEditNums(dto.getInClassHwEditNums());
				item.setAfterHwUpNums(dto.getAfterHwUpNums());
				item.setAfterHwQuoteNums(dto.getAfterHwQuoteNums());
				item.setAfterHwEditNums(dto.getAfterHwEditNums());
				item.setHwLessonNums(hwlessonNum.get(0).getHwLessonNums());
			}
			// 备课包数据
			List.clear();
			List = bkgList.stream().filter(t -> t.getSchoolId().equals(item.getSchoolId())).collect(Collectors.toList());
			if (List != null && List.size() > 0)
			{
				dto = List.get(0);
				item.setBkgUpNums(dto.getBkgUpNums());
				item.setBkgQuoteNums(dto.getBkgQuoteNums());
				item.setBkgEditNums(dto.getBkgEditNums());
			}

			// 微课数据
			List.clear();
			List = wkList.stream().filter(t -> t.getSchoolId().equals(item.getSchoolId())).collect(Collectors.toList());
			if (List != null && List.size() > 0)
			{
				dto = List.get(0);
				item.setBeforeWkUpNums(dto.getBeforeWkUpNums());
				item.setBeforeWkQuoteNums(dto.getBeforeWkQuoteNums());
				item.setAfterWkUpNums(dto.getAfterWkUpNums());
				item.setAfterWkQuoteNums(dto.getAfterWkQuoteNums());
			}
		}
		return mainList;
	}

	/**
	 * 年级学科备课数据
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	@Override
	public List<BeikeStatisDTO> getGradebeikeList(BeikeStatisDTO query, Page page)
	{
		if (query.getSchoolId() == null)
		{
			return null;
		}
		// step1.获取时间段内潜前10条学校备课数据
		List<BeikeStatisDTO> mainList = beikeStatisDao.selectGradeBeikeList(query, page);
		if (mainList.size() <= 0)
		{
			return null;
		}
		// List<Long> schoolIds = mainList.stream().map(t ->
		// t.getSchoolId()).collect(Collectors.toList());
		List<Long> gradeIds = mainList.stream().filter(t -> t.getGradeId() != null).map(t -> t.getGradeId()).collect(Collectors.toList());
		List<Long> subjectIds = mainList.stream().filter(t -> t.getSubjectId() != null).map(t -> t.getSubjectId()).collect(Collectors.toList());
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("fromDate", query.getFromDate());
		params.put("endDate", query.getEndDate());
		params.put("schoolId", query.getSchoolId());
		params.put("gradeIds", gradeIds);
		params.put("subjectIds", subjectIds);
		// step2.获取备课课件数据
		List<BeikeStatisDTO> coursewareList = beikeStatisDao.selectGradeCoursewareData(params);
		// step3.获取备课作业数据
		List<BeikeStatisDTO> homeworkList = beikeStatisDao.selectGradeHomeworkData(params);
		// step4.获取微课数据
		List<BeikeStatisDTO> wkList = beikeStatisDao.selectGradeWkData(params);
		// step5.获取备课包数据
		List<BeikeStatisDTO> bkgList = beikeStatisDao.selectGradeBkgData(params);
		List<BeikeStatisDTO> cwLessonNumList = beikeStatisDao.selectGradeCwLessonNums(params);
		List<BeikeStatisDTO> hwLessonNumList = beikeStatisDao.selectGradeHwLessonNums(params);
		// step6.填充数据
		for (BeikeStatisDTO item : mainList)
		{
			List<BeikeStatisDTO> List = new ArrayList<BeikeStatisDTO>();
			List<BeikeStatisDTO> cwlessonNum = new ArrayList<BeikeStatisDTO>();
			List<BeikeStatisDTO> hwlessonNum = new ArrayList<BeikeStatisDTO>();
			BeikeStatisDTO dto = new BeikeStatisDTO();
			// 课前课中课件数据
			List = coursewareList
					.stream()
					.filter(t -> t.getSchoolId().equals(item.getSchoolId()) && t.getGradeId().equals(item.getGradeId())
							&& t.getSubjectId().equals(item.getSubjectId())).collect(Collectors.toList());
			if (List != null && List.size() > 0)
			{
				cwlessonNum = cwLessonNumList
						.stream()
						.filter(t -> t.getSchoolId().equals(item.getSchoolId()) && t.getGradeId().equals(item.getGradeId())
								&& t.getSubjectId().equals(item.getSubjectId())).collect(Collectors.toList());
				dto = List.get(0);
				item.setBeforeCwUpNums(dto.getBeforeCwUpNums());
				item.setBeforeCwQuoteNums(dto.getBeforeCwQuoteNums());
				item.setBeforeCwEditNums(dto.getBeforeCwEditNums());
				item.setInClassCwUpNums(dto.getInClassCwUpNums());
				item.setInClassCwQuoteNums(dto.getInClassCwQuoteNums());
				item.setInClassCwEditNums(dto.getInClassCwEditNums());
				item.setCwLessonNums(cwlessonNum.get(0).getCwLessonNums());
			}
			// 课前课中作业数据
			List.clear();
			List = homeworkList
					.stream()
					.filter(t -> t.getSchoolId().equals(item.getSchoolId()) && t.getGradeId().equals(item.getGradeId())
							&& t.getSubjectId().equals(item.getSubjectId())).collect(Collectors.toList());
			if (List != null && List.size() > 0)
			{
				hwlessonNum = hwLessonNumList
						.stream()
						.filter(t -> t.getSchoolId().equals(item.getSchoolId()) && t.getGradeId().equals(item.getGradeId())
								&& t.getSubjectId().equals(item.getSubjectId())).collect(Collectors.toList());
				dto = List.get(0);
				item.setBeforeHwUpNums(dto.getBeforeHwUpNums());
				item.setBeforeHwQuoteNums(dto.getBeforeHwQuoteNums());
				item.setBeforeHwEditNums(dto.getBeforeHwEditNums());
				item.setInClassHwUpNums(dto.getInClassHwUpNums());
				item.setInClassHwQuoteNums(dto.getInClassHwQuoteNums());
				item.setInClassHwEditNums(dto.getInClassHwEditNums());
				item.setAfterHwUpNums(dto.getAfterHwUpNums());
				item.setAfterHwQuoteNums(dto.getAfterHwQuoteNums());
				item.setAfterHwEditNums(dto.getAfterHwEditNums());
				item.setHwLessonNums(hwlessonNum.get(0).getHwLessonNums());
			}
			// 备课包数据
			List.clear();
			List = bkgList
					.stream()
					.filter(t -> t.getSchoolId().equals(item.getSchoolId()) && t.getGradeId().equals(item.getGradeId())
							&& t.getSubjectId().equals(item.getSubjectId())).collect(Collectors.toList());
			if (List != null && List.size() > 0)
			{
				dto = List.get(0);
				item.setBkgUpNums(dto.getBkgUpNums());
				item.setBkgQuoteNums(dto.getBkgQuoteNums());
				item.setBkgEditNums(dto.getBkgEditNums());
			}

			// 微课数据
			List.clear();
			List = wkList
					.stream()
					.filter(t -> t.getSchoolId().equals(item.getSchoolId()) && t.getGradeId().equals(item.getGradeId())
							&& t.getSubjectId().equals(item.getSubjectId())).collect(Collectors.toList());
			if (List != null && List.size() > 0)
			{
				dto = List.get(0);
				item.setBeforeWkUpNums(dto.getBeforeWkUpNums());
				item.setBeforeWkQuoteNums(dto.getBeforeWkQuoteNums());
				item.setAfterWkUpNums(dto.getAfterWkUpNums());
				item.setAfterWkQuoteNums(dto.getAfterWkQuoteNums());
			}
		}
		return mainList;
	}
}
