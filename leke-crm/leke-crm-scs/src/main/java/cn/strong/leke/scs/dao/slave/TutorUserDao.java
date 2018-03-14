/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.scs.dao.slave;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.scs.dao.Slave;
import cn.strong.leke.scs.model.TutorUser;
import cn.strong.leke.scs.model.query.TutorUserQuery;

/**
 *
 * 描述:
 *
 * @author  yuanyi
 * @created 2015年9月1日 下午4:38:22
 * @since   v1.0.0
 */
@Slave
public interface TutorUserDao {

	List<TutorUser> queryTutorUserList(TutorUserQuery query, Page page);
	
	List<Long> querySchoolIdsBySeller(@Param("sellerId")Long sellerId);
}
