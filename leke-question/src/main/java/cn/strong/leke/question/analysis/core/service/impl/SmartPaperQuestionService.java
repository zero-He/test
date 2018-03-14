/**
 * 
 */
package cn.strong.leke.question.analysis.core.service.impl;

import static cn.strong.leke.common.utils.CollectionUtils.isEmpty;
import static cn.strong.leke.common.utils.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.context.base.LeagueContext;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.question.QuestionTypeContext;
import cn.strong.leke.framework.exceptions.LekeRuntimeException;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionType;
import cn.strong.leke.model.repository.ShareScopes;
import cn.strong.leke.question.analysis.core.model.SmartPaperCriteria;
import cn.strong.leke.question.analysis.core.model.SmartPaperCriteria.TypeCnt;
import cn.strong.leke.question.analysis.core.model.SmartPaperGenerateResult;
import cn.strong.leke.question.analysis.core.model.SmartPaperGenerateResult.Grp;
import cn.strong.leke.question.analysis.core.model.SmartPaperGenerateResult.Que;
import cn.strong.leke.question.analysis.core.service.ISmartPaperQuestionService;
import cn.strong.leke.question.analysis.db.dao.mybatis.ISmartPaperQuestionDao;
import cn.strong.leke.question.analysis.db.model.SmartPaperDbQuery;
import cn.strong.leke.question.analysis.db.model.SmartPaperDbQuery.SectionCriteria;
import cn.strong.leke.question.analysis.db.model.SmartPaperQue;
import cn.strong.leke.question.analysis.mongo.dao.ISmartPaperQueryCacheDao;

/**
 * @author liulongbiao
 *
 */
@Service
public class SmartPaperQuestionService implements ISmartPaperQuestionService {

	private ExecutorService executor;
	@Resource
	private ISmartPaperQuestionDao smartPaperQuestionDao;
	@Resource
	private ISmartPaperQueryCacheDao smartPaperQueryCacheDao;

	@PostConstruct
	public void afterPropertiesSet() {
		executor = Executors.newFixedThreadPool(20);
	}

	@PreDestroy
	public void destroy() {
		executor.shutdown();
	}

	@Override
	public SmartPaperGenerateResult generate(SmartPaperCriteria criteria) {
		preprocess(criteria);
		if (isEmpty(criteria.getShareScopes()) || isEmpty(criteria.getTypeCnts())) {
			return null;
		}

		try {
			CompletableFuture<SmartPaperGenerateResult> promise = doGenerate(criteria);
			return promise.get(3, TimeUnit.MINUTES);
		} catch (TimeoutException e) {
			throw new LekeRuntimeException("生成智能组卷习题超时", e);
		} catch (Exception ex) {
			throw new LekeRuntimeException("生成智能组卷习题失败", ex);
		}
	}

	/*
	 * 预处理
	 */
	private void preprocess(SmartPaperCriteria criteria) {
		Validation.notNull(criteria.getSubjectId(), "que.question.subject.missing");

		Set<Integer> shareScopes = criteria.getShareScopes();
		if (shareScopes == null || shareScopes.isEmpty()) {
			criteria.setShareScopes(Collections.emptySet());
			return;
		}
		if (shareScopes.contains(ShareScopes.LEAGUE)) {
			Set<Long> leagueMemberIds = new HashSet<>();
			Long schoolId = criteria.getSchoolId();
			if (schoolId != null) {
				LeagueContext.findJoinedLeagues(schoolId).forEach(lg -> {
					leagueMemberIds.addAll(LeagueContext.toLeagueMemberIds(lg.getLeagueId(), null, schoolId));
				});
			}
			criteria.setLeagueMemberIds(leagueMemberIds);
			if (leagueMemberIds.isEmpty()) {
				shareScopes.remove(ShareScopes.LEAGUE);
			}
		}
	}

	private CompletableFuture<SmartPaperGenerateResult> doGenerate(SmartPaperCriteria criteria) {
		List<TypeCnt> typeCnts = criteria.getTypeCnts();
		@SuppressWarnings("unchecked")
		CompletableFuture<Grp>[] subtasks = new CompletableFuture[typeCnts.size()];
		int i = 0;
		for (TypeCnt tc : typeCnts) {
			subtasks[i] = generateGrp(tc, criteria);
			i++;
		}
		return CompletableFuture.allOf(subtasks).thenApply(v -> {
			List<Grp> groups = new ArrayList<>();
			for (CompletableFuture<Grp> task : subtasks) {
				Grp grp = task.join();
				if (grp != null && !grp.wasEmpty()) {
					groups.add(grp);
				}
			}
			SmartPaperGenerateResult result = new SmartPaperGenerateResult();
			result.setGroups(groups);
			return result;
		});
	}

	private CompletableFuture<Grp> generateGrp(TypeCnt tc, SmartPaperCriteria criteria) {
		return CompletableFuture.supplyAsync(() -> {
			return doGenGrp(tc, criteria);
		}, executor);
	}

	private Grp doGenGrp(TypeCnt tc, SmartPaperCriteria criteria) {
		Long typeId = tc.getQuestionTypeId();
		QuestionType qt = QuestionTypeContext.getQuestionType(typeId);
		if (qt == null) {
			return null;
		}

		List<SmartPaperQue> ques = findQuestions(tc, criteria);
		if (isEmpty(ques)) {
			return null;
		}

		Collection<Long> qids = pickQids(tc.getCount(), ques, criteria.prefDiff());
		List<Que> questions = toQues(qids);

		Grp result = new Grp();
		result.setGroupTitle(qt.getQuestionTypeName());
		result.setQuestionTypeId(typeId);
		result.setQuestions(questions);
		return result;
	}

	public List<SmartPaperQue> findQuestions(TypeCnt tc, SmartPaperCriteria criteria) {
		try {
			CompletableFuture<List<SmartPaperQue>> promise = doFindQuestions(tc, criteria);
			return promise.get(3, TimeUnit.MINUTES);
		} catch (TimeoutException e) {
			throw new LekeRuntimeException("获取智能组卷习题超时", e);
		} catch (Exception ex) {
			throw new LekeRuntimeException("获取智能组卷习题失败", ex);
		}
	}

	private CompletableFuture<List<SmartPaperQue>> doFindQuestions(TypeCnt tc, SmartPaperCriteria criteria) {
		return findQuestionIds(tc, criteria).thenApply(qids -> {
			if (isEmpty(qids)) {
				return Collections.emptyList();
			} else {
				return smartPaperQuestionDao.findSmartPaperQues(qids);
			}
		});
	}

	private CompletableFuture<Set<Long>> findQuestionIds(TypeCnt tc, SmartPaperCriteria criteria) {
		if (isNotEmpty(criteria.getSections())) {
			return findBySections(tc, criteria);
		} else if (isNotEmpty(criteria.getKnowledgeIds())) {
			return findSmartPaperQids(newDbQuery(tc, criteria).knowledgeIds(criteria.getKnowledgeIds()));
		} else {
			return CompletableFuture.completedFuture(Collections.emptySet());
		}
	}

	private CompletableFuture<Set<Long>> findBySections(TypeCnt tc, SmartPaperCriteria criteria) {
		List<SectionCriteria> sections = criteria.getSections();
		@SuppressWarnings("unchecked")
		CompletableFuture<Set<Long>>[] subtasks = new CompletableFuture[sections.size()];
		int i = 0;
		for (SectionCriteria section : sections) {
			subtasks[i] = findSmartPaperQids(newDbQuery(tc, criteria).section(section));
			i++;
		}
		return CompletableFuture.allOf(subtasks).thenApply(v -> {
			Set<Long> result = new HashSet<>();
			for (CompletableFuture<Set<Long>> task : subtasks) {
				result.addAll(task.join());
			}
			return result;
		});
	}

	private static SmartPaperDbQuery newDbQuery(TypeCnt tc, SmartPaperCriteria criteria) {
		SmartPaperDbQuery result = new SmartPaperDbQuery();
		result.setSchoolStageId(criteria.getSchoolStageId());
		result.setSubjectId(criteria.getSubjectId());
		result.setShareScopes(criteria.getShareScopes());
		result.setUserId(criteria.getUserId());
		result.setSchoolId(criteria.getSchoolId());
		result.setLeagueMemberIds(criteria.getLeagueMemberIds());

		result.setQuestionTypeId(tc.getQuestionTypeId());
		int limit = Math.min(tc.getCount() * 10, tc.getCount() + 200);
		result.setLimit(limit);
		return result;
	}

	private CompletableFuture<Set<Long>> findSmartPaperQids(SmartPaperDbQuery query) {
		return CompletableFuture.supplyAsync(() -> {
			return smartPaperQuestionDao.findSmartPaperQids(query);
		}, executor);
	}

	private Collection<Long> pickQids(int count, List<SmartPaperQue> ques, double prefDiff) {
		Map<Long, Double> ids = ListUtils.toMap(ques, SmartPaperQue::getQuestionId, SmartPaperQue::getDifficulty);
		int total = ids.size();
		if (total <= count) {
			return ids.keySet();
		} else if (total <= count * 2) { // 当可选题量较少时，直接随机选择
			return randomPick(count, ids);
		} else { // 当可选题量较多时，使用正态分布，按离给定难度值的远近，进行随机选择
			return weightRandomPick(count, ids, prefDiff);
		}
	}

	/*
	 * 随机选取，不考虑难度权重
	 */
	private static List<Long> randomPick(int count, Map<Long, Double> ids) {
		List<Long> source = new ArrayList<>(ids.keySet());
		Collections.shuffle(source);
		return source.subList(0, count);
	}

	/*
	 * 带难度权重的随机选取
	 */
	private static Collection<Long> weightRandomPick(int count, Map<Long, Double> ids, double prefDiff) {
		List<WeightQid> wqids = toWQids(ids, prefDiff);
		Set<Integer> indexs = guassianIndexes(count, wqids.size());

		List<Long> result = new ArrayList<>(count);
		for (Integer idx : indexs) {
			result.add(wqids.get(idx).qid);
		}
		return result;
	}

	private static Set<Integer> guassianIndexes(int count, int total) {
		Random rdm = new Random();
		Set<Integer> indexes = new HashSet<>();
		for (int i = 0; i < count; i++) {
			int idx;
			do {
				idx = (int) (Math.abs(rdm.nextGaussian()) * total / 3);
			} while (idx > total || indexes.contains(idx));
			indexes.add(idx);
		}
		return indexes;
	}

	private static List<WeightQid> toWQids(Map<Long, Double> ids, double prefDiff) {
		List<WeightQid> wqs = new ArrayList<>(ids.size());
		ids.forEach((qid, diff) -> {
			wqs.add(new WeightQid(qid, diff == null ? 0.5 : diff));
		});
		wqs.sort((a, b) -> {
			int r = Double.compare(Math.abs(a.diff - prefDiff), Math.abs(b.diff - prefDiff));
			if (r == 0) {
				r = Long.compare(b.qid, a.qid); // 更新的习题放前面
			}
			return r;
		});
		return wqs;
	}

	private static class WeightQid {
		public final long qid;
		public final double diff;

		public WeightQid(long qid, double diff) {
			this.qid = qid;
			this.diff = diff;
		}

	}

	private List<Que> toQues(Collection<Long> qids) {
		if (isEmpty(qids)) {
			return Collections.emptyList();
		}
		List<Que> result = new ArrayList<>();
		for (Long qid : qids) {
			QuestionDTO que = QuestionContext.getQuestionDTO(qid);
			if (que != null) {
				result.add(toQue(que));
			}
		}

		// 根据难度值排序，难度低的排前面
		Collections.sort(result, (a, b) -> {
			int r = Double.compare(a.getDifficulty(), b.getDifficulty());
			if (r == 0) {
				r = Long.compare(a.getQuestionId(), b.getQuestionId());
			}
			return r;
		});
		return result;
	}

	private static Que toQue(QuestionDTO que) {
		Que result = new Que();
		result.setQuestionId(que.getQuestionId());
		result.setQuestionTypeId(que.getQuestionTypeId());
		result.setDifficulty(que.getDifficulty());
		result.setSubjective(que.getSubjective());
		result.setHandwritten(que.getHandwritten());
		result.setHasSub(que.getHasSub());
		return result;
	}
}
