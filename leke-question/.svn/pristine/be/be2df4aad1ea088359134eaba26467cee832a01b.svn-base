/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.strong.leke.framework.spring.SpringContextHolder;
import cn.strong.leke.question.duplication.service.DuplicationQuestionManager;

/**
 * 标记疑似重复定时任务
 * 
 * @author liulongbiao
 * @created 2015年1月19日 下午6:42:11
 * @since v3.2.2
 */
public class MarkDuplicationJob extends QuartzJobBean {

	private static Logger logger = LoggerFactory
			.getLogger(MarkDuplicationJob.class);

	private static final DuplicationQuestionManager duplicationQuestionManager = SpringContextHolder
			.getBean(DuplicationQuestionManager.class);

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {
			logger.info("start mark duplication job...");
			duplicationQuestionManager.runDupJob();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new JobExecutionException(e.getMessage(), e);
		}
	}

}
