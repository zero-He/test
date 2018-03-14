/**
 * 
 */
package cn.strong.leke.question.service;

import cn.strong.leke.model.paper.ScoredQuestion;
import cn.strong.leke.model.question.QuestionDTO;

/**
 * 习题图片处理器
 * 
 * @author liulongbiao
 *
 */
public interface IQueImageProcessor {

	/**
	 * 处理习题内容中的 Base64 图片
	 * 
	 * @param dto
	 */
	void processBase64Images(QuestionDTO dto);

	/**
	 * 处理答题卡习题内容中的 Base64 图片
	 * 
	 * @param sq
	 */
	void processBase64Images(ScoredQuestion sq);

	/**
	 * 保存 Base64 格式的图片，并返回替换的 &lt;img/&gt; 标签
	 * 
	 * @param b64Str
	 * @param ext
	 * @return
	 */
	String saveBase64Image(String b64Str, String ext);
}
