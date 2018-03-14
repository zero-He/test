package cn.strong.leke.homework.controller.work;

import cn.strong.leke.context.paper.PaperContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.HomeworkDtl;
import cn.strong.leke.homework.model.WebWorkModel;
import cn.strong.leke.homework.util.HomeworkUtils;
import cn.strong.leke.model.paper.PaperDTO;
import cn.strong.leke.model.user.User;
import cn.strong.leke.tags.answer.ResDataModel;

public class WorkSupport {

	/**
	 * 获取试卷信息
	 * @param homework
	 * @param homeworkDtl
	 * @return
	 */
	public static PaperDTO getOrBuildPaperDTO(Homework homework, HomeworkDtl homeworkDtl) {
		if (homework.getPaperId() != null) {
			return PaperContext.getPaperDTO(homework.getPaperId());
		} else {
			return HomeworkUtils.buildPaperDTO(homeworkDtl.getPaperId(), homework.getHomeworkName(),
					homework.getSubjectName(), homework.getSubjectId());
		}
	}

	/**
	 * 构造试卷显示的学生数据结构
	 */
	public static ResDataModel buildResDataModel(User user, String workMode, Homework homework,
			HomeworkDtl homeworkDtl) {
		ResDataModel resDataModel = new ResDataModel();
		resDataModel.setWorkMode(workMode);
		resDataModel.setRoleId(user.getCurrentRole().getId());
		resDataModel.setUserName(homeworkDtl.getStudentName());
		if (!ResDataModel.DO_ANSWER.equals(workMode)) {
			resDataModel.setUsedTime(convertUsedTimeToMinute(homeworkDtl.getUsedTime()));
		}
		resDataModel.setScore(homeworkDtl.getScore());
		resDataModel.setRemark(homeworkDtl.getSoundFile());
		if (RoleCst.STUDENT.equals(user.getCurrentRole().getId())) {
			if (ResDataModel.DO_ANSWER.equals(workMode) || ResDataModel.DO_BUGFIX.equals(workMode)) {
				resDataModel.setShowAnswer(false);
			} else if (ResDataModel.DO_CORRECT.equals(workMode)) {
				resDataModel.setShowAnswer(true);
			} else {
				resDataModel.setShowAnswer(homework.getIsOpenAnswer());
			}
		} else {
			resDataModel.setShowAnswer(true);
		}
		resDataModel.setWorkStage(resolveWorkStage(homeworkDtl));
		resDataModel.setIsSelfCheck(homework.getIsSelfCheck());
		if (ResDataModel.DO_ANSWER.equals(workMode) || ResDataModel.DO_ANSWER.equals(resDataModel.getWorkStage())) {
			resDataModel.setUsedTime(null);
		}
		return resDataModel;
	}

	private static Integer convertUsedTimeToMinute(Integer usedTime) {
		if (usedTime == null || usedTime < 60) {
			return 1;
		}
		return usedTime / 60;
	}

	/**
	 * 确定作业当前所处的阶段。
	 * 阶段：none, answer, correct, bugfix, review, finish.
	 * @param homeworkDtl
	 * @return
	 */
	private static String resolveWorkStage(HomeworkDtl homeworkDtl) {
		if (homeworkDtl.getSubmitTime() == null) {
			return ResDataModel.DO_ANSWER;
		}
		if (homeworkDtl.getBugFixStage() == null || homeworkDtl.getBugFixStage() == 0) {
			if (homeworkDtl.getCorrectTime() != null) {
				return ResDataModel.DO_FINISH;
			} else {
				return ResDataModel.DO_CORRECT;
			}
		}
		if (homeworkDtl.getBugFixStage() == 1) {
			return ResDataModel.DO_BUGFIX;
		} else if (homeworkDtl.getBugFixStage() == 2) {
			return ResDataModel.DO_REVIEW;
		}
		return ResDataModel.DO_FINISH;
	}

	/**
	 * 构造Web端作业信息数据结构
	 */
	public static WebWorkModel buildWebWorkModel(ResDataModel respond, Homework homework, HomeworkDtl homeworkDtl) {
		WebWorkModel webWorkModel = new WebWorkModel();
		webWorkModel.setWorkMode(respond.getWorkMode());
		webWorkModel.setWorkStage(respond.getWorkStage());
		webWorkModel.setHomeworkId(homework.getHomeworkId());
		webWorkModel.setHomeworkType(homework.getHomeworkType());
		webWorkModel.setSubjectId(homework.getSubjectId());
		webWorkModel.setTeacherId(homework.getTeacherId());
		webWorkModel.setHomeworkName(homework.getHomeworkName());
		webWorkModel.setHomeworkDtlId(homeworkDtl.getHomeworkDtlId());
		webWorkModel.setSoundFile(homeworkDtl.getSoundFile());
		webWorkModel.setIsSubmit(homeworkDtl.getSubmitTime() != null);
		webWorkModel.setIsCorrect(homeworkDtl.getCorrectTime() != null);
		webWorkModel.setPaperId(homework.getPaperId());
		return webWorkModel;
	}

	/**
	 * 构造全局笔记参数串
	 */
	public static String buildGlobalNoteParams(Homework homework, HomeworkDtl homeworkDtl) {
		StringBuilder builder = new StringBuilder();
		builder.append("type=1");
		builder.append("&id=").append(homeworkDtl.getHomeworkDtlId());
		builder.append("&name=").append(homework.getHomeworkName());
		builder.append("&subjectId=").append(homework.getSubjectId());
		return builder.toString();
	}
}
