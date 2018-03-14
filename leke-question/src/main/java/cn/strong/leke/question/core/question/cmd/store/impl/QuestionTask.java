package cn.strong.leke.question.core.question.cmd.store.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.strong.leke.question.core.question.cmd.store.IQuestionTask;
import cn.strong.leke.question.dao.mybatis.QuestionDao;

@Service
public class QuestionTask implements IQuestionTask {

	private static final Logger logger = LoggerFactory.getLogger(QuestionTask.class);

	@Resource
	private QuestionDao questionDao;

	@Override
	public void updateStatusTask() {
		Long questionId = questionDao.maxLekeBoutiqueQuestionId();
		logger.info("修改习题状态定时任务开始questionId:" + questionId);
		while (questionId != null && questionId > 0) {
			questionId -= 1000L;
			if (questionId < 0L) {
				questionId = 0L;
			}
			int up = questionDao.updateStatusTask(questionId);
			logger.info("执行循环最小questionId:" + questionId + ";修改影响次数:" + up);
		}
		logger.info("修改习题状态定时任务结束");
	}

}
