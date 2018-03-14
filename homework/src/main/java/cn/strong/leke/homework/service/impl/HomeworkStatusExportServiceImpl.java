package cn.strong.leke.homework.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.homework.dao.mybatis.HomeworkStatusExportDao;
import cn.strong.leke.homework.model.ExportStatus;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.query.ClassHomeworkQuery;
import cn.strong.leke.homework.service.HomeworkStatusExportService;

@Service
public class HomeworkStatusExportServiceImpl implements HomeworkStatusExportService{

	@Resource
	private HomeworkStatusExportDao homeworkStatusExportDao;
	
	@Override
	public List<Map<String, Object>> getStatusData(ClassHomeworkQuery query, boolean export) {
		if (CollectionUtils.isEmpty(query.getClassId())) {
			throw new ValidateException("请选择班级");
		}
		Integer count = homeworkStatusExportDao.countExportStatus(query);
		if (count == null || count == 0) {
			if (!export) {
				throw new ValidateException("没有数据");
			}
			return null;
		}
		if (!export && count > 100000) {
			throw new ValidateException("数据过多，无法导出");
		}
		if(query.getCloseTime() != null) {
			query.setCloseTime(DateUtils.addDays(query.getCloseTime(), 1));
		}
		List<ExportStatus> list = homeworkStatusExportDao.findExportStatus(query);
		Map<Long, List<ExportStatus>> a = list.stream().collect(Collectors.groupingBy(v -> v.getStudentId()));
		List<Map<String, Object>> items = a.values().stream().map(l -> {
			Map<String, Object> item = new HashMap<>();
			item.put(STUDENT_NAME, l.get(0).getStudentName());
			l.forEach(v -> {
				item.put(String.valueOf(v.getHomeworkId()), getStatus(v.getSubmitStatus()));
			});
			return item;
		}).collect(Collectors.toList());
		return items;
	}

	@Override
	public List<Homework> getHomeWork(ClassHomeworkQuery query ) {
		return homeworkStatusExportDao.findHomeWorkByClassId(query);
	}
	
	private String getStatus(Integer status){
		if(status == null){
			return "未提交";
		}
		String statusStr = null;
		switch (status) {
		case 0:
			statusStr = "未提交";
			break;
		case 1:
			statusStr = "正常提交";
			break;
		case 2:
			statusStr = "延迟提交";
			break;

		default:
			statusStr = "正常提交";
			break;
		}
		return statusStr;
	}
}
