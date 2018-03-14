package cn.strong.leke.homework.service;


public interface IJobService {

	/**
	* 生成周作业
	*/
	void excuteWeekHomework();

	/**
	 * 生成月作业
	 */
	void excuteMonthHomework();
	
	/**
	 * 推送 老师错题本
	 */
	void excuteWrontQuestion();

	/**
	 * 催交寒暑假作业
	 */
	void excuteCallVacationHomework();

	/**
	 * 在线考试提醒
	 */
	void excuteOnlineExam();
}
