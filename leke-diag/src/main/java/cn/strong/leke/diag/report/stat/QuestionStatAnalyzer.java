package cn.strong.leke.diag.report.stat;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.diag.model.StudentAnswer;
import cn.strong.leke.diag.model.WorkStats;
import cn.strong.leke.diag.model.WorkStats.Named;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.user.UserBase;

public interface QuestionStatAnalyzer {

	public void analyze(QuestionDTO questionDTO, List<StudentAnswer> stuAnswerList, WorkStats workStats);

	public default Map<String, Object> buildData(String name, Integer value, Boolean isRight) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("name", name);
		dataMap.put("value", value);
		dataMap.put("isRight", isRight);
		return dataMap;
	}

	public default Named buildName(String label, Set<Long> userIds) {
		List<UserBase> users = UserBaseContext.findUserBaseByUserId(userIds.toArray(new Long[0]));
		List<String> names = users.stream().filter(v -> v != null).map(UserBase::getUserName).collect(toList());
		return new Named(label, names);
	}
}
