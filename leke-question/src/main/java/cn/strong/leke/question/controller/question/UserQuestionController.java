package cn.strong.leke.question.controller.question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.user.ResearcherContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.incentive.Award;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionFeedbackDTO;
import cn.strong.leke.model.question.querys.RepositoryQuestionQuery;
import cn.strong.leke.model.repository.QuestionRepositoryRecord;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.repository.ShareScopes;
import cn.strong.leke.model.user.Researcher;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.model.QuestionShareEvent;
import cn.strong.leke.question.model.question.query.QuestionShareLogQuery;
import cn.strong.leke.question.service.IQuestionShareLogService;
import cn.strong.leke.question.service.QuestionManager;
import cn.strong.leke.question.service.QuestionService;
import cn.strong.leke.repository.common.cmd.store.IPersonalRepoHandler;

@Controller
@RequestMapping("/auth/common/question/")
public class UserQuestionController {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private IQuestionShareLogService questionShareLogService;
	@Autowired
	@Qualifier("personalQuestionHandler")
	private IPersonalRepoHandler personalRepoHandler;
	@Resource
	@Qualifier("questionShareSender")
	private MessageSender questionShareSender;

	@RequestMapping("personal/details")
	public void personalDetails(RepositoryQuestionQuery query, Page page, Model model) {
		query.setUserId(UserContext.user.getUserId());
		List<RepositoryRecord> records = new ArrayList<>();
		if(query.getShareScope()==ShareScopes.PERSONAL){
			records = questionService.queryPersonalQuestions(query, page);
		} else if (query.getShareScope() == ShareScopes.FAVORITE) {
			records = questionService.queryFavoriteQuestions(query, page);
		} else {
			records = Collections.emptyList();
		}
		model.addAttribute("records", records);
		model.addAttribute("page", page);
		model.addAttribute("userResGroupId", query.getUserResGroupId());
		model.addAttribute("shareScope", query.getShareScope());
	}

	@RequestMapping("share/details")
	public void shareDetails(QuestionShareLogQuery query, Page page, Model model) {
		query.setUserId(UserContext.user.getUserId());
		Long userId = UserContext.user.getUserId();
		query.setUserId(userId);
		List<QuestionRepositoryRecord> records = questionShareLogService.queryQuestionShareInfo(query, page);
		model.addAttribute("records", records);
		model.addAttribute("page", page);

	}

	@RequestMapping("getQuestionShareInfo")
	@ResponseBody
	public JsonResult getQuestionShareInfo(Long questionId) {
		JsonResult jResult = new JsonResult();
		User user = UserContext.user.get();
		jResult.addDatas("info", questionManager.getQuestionShareInfo(questionId, user));
		return jResult;
	}

	@RequestMapping("doShare")
	@ResponseBody
	public JsonResult doShare(String dataJson) {
		JsonResult json = new JsonResult();
		QuestionShareEvent event = JsonUtils.fromJSON(dataJson, QuestionShareEvent.class);
		Long questionId = event.getQuestionId();
		User user = UserContext.user.get();
		Long userId = user.getId();
		checkIsQuestionCreator(questionId, userId);
		Award award = questionManager.updateQuestionShare(event, user);
		questionShareSender.send(questionId);
		if (award != null && award.getSucc() && award.getHave()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("leke", award.getLekeVal());
			map.put("exp", award.getExpVal());
			json.addDatas("tips", map);
		}
		return json;
	}

	@RequestMapping("doDisable")
	@ResponseBody
	public JsonResult doDisable(Long questionId) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		Long userId = user.getId();
		checkIsQuestionCreator(questionId, userId);
		QuestionDTO dto = new QuestionDTO();
		dto.setQuestionId(questionId);
		dto.setModifiedBy(user.getId());
		dto.setOperatorName(user.getUserName());
		questionManager.disableQuestion(dto, user);
		return json;
	}

	@RequestMapping("doRemove")
	@ResponseBody
	public JsonResult doRemove(Long questionId) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		Long userId = user.getId();
		checkIsQuestionCreator(questionId, userId);
		personalRepoHandler.remove(questionId, userId, user);
		return json;
	}

	@RequestMapping("/add/index")
	public void questionAddIndex(Long questionId, Model model) {
		if(questionId != null){
			QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
			question.setQuestionId(null);
			model.addAttribute("initJson", JsonUtils.toJSON(question));
		} else {
			model.addAttribute("initJson", "{}");
		}
	}

	@RequestMapping(value = "questionEdit")
	public void questionEditPage(Long questionId,
			@RequestParam(name = "crossDomain", defaultValue = "false") Boolean crossDomain,
			Model model) {
		QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
		if (crossDomain == null) {
			crossDomain = false;
		}
		model.addAttribute("initJson", JsonUtils.toJSON(question));
		model.addAttribute("crossDomain", crossDomain);
	}

	@RequestMapping("remoteAdd")
	public void remoteAddPage(QuestionDTO question,
			@RequestParam(name = "copyAdd", defaultValue = "false") Boolean copyAdd, Model model) {
		Long questionId = question.getQuestionId();
		if (questionId != null) {
			question = QuestionContext.getQuestionDTO(questionId);
		}
		model.addAttribute("initJson", JsonUtils.toJSON(question));
		model.addAttribute("copyAdd", copyAdd);
	}

	private void checkIsQuestionCreator(Long questionId, Long userId) {
		QuestionDTO old = QuestionContext.getQuestionDTO(questionId);
		if (old == null) {
			throw new ValidateException("que.question.not.exist");
		}
		if (!userId.equals(old.getCreatedBy())) {
			throw new ValidateException("que.warn.permission.denied");
		}
	}
	
	@RequestMapping("feedback/details")
	public void feedbackdata(QuestionShareLogQuery query, Page page, Model model) {
		Long userId = UserContext.user.getUserId();
		query.setUserId(userId);
		Researcher researcher = ResearcherContext.researcher.get();
		QuestionFeedbackDTO questionFeedbackDTO = new QuestionFeedbackDTO();
		List<QuestionRepositoryRecord> records = questionManager.queryAllFeedbackQuestionRecord(questionFeedbackDTO, researcher, page);

		model.addAttribute("records", records);
		model.addAttribute("page", page);
	}
}
