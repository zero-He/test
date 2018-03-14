/**
 * 
 */
package cn.strong.leke.question.analysis.core.service;

import cn.strong.leke.question.analysis.core.model.SmartPaperCriteria;
import cn.strong.leke.question.analysis.core.model.SmartPaperGenerateResult;

/**
 * 智能组卷习题服务接口
 * 
 * @author liulongbiao
 *
 */
public interface ISmartPaperQuestionService {

	/**
	 * 生成智能组卷习题结果
	 * 
	 * @param criteria
	 * @return
	 */
	SmartPaperGenerateResult generate(SmartPaperCriteria criteria);
}
