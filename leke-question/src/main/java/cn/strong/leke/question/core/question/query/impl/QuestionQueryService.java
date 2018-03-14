/**
 * 
 */
package cn.strong.leke.question.core.question.query.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.BeanUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.common.utils.function.Predicate;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.question.QuestionTypeContext;
import cn.strong.leke.context.repository.RepositoryContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.Analysis;
import cn.strong.leke.model.question.Answer;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionKnowledge;
import cn.strong.leke.model.question.QuestionOfficialTag;
import cn.strong.leke.model.question.QuestionSection;
import cn.strong.leke.model.question.QuestionStem;
import cn.strong.leke.model.question.QuestionType;
import cn.strong.leke.model.question.querys.QuestionSelectQuery;
import cn.strong.leke.model.question.querys.RepositoryQuestionQuery;
import cn.strong.leke.model.repository.RepoCsts.OrderBys;
import cn.strong.leke.model.repository.RepoDayGroup;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.repository.ShareScopes;
import cn.strong.leke.question.core.question.query.IQuestionQueryService;
import cn.strong.leke.question.dao.mybatis.AnalysisDao;
import cn.strong.leke.question.dao.mybatis.AnswerDao;
import cn.strong.leke.question.dao.mybatis.QuestionDao;
import cn.strong.leke.question.dao.mybatis.QuestionKnowledgeDao;
import cn.strong.leke.question.dao.mybatis.QuestionOfficialTagDao;
import cn.strong.leke.question.dao.mybatis.QuestionStemDao;
import cn.strong.leke.question.dao.mybatis.question.IQuestionSectionDao;
import cn.strong.leke.question.model.InputStatisDTO;
import cn.strong.leke.question.model.RandSelectQuestionQuery;
import cn.strong.leke.question.model.question.QuestionTotal;
import cn.strong.leke.question.model.question.QuestionTotalResult;
import cn.strong.leke.question.model.question.query.AmountQuestionQuery;
import cn.strong.leke.question.model.question.query.RandomGetQuestionQuery;
import cn.strong.leke.question.service.QuestionService;
import cn.strong.leke.remote.model.common.PageRemote;
import cn.strong.leke.remote.model.tukor.UserRemote;
import cn.strong.leke.remote.service.repository.IRepositoryRemoteService;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;

/**
 * @author liulongbiao
 *
 */
@Service
public class QuestionQueryService implements IQuestionQueryService {

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
	private IUserRemoteService userRemoteService;
	@Resource
	private IRepositoryRemoteService repoRemoteService;
	@Resource
	private QuestionService questionService;

	@Override
	public QuestionDTO getById(Long questionId) {
		QuestionDTO que = questionDao.getQuestion(questionId);
		if (que == null) {
			return null;
		}
		QuestionDTO result = (QuestionDTO) BeanUtils.cloneBean(que);
		fillContents(result);
		fillRelations(result);
		return result;
	}

	@Override
	public QuestionTotal countInputQuestion(AmountQuestionQuery query) {
		QuestionTotal total = new QuestionTotal();
		total.setTotal(questionDao.countInputQuestion(query));
		query.setQuestionFrom(AmountQuestionQuery.FROM_PAPER);
		total.setTotalFromPaper(questionDao.countInputQuestion(query));
		query.setQuestionFrom(AmountQuestionQuery.FROM_WB);
		total.setTotalFromWb(questionDao.countInputQuestion(query));
		return total;
	}
	
	@Override
	public List<InputStatisDTO> queryInputStatisDTO(InputStatisDTO query) {
		return questionDao.queryInputStatisDTO(query);
	}

	@Override
	public List<InputStatisDTO> queryQuestionAmount(InputStatisDTO query) {
		return Collections.emptyList();
	}
	
	@Override
	public Long queryQuestionTotalAmount(InputStatisDTO query) {
		return questionDao.queryQuestionTotalAmount(query);
	}
 
	
	@Override
	public long countUnCheckedQuestionOfLekeBoutique(AmountQuestionQuery query) {
		return questionDao.countUnCheckedQuestionOfLekeBoutique(query);
	}

	private void fillContents(QuestionDTO result) {
		Long questionId = result.getQuestionId();

		List<QuestionStem> stems = questionStemDao.findQuestionStemByQuestionId(questionId);
		QuestionStem stem = CollectionUtils.getFirst(stems);
		result.setStem(stem);

		List<Analysis> analysises = analysisDao.findAnalysisByQuestionId(questionId);
		Analysis analysis = CollectionUtils.getFirst(analysises);
		result.setAnalysis(analysis);

		Boolean hasSub = result.getHasSub();
		if (hasSub != null && hasSub.equals(true)) {
			List<QuestionDTO> subs = questionDao.findSubQuestions(questionId);
			if (!CollectionUtils.isEmpty(subs)) {
				for (QuestionDTO sub : subs) {
					fillContents(sub);
				}
			}
			result.setSubs(subs);
		} else {
			List<Answer> answers = answerDao.findAnswersByQuestionId(questionId);
			result.setAnswers(answers);
		}
	}

	private void fillRelations(QuestionDTO result) {
		Long questionId = result.getQuestionId();
		List<QuestionSection> sections = questionSectionDao.findByQuestionId(questionId);
		result.setSections(sections);
		List<QuestionKnowledge> knowledges = questionKnowledgeDao
				.findQuestionKnowledgeByQuestionId(questionId);
		result.setKnowledges(knowledges);
		List<QuestionOfficialTag> tags = questionOfficialTagDao
				.findQuestionOfficialTagByQuestionId(questionId);
		result.setTags(tags);
		// 习题册章节不属于试题本身内容，不纳入
	}

	@Override
	public Long getLikeQuestionId(List<Long> questionIds) {
		RandomGetQuestionQuery query = new RandomGetQuestionQuery();
		Long questionId = questionIds.get(0);
		QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
		questionIds.add(questionId);
		query.setQuestionIds(questionIds);
		query.setQuestionTypeId(question.getQuestionTypeId());
		query.setDifficulty(question.getDifficulty());
		List<QuestionKnowledge> knowledges = questionKnowledgeDao.findQuestionKnowledgeByQuestionId(questionId);
		List<QuestionSection> sections = questionSectionDao.findByQuestionId(questionId);
		Long resultQuestionId = null;
		if (CollectionUtils.isNotEmpty(knowledges)) {
			resultQuestionId = randomByknowledge(query, knowledges);
		}
		if (resultQuestionId == null) {
			if (CollectionUtils.isEmpty(sections)) {
				throw new ValidateException("请完善题目信息以便系统选题");
			}
			resultQuestionId = randomBySection(query, sections);
		}
		if (resultQuestionId == null) {
			String err = CollectionUtils.isNotEmpty(knowledges) ? "没有更多相同知识点的题目了" : "没有更多相同教材章节的题目了";
			throw new ValidateException(err);
		}
		return resultQuestionId;
	}

	private Long randomByknowledge(RandomGetQuestionQuery query, List<QuestionKnowledge> knowledges) {
		List<Long> knowledgeIds = ListUtils.map(knowledges, k -> {
			return k.getKnowledgeId();
		});
		query.setKnowledgeIds(knowledgeIds);
		return questionDao.getRandomQuestionId(query);
	}

	private Long randomBySection(RandomGetQuestionQuery query, List<QuestionSection> sections) {
		query.setMaterialNodeId(sections.get(0).getMaterialNodeId());
		return questionDao.getRandomQuestionId(query);
	}


	@Override
	public List<Long> queryHomeworkSelectQuestions(QuestionSelectQuery query) {
		return questionDao.queryHomeworkSelectQuestions(query);
	}

	@Override
	public List<Long> queryRandSelectQuestions(RandSelectQuestionQuery query) {
		if (query == null) {
			return Collections.emptyList();
		}

		RepositoryQuestionQuery rq = new RepositoryQuestionQuery();
		rq.setSchoolStageId(query.getSchoolStageId());
		rq.setSubjectId(query.getSubjectId());
		rq.setContent(query.getContent());
		rq.setMaterialNodeId(query.getMaterialNodeId());
		rq.setMaterialNodeIds(ListUtils.toSet(query.getMaterialNodeIds()));
		rq.setKnowledgeId(query.getKnowledgeId());
		rq.setKnowledgeIds(ListUtils.toSet(query.getKnowledgeIds()));
		rq.setDiffLevel(query.getDiffLevel());
		rq.setQuestionTypeId(query.getQuestionTypeId());
		rq.setQuestionTypeIds(getQuestionTypeIds(query));
		

		rq.setShareScope(ShareScopes.LEKE_BOUTIQUE);
		rq.setOrderBy(OrderBys.RANDOM);

		Integer limit = query.getQuestionNum();
		PageRemote<Long> page = new PageRemote<>(1, limit == null ? 10 : limit);
		PageRemote<Long> result = repoRemoteService.querySolrQuestions(rq, page);
		return result == null ? Collections.emptyList() : result.getDataList();
	}

	public Set<Long> getQuestionTypeIds(RandSelectQuestionQuery query) {
		Set<Long> typeIds = query.getInQuestionTypeIds();
		if(!CollectionUtils.isEmpty(typeIds)) {
			return typeIds;
		}
		if(query.getSubjectId() == null) {
			return null;
		}

		Boolean subjective = query.getSubjective();
		Boolean hasSub = query.getHasSub();
		if (subjective == null && hasSub == null) {
			return null;
		}

		List<QuestionType> types = QuestionTypeContext.findQuestionTypesBySubjectId(query.getSubjectId());
		return types.stream().filter(type -> subjective != null ? subjective.equals(type.getSubjective()) : true)
				.filter(type -> hasSub != null ? hasSub.equals(type.getHasSub()) : true)
				.map(QuestionType::getQuestionTypeId).collect(Collectors.toSet());
	}

	public List<QuestionTotal> queryTotalQuestionFrom(AmountQuestionQuery query, String userName, Long roleId) {
		// 远程接口调用角色用户列表
		List<UserRemote> userList = new ArrayList<>();
		if (roleId - RoleCst.RESEARCHER == 0){
			userList.addAll(userRemoteService.findUserListForQuestion(userName, RoleCst.RESEARCHER));
		}else if (roleId - RoleCst.INPUTER == 0){
			userList.addAll(userRemoteService.findUserListForQuestion(userName, RoleCst.INPUTER));
		}else if (roleId - RoleCst.QUESTION_ADMIN == 0){
			userList.addAll(userRemoteService.findUserListForQuestion(userName, RoleCst.QUESTION_ADMIN));
		}else{
			userList.addAll(userRemoteService.findUserListForQuestion(userName, RoleCst.RESEARCHER));
			userList.addAll(userRemoteService.findUserListForQuestion(userName, RoleCst.INPUTER));
			userList.addAll(userRemoteService.findUserListForQuestion(userName, RoleCst.QUESTION_ADMIN));
		}
		
/*		// 远程接口调用角色用户列表
				List<UserRemote> userList = userRemoteService.findUserListForQuestion(userName, RoleCst.INPUTER);
				List<UserRemote> researcherList = userRemoteService.findUserListForQuestion(userName, RoleCst.RESEARCHER);
				userList.addAll(researcherList);*/
		
		Map<Long, QuestionTotal> totals = new HashMap<Long, QuestionTotal>();
		for (UserRemote n : userList) {
			Long userId = n.getId();
			if (!totals.containsKey(userId)) {
				QuestionTotal total = new QuestionTotal();
				total.setUserId(userId);
				total.setUserName(n.getUserName());
				totals.put(n.getId(), total);
			}
		}
		List<Long> userIds = ListUtils.newArrayList();
		totals.forEach((k, v) -> {
			userIds.add(k);
		});
		query.setUserIds(userIds);
		setQuestionTotal(query, totals);
		List<QuestionTotal> result = new ArrayList<QuestionTotal>(totals.values());
		return ListUtils.filter(result, EmptyFilter.INSTANCE);
	
	}

	private void setQuestionTotal(AmountQuestionQuery query, Map<Long, QuestionTotal> totals) {
		List<QuestionTotalResult> results1 = questionDao.countInputQuestionFrom(query);
		results1.forEach(result -> {
			QuestionTotal total = totals.get(result.getUserId());
			total.setTotal(result.getAmount());
		});

		query.setQuestionFrom(AmountQuestionQuery.FROM_PAPER);
		List<QuestionTotalResult> results2 = questionDao.countInputQuestionFrom(query);
		results2.forEach(result -> {
			QuestionTotal total = totals.get(result.getUserId());
			total.setTotalFromPaper(result.getAmount());
		});

		query.setQuestionFrom(AmountQuestionQuery.FROM_WB);
		List<QuestionTotalResult> results3 = questionDao.countInputQuestionFrom(query);
		results3.forEach(result -> {
			QuestionTotal total = totals.get(result.getUserId());
			total.setTotalFromWb(result.getAmount());
		});
	}

	public static enum EmptyFilter implements Predicate<QuestionTotal> {
		INSTANCE;

		@Override
		public boolean test(QuestionTotal t) {
			Long zero = Long.valueOf(0);
			if (t.getTotal() == null) {
				t.setTotal(zero);
			}
			if (t.getTotalFromPaper() == null) {
				t.setTotalFromPaper(zero);
			}
			if (t.getTotalFromWb() == null) {
				t.setTotalFromWb(zero);
			}
			return !t.getTotal().equals(0L) || !t.getTotalFromPaper().equals(0L) || !t.getTotalFromWb().equals(0L);
		}
	}

	@Override
	public List<RepositoryRecord> queryRepoQuestions(RepositoryQuestionQuery query, Page page) {
		if (query == null || page == null) {
			return Collections.emptyList();
		}
		switch (query.getShareScope()) {
		case ShareScopes.PERSONAL:
			return questionService.queryPersonalQuestions(query, page);
		case ShareScopes.FAVORITE:
			return questionService.queryFavoriteQuestions(query, page);
		case ShareScopes.SCHOOL:
			return questionService.querySchoolQuestions(query, page);
		case ShareScopes.LEAGUE:
			return questionService.queryLeagueQuestions(query, page);
		case ShareScopes.LEKE_BOUTIQUE:
		case ShareScopes.PLATFORM:
		case ShareScopes.PERSONAL_ALL:
			List<Long> ids = querySolrQuestionIds(query, page);
			return RepositoryContext.ofQuestions(ids);
		default:
			return Collections.emptyList();
		}
	}

	private List<Long> querySolrQuestionIds(RepositoryQuestionQuery query, Page page) {
		if (query == null || page == null) {
			return Collections.emptyList();
		}
		PageRemote<Long> pr = new PageRemote<>(page.getCurPage(), page.getPageSize());
		PageRemote<Long> result = repoRemoteService.querySolrQuestions(query, pr);
		if (result == null) {
			return Collections.emptyList();
		} else {
			page.setTotalSize(result.getTotalSize());
			return result.getDataList();
		}
	}

	@Override
	public int countLekeQuestion(RepositoryQuestionQuery query) {
		return questionDao.countLekeQuestion(query);
	}

	@Override
	public List<RepoDayGroup> groupCreatedOnLekeQuestion(RepositoryQuestionQuery query) {
		return questionDao.groupCreatedOnLekeQuestion(query);
	}
}
