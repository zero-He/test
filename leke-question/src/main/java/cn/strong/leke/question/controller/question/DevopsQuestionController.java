/**
 * 
 */
package cn.strong.leke.question.controller.question;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.question.analysis.core.service.IMatNodeKlgService;
import cn.strong.leke.question.dao.mybatis.QuestionDao;

/**
 * @author liulongbiao
 *
 */
@Controller
@RequestMapping("/auth/devops/question")
public class DevopsQuestionController {
	private static Logger LOG = LoggerFactory.getLogger(DevopsQuestionController.class);
	static final int BATCH_SIZE = 10000;

	static final String SQL_MAX_QID = "SELECT MAX(questionId) FROM que_question";
	static final String SQL_FIND_BIGQUE = "SELECT questionId FROM que_question WHERE parentId IS NULL AND isDeleted = 0 AND hasSub = 1 AND questionId > ? AND questionId <= ?";
	static final String SQL_COUNT_SUBJECTIVE_SUBS = "SELECT COUNT(*) FROM que_question WHERE parentId = ? AND isDeleted = 0 AND subjective = 1";
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private QuestionDao questionDao;
	@Resource
	private IMatNodeKlgService matNodeKlgService;

	@RequestMapping("doFixSubjective")
	@ResponseBody
	public JsonResult doFixSubjective() {
		LOG.info("start fix subjective...");
		Long maxQid = jdbcTemplate.queryForObject(SQL_MAX_QID, Long.class);
		long batchs = maxQid / BATCH_SIZE + 1;
		int modified = 0;
		for (long i = 0; i < batchs; i++) {
			long start = i * BATCH_SIZE;
			modified += doFixSubjective(start, start + BATCH_SIZE);
		}
		JsonResult json = new JsonResult();
		json.addDatas("modified", modified);
		LOG.info("totally fixed subjective of " + modified + " records.");
		return json;
	}

	@RequestMapping("doBatchFixSubjective")
	@ResponseBody
	public JsonResult doBatchFixSubjective(long start, long end) {
		int modified = doFixSubjective(start, end);
		JsonResult json = new JsonResult();
		json.addDatas("modified", modified);
		LOG.info("totally fixed subjective of " + modified + " records.");
		return json;
	}

	private int doFixSubjective(long start, long end) {
		LOG.info("start fix subjective with id from " + start + " to " + end);
		List<Long> qids = jdbcTemplate.queryForList(SQL_FIND_BIGQUE, new Object[] { start, end }, Long.class);
		int modified = 0;
		for (Long qid : qids) {
			try {
				modified += tryFixSubjective(qid);
			} catch (Exception e) {
				LOG.debug("try fix subjective of question " + qid + " has error", e);
			}
		}
		LOG.info("fixed subjective of " + modified + " records with id from " + start + " to " + end);
		return modified;
	}

	private int tryFixSubjective(Long qid) {
		LOG.debug("try fix subjective of question " + qid);
		QuestionDTO que = questionDao.getQuestion(qid);
		Validation.notNull(que, "cannot find question " + qid);
		int count = jdbcTemplate.queryForObject(SQL_COUNT_SUBJECTIVE_SUBS, new Object[] { qid }, Integer.class);
		boolean newSubj = count > 0;
		Boolean oldSubj = que.getSubjective();
		if (oldSubj == null || !oldSubj.equals(newSubj)) {
			// 更新数据库
			questionDao.updateSubjective(qid, newSubj);
			// 更新缓存
			QuestionDTO cache = QuestionContext.getQuestionDTO(qid);
			if (cache != null && !cache.getSubjective().equals(newSubj)) {
				cache.setSubjective(newSubj);
				QuestionContext.updateQuestionToCache(cache);
			}
			return 1;
		}
		return 0;
	}

	@RequestMapping("startCountMatNodeKlgs")
	@ResponseBody
	public JsonResult startCountMatNodeKlgs() {
		CompletableFuture.runAsync(() -> {
			matNodeKlgService.startCount();
		});
		return new JsonResult();
	}
}
