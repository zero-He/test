package cn.strong.leke.diag.report.stat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.NumberUtils;
import cn.strong.leke.diag.model.StudentAnswer;
import cn.strong.leke.diag.model.WorkStats;
import cn.strong.leke.model.question.QuestionDTO;

/**
 * 生成问答题分析数据格式。
 * 格式：
 * <pre>
 * [{value:6,name: "9"},{value:3,name: "8"},{value:6,name: "7"},{value:4,name: "6"},{value:8,name: "5"},
 *  {value:5,name: "4"},{value:7,name: "3"},{value:3,name: "2"},{value:4,name: "1"},{value:5,name: "0"}]
 * </pre>
 * @author  andy
 * @created 2014年8月7日 下午2:48:35
 * @since   v1.0.0
 */
@Component("stat_Openend")
public class OpenendStatAnalyzer implements QuestionStatAnalyzer {

	@Override
	public void analyze(QuestionDTO questionDTO, List<StudentAnswer> stuAnswerList, WorkStats workStats) {
		Bucket<String, Long> bucket = new Bucket<String, Long>();
		stuAnswerList.stream().filter(v -> v.getQuestionResult().getTotalResultScore() != null)
				.forEach(v -> bucket.put(NumberUtils.formatScore(v.getQuestionResult().getTotalResultScore()), v.getStudentId()));
		List<String> labels = new ArrayList<String>(bucket.keySet());
		Collections.sort(labels);
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		for (String label : labels) {
			datas.add(this.buildData(label, bucket.size(label), false));
		}
		// 反转顺序
		Collections.reverse(datas);

		workStats.getCharts().add(new WorkStats.Chart(questionDTO.getQuestionId(), JsonUtils.toJSON(datas)));
	}
}
