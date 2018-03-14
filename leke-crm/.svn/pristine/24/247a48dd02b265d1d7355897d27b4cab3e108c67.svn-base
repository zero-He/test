package cn.strong.leke.scs.dao.master;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.scs.model.SpecialDate;



/**
 *
 * 描述:特殊节假日
 *
 * @author  zhengyiyin
 * @created 2015年11月26日 下午6:07:30
 * @since   v1.0.0
 */

public interface SpecialDateDao {
	
	/**	
	 * 描述:保存
	 * @author  zhengyiyin
	 * @created 2015年11月26日 下午6:06:51
	 */
	Integer insert(@Param("specialDateList") List<SpecialDate> specialDateList);
	
	/**	
	 * 描述:删除 原先选中的 日期
	 * @author  zhengyiyin
	 * @created 2015年11月26日 下午8:56:06
	 */
//	Integer delete(@Param("list") List<String> list);
	Integer delete();
	
	
	Integer queryCountByDate(@Param("date") String date);
	
//	List<SpecialDate> queryList(@Param("yearMonth") String yearMonth);
	List<String> queryList(@Param("type") Integer type);

}
