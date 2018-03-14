package cn.strong.leke.diag.report.stat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.diag.model.StudentAnswer;
import cn.strong.leke.diag.model.WorkStats;
import cn.strong.leke.diag.model.WorkStats.Named;
import cn.strong.leke.model.question.Answer;
import cn.strong.leke.model.question.AnswerResult;
import cn.strong.leke.model.question.QuestionDTO;

/**
 * 生成单选题分析数据格式。
 * 格式：
 * <pre>
 * [{value:28,name:'D'},{value:13,name:'C'},{value:5,name:'B'},{value:4,name:'A'}]
 * </pre>
 * @author  andy
 * @created 2014年8月7日 下午1:58:47
 * @since   v1.0.0
 */
@Component("stat_SingleChoice")
public class SingleChoiceStatAnalyzer implements QuestionStatAnalyzer {

	private static final String[] OPTION_LABELS = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z".split(",");

	@Override
	public void analyze(QuestionDTO questionDTO, List<StudentAnswer> stuAnswerList, WorkStats workStats) {
		Bucket<String, Long> bucket = new Bucket<String, Long>();
		for (StudentAnswer stuAnswer : stuAnswerList) {
			if (CollectionUtils.isEmpty(stuAnswer.getQuestionResult().getAnswerResults())) {
				// 答题信息为空时忽略本次循环
				bucket.put("0", stuAnswer.getStudentId());
				continue;
			}
			for (AnswerResult answerResult : stuAnswer.getQuestionResult().getAnswerResults()) {
				bucket.put(answerResult.getMyAnswer(), stuAnswer.getStudentId());
			}
		}
		// 根据答案ID，获取数量
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		List<Named> names = new ArrayList<Named>();
		for (int i = 0; i < questionDTO.getAnswers().size(); i++) {
			Answer answer = questionDTO.getAnswers().get(i);
			Set<Long> userIds = bucket.getOrDefault(String.valueOf(answer.getAnswerId()), Collections.emptySet());
			datas.add(this.buildData(OPTION_LABELS[i], userIds.size(), answer.getIsCorrect()));
			names.add(this.buildName(OPTION_LABELS[i], userIds));
		}
		if (bucket.get("0") != null) {
			Set<Long> userIds = bucket.get("0");
			datas.add(this.buildData("未答题", userIds.size(), false));
			names.add(this.buildName("未答题", userIds));
		}
		workStats.getCharts().add(new WorkStats.Chart(questionDTO.getQuestionId(), JsonUtils.toJSON(datas), names));
	}
}
