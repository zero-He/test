/**
 * 
 */
package cn.strong.leke.question.core.question.cmd.store;

import cn.strong.leke.model.user.User;

/**
 * 习题和习题册章节关联处理器
 * 
 * @author liulongbiao
 *
 */
public interface IQuestionWbnodeHandler {
	/**
	 * 添加
	 * 
	 * @param questionId
	 * @param workbookNodeId
	 * @param user
	 * @return
	 */
	void add(Long questionId, Long workbookNodeId, User user);
	
	/**
	 * 移除习题和习题册章节关联
	 * 
	 * @param questionId
	 * @param workbookNodeId
	 * @param user
	 * @return
	 */
	void remove(Long questionId, Long workbookNodeId, User user);
}
