package cn.strong.leke.homework.util;

public interface HomeworkCst {

	/**
	 * 作业数据(答题、批改)来源：1 - Web
	 */
	int HOMEWORK_DATA_SOURCE_WEBSITE = 1;

	/**
	 * 作业数据(答题、批改)来源：2 - Class
	 */
	int HOMEWORK_DATA_SOURCE_CLASS = 2;

	/**
	 * 作业数据(答题、批改)来源：3 - Pad
	 */
	int HOMEWORK_DATA_SOURCE_PAD = 3;

	/**
	 * 作业数据(答题、批改)来源：4 - Sheet
	 */
	int HOMEWORK_DATA_SOURCE_SHEET = 4;

	/**
	 * 作业数据(答题、批改)来源：4 - Mobile
	 */
	int HOMEWORK_DATA_SOURCE_MOBILE = 5;

	/**
	 * 移动端提交类型：1-保存作业
	 */
	int API_MOBILE_SUBMIT_TYPE_SAVE = 1;
	/**
	 * 移动端提交类型：2-提交作业
	 */
	int API_MOBILE_SUBMIT_TYPE_SUBMIT = 2;

	/** 提交状态：0 - 未提交 */
	int HOMEWORK_SUBMIT_STATUS_NOT = 0;
	/** 提交状态：1 - 正常提交 */
	int HOMEWORK_SUBMIT_STATUS_NORMAL = 1;
	/** 提交状态：2 - 延迟提交 */
	int HOMEWORK_SUBMIT_STATUS_DELAY = 2;
	
	/** 订正阶段：0 - 无需订正 */
	int HOMEWORK_BUGFIX_STAGE_NORMAL = 0;
	/** 订正阶段：1 - 订正中 */
	int HOMEWORK_BUGFIX_STAGE_BUGFIX = 1;
	/** 订正阶段：2 - 复批中 */
	int HOMEWORK_BUGFIX_STAGE_REVIEW = 2;
	/** 订正阶段：3 - 结束 */
	int HOMEWORK_BUGFIX_STAGE_FINISH = 3;

	/** 作业分析数据状态 */
	/** 未统计 */
	int HOMEWORK_STATS_STATUS_NOTSTAT = 0;
	/** 统计中 */
	int HOMEWORK_STATS_STATUS_STATING = 1;
	/** 已统计 */
	int HOMEWORK_STATS_STATUS_FINISH = 2;
	
	/* 周月作业类型*/
	int HOMEWORK_WRONG_WEEK = 1;
	int HOMEWORK_WRONG_MONTH = 2;
	
	
	/* 作业资源类型  */
	/**
	 * 作业资源类型：课件
	 */
	int HOMEWORK_RES_COURSEWARE = 1;
	/**
	 * 作业资源类型：微课
	 */
	int HOMEWORK_RES_MICROCOURSE = 2;
	/**
	 * 作业资源类型：试卷
	 */
	int HOMEWORK_RES_PAPER = 3;
	
	/**
	 * 资源使用阶段 ：预习
	 */
	int HOMEWORK_USE_PHASE_BEFORE = 1;
	/**
	 * 资源使用阶段 ：课堂
	 */
	int HOMEWORK_USE_PHASE_LESSON = 2;
	/**
	 * 资源使用阶段 ：课后
	 */
	int HOMEWORK_USE_PHASE_AFTER = 3;
	/**
	 * 暑假作业
	 */
	int SUMMER_VACATION_HOMEWORK = 2;
	/**
	 * 寒假作业
	 */
	int WINTER_VACATION_HOMEWORK = 1;

	/**
	 * 作业作废消息提醒
	 * 老师已作废【寒假作业】。
	 */
	public static final String HOMWORK_INVALID_TEMPLATE = "HOMWORK_INVALID_TEMPLATE";
	
	/**
	 * 批改结果消息提醒
	 * 【${1}】作业已经批改完成。
	 */
	public static final String HOMWORK_CORRECTING_TEMPLATE = "HOMWORK_CORRECTING_TEMPLATE";
	
	/**
	 * 作业满分消息提醒
	 * 机智如你！恭喜你在【${homeworkName}】中获得满分！
	 */
	public static final String HOMWORK_FULLSCORE_TEMPLATE = "HOMWORK_FULLSCORE_TEMPLATE";
	
	/**
	 * 作业自行校对
	 * 老师要求自行校对【${homeworkName}】，请自行进行校对。 
	 */
	public static final String HOMWORK_SELF_CHECK_TEMPLATE = "HOMWORK_SELF_CHECK_TEMPLATE";

	/**
	 * 作业公布答案
	 * 老师已公布【homeworkName】的答案，请注意查看。
	 */
	public static final String HOMWORK_OPEN_ANSWER_TEMPLATE = "HOMWORK_OPEN_ANSWER_TEMPLATE";
	
	/**
	 * 作业通过订正
	 * 知错能改，善莫大焉。你的【${homeworkName}】已通过订正！ 
	 */
	public static final String HOMWORK_REVIEW_PASSED_TEMPLATE = "HOMWORK_REVIEW_PASSED_TEMPLATE";
	/**
	 * 作业截止时间修改
	 * 【${homeworkName}】作业截止时间已更改为${closeTime}，请注意作业截止时间。

	 */
	public static final String HOMWORK_MODIFY_CLOSE_TIME_TEMPLATE = "HOMWORK_MODIFY_CLOSE_TIME_TEMPLATE";
	/**
	 * 乐课网已向【${1}】学生推送了【${2}】作业，您可以在全部作业中查看！
	 */
	public static final String HOMEWORK_WEEK_MONTH_TEMPLETE = "HOMEWORK_WEEK_MONTH_TEMPLETE";

	/**
	 * 催交寒暑假作业
	 * 老师喊你交【${vacation}】作业啦!
	 */
	public static final String HOMWORK_CALL_VACATION_HOMEWORKS = "HOMWORK_CALL_VACATION_HOMEWORKS";

	/**
	 * 在线考试提醒
	 * 【${teacher}】老师在【${time}】安排了一场考试，请提前做好准备!
	 */
	public static final String ONLINE_EXAM_REMIND = "ONLINE_EXAM_REMIND";

	/**
	 *在线考试提醒
	 * 【${teacher}】老师安排的一场考试，将于【${minute}】分钟后开考，请提前在平板上准备好!
	 */
	public static final String AUTO_ONLINE_EXAM_REMIND = "AUTO_ONLINE_EXAM_REMIND";


	//寒暑假消息模板
	//乐课网已向你推送${1}作业，请在寒/暑假期间认真完成~
	public static final String HOMEWORK__HOLIDAY_STU_TEMPLETE = "HOMEWORK__HOLIDAY_STU_TEMPLETE";
	//乐课网已向您的孩子推送${1}作业，您可以在寒暑假作业中查看孩子的完成情况~
	public static final String HOMEWORK__HOLIDAY_TEACHER_TEMPLETE = "HOMEWORK__HOLIDAY_TEACHER_TEMPLETE";
	//乐课网已向您的孩子推送${1}作业，您可以在寒暑假作业中查看孩子的完成情况~
	public static final String HOMEWORK__HOLIDAY_TEACHER_HEAD_TEMPLETE = "HOMEWORK__HOLIDAY_TEACHER_HEAD_TEMPLETE";
	//乐课网已向学生推送${1}作业，您可以在寒暑假作业中查看学生的完成情况~
	public static final String HOMEWORK__HOLIDAY_PARENTS_TEMPLETE = "HOMEWORK__HOLIDAY_PARENTS_TEMPLETE";
	//你的【${1}】作业还未提交，老师喊你交作业啦~
	public static final String HOMEWORK__CUIJIAO_TEMPLETE = "HOMEWORK__CUIJIAO_TEMPLETE";
}
