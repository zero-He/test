/**
 * 
 */
package cn.strong.leke.question.core.question.cmd.store.impl;

import static cn.strong.leke.common.utils.CollectionUtils.isNotEmpty;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.model.base.SchoolCst;
import cn.strong.leke.model.question.Analysis;
import cn.strong.leke.model.question.Answer;
import cn.strong.leke.model.question.Question;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionKnowledge;
import cn.strong.leke.model.question.QuestionOfficialTag;
import cn.strong.leke.model.question.QuestionSection;
import cn.strong.leke.model.question.QuestionStem;
import cn.strong.leke.model.question.SchoolQuestionOutlineNode;
import cn.strong.leke.model.repository.RepoCsts;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.question.cmd.store.IQuestionAddHandler;
import cn.strong.leke.question.core.question.cmd.store.IQuestionUpdateHandler;
import cn.strong.leke.question.dao.mybatis.AnalysisDao;
import cn.strong.leke.question.dao.mybatis.AnswerDao;
import cn.strong.leke.question.dao.mybatis.QuestionDao;
import cn.strong.leke.question.dao.mybatis.QuestionKnowledgeDao;
import cn.strong.leke.question.dao.mybatis.QuestionOfficialTagDao;
import cn.strong.leke.question.dao.mybatis.QuestionStemDao;
import cn.strong.leke.question.dao.mybatis.question.IQuestionEditLogDao;
import cn.strong.leke.question.dao.mybatis.question.IQuestionSectionDao;
import cn.strong.leke.question.dao.mybatis.question.ISchoolQuestionOutlineNodeDao;
import cn.strong.leke.question.model.question.QuestionEditLog;
import cn.strong.leke.question.service.IQueImageProcessor;
import cn.strong.leke.question.util.QuestionSendMsgContext;
import cn.strong.leke.repository.common.model.RepoSaveContext;

/**
 * @author liulongbiao
 *
 */
@Service
public class QuestionAddHandler implements IQuestionAddHandler {

	@Resource
	private QuestionDao questionDao;
	@Resource
	private QuestionStemDao questionStemDao;
	@Resource
	private AnswerDao answerDao;
	@Resource
	private AnalysisDao analysisDao;
	@Resource
	private IQuestionSectionDao questionSectionDao;
	@Resource
	private QuestionKnowledgeDao questionKnowledgeDao;
	@Resource
	private QuestionOfficialTagDao questionOfficialTagDao;
	@Resource
	private IQueImageProcessor queImageProcessor;
	@Resource
	private ISchoolQuestionOutlineNodeDao schoolQuestionOutlineNodeDao;
	@Resource
	private IQuestionUpdateHandler questionUpdateHandler;
	@Resource
	private IQuestionEditLogDao questionEditLogDao;

	@Override
	@Transactional
	public Long add(QuestionDTO data, User user, RepoSaveContext<QuestionDTO> ctx) {
		Long userId = user.getId();
		data.setModifiedBy(userId);
		if (isUpadate(user, ctx)) {
			update(data, user);
		} else {
			Long oldId = ctx.getOldId();
			if (data.getSourceId() == null && oldId != null) {
				data.setSourceId(oldId);
			}
			insertQuestion(data);
			if (oldId != null) {
				copyOutline(user, oldId, data.getQuestionId(), ctx.getAction());
				QuestionSendMsgContext.editQuestion(oldId, user);
			}

		}
		saveQuestionContents(data);
		saveQuestionRelations(data);
		Long questionId = data.getQuestionId();
		QuestionContext.deleteCache(questionId);
		return questionId;
	}

	private boolean isUpadate(User user, RepoSaveContext<QuestionDTO> ctx) {
		if (ctx.getOldId() == null) {
			return false;
		}
		Long userId = user.getId();
		Long roleId = user.getCurrentRole().getId();
		QuestionDTO backend = QuestionContext.getQuestionDTO(ctx.getOldId());
		String action = ctx.getAction();
		if (roleId.equals(RoleCst.RESEARCHER)
				&& (RepoCsts.SaveActions.CORRECT.equals(action) || RepoCsts.SaveActions.CHECK_EDIT.equals(action))) {
			return true;
		}
		if (backend == null) {
			return false;
		}
		if (roleId.equals(RoleCst.RESEARCHER) && backend.getSchoolId() != null
				&& SchoolCst.LEKE == backend.getSchoolId()) {
			ctx.setOldId(null);// 为了教研员修改不从精品库移除
			return true;
		}
		Long createdBy = backend.getCreatedBy();
		if (!userId.equals(createdBy) && RepoCsts.SaveActions.CREATE.equals(action)) {
			return false;
		}
		if (userId.equals(createdBy)) {
			ctx.setOldId(null);// 修改不从相应库移除
			return true;
		}
		return false;
	}

	private Long update(QuestionDTO question, User user) {
		Long questionId = question.getQuestionId();
		questionUpdateHandler.updateQuestionContent(question, user);
		addLog(questionId, user);
		return questionId;
	}

	private void saveQuestionContents(QuestionDTO dto) {
		Long questionId = dto.getQuestionId();
		queImageProcessor.processBase64Images(dto);
		questionStemDao.deleteQuestionStemByQuestionId(questionId);
		analysisDao.deleteAnalysisByQuestionId(questionId);
		QuestionStem stem = dto.getStem();
		if (stem != null) {
			stem.setQuestionId(questionId);
			questionStemDao.insertQuestionStem(stem);
		}
		saveAnswers(dto);
		Analysis analysis = dto.getAnalysis();
		if (analysis != null) {
			analysis.setQuestionId(questionId);
			analysisDao.insertAnalysis(analysis);
		}
		if (dto.getHasSub()) {
			saveSubs(dto);
		}
	}

	private void saveSubs(QuestionDTO dto){
		List<QuestionDTO> subs = dto.getSubs();
		Long userId = dto.getCreatedBy();
		if (!CollectionUtils.isEmpty(subs)) {
			Long questionId = dto.getQuestionId();
			Long schoolId = dto.getSchoolId();
			List<QuestionDTO> oldBubs = questionDao.findSubQuestions(questionId);
			int index = 0;
			for (; index < subs.size(); index++) {
				QuestionDTO sub = subs.get(index);
				if (oldBubs.size() > index) {
					QuestionDTO old = oldBubs.get(index);
					old.setSchoolId(schoolId);
					sub.setQuestionId(old.getQuestionId());
					sub.setOrd(index);
					sub.setSchoolStageId(dto.getSchoolStageId());
					sub.setSubjectId(dto.getSubjectId());
					sub.setParentId(questionId);
					sub.setCreatorName(dto.getCreatorName());
					sub.setSchoolName(dto.getSchoolName());
					questionDao.updateQuestion(sub);
				} else {
					sub.setSchoolId(schoolId);
					sub.setCreatedBy(userId);
					sub.setModifiedBy(userId);
					sub.setOrd(index);
					sub.setSchoolStageId(dto.getSchoolStageId());
					sub.setSubjectId(dto.getSubjectId());
					sub.setParentId(questionId);
					sub.setCreatorName(dto.getCreatorName());
					sub.setSchoolName(dto.getSchoolName());
					insertQuestion(sub);
				}
				saveQuestionContents(sub);
			}
			for (; index < oldBubs.size(); index++) {
				QuestionDTO old = oldBubs.get(index);
				questionDao.deleteQuestion(old);
			}
		}
		
	}

	private void saveAnswers(QuestionDTO dto) {
		Long questionId = dto.getQuestionId();
		List<Answer> answers = dto.getAnswers();
		List<Answer> answers2 = answerDao.findAnswersByQuestionId(questionId);
		if (CollectionUtils.isNotEmpty(answers2)) {
			int index = 0;
			for (; index < answers.size(); index++) {
				Answer answer = answers.get(index);
				answer.setOrd(index);
				answer.setQuestionId(questionId);
				if (answers2.size() > index) {
					Answer answer2 = answers2.get(index);
					answer.setAnswerId(answer2.getAnswerId());
					answerDao.updateAnswer(answer);
				} else {
					answerDao.insertAnswer(answer);
				}
			}
			for (; index < answers2.size(); index++) {
				answerDao.deleteAnswer(answers2.get(index).getAnswerId());
			}

		} else {
			for (int i = 0; i < answers.size(); i++) {
				Answer answer = answers.get(i);
				answer.setOrd(i);
				answer.setQuestionId(questionId);
				answerDao.insertAnswer(answer);
			}
		}
	}

	private void saveQuestionRelations(QuestionDTO dto) {
		if (dto == null || dto.getQuestionId() == null) {
			return;
		}
		Long questionId = dto.getQuestionId();
		Long userId = dto.getModifiedBy();
		List<QuestionSection> sections = dto.getSections();
		questionSectionDao.deleteQuestionSections(questionId, userId);
		if (isNotEmpty(sections)) {
			for (QuestionSection section : sections) {
				section.setQuestionId(questionId);
				section.setCreatedBy(userId);
				section.setModifiedBy(userId);
				questionSectionDao.insertQuestionSection(section);
			}
		}
		List<QuestionKnowledge> knowledges = dto.getKnowledges();
		questionKnowledgeDao.deleteQuestionKnowledgeByQuestionId(questionId);
		if (isNotEmpty(knowledges)) {
			for (QuestionKnowledge knowledge : knowledges) {
				knowledge.setQuestionId(questionId);
				knowledge.setCreatedBy(userId);
				knowledge.setModifiedBy(userId);
				questionKnowledgeDao.insertQuestionKnowledge(knowledge);
			}
		}
		List<QuestionOfficialTag> tags = dto.getTags();
		questionOfficialTagDao.deleteQuestionOfficialTagByQuestionId(questionId);
		if (isNotEmpty(tags)) {
			for (QuestionOfficialTag tag : tags) {
				tag.setQuestionId(questionId);
				tag.setCreatedBy(userId);
				tag.setModifiedBy(userId);
				questionOfficialTagDao.insertQuestionOfficialTag(tag);
			}
		}
	}

	private void insertQuestion(QuestionDTO data) {
		data.setQuestionStatus(Question.QUE_STATUS_INPUT);
		if (data.getHasSub() == null) {
			data.setHasSub(false);
		}
		if (data.getOrd() == null) {
			data.setOrd(0);
		}
		if (data.getDifficulty() == null) {
			data.setDifficulty(0.5);
		}

		data.setSharePersonal(false);
		data.setShareSchool(false);
		data.setSharePlatform(false);
		questionDao.insertQuestion(data);
	}

	private void copyOutline(User user, Long oldId, Long newId, String action) {
		if (action.equals(RepoCsts.SaveActions.OVERRIDE)) {
			SchoolQuestionOutlineNode olnode = schoolQuestionOutlineNodeDao.findOutlineNodeByQuestionId(oldId);
			if (olnode != null) {
				olnode.setQuestionId(newId);
				schoolQuestionOutlineNodeDao.add(olnode);
			}
		}

	}

	private void addLog(Long questionId, User user) {
		QuestionEditLog questionEditLog = new QuestionEditLog();
		Long userId = user.getId();
		questionEditLog.setCreatedBy(userId);
		questionEditLog.setQuestionId(questionId);
		questionEditLogDao.add(questionEditLog);
	}

}
