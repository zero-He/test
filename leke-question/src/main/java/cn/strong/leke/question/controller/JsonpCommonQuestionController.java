/*
\ * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.controller;

import static cn.strong.leke.core.business.web.JSONP.jsonp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.common.utils.json.JSON;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.model.incentive.Award;
import cn.strong.leke.model.paper.QuestionGroup;
import cn.strong.leke.model.paper.ScoredQuestion;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionFeedbackDTO;
import cn.strong.leke.model.question.QuestionKnowledge;
import cn.strong.leke.model.question.QuestionPraise;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.analysis.core.model.SmartPaperCriteria;
import cn.strong.leke.question.analysis.core.model.SmartPaperGenerateResult;
import cn.strong.leke.question.analysis.core.service.ISmartPaperQuestionService;
import cn.strong.leke.question.core.question.query.IFavoriteQuestionQueryService;
import cn.strong.leke.question.core.question.query.IQuestionQueryService;
import cn.strong.leke.question.model.question.TeacherQuestion;
import cn.strong.leke.question.service.IQuestionFeedbackService;
import cn.strong.leke.question.service.IQuestionPraiseService;
import cn.strong.leke.question.service.TeacherQuestionService;
import cn.strong.leke.question.util.QuestionIndexContext;
import cn.strong.leke.repository.common.cmd.store.ILekeBoutiqueRepoHandler;
import cn.strong.leke.repository.common.cmd.store.ILekeShareRepoHandler;

import com.fasterxml.jackson.databind.util.JSONPObject;

/**
 * JSONP 通用用户习题控制器
 * 
 * @author liulongbiao
 */
@Controller
@RequestMapping("/auth/common/question/jsonp")
public class JsonpCommonQuestionController {
	@Resource
	private IQuestionPraiseService questionPraiseService;
	@Resource
	private TeacherQuestionService teacherQuestionService;
	@Resource(name = "lekeShareQuestionHandler")
	private ILekeShareRepoHandler lekeShareQuestionHandler;
	@Resource(name = "lekeBoutiqueQuestionHandler")
	private ILekeBoutiqueRepoHandler lekeBoutiqueQuestionHandler;
	@Resource(name = "questionShareSender")
	private MessageSender questionShareSender;
	@Resource
	private IFavoriteQuestionQueryService favoriteQuestionQueryService;
	@Resource
	private IQuestionQueryService questionQueryService;
	@Resource
	private IQuestionFeedbackService questionFeedbackService;
	@Resource
	private ISmartPaperQuestionService smartPaperQuestionService;

	@RequestMapping("doPraise")
	@ResponseBody
	public JSONPObject doPraise(final Long questionId, String callback) {
		return jsonp(callback, (json) -> {
			Long teacherId = UserContext.user.getUserId();
			QuestionPraise praise = new QuestionPraise();
			praise.setQuestionId(questionId);
			praise.setCreatedBy(teacherId);
			questionPraiseService.addQuestionPraise(praise);
			questionShareSender.send(questionId);
		});
	}
	
	@ResponseBody
	@RequestMapping("doFeedbackQuestion")
	public JSONPObject doFeedbackQuestion(final String dataJson, String callback) {
		return jsonp(callback, (json) -> {
			if (StringUtils.isEmpty(dataJson)) {
				throw new ValidateException("que.question.tag.not.exist");
			}
			QuestionFeedbackDTO qf = JsonUtils.fromJSON(dataJson, QuestionFeedbackDTO.class);
			User user = UserContext.user.get();
			questionFeedbackService.addQuestionFeedback(qf, user);
		});
	}

	public static class QueCartType {
		private List<Long> questionIds;
		private Long questionTypeId;

		public Long getQuestionTypeId() {
			return questionTypeId;
		}

		public void setQuestionTypeId(Long questionTypeId) {
			this.questionTypeId = questionTypeId;
		}

		public List<Long> getQuestionIds() {
			return questionIds;
		}

		public void setQuestionIds(List<Long> questionIds) {
			this.questionIds = questionIds;
		}
	}
	
	@ResponseBody
	@RequestMapping("queryQuestionsByType")
	public JSONPObject queryQuestionsByType(final String dataJson, String callback) {
		return jsonp(callback, (json) -> {
			if (StringUtils.isEmpty(dataJson)) {
				throw new ValidateException("que.question.tag.not.exist");
			}
			
			QueCartType options = JsonUtils.fromJSON(dataJson, QueCartType.class);
			List<Long> questionList = new ArrayList<Long>();
			List<QuestionDTO> questions = QuestionContext.findQuestions(options.getQuestionIds());
			for(QuestionDTO question : questions){
				if(question.getQuestionTypeId() == options.getQuestionTypeId()){
					questionList.add(question.getQuestionId());
				}
			}
			
			json.addDatas("questionList", questionList);
		});
	}
	
	@ResponseBody
	@RequestMapping("queryQuestionDetails")
	public JSONPObject queryQuestionDetails(final String dataJson, String callback) {
		return jsonp(callback, (json) -> {
			if (StringUtils.isEmpty(dataJson)) {
				throw new ValidateException("que.question.tag.not.exist");
			}
			//增加过滤条件防止空值
			List<ScoredQuestion> sqList = JsonUtils.readList(dataJson, ScoredQuestion.class);
			//可优化到直接扔入数据库,并用hashmap传下来,用questionDTo的形式传下来直接查，不打开
			List<Long> questionIds = new ArrayList<Long>();
			Map<String, BigDecimal> questionMap = new HashMap<>();
			
			for (ScoredQuestion sq: sqList){
				questionIds.add(sq.getQuestionId());
				questionMap.put(sq.getQuestionId().toString(), sq.getScore());
			}
			
			List<Map<String, Object>> difficultyList = new ArrayList<Map<String, Object>>();
			Map<String, Object> difficultyMap = new HashMap<>();
			
			List<QuestionKnowledge> knowledges = new ArrayList<QuestionKnowledge>();
			List<QuestionDTO> questions = QuestionContext.findQuestions(questionIds);
			for (QuestionDTO que : questions){
				knowledges.addAll(que.getKnowledges());
				Long keyQue = (long) Math.ceil(que.getDifficulty() * 5);
				if (difficultyMap.containsKey(keyQue.toString()) == false){
					difficultyMap.put(keyQue.toString(), 1);
				}else{
					difficultyMap.put(keyQue.toString(), (int)difficultyMap.get(keyQue.toString()) + 1);
				}
			}
			
			for(Map.Entry<String, Object> entry:difficultyMap.entrySet()){ 
				Map<String, Object> difficultyDataMap = new HashMap<>();
				Integer count = (Integer) entry.getValue();
				//difficultyDataMap.put("name", actMap.get(entry.getKey()));
				difficultyDataMap.put("name", entry.getKey());
				difficultyDataMap.put("quantity", count);
				//studentMap.put("score", qGroup.getScore());
				difficultyList.add(difficultyDataMap);	
			}
			Map<String, Object> knowledgesNoDuplicate = new HashMap<>();
			//数据库中查到的去重后的
			
			for (QuestionKnowledge qKnowledge : knowledges) {
				String knowledgeId = qKnowledge.getKnowledgeId().toString();
				if (knowledgesNoDuplicate.containsKey(knowledgeId) == false){
					QuestionGroup qGroup = new QuestionGroup();
					qGroup.setQuestionTypeId(qKnowledge.getKnowledgeId());
					qGroup.setGroupTitle(qKnowledge.getPath());
					qGroup.setQuestionNum(1);
					qGroup.setScore(questionMap.get(qKnowledge.getQuestionId().toString()));
					knowledgesNoDuplicate.put(knowledgeId, qGroup);
				}else{
					QuestionGroup qGroup = (QuestionGroup) knowledgesNoDuplicate.get(knowledgeId);
					BigDecimal tScore = qGroup.getScore();
					qGroup.setQuestionNum(qGroup.getQuestionNum() + 1);
					BigDecimal nScore = questionMap.get(qKnowledge.getQuestionId().toString());
					qGroup.setScore(nScore.add(tScore));
					knowledgesNoDuplicate.replace(knowledgeId, qGroup);
				}
			}
			
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for(Map.Entry<String, Object> entry:knowledgesNoDuplicate.entrySet()){
				//转化好的类型应该转换到问题组里去而非questionKnowledge
				QuestionGroup qGroup = (QuestionGroup) entry.getValue();
				Map<String, Object> studentMap = new HashMap<>();
				studentMap.put("knowledgeName", qGroup.getGroupTitle());
				studentMap.put("quantity", qGroup.getQuestionNum());
				studentMap.put("score", qGroup.getScore());
				resultList.add(studentMap);	  
			}   
			
			json.addDatas("knowledges", resultList);
			json.addDatas("difficultyList", difficultyList);
		});
	}

	@RequestMapping("doFavorite")
	@ResponseBody
	public JSONPObject doFavorite(final Long questionId, String callback) {
		return jsonp(callback, (json) -> {
			User user = UserContext.user.get();
			Long teacherId = user.getId();
			TeacherQuestion fav = new TeacherQuestion();
			fav.setQuestionId(questionId);
			fav.setTeacherId(teacherId);
			fav.setCreatedBy(teacherId);
			fav.setModifiedBy(teacherId);
			Award award = teacherQuestionService.addTeacherQuestion(fav, user);
			json.addDatas("award", award);
		});
	}

	@RequestMapping("doBatchFavorite")
	@ResponseBody
	public JSONPObject doBatchFavorite(final Long[] questionIds, String callback) {
		return jsonp(callback, (json) -> {
			User user = UserContext.user.get();
			Award award = teacherQuestionService.addBatchTeacherQuestion(questionIds, user);
			json.addDatas("award", award);
		});
	}

	@RequestMapping("doUnfavorite")
	@ResponseBody
	public JSONPObject doUnfavorite(final Long questionId, String callback) {
		return jsonp(callback, (json) -> {
			Long teacherId = UserContext.user.getUserId();
			TeacherQuestion fav = new TeacherQuestion();
			fav.setQuestionId(questionId);
			fav.setTeacherId(teacherId);
			fav.setModifiedBy(teacherId);
			teacherQuestionService.deleteTeacherQuestion(fav);
		});
	}

	@RequestMapping("doBatchUnfavorite")
	@ResponseBody
	public JSONPObject doBatchUnfavorite(@RequestParam("questionIds[]") Long[] questionIds,
			String callback) {
		return jsonp(callback, (json) -> {
			Long teacherId = UserContext.user.getUserId();
			teacherQuestionService.deleteBatchTeacherQuestion(questionIds, teacherId);
		});
	}

	@RequestMapping("doRemoveFromLekeShare")
	@ResponseBody
	public JSONPObject doRemoveFromLekeShare(final Long questionId, String callback) {
		return jsonp(callback, (json) -> {
			User user = UserContext.user.get();
			checkHasRemovePermission(user);
			lekeShareQuestionHandler.remove(questionId, user);
			questionShareSender.send(questionId);
			QuestionIndexContext.sendUpdateIndex(questionId);
		});
	}

	@RequestMapping("doRemoveFromLekeBoutique")
	@ResponseBody
	public JSONPObject doRemoveFromLekeBoutique(final Long questionId, String callback) {
		return jsonp(callback, (json) -> {
			User user = UserContext.user.get();
			checkHasRemovePermission(user);
			lekeBoutiqueQuestionHandler.remove(questionId, user);
			questionShareSender.send(questionId);
			QuestionIndexContext.sendUpdateIndex(questionId);
		});
	}

	@ResponseBody
	@RequestMapping("filterFavoriteQuestions")
	public JSONPObject filterFavoriteQuestions(String dataJson, String callback) {
		return jsonp(callback, (json) -> {
			List<Long> questionIds = JsonUtils.readList(dataJson, Long.class);
			User user = UserContext.user.get();
			List<Long> resIds = favoriteQuestionQueryService.filterFavoriteQuestions(questionIds, user);
			json.addDatas("resIds", resIds);
		});
	}

	@ResponseBody
	@RequestMapping("getLikeRandom")
	public JSONPObject getLikeRandom(String dataJson, String callback) {
		return jsonp(callback, (json) -> {
			List<Long> questionIds = JsonUtils.readList(dataJson, Long.class);
			Long questionId = questionQueryService.getLikeQuestionId(questionIds);
			json.addDatas("questionId", questionId);
		});
	}
	

	private void checkHasRemovePermission(User user) {
		Long roleId = user.getCurrentRole().getId();
		if (!roleId.equals(RoleCst.RESEARCHER)) {
			throw new ValidateException("que.warn.permission.denied");
		}
	}

	/**
	 * 生成智能组卷习题
	 * 
	 * @param dataJson
	 * @param callback
	 * @return
	 */
	@ResponseBody
	@RequestMapping("genSmartPaper")
	public JSONPObject genSmartPaper(String dataJson, String callback) {
		return jsonp(callback, (json) -> {
			SmartPaperCriteria criteria = JSON.parse(dataJson, SmartPaperCriteria.class);
			criteria.setUserId(UserContext.user.getUserId());
			criteria.setSchoolId(UserContext.user.getCurrentSchoolId());
			SmartPaperGenerateResult result = smartPaperQuestionService.generate(criteria);
			json.addDatas("result", result);
		});
	}
}
