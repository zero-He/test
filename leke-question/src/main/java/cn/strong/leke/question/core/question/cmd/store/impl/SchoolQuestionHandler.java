/**
 * 
 */
package cn.strong.leke.question.core.question.cmd.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.question.SchoolQuestionOutlineNode;
import cn.strong.leke.model.repository.RepoCsts.CheckStatus;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.question.cmd.store.ISchoolQuestionHandler;
import cn.strong.leke.question.dao.mybatis.QuestionDao;
import cn.strong.leke.question.dao.mybatis.question.IQuestionShareLogDao;
import cn.strong.leke.question.dao.mybatis.question.ISchoolQuestionCheckLogDao;
import cn.strong.leke.question.dao.mybatis.question.ISchoolQuestionDao;
import cn.strong.leke.question.dao.mybatis.question.ISchoolQuestionOutlineNodeDao;
import cn.strong.leke.question.model.question.SchoolQuestion;
import cn.strong.leke.question.model.question.SchoolQuestionCheckLog;
import cn.strong.leke.repository.common.utils.RepoUtils;

/**
 * @author liulongbiao
 *
 */
@Service
public class SchoolQuestionHandler implements ISchoolQuestionHandler {

	private static final String ERR_NOT_EXIST = "que.question.not.exist";
	@Resource
	private ISchoolQuestionDao schoolQuestionDao;
	@Resource
	private ISchoolQuestionCheckLogDao checkLogDao;
	@Resource
	private IQuestionShareLogDao shareLogDao;
	@Resource
	private ISchoolQuestionOutlineNodeDao schoolQuestionOutlineNodeDao;
	@Resource
	private QuestionDao questionDao;

	@Override
	@Transactional
	public void add(Long questionId, Long schoolId, int addType, int status, String editLog, User user) {
		SchoolQuestion assoc = new SchoolQuestion();
		assoc.setQuestionId(questionId);
		assoc.setSchoolId(schoolId);
		assoc.setAddType(addType);
		assoc.setStatus(status);
		assoc.setEditLog(editLog);
		Long userId = user.getId();
		assoc.setCreatedBy(userId);
		assoc.setModifiedBy(userId);

		schoolQuestionDao.add(assoc);
		questionDao.addToSchoolShare(questionId, userId);
		QuestionContext.deleteCache(questionId);
	}

	@Override
	@Transactional
	public int remove(Long questionId, Long schoolId, User user) {
		SchoolQuestion assoc = new SchoolQuestion();
		assoc.setQuestionId(questionId);
		assoc.setSchoolId(schoolId);
		assoc.setModifiedBy(user.getId());
		return schoolQuestionDao.remove(assoc);
	}

	@Override
	@Transactional
	public void override(Long oldId, Long newId, Long schoolId, User user) {
		if (oldId == null) {
			return;
		}
		SchoolQuestion old = schoolQuestionDao.getByQuestionAndSchool(oldId, schoolId);
		Validation.notNull(old, ERR_NOT_EXIST);
		remove(oldId, schoolId, user);
		String editLog = RepoUtils.getEditLog(user);
		add(newId, schoolId, old.getAddType(), CheckStatus.INIT, editLog, user);
		tryCopyOutlineNodeRelation(oldId, newId, schoolId, user);
	}

	@Override
	@Transactional
	public void checkEdit(Long oldId, Long newId, Long schoolId, User user) {
		SchoolQuestion old = schoolQuestionDao.getByQuestionAndSchool(oldId, schoolId);
		Validation.notNull(old, ERR_NOT_EXIST);

		String editLog = RepoUtils.getEditLog(user);

		updateStatus(oldId, schoolId, CheckStatus.EDITED, editLog, user);
		remove(oldId, schoolId, user);
		add(newId, schoolId, old.getAddType(), CheckStatus.CHECKED, editLog, user);
		tryCopyOutlineNodeRelation(oldId, newId, schoolId, user);

		Long checkLogId = addCheckLog(oldId, schoolId, old.getStatus(), CheckStatus.EDITED,
				editLog, user);
		shareLogDao.updateSchoolCheckLogId(oldId, schoolId, checkLogId, user.getId());
	}

	private void updateStatus(Long questionId, Long schoolId, int status, String editLog, User user) {
		SchoolQuestion update = new SchoolQuestion();
		update.setQuestionId(questionId);
		update.setSchoolId(schoolId);
		update.setStatus(status);
		update.setEditLog(editLog);
		update.setModifiedBy(user.getId());
		schoolQuestionDao.updateStatus(update);
	}

	private Long addCheckLog(Long questionId, Long schoolId, int fromStatus, int toStatus,
			String checkNote, User user) {
		SchoolQuestionCheckLog log = new SchoolQuestionCheckLog();
		log.setQuestionId(questionId);
		log.setSchoolId(schoolId);
		log.setFromStatus(fromStatus);
		log.setToStatus(toStatus);
		log.setCheckNote(checkNote);
		log.setCheckedUser(user.getUserName());
		log.setCreatedBy(user.getId());
		checkLogDao.add(log);
		return log.getCheckLogId();
	}

	private void tryCopyOutlineNodeRelation(Long oldId, Long newId, Long schoolId, User user) {
		SchoolQuestionOutlineNode old = schoolQuestionOutlineNodeDao
				.findOutlineNodeByQuestionId(oldId);
		if (old != null && schoolId.equals(old.getSchoolId())) {
			SchoolQuestionOutlineNode s = new SchoolQuestionOutlineNode();
			s.setQuestionId(newId);
			s.setSchoolId(schoolId);
			s.setOutlineId(old.getOutlineId());
			s.setOutlineNodeId(old.getOutlineNodeId());
			s.setPath(old.getPath());
			Long userId = user.getId();
			s.setCreatedBy(userId);
			s.setModifiedBy(userId);
			schoolQuestionOutlineNodeDao.add(s);
		}
	}

	@Override
	@Transactional
	public int checkPass(Long questionId, Long schoolId, User user) {
		SchoolQuestion record = schoolQuestionDao.getByQuestionAndSchool(questionId, schoolId);
		if (record == null) {
			return 0;
		}
		updateStatus(questionId, schoolId, CheckStatus.CHECKED, null, user);
		Long checkLogId = addCheckLog(questionId, schoolId, record.getStatus(), CheckStatus.CHECKED,
				null, user);
		shareLogDao.updateSchoolCheckLogId(questionId, schoolId, checkLogId, user.getId());
		return 1;
	}

	@Override
	@Transactional
	public int checkReject(Long questionId, Long schoolId, String checkNote, User user) {
		SchoolQuestion record = schoolQuestionDao.getByQuestionAndSchool(questionId, schoolId);
		if (record == null) {
			return 0;
		}

		updateStatus(questionId, schoolId, CheckStatus.REJECTED, checkNote, user);
		// remove(questionId, schoolId, user);

		Long checkLogId = addCheckLog(questionId, schoolId, record.getStatus(), CheckStatus.REJECTED,
				checkNote, user);
		shareLogDao.updateSchoolCheckLogId(questionId, schoolId, checkLogId, user.getId());
		return 1;
	}

	@Override
	public boolean contains(Long resId, Long schoolId) {
		if (resId == null) {
			return false;
		}
		SchoolQuestion old = schoolQuestionDao.getByQuestionAndSchool(resId, schoolId);
		return old != null;
	}

	@Override
	@Transactional
	public void updateOutlineNode(SchoolQuestionOutlineNode olnode, User user) {
		Validation.isTrue(
				olnode != null && olnode.getQuestionId() != null
						&& olnode.getOutlineNodeId() != null, "传入的章节信息不完整");
		Long userId = user.getId();
		schoolQuestionOutlineNodeDao.delete(olnode.getQuestionId(), userId);
		olnode.setModifiedBy(userId);
		olnode.setCreatedBy(userId);
		olnode.setSchoolId(user.getCurrentSchool().getId());
		schoolQuestionOutlineNodeDao.add(olnode);
	}

}
