/* 
 * 包名: cn.strong.leke.question.service.impl
 *
 * 文件名：QuestionTaskConfigService.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-6-10
 */
package cn.strong.leke.question.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.question.dao.mybatis.IQuestionTaskConfigDao;
import cn.strong.leke.question.model.QueTaskConfig;
import cn.strong.leke.question.service.IQuestionTaskConfigService;

/**
 * 习题领取设置服务层实现
 * @author    lavender
 * @version   Avatar 
 */
@Service
public class QuestionTaskConfigService implements IQuestionTaskConfigService {
	@Resource
	private IQuestionTaskConfigDao questionTaskConfigDao;
	
	@Override
	public Integer addQueTaskConfig(QueTaskConfig qtc) {
		//删除原有数据
		questionTaskConfigDao.deleteQueTaskConfig(qtc);
		
		return questionTaskConfigDao.saveQueTaskConfig(qtc);
	}

	@Override
	public Integer findTaskCount() {
		return questionTaskConfigDao.queryTaskCount();
	}

}
