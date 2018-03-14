/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.remote;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.question.dao.mybatis.wrongquestion.IWrongQuestionDao;
import cn.strong.leke.question.model.wrongquestion.WrongSubject;
import cn.strong.leke.remote.service.question.IWrongQuestionRemoteService;

/**
 * 老师相关的错题题目 服务
 * @author Zhang Fujun
 * @date 2017年1月11日
 */
@Service
public class WrongQuestionRemoteService implements IWrongQuestionRemoteService {

	@Resource
	private IWrongQuestionDao iWrongQuestionDao;

	@Override
	public BigDecimal getRate(Long teacherId, Long classId, Long subjectId, Long schoolId) {
		WrongSubject wrongSubject = iWrongQuestionDao.getWrongSubject(teacherId, classId, subjectId, schoolId);
		if(wrongSubject != null){
			return wrongSubject.getRate();
		}
		return null;
	}

}
