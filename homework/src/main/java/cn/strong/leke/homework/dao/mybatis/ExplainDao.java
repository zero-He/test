/* 
 * 包名: cn.strong.leke.homework.dao.mybatis
 *
 * 文件名：ExplainDao.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: luf
 *
 * 日期：2014-6-19
 */
package cn.strong.leke.homework.dao.mybatis;

import java.util.List;

import cn.strong.leke.homework.model.Explain;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    luf
 * @version   Avatar 
 */
public interface ExplainDao {
	
	Integer saveExplain(Explain explain);
	
	List<Explain> getExplainList(Explain explain);
	
	Explain getExplainDetail(Explain explain);
	
	Integer updateAgainDoubt(long doubtId);
	
	Integer firstTime(long doubtId);
}
