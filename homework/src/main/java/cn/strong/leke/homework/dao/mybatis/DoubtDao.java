/* 
 * 包名: cn.strong.leke.homework.dao.mybatis
 *
 * 文件名：DoubtDao.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: luf
 *
 * 日期：2014-6-18
 */
package cn.strong.leke.homework.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.homework.model.Doubt;
import cn.strong.leke.homework.model.DoubtDtl;

/**
 * @author    luf
 * @version   Avatar 
 */
public interface DoubtDao {

	void saveDoubt(Doubt doubt);

	List<DoubtDtl> doubtList(Doubt doubt, Page page);

	DoubtDtl getDetail(DoubtDtl doubt);
	
	void updateDetailExplainTime(@Param("doubtId")Long doubtId,@Param("askAgain")Integer askAgain);
	
	Doubt getDoubt(Doubt doubt);

	List<DoubtDtl> getTeacherDoubtl(Doubt doubt, Page page);

	Integer isResolved(long doubtId);

	Integer resoveDoubt(long doubtId);
	
	/**
	 * 查找老师待解答的乐答数量
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
	
	void updateSection(Doubt doubt);
	
	void deleteMySelfDoubt(@Param("doubtIds")List<Long> doubtIds,@Param("roleId")Long roleId);
	
	void markMySelfDoubt(@Param("doubtId")Long doubtId,@Param("roleId")Long roleId);

}
