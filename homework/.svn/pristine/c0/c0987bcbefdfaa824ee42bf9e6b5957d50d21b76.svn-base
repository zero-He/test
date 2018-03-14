package cn.strong.leke.homework.manage;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.homework.dao.mongo.HwWrongStudentDao;
import cn.strong.leke.homework.model.HwWrongStudent;

/**
 * 作业单题错误学生
 * @author Zhang Fujun
 * @date 2017年5月9日
 */
@Component
public class HwWrongStudentService {

	@Resource
	private HwWrongStudentDao hwWrongStudentDao;


	/**
	 * 批量批改查询，只查询作业中的一题
	 * @param homeworkDtlId
	 * @param questionId
	 * @return
	 */
	public void insertHwWrongStudent(HwWrongStudent hwWrongStudent) {
		this.hwWrongStudentDao.saveHwWrongStudent(hwWrongStudent);
	}

	/**
	 * 根据老师作业ID查询作业中试题的批改数量
	 * @param homeworkId 老师作业ID
	 * @return
	 */
	public List<Long> findWrongStudent(Long homeworkId, Long questionId) {
		return this.hwWrongStudentDao.findWrongStudent(homeworkId, questionId);
	}
}
