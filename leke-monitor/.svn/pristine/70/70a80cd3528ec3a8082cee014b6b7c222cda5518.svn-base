package cn.strong.leke.monitor.core.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.context.base.SchoolStageContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.core.dao.mybatis.IClassStatisticsDao;
import cn.strong.leke.monitor.core.model.ClassStatistics;
import cn.strong.leke.monitor.core.service.IClassStatisticsService;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;

/**
 * @ClassName: ClassStatisticsServiceImpl
 * @Description: CRM课堂统计服务实现层
 * @author huangkai
 * @date 2016年11月16日 上午11:42:05
 * @version V1.0
 */
@Service
public class ClassStatisticsServiceImpl implements IClassStatisticsService
{
	@Resource
	private IClassStatisticsDao classStatisticsDao;

	/**
	 * @Description: 获取学校学段对应的课堂数
	 * @param item
	 * @param listMap
	 * @return    参数
	 * @throws
	 */
	private Integer getClassNum(ClassStatistics item, List<Map<String, Object>> listMap)
	{
		Integer result = 0;
		for (Map<String, Object> entry : listMap)
		{
			if (item.getSchoolId().equals(Long.valueOf(entry.get("schoolId").toString()))
					&& item.getSchoolStageId().equals(Long.valueOf(entry.get("schoolStageId").toString()))
					&& entry.get("num") != null)
			{
				return Integer.valueOf(entry.get("num").toString());
			}
		}

		return result;
	}

	/**
	 * @Description: 获取老师学科年级对应的课堂数
	 * @param item
	 * @param listMap
	 * @return    参数
	 * @throws
	 */
	private Integer getTeacherClassNum(ClassStatistics item, List<Map<String, Object>> listMap)
	{
		Integer result = 0;
		for (Map<String, Object> entry : listMap)
		{
			if (item.getTeacherId().equals(Long.valueOf(entry.get("teacherId").toString()))
					&& item.getSubjectId().equals(Long.valueOf(entry.get("subjectId").toString()))
					&& item.getGradeId().equals(Long.valueOf(entry.get("gradeId").toString()))
					&& entry.get("num") != null)
			{
				return Integer.valueOf(entry.get("num").toString());
			}
		}

		return result;
	}

	/**
	 * @Description: 查询学校课堂统计
	 * @param classStatistics（课堂统计）
	 * @param page（分页）
	 * @return List<ClassStatistics>
	 * @throws
	 */
	@Override
	public List<ClassStatistics> querySchoolClassStatistics(ClassStatistics classStatistics, Page page)
	{
		// 客户经理负责的学校学段。
		List<ClassStatistics> listClassStatistics = classStatisticsDao.querySellerSchool(classStatistics, page);

		if (listClassStatistics == null || listClassStatistics.size() <= 0)
		{
			return Collections.emptyList();
		}

		// 学校ID
		List<Long> schoolId = ListUtils.map(listClassStatistics, e ->
		{
			return e.getSchoolId();
		});

		// 学段ID
		List<Long> schoolStageId = ListUtils.map(listClassStatistics, e ->
		{
			return e.getSchoolStageId();
		});

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("schoolId", schoolId);
		map.put("schoolStageId", schoolStageId);
		map.put("statisticsTimeStart", classStatistics.getStatisticsTimeStart());
		map.put("statisticsTimeEnd", classStatistics.getStatisticsTimeEnd());

		// 已结束课堂数
		List<Map<String, Object>> listEndClassNum = classStatisticsDao.getEndClassNum(map);

		// 备课课堂数
		List<Map<String, Object>> listBeikeNum = classStatisticsDao.getBeikeClassNum(map);

		// 上课课堂数
		List<Map<String, Object>> listClassNum = classStatisticsDao.getClassNum(map);

		for (ClassStatistics item : listClassStatistics)
		{
			item.setEndClassNum(getClassNum(item, listEndClassNum));
			item.setBeikeClassNum(getClassNum(item, listBeikeNum));
			item.setClassNum(getClassNum(item, listClassNum));

			if (!item.getEndClassNum().equals(0))
			{
				String beikRate = String.format("%.2f",
						(Double.valueOf(item.getBeikeClassNum()) / Double.valueOf(item.getEndClassNum())) * 100);
				String classRate = String.format("%.2f",
						(Double.valueOf(item.getClassNum()) / Double.valueOf(item.getEndClassNum())) * 100);

				item.setBeikeRate(beikRate + "%");
				item.setClassRate(classRate + "%");
			} else
			{
				item.setBeikeRate("0.00%");
				item.setClassRate("0.00%");
			}
		}

		return listClassStatistics;
	}

	/**
	 * @Description: 获取所有的学段
	 * @return List<Map<String, String>>
	 * @throws
	 */
	@Override
	public List<Map<String, String>> getSchoolStage()
	{
		// 学段接口
		List<SchoolStageRemote> listSchoolStageRemote = SchoolStageContext.findSchoolStages();

		List<Map<String, String>> listMapSchoolStage = new ArrayList<Map<String, String>>();

		for (SchoolStageRemote item : listSchoolStageRemote)
		{
			Map<String, String> mapSchoolStage = new HashMap<String, String>();
			mapSchoolStage.put("schoolStageId", item.getSchoolStageId().toString());
			mapSchoolStage.put("schoolStageName", item.getSchoolStageName());
			listMapSchoolStage.add(mapSchoolStage);
		}

		return listMapSchoolStage;
	}

	/**
	 * @Description: 课堂明细
	 * @param classStatistics（课堂统计）
	 * @return ClassStatistics
	 * @throws
	 */
	@Override
	public ClassStatistics getClassDetails(ClassStatistics classStatistics)
	{
		List<Long> schoolId = new ArrayList<Long>();
		schoolId.add(classStatistics.getSchoolId());

		List<Long> schoolStageId = new ArrayList<Long>();
		schoolStageId.add(classStatistics.getSchoolStageId());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("schoolId", schoolId);
		map.put("schoolStageId", schoolStageId);
		map.put("statisticsTimeStart", classStatistics.getStatisticsTimeStart());
		map.put("statisticsTimeEnd", classStatistics.getStatisticsTimeEnd());

		// 作业数及课件数
		List<ClassStatistics> listWorkAndCoursewareNum = classStatisticsDao.getWorkAndCoursewareNum(map);
		if (listWorkAndCoursewareNum != null && listWorkAndCoursewareNum.size() > 0)
		{
			ClassStatistics workAndCoursewareNum = listWorkAndCoursewareNum.get(0);

			classStatistics.setPreviewWorkNum(workAndCoursewareNum.getPreviewWorkNum());
			classStatistics.setCoursewareNum(workAndCoursewareNum.getCoursewareNum());
			classStatistics.setClassWorkNum(workAndCoursewareNum.getClassWorkNum());
			classStatistics.setAfterClassWorkNum(workAndCoursewareNum.getAfterClassWorkNum());
			classStatistics.setBeiPreviewWorkNum(workAndCoursewareNum.getBeiPreviewWorkNum());
			classStatistics.setBeiCoursewareNum(workAndCoursewareNum.getBeiCoursewareNum());
			classStatistics.setBeiClassWorkNum(workAndCoursewareNum.getBeiClassWorkNum());

			if (!classStatistics.getBeikeClassNum().equals(0))
			{
				String previewWorkRate = String.format("%.2f",
						(Double.valueOf(workAndCoursewareNum.getBeiPreviewWorkNum())
								/ Double.valueOf(classStatistics.getBeikeClassNum())) * 100);

				classStatistics.setPreviewWorkRate(previewWorkRate + "%");

				String coursewareRate = String.format("%.2f",
						(Double.valueOf(workAndCoursewareNum.getBeiCoursewareNum())
								/ Double.valueOf(classStatistics.getBeikeClassNum())) * 100);
				classStatistics.setCoursewareRate(coursewareRate + "%");

				String classWorkRate = String.format("%.2f", (Double.valueOf(workAndCoursewareNum.getBeiClassWorkNum())
						/ Double.valueOf(classStatistics.getBeikeClassNum())) * 100);
				classStatistics.setClassWorkRate(classWorkRate + "%");
			} else
			{
				classStatistics.setPreviewWorkRate("0.00%");
				classStatistics.setCoursewareRate("0.00%");
				classStatistics.setClassWorkRate("0.00%");
			}
		} else
		{
			classStatistics.setPreviewWorkRate("0.00%");
			classStatistics.setCoursewareRate("0.00%");
			classStatistics.setClassWorkRate("0.00%");
		}

		// 测试次数及问答次数
		List<ClassStatistics> listClassTestAndAskNum = classStatisticsDao.getClassTestAndAskNum(map);
		if (listClassTestAndAskNum != null && listClassTestAndAskNum.size() > 0)
		{
			ClassStatistics classTestAndAskNum = listClassTestAndAskNum.get(0);

			classStatistics.setClassTestNum(classTestAndAskNum.getClassTestNum());
			classStatistics.setQuestionsAnswersNum(classTestAndAskNum.getQuestionsAnswersNum());

			if (!classStatistics.getClassNum().equals(0))
			{
				String classTestRate = String.format("%.2f", (Double.valueOf(classTestAndAskNum.getTestAttenNum())
						/ Double.valueOf(classStatistics.getClassNum())) * 100);

				classStatistics.setClassTestRate(classTestRate + "%");

				String questionsAnswersRate = String.format("%.2f",
						(Double.valueOf(classTestAndAskNum.getAskAttenNum())
								/ Double.valueOf(classStatistics.getClassNum())) * 100);
				classStatistics.setQuestionsAnswersRate(questionsAnswersRate + "%");
			} else
			{
				classStatistics.setClassTestRate("0.00%");
				classStatistics.setQuestionsAnswersRate("0.00%");
			}
		} else
		{
			classStatistics.setClassTestRate("0.00%");
			classStatistics.setQuestionsAnswersRate("0.00%");
		}

		// 课后数据
		List<ClassStatistics> listEndClassData = classStatisticsDao.getEndClassData(map);
		if (listEndClassData != null && listEndClassData.size() > 0)
		{
			ClassStatistics endClassData = listEndClassData.get(0);

			classStatistics.setClassWorkTotalNum(endClassData.getClassWorkTotalNum());
			classStatistics.setWorkFinishNum(endClassData.getWorkFinishNum());
			classStatistics.setWorkcorrectNum(endClassData.getWorkcorrectNum());

			if (!endClassData.getClassWorkTotalNum().equals(0))
			{
				String workFinishRate = String.format("%.2f", (Double.valueOf(endClassData.getWorkFinishNum())
						/ Double.valueOf(endClassData.getClassWorkTotalNum())) * 100);
				classStatistics.setWorkFinishRate(workFinishRate + "%");
			} else
			{
				classStatistics.setWorkFinishRate("0.00%");
			}

			if (!endClassData.getWorkFinishNum().equals(0))
			{
				String workcorrectRate = String.format("%.2f", (Double.valueOf(endClassData.getWorkcorrectNum())
						/ Double.valueOf(endClassData.getWorkFinishNum())) * 100);
				classStatistics.setWorkcorrectRate(workcorrectRate + "%");
			} else
			{
				classStatistics.setWorkcorrectRate("0.00%");
			}

		} else
		{
			classStatistics.setWorkFinishRate("0.00%");
			classStatistics.setWorkcorrectRate("0.00%");
		}

		return classStatistics;
	}

	/**
	 * @Description: 获取导出数据源
	 * @param classStatistics
	 * @return ClassStatistics
	 * @throws
	 */
	@Override
	public List<ClassStatistics> getExportData(ClassStatistics classStatistics)
	{
		List<ClassStatistics> listClassStatistics = classStatisticsDao.querySellerSchool(classStatistics, null);

		if (listClassStatistics == null || listClassStatistics.size() <= 0)
		{
			return Collections.emptyList();
		}

		// 学校ID
		List<Long> schoolId = ListUtils.map(listClassStatistics, e ->
		{
			return e.getSchoolId();
		});

		// 学段ID
		List<Long> schoolStageId = ListUtils.map(listClassStatistics, e ->
		{
			return e.getSchoolStageId();
		});

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("schoolId", schoolId);
		map.put("schoolStageId", schoolStageId);
		map.put("statisticsTimeStart", classStatistics.getStatisticsTimeStart());
		map.put("statisticsTimeEnd", classStatistics.getStatisticsTimeEnd());

		// 已结束课堂数
		List<Map<String, Object>> listEndClassNum = classStatisticsDao.getEndClassNum(map);

		// 备课课堂数
		List<Map<String, Object>> listBeikeNum = classStatisticsDao.getBeikeClassNum(map);

		// 上课课堂数
		List<Map<String, Object>> listClassNum = classStatisticsDao.getClassNum(map);

		// 作业数及课件数
		List<ClassStatistics> listWorkAndCoursewareNum = classStatisticsDao.getWorkAndCoursewareNum(map);

		// 测试次数及问答次数
		List<ClassStatistics> listClassTestAndAskNum = classStatisticsDao.getClassTestAndAskNum(map);

		// 课后数据
		List<ClassStatistics> listEndClassData = classStatisticsDao.getEndClassData(map);

		for (ClassStatistics item : listClassStatistics)
		{
			item.setEndClassNum(getClassNum(item, listEndClassNum));
			item.setBeikeClassNum(getClassNum(item, listBeikeNum));
			item.setClassNum(getClassNum(item, listClassNum));

			if (!item.getEndClassNum().equals(0))
			{
				String beikRate = String.format("%.2f",
						(Double.valueOf(item.getBeikeClassNum()) / Double.valueOf(item.getEndClassNum())) * 100);
				String classRate = String.format("%.2f",
						(Double.valueOf(item.getClassNum()) / Double.valueOf(item.getEndClassNum())) * 100);

				item.setBeikeRate(beikRate + "%");
				item.setClassRate(classRate + "%");
			} else
			{
				item.setBeikeRate("0.00%");
				item.setClassRate("0.00%");
			}

			// 作业数及课件数
			Boolean existenceWork = true;
			for (ClassStatistics workAndCoursewareNum : listWorkAndCoursewareNum)
			{
				if (item.getSchoolId().equals(workAndCoursewareNum.getSchoolId())
						&& item.getSchoolStageId().equals(workAndCoursewareNum.getSchoolStageId()))
				{
					existenceWork = false;
					item.setPreviewWorkNum(workAndCoursewareNum.getPreviewWorkNum());
					item.setCoursewareNum(workAndCoursewareNum.getCoursewareNum());
					item.setClassWorkNum(workAndCoursewareNum.getClassWorkNum());
					item.setAfterClassWorkNum(workAndCoursewareNum.getAfterClassWorkNum());
					item.setBeiPreviewWorkNum(workAndCoursewareNum.getBeiPreviewWorkNum());
					item.setBeiCoursewareNum(workAndCoursewareNum.getBeiCoursewareNum());
					item.setBeiClassWorkNum(workAndCoursewareNum.getBeiClassWorkNum());

					if (!item.getBeikeClassNum().equals(0))
					{
						String previewWorkRate = String.format("%.2f",
								(Double.valueOf(workAndCoursewareNum.getBeiPreviewWorkNum())
										/ Double.valueOf(item.getBeikeClassNum())) * 100);

						item.setPreviewWorkRate(previewWorkRate + "%");

						String coursewareRate = String.format("%.2f",
								(Double.valueOf(workAndCoursewareNum.getBeiCoursewareNum())
										/ Double.valueOf(item.getBeikeClassNum())) * 100);
						item.setCoursewareRate(coursewareRate + "%");

						String classWorkRate = String.format("%.2f",
								(Double.valueOf(workAndCoursewareNum.getBeiClassWorkNum())
										/ Double.valueOf(item.getBeikeClassNum())) * 100);
						item.setClassWorkRate(classWorkRate + "%");
					} else
					{
						item.setPreviewWorkRate("0.00%");
						item.setCoursewareRate("0.00%");
						item.setClassWorkRate("0.00%");
					}
				}
			}

			if (existenceWork)
			{
				item.setPreviewWorkNum(0);
				item.setCoursewareNum(0);
				item.setClassWorkNum(0);
				item.setAfterClassWorkNum(0);
				item.setPreviewWorkRate("0.00%");
				item.setCoursewareRate("0.00%");
				item.setClassWorkRate("0.00%");

			}

			// 测试次数及问答次数
			Boolean existenceAsk = true;
			for (ClassStatistics classTestAndAskNum : listClassTestAndAskNum)
			{
				if (item.getSchoolId().equals(classTestAndAskNum.getSchoolId())
						&& item.getSchoolStageId().equals(classTestAndAskNum.getSchoolStageId()))
				{
					existenceAsk = false;
					item.setClassTestNum(classTestAndAskNum.getClassTestNum());
					item.setQuestionsAnswersNum(classTestAndAskNum.getQuestionsAnswersNum());

					if (!item.getClassNum().equals(0))
					{
						String classTestRate = String.format("%.2f",
								(Double.valueOf(classTestAndAskNum.getTestAttenNum())
										/ Double.valueOf(item.getClassNum())) * 100);

						item.setClassTestRate(classTestRate + "%");

						String questionsAnswersRate = String.format("%.2f",
								(Double.valueOf(classTestAndAskNum.getAskAttenNum())
										/ Double.valueOf(item.getClassNum())) * 100);
						item.setQuestionsAnswersRate(questionsAnswersRate + "%");
					} else
					{
						item.setClassTestRate("0.00%");
						item.setQuestionsAnswersRate("0.00%");
					}
				}
			}

			if (existenceAsk)
			{
				item.setClassTestNum(0);
				item.setClassTestRate("0.00%");
				item.setQuestionsAnswersNum(0);
				item.setQuestionsAnswersRate("0.00%");
			}

			// 课后数据
			Boolean existenceEndClass = true;
			for (ClassStatistics endClassData : listEndClassData)
			{
				if (item.getSchoolId().equals(endClassData.getSchoolId())
						&& item.getSchoolStageId().equals(endClassData.getSchoolStageId()))
				{
					existenceEndClass = false;
					item.setClassWorkTotalNum(endClassData.getClassWorkTotalNum());
					item.setWorkFinishNum(endClassData.getWorkFinishNum());
					item.setWorkcorrectNum(endClassData.getWorkcorrectNum());

					if (!endClassData.getClassWorkTotalNum().equals(0))
					{
						String workFinishRate = String.format("%.2f", (Double.valueOf(endClassData.getWorkFinishNum())
								/ Double.valueOf(endClassData.getClassWorkTotalNum())) * 100);
						item.setWorkFinishRate(workFinishRate + "%");
					} else
					{
						item.setWorkFinishRate("0.00%");
					}

					if (!endClassData.getWorkFinishNum().equals(0))
					{
						String workcorrectRate = String.format("%.2f", (Double.valueOf(endClassData.getWorkcorrectNum())
								/ Double.valueOf(endClassData.getWorkFinishNum())) * 100);
						item.setWorkcorrectRate(workcorrectRate + "%");
					} else
					{
						item.setWorkcorrectRate("0.00%");
					}
				}
			}

			if (existenceEndClass)
			{
				item.setClassWorkTotalNum(0);
				item.setWorkFinishNum(0);
				item.setWorkcorrectNum(0);
				item.setWorkFinishRate("0.00%");
				item.setWorkcorrectRate("0.00%");
			}
		}

		return listClassStatistics;
	}

	/**
	 * @Description: 查询老师课堂统计
	 * @param classStatistics（课堂统计）
	 * @param page（分页）
	 * @return List<ClassStatistics>
	 * @throws
	 */
	@Override
	public List<ClassStatistics> queryTeacherClassStatistics(ClassStatistics classStatistics, Page page)
	{
		// 客户经理负责的学校学段下老师的课堂数
		List<ClassStatistics> listClassStatistics = classStatisticsDao.queryTeacherSchool(classStatistics, page);

		if (listClassStatistics == null || listClassStatistics.size() <= 0)
		{
			return Collections.emptyList();
		}

		// 学科ID
		List<Long> subjectId = ListUtils.map(listClassStatistics, e ->
		{
			return e.getSubjectId();
		});

		// 年级ID
		List<Long> gradeId = ListUtils.map(listClassStatistics, e ->
		{
			return e.getGradeId();
		});

		// 老师ID
		List<Long> teacherId = ListUtils.map(listClassStatistics, e ->
		{
			return e.getTeacherId();
		});

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjectId", subjectId);
		map.put("gradeId", gradeId);
		map.put("teacherId", teacherId);
		map.put("schoolId", classStatistics.getSchoolId());
		map.put("schoolStageId", classStatistics.getSchoolStageId());
		map.put("statisticsTimeStart", classStatistics.getStatisticsTimeStart());
		map.put("statisticsTimeEnd", classStatistics.getStatisticsTimeEnd());

		// 已结束课堂数
		List<Map<String, Object>> listEndClassNum = classStatisticsDao.getTeacherEndClassNum(map);

		// 备课课堂数
		List<Map<String, Object>> listBeikeNum = classStatisticsDao.getTeacherBeikeClassNum(map);

		// 上课课堂数
		List<Map<String, Object>> listClassNum = classStatisticsDao.getTeacherClassNum(map);

		for (ClassStatistics item : listClassStatistics)
		{
			item.setEndClassNum(getTeacherClassNum(item, listEndClassNum));
			item.setBeikeClassNum(getTeacherClassNum(item, listBeikeNum));
			item.setClassNum(getTeacherClassNum(item, listClassNum));

			if (!item.getEndClassNum().equals(0))
			{
				String beikRate = String.format("%.2f",
						(Double.valueOf(item.getBeikeClassNum()) / Double.valueOf(item.getEndClassNum())) * 100);
				String classRate = String.format("%.2f",
						(Double.valueOf(item.getClassNum()) / Double.valueOf(item.getEndClassNum())) * 100);

				item.setBeikeRate(beikRate + "%");
				item.setClassRate(classRate + "%");
			} else
			{
				item.setBeikeRate("0.00%");
				item.setClassRate("0.00%");
			}
		}

		return listClassStatistics;
	}

	/**
	 * @Description: 查询老师课堂明细
	 * @param classStatistics（课堂统计）
	 * @return ClassStatistics
	 * @throws
	 */
	@Override
	public ClassStatistics getTeacherClassDetails(ClassStatistics classStatistics)
	{
		List<Long> subjectId = new ArrayList<Long>();
		subjectId.add(classStatistics.getSubjectId());

		List<Long> gradeId = new ArrayList<Long>();
		gradeId.add(classStatistics.getGradeId());

		List<Long> teacherId = new ArrayList<Long>();
		teacherId.add(classStatistics.getTeacherId());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjectId", subjectId);
		map.put("gradeId", gradeId);
		map.put("teacherId", teacherId);
		map.put("schoolId", classStatistics.getSchoolId());
		map.put("schoolStageId", classStatistics.getSchoolStageId());
		map.put("statisticsTimeStart", classStatistics.getStatisticsTimeStart());
		map.put("statisticsTimeEnd", classStatistics.getStatisticsTimeEnd());

		// 作业数及课件数
		List<ClassStatistics> listWorkAndCoursewareNum = classStatisticsDao.getTeacherWorkAndCoursewareNum(map);
		if (listWorkAndCoursewareNum != null && listWorkAndCoursewareNum.size() > 0)
		{
			ClassStatistics workAndCoursewareNum = listWorkAndCoursewareNum.get(0);

			classStatistics.setPreviewWorkNum(workAndCoursewareNum.getPreviewWorkNum());
			classStatistics.setCoursewareNum(workAndCoursewareNum.getCoursewareNum());
			classStatistics.setClassWorkNum(workAndCoursewareNum.getClassWorkNum());
			classStatistics.setAfterClassWorkNum(workAndCoursewareNum.getAfterClassWorkNum());
			classStatistics.setBeiPreviewWorkNum(workAndCoursewareNum.getBeiPreviewWorkNum());
			classStatistics.setBeiCoursewareNum(workAndCoursewareNum.getBeiCoursewareNum());
			classStatistics.setBeiClassWorkNum(workAndCoursewareNum.getBeiClassWorkNum());

			if (!classStatistics.getBeikeClassNum().equals(0))
			{
				String previewWorkRate = String.format("%.2f",
						(Double.valueOf(workAndCoursewareNum.getBeiPreviewWorkNum())
								/ Double.valueOf(classStatistics.getBeikeClassNum())) * 100);

				classStatistics.setPreviewWorkRate(previewWorkRate + "%");

				String coursewareRate = String.format("%.2f",
						(Double.valueOf(workAndCoursewareNum.getBeiCoursewareNum())
								/ Double.valueOf(classStatistics.getBeikeClassNum())) * 100);
				classStatistics.setCoursewareRate(coursewareRate + "%");

				String classWorkRate = String.format("%.2f", (Double.valueOf(workAndCoursewareNum.getBeiClassWorkNum())
						/ Double.valueOf(classStatistics.getBeikeClassNum())) * 100);
				classStatistics.setClassWorkRate(classWorkRate + "%");
			} else
			{
				classStatistics.setPreviewWorkRate("0.00%");
				classStatistics.setCoursewareRate("0.00%");
				classStatistics.setClassWorkRate("0.00%");
			}
		} else
		{
			classStatistics.setPreviewWorkRate("0.00%");
			classStatistics.setCoursewareRate("0.00%");
			classStatistics.setClassWorkRate("0.00%");
		}

		// 测试次数及问答次数
		List<ClassStatistics> listClassTestAndAskNum = classStatisticsDao.getTeacherClassTestAndAskNum(map);
		if (listClassTestAndAskNum != null && listClassTestAndAskNum.size() > 0)
		{
			ClassStatistics classTestAndAskNum = listClassTestAndAskNum.get(0);

			classStatistics.setClassTestNum(classTestAndAskNum.getClassTestNum());
			classStatistics.setQuestionsAnswersNum(classTestAndAskNum.getQuestionsAnswersNum());

			if (!classStatistics.getClassNum().equals(0))
			{
				String classTestRate = String.format("%.2f", (Double.valueOf(classTestAndAskNum.getTestAttenNum())
						/ Double.valueOf(classStatistics.getClassNum())) * 100);

				classStatistics.setClassTestRate(classTestRate + "%");

				String questionsAnswersRate = String.format("%.2f",
						(Double.valueOf(classTestAndAskNum.getAskAttenNum())
								/ Double.valueOf(classStatistics.getClassNum())) * 100);
				classStatistics.setQuestionsAnswersRate(questionsAnswersRate + "%");
			} else
			{
				classStatistics.setClassTestRate("0.00%");
				classStatistics.setQuestionsAnswersRate("0.00%");
			}
		} else
		{
			classStatistics.setClassTestRate("0.00%");
			classStatistics.setQuestionsAnswersRate("0.00%");
		}

		// 课后数据
		List<ClassStatistics> listEndClassData = classStatisticsDao.getTeacherEndClassData(map);
		if (listEndClassData != null && listEndClassData.size() > 0)
		{
			ClassStatistics endClassData = listEndClassData.get(0);

			classStatistics.setClassWorkTotalNum(endClassData.getClassWorkTotalNum());
			classStatistics.setWorkFinishNum(endClassData.getWorkFinishNum());
			classStatistics.setWorkcorrectNum(endClassData.getWorkcorrectNum());

			if (!endClassData.getClassWorkTotalNum().equals(0))
			{
				String workFinishRate = String.format("%.2f", (Double.valueOf(endClassData.getWorkFinishNum())
						/ Double.valueOf(endClassData.getClassWorkTotalNum())) * 100);
				classStatistics.setWorkFinishRate(workFinishRate + "%");
			} else
			{
				classStatistics.setWorkFinishRate("0.00%");
			}

			if (!endClassData.getWorkFinishNum().equals(0))
			{
				String workcorrectRate = String.format("%.2f", (Double.valueOf(endClassData.getWorkcorrectNum())
						/ Double.valueOf(endClassData.getWorkFinishNum())) * 100);
				classStatistics.setWorkcorrectRate(workcorrectRate + "%");
			} else
			{
				classStatistics.setWorkcorrectRate("0.00%");
			}

		} else
		{
			classStatistics.setWorkFinishRate("0.00%");
			classStatistics.setWorkcorrectRate("0.00%");
		}

		return classStatistics;
	}

	/**
	 * @Description: 获取导出数据源
	 * @param classStatistics
	 * @return ClassStatistics
	 * @throws
	 */
	@Override
	public List<ClassStatistics> getTeacherExportData(ClassStatistics classStatistics)
	{
		List<ClassStatistics> listClassStatistics = classStatisticsDao.queryTeacherSchool(classStatistics, null);

		if (listClassStatistics == null || listClassStatistics.size() <= 0)
		{
			return Collections.emptyList();
		}

		// 学科ID
		List<Long> subjectId = ListUtils.map(listClassStatistics, e ->
		{
			return e.getSubjectId();
		});

		// 年级ID
		List<Long> gradeId = ListUtils.map(listClassStatistics, e ->
		{
			return e.getGradeId();
		});

		// 老师ID
		List<Long> teacherId = ListUtils.map(listClassStatistics, e ->
		{
			return e.getTeacherId();
		});

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjectId", subjectId);
		map.put("gradeId", gradeId);
		map.put("teacherId", teacherId);
		map.put("schoolId", classStatistics.getSchoolId());
		map.put("schoolStageId", classStatistics.getSchoolStageId());
		map.put("statisticsTimeStart", classStatistics.getStatisticsTimeStart());
		map.put("statisticsTimeEnd", classStatistics.getStatisticsTimeEnd());

		// 已结束课堂数
		List<Map<String, Object>> listEndClassNum = classStatisticsDao.getTeacherEndClassNum(map);

		// 备课课堂数
		List<Map<String, Object>> listBeikeNum = classStatisticsDao.getTeacherBeikeClassNum(map);

		// 上课课堂数
		List<Map<String, Object>> listClassNum = classStatisticsDao.getTeacherClassNum(map);

		// 作业数及课件数
		List<ClassStatistics> listWorkAndCoursewareNum = classStatisticsDao.getTeacherWorkAndCoursewareNum(map);

		// 测试次数及问答次数
		List<ClassStatistics> listClassTestAndAskNum = classStatisticsDao.getTeacherClassTestAndAskNum(map);

		// 课后数据
		List<ClassStatistics> listEndClassData = classStatisticsDao.getTeacherEndClassData(map);

		for (ClassStatistics item : listClassStatistics)
		{
			item.setEndClassNum(getTeacherClassNum(item, listEndClassNum));
			item.setBeikeClassNum(getTeacherClassNum(item, listBeikeNum));
			item.setClassNum(getTeacherClassNum(item, listClassNum));

			if (!item.getEndClassNum().equals(0))
			{
				String beikRate = String.format("%.2f",
						(Double.valueOf(item.getBeikeClassNum()) / Double.valueOf(item.getEndClassNum())) * 100);
				String classRate = String.format("%.2f",
						(Double.valueOf(item.getClassNum()) / Double.valueOf(item.getEndClassNum())) * 100);

				item.setBeikeRate(beikRate + "%");
				item.setClassRate(classRate + "%");
			} else
			{
				item.setBeikeRate("0.00%");
				item.setClassRate("0.00%");
			}

			// 作业数及课件数
			Boolean existenceWork = true;
			for (ClassStatistics workAndCoursewareNum : listWorkAndCoursewareNum)
			{
				if (item.getTeacherId().equals(workAndCoursewareNum.getTeacherId())
						&& item.getSubjectId().equals(workAndCoursewareNum.getSubjectId())
						&& item.getGradeId().equals(workAndCoursewareNum.getGradeId()))
				{
					existenceWork = false;
					item.setPreviewWorkNum(workAndCoursewareNum.getPreviewWorkNum());
					item.setCoursewareNum(workAndCoursewareNum.getCoursewareNum());
					item.setClassWorkNum(workAndCoursewareNum.getClassWorkNum());
					item.setAfterClassWorkNum(workAndCoursewareNum.getAfterClassWorkNum());
					item.setBeiPreviewWorkNum(workAndCoursewareNum.getBeiPreviewWorkNum());
					item.setBeiCoursewareNum(workAndCoursewareNum.getBeiCoursewareNum());
					item.setBeiClassWorkNum(workAndCoursewareNum.getBeiClassWorkNum());

					if (!item.getBeikeClassNum().equals(0))
					{
						String previewWorkRate = String.format("%.2f",
								(Double.valueOf(workAndCoursewareNum.getBeiPreviewWorkNum())
										/ Double.valueOf(item.getBeikeClassNum())) * 100);

						item.setPreviewWorkRate(previewWorkRate + "%");

						String coursewareRate = String.format("%.2f",
								(Double.valueOf(workAndCoursewareNum.getBeiCoursewareNum())
										/ Double.valueOf(item.getBeikeClassNum())) * 100);
						item.setCoursewareRate(coursewareRate + "%");

						String classWorkRate = String.format("%.2f",
								(Double.valueOf(workAndCoursewareNum.getBeiClassWorkNum())
										/ Double.valueOf(item.getBeikeClassNum())) * 100);
						item.setClassWorkRate(classWorkRate + "%");
					} else
					{
						item.setPreviewWorkRate("0.00%");
						item.setCoursewareRate("0.00%");
						item.setClassWorkRate("0.00%");
					}
				}
			}

			if (existenceWork)
			{
				item.setPreviewWorkNum(0);
				item.setCoursewareNum(0);
				item.setClassWorkNum(0);
				item.setAfterClassWorkNum(0);
				item.setPreviewWorkRate("0.00%");
				item.setCoursewareRate("0.00%");
				item.setClassWorkRate("0.00%");
			}

			// 测试次数及问答次数
			Boolean existenceAsk = true;
			for (ClassStatistics classTestAndAskNum : listClassTestAndAskNum)
			{
				if (item.getTeacherId().equals(classTestAndAskNum.getTeacherId())
						&& item.getSubjectId().equals(classTestAndAskNum.getSubjectId())
						&& item.getGradeId().equals(classTestAndAskNum.getGradeId()))
				{
					existenceAsk = false;
					
					item.setClassTestNum(classTestAndAskNum.getClassTestNum());
					item.setQuestionsAnswersNum(classTestAndAskNum.getQuestionsAnswersNum());

					if (!item.getClassNum().equals(0))
					{
						String classTestRate = String.format("%.2f",
								(Double.valueOf(classTestAndAskNum.getTestAttenNum())
										/ Double.valueOf(item.getClassNum())) * 100);

						item.setClassTestRate(classTestRate + "%");

						String questionsAnswersRate = String.format("%.2f",
								(Double.valueOf(classTestAndAskNum.getAskAttenNum())
										/ Double.valueOf(item.getClassNum())) * 100);
						item.setQuestionsAnswersRate(questionsAnswersRate + "%");
					} else
					{
						item.setClassTestRate("0.00%");
						item.setQuestionsAnswersRate("0.00%");
					}
				}
			}

			if (existenceAsk)
			{
				item.setClassTestNum(0);
				item.setClassTestRate("0.00%");
				item.setQuestionsAnswersNum(0);
				item.setQuestionsAnswersRate("0.00%");
			}

			// 课后数据
			Boolean existenceEndClass = true;
			for (ClassStatistics endClassData : listEndClassData)
			{
				if (item.getTeacherId().equals(endClassData.getTeacherId())
						&& item.getSubjectId().equals(endClassData.getSubjectId())
						&& item.getGradeId().equals(endClassData.getGradeId()))
				{
					existenceEndClass = false;
					item.setClassWorkTotalNum(endClassData.getClassWorkTotalNum());
					item.setWorkFinishNum(endClassData.getWorkFinishNum());
					item.setWorkcorrectNum(endClassData.getWorkcorrectNum());

					if (!endClassData.getClassWorkTotalNum().equals(0))
					{
						String workFinishRate = String.format("%.2f", (Double.valueOf(endClassData.getWorkFinishNum())
								/ Double.valueOf(endClassData.getClassWorkTotalNum())) * 100);
						item.setWorkFinishRate(workFinishRate + "%");
					} else
					{
						item.setWorkFinishRate("0.00%");
					}

					if (!endClassData.getWorkFinishNum().equals(0))
					{
						String workcorrectRate = String.format("%.2f", (Double.valueOf(endClassData.getWorkcorrectNum())
								/ Double.valueOf(endClassData.getWorkFinishNum())) * 100);
						item.setWorkcorrectRate(workcorrectRate + "%");
					} else
					{
						item.setWorkcorrectRate("0.00%");
					}
				}
			}

			if (existenceEndClass)
			{
				item.setClassWorkTotalNum(0);
				item.setWorkFinishNum(0);
				item.setWorkcorrectNum(0);
				item.setWorkFinishRate("0.00%");
				item.setWorkcorrectRate("0.00%");
			}
		}

		return listClassStatistics;
	}

	/**
	 * @Description: 查询年级
	 * @param subjectId（学科ID）
	 * @param gradeId（年级ID）
	 * @return ClassStatistics
	 * @throws
	 */
	public List<Map<String, Object>> getGrade(Long schoolId, Long schoolStageId)
	{
		return classStatisticsDao.getGrade(schoolId, schoolStageId);
	}

	/**
	 * @Description: 查询学科
	 * @param subjectId（学科ID）
	 * @param gradeId（年级ID）
	 * @return ClassStatistics
	 * @throws
	 */
	public List<Map<String, Object>> getSubject(Long schoolId, Long schoolStageId)
	{
		return classStatisticsDao.getSubject(schoolId, schoolStageId);
	}
}
