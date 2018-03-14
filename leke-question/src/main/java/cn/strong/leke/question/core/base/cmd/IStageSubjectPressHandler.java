package cn.strong.leke.question.core.base.cmd;

import java.util.List;

import cn.strong.leke.question.model.base.StageSubjectPress;

/**
 *
 * 描述: 学段学科关联教材版本
 *
 * @author raolei
 * @created 2016年11月10日 上午11:14:04
 * @since v1.0.0
 */
public interface IStageSubjectPressHandler {

	void insert(StageSubjectPress assocs);

	void insertList(List<StageSubjectPress> assocs);

}
