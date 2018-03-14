package cn.strong.leke.homework.manage;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.homework.dao.mongo.HwQueCommentaryDao;
import cn.strong.leke.homework.dao.mongo.WorkDetailDao;
import cn.strong.leke.homework.model.WorkDetail;
import cn.strong.leke.homework.model.mongo.HwQueCommentary;
import cn.strong.leke.model.question.CorrectComment;
import cn.strong.leke.model.question.QuestionResult;

/**
 * 作业题目批注service
 * @author Zhang Fujun
 * @date 2017年12月4日
 */
@Component
public class HwQueCommentaryService {

	@Resource
	private HwQueCommentaryDao hwQueCommentaryDao;
	@Resource
	private  WorkDetailDao workDetailDao;
	
	/**
	 * 新增 作业题目批注
	 * @param commentary
	 */
	public void saveCommentary(HwQueCommentary commentary) {
		commentary.setId(new ObjectId().toHexString());
		commentary.setIsDeleted(false);
		commentary.setCreatedOn(new Date());
		HwQueCommentary db_commentary = this.getSingle(commentary.getHomeworkId(), commentary.getQuestionId(),
				commentary.getResId());
		if (db_commentary == null) {
			hwQueCommentaryDao.saveCommentary(commentary);
		} else {
			if (commentary.getType().equals(HwQueCommentary.WAY_PERSONAL)) {
				db_commentary.getIncludeUserIds().addAll(commentary.getIncludeUserIds());
				db_commentary.getExcludeUserIds().removeAll(commentary.getIncludeUserIds());
			} else {
				db_commentary.setType(commentary.getType());
				db_commentary.setExcludeUserIds(Collections.emptySet());
			}
			hwQueCommentaryDao.updateCommentary(db_commentary);
		}
	}

	/**
	 * 撤销推送 
	 * @param homeworkId
	 * @param questionId
	 */
	public void removeCommentary(Long homeworkId, Long questionId, Long resId, Long modifiedBy, Long excludeUserId) {
		this.hwQueCommentaryDao.removeCommentary(homeworkId, questionId, resId, modifiedBy);
		this.hwQueCommentaryDao.pushIncludeUserIds(homeworkId, questionId, resId, modifiedBy, excludeUserId);
	}

	/**
	 * 移除 单个学生微课批注推送
	 * @param homeworkId
	 * @param questionId
	 */
	public void removeCommentaryForStu(Long homeworkId, Long questionId, Long resId, Long stuId, Long modifiedBy) {
		HwQueCommentary commentary = this.getSingle(homeworkId, questionId, resId);
		commentary.getIncludeUserIds().remove(stuId);
		if (!commentary.getType().equals(HwQueCommentary.WAY_PERSONAL)) {
			commentary.getExcludeUserIds().add(stuId);
		}
		this.hwQueCommentaryDao.updateCommentary(commentary);
	}

	/**
	 * 获取 一个作业题目批注
	 * @param homeworkId
	 * @param questionId
	 * @return
	 */
	public HwQueCommentary getSingle(Long homeworkId, Long questionId, Long resId) {
		return this.hwQueCommentaryDao.getSingle(homeworkId, questionId, resId);
	}

	/**
	 * 获取 某个作业题目的所有批注
	 * @param homeworkId
	 * @param questionId 可为null
	 * @return
	 */
	public List<HwQueCommentary> findCommentaries(Long homeworkId, Long questionId, Long studentId) {
		List<HwQueCommentary> commentaries = this.hwQueCommentaryDao.findCommentaries(homeworkId, questionId);
		commentaries = commentaries
				.stream()
				.filter(v -> v.getType().equals(HwQueCommentary.WAY_PERSONAL)
						&& v.getIncludeUserIds().contains(studentId)
						|| !v.getType().equals(HwQueCommentary.WAY_PERSONAL)
						&& !v.getExcludeUserIds().contains(studentId)).collect(toList());
		if (questionId != null && commentaries.stream().anyMatch(v -> v.getType().equals(HwQueCommentary.WAY_PART_WRONG))) {
			Boolean isWrongQuestion = workDetailDao.isWrongQuestion(homeworkId, studentId, questionId);
			//错题推送时，移除不能看的微课批注
			commentaries.removeIf(v -> v.getType().equals(HwQueCommentary.WAY_PART_WRONG) && !isWrongQuestion &&  !v.getIncludeUserIds().contains(studentId));
		}
		return commentaries;
	}
	
	/**
	 * 将 微课批注信息转化为 HwQueCommentary，且移除该微课批注数据
	 * @param workDetail
	 * @return
	 */
	public List<HwQueCommentary> convertToCommentary(WorkDetail workDetail){
		List<HwQueCommentary> commentaries = new ArrayList<>();
		for (QuestionResult questionResult : workDetail.getQuestions()) {
			 if(questionResult.getCorrectComments() != null) {
				List<HwQueCommentary> singleCommentaries = questionResult.getCorrectComments().stream().filter(v->v.getType().equals("micro")).map(v->{
					 HwQueCommentary item = new HwQueCommentary();
						item.setCreatedBy(workDetail.getCreatedBy());
						item.setHomeworkId(workDetail.getHomeworkId());
						item.setQuestionId(questionResult.getQuestionId());
						item.setType(HwQueCommentary.WAY_PERSONAL);
						item.setResId( Long.parseLong(v.getLink().substring(v.getLink().lastIndexOf("="))));
						item.setResName(v.getText());
						return item;
				 }).collect(toList());
				commentaries.addAll(singleCommentaries);
				questionResult.getCorrectComments().removeIf(v->v.getType().equals("micro"));
			 }
		}
		return commentaries;
		
	}
	/**
	 *  新老微课数据合并
	 * @param workDetail
	 */
	@Deprecated
	public void mergeWorkDetailForVodMic(WorkDetail workDetail){
		if (workDetail == null) {
			return;
		}
		List<HwQueCommentary> commentaries = this.findCommentaries(workDetail.getHomeworkId(), null, workDetail.getStudentId());
		fillQuesComments(workDetail.getQuestions(), commentaries, workDetail.getStudentId());
	}

	/**
	 * @param workDetail
	 * @param commentaries
	 */
	private void fillQuesComments(List<QuestionResult> questionResults, List<HwQueCommentary> commentaries, Long studentId) {
		for (QuestionResult queResult : questionResults) {
			if(CollectionUtils.isNotEmpty(queResult.getSubs())) {
				this.fillQuesComments(queResult.getSubs(), commentaries, studentId);
			} else {
				setSingleQuesComments(commentaries, queResult, studentId);
			}
		}
	}
	
	/**
	 * 移除 微课批注信息
	 * @param questionResults
	 */
	public static void removeMicroComments(List<QuestionResult> questionResults) {
		questionResults.forEach(v->{
			if(CollectionUtils.isNotEmpty(v.getCorrectComments())) {
				v.getCorrectComments().removeIf(c->c.getType().equals("micro"));
			}
		});
	}

	/**
	 * @param commentaries
	 * @param queResult
	 */
	private void setSingleQuesComments(List<HwQueCommentary> commentaries, QuestionResult queResult, Long studentId) {
		//新存储的微课批注
		List<HwQueCommentary> filterCommentaries = new ArrayList<>();
		for (HwQueCommentary item : commentaries) {
			if(isHasMicro(queResult, studentId, item)) {
				filterCommentaries.add(item);
			}
		}
		if(CollectionUtils.isEmpty(filterCommentaries)) {
			return;
		}
		List<CorrectComment> comments = convertCorrectComment(filterCommentaries, queResult);

		//排除重复的
		if (queResult.getCorrectComments() != null) {
			comments.removeIf(c -> queResult.getCorrectComments().stream()
					.anyMatch(v -> v.getType().equals("micro") && v.getLink().equals(c.getLink())));
			queResult.getCorrectComments().addAll(comments);
		} else {
			queResult.setCorrectComments(comments);
		}
	}

	/**
	 * @param queResult
	 * @param studentId
	 * @param item
	 */
	private boolean isHasMicro(QuestionResult queResult, Long studentId, HwQueCommentary item) {
		boolean isHave = false;
		if(item.getQuestionId().equals(queResult.getQuestionId())) {
			if(item.getType().equals(HwQueCommentary.WAY_PART_WRONG)) {
				isHave = Boolean.FALSE.equals(queResult.getTotalIsRight()) 
						|| Boolean.TRUE.equals(queResult.getTotalIsRight()) && item.getIncludeUserIds().contains(studentId);
			} else if(item.getType().equals(HwQueCommentary.WAY_PERSONAL)) {
				isHave = item.getIncludeUserIds().contains(studentId);
			} else {
				isHave = true;
			}
		}
		return isHave;
	}

	/**
	 * @param commentaries
	 * @param queResult
	 * @return
	 */
	private List<CorrectComment> convertCorrectComment(List<HwQueCommentary> commentaries, QuestionResult queResult) {
		return commentaries
				.stream().map(w -> {
					CorrectComment item = new CorrectComment();
					item.setLink("https://beike.leke.cn/auth/common/microcourse/preview.htm?microcourseId="
							+ w.getResId());
					item.setText(w.getResName());
					item.setType("micro");
					return item;
				}).collect(toList());
	}
}
