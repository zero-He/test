package cn.strong.leke.homework.service;

import java.util.Date;




/**
 * 老师推送错题本
 * @author Zhang Fujun
 * @date 2017年1月10日
 */
public interface HomeworkForWrongService {
	
	/**
	 * 查询符合条件的错题作业
	 * 条件：
	 * 截止时间+24小时
	 * 作业批改率大于80%
	 * @return
	 */
	public void excuteWrontQuestion();
	/**
	 * 查询符合条件的错题作业
	 * 条件：
	 * 截止时间+72小时
	 * 作业批改率大于80%
	 * @return
	 */
	public void executeWrongQuestion(Date start, Date end);
}
