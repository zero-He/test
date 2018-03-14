package cn.strong.leke.diag.report.stat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.diag.model.StudentAnswer;
import cn.strong.leke.diag.model.WorkStats;
import cn.strong.leke.model.question.QuestionDTO;

/**
 * 生成断句题分析数据格式。
 * 格式：
 * <pre>
 * [{value:28,name:'FULL'},{value:25,name:'LESS'}]
 * </pre>
 * @author  andy
 * @created 2014年8月7日 下午1:58:47
 * @since   v1.0.0
 */
@Component("stat_Punctuate")
public class PunctuateStatAnalyzer implements QuestionStatAnalyzer {

	// 满分
	private static final String LABEL_FULL_MARK = "FULL";
	// 非满分
	private static final String LABEL_LESS_MARK = "LESS";

	@Override
	public void analyze(QuestionDTO questionDTO, List<StudentAnswer> stuAnswerList, WorkStats workStats) {
		int full = (int) stuAnswerList.stream().filter(v -> v.getQuestionResult().getTotalIsRight()).count();
		int less = stuAnswerList.size() - full;
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		datas.add(this.buildData(LABEL_FULL_MARK, full, false));
		datas.add(this.buildData(LABEL_LESS_MARK, less, false));

		workStats.getCharts().add(new WorkStats.Chart(questionDTO.getQuestionId(), JsonUtils.toJSON(datas)));
	}
}
