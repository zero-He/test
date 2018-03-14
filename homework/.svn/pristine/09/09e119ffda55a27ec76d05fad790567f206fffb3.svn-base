/* 
 * 包名: cn.strong.leke.homework.service
 *
 * 文件名：DoubtService.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: luf
 *
 * 日期：2014-6-18
 */
package cn.strong.leke.homework.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.homework.model.Doubt;
import cn.strong.leke.homework.model.DoubtDtl;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author luf
 * @version Avatar
 */
public interface DoubtService {

	boolean saveDoubt(Doubt doubt);

	List<DoubtDtl> doubtList(Doubt doubt, Page page,Boolean filtration);

	DoubtDtl getDetail(DoubtDtl doubt);
	
	Doubt getQuestionDtail(Doubt doubt, Long questionId);
	
	List<DoubtDtl> getTeacherDoubtl(Doubt doubt, Page page);
	
	/**
	 * 获取老师待解答的乐答数量
	 * @param teacherId
	 * @return
	 */
	Integer getResolveDoubtTotal(Long teacherId);
	
	/**
	 * 查找老师待解答的乐答数量
	 * @param teacherId
	 * @return
	 */
	Integer getTeacherResolveDoubt(Long teacherId);
	
	/**
	 * 查找学生待解答的乐答数量
	 * @param teacherId
	 * @return
	 */
	Integer getStudentResolveDoubt(Long createdBy);
	
	/**
	 * 更新版本信息
	 * @param doubt
	 */
	void updateSection(Doubt doubt);
	
	/**
	 * 删除自己的乐大
	 * @param doubtIds
	 * @param roleId
	 */
	void deleteMySelfDoubt(Long[] doubtIds,Long roleId);
	/**
	 * 收藏或取消收藏
	 * @param doubtId
	 * @param roleId
	 */
	void markMySelfDoubt(Long doubtId,Long roleId);
	
	/**
	 * 获取乐答的详细信息，包括追问，回答信息
	 * @return
	 */
	Map<String,Object> getDoubt_Explain_Question(DoubtDtl doubt,HttpServletRequest request);
	
}
