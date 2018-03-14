/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.question.dao.mybatis.WordImportRecordDao;
import cn.strong.leke.question.dao.mybatis.WordImportRecordErrorDao;
import cn.strong.leke.question.model.WordImportRecord;
import cn.strong.leke.question.model.WordImportRecordDTO;
import cn.strong.leke.question.model.WordImportRecordError;
import cn.strong.leke.question.service.WordImportRecordService;

/**
 * 
 * 
 * @author liulongbiao
 * @created 2014年12月12日 下午4:44:08
 * @since v3.2
 */
@Service
public class WordImportRecordServiceImpl implements WordImportRecordService {

	@Autowired
	private WordImportRecordDao wordImportRecordDao;
	@Autowired
	private WordImportRecordErrorDao wordImportRecordErrorDao;

	@Override
	public void addWordImportRecord(WordImportRecord record) {
		wordImportRecordDao.addWordImportRecord(record);
	}

	@Override
	public void updateIncSuccessedCount(String recordId) {
		wordImportRecordDao.incSuccessedCount(recordId);
	}

	@Override
	public void updateProcessStatus(String recordId, int status) {
		WordImportRecord record = new WordImportRecord();
		record.setRecordId(recordId);
		record.setStatus(status);
		wordImportRecordDao.updateProcessStatus(record);
	}

	@Override
	public WordImportRecordDTO getWordImportRecord(String recordId) {
		WordImportRecordDTO result = wordImportRecordDao
				.getWordImportRecord(recordId);
		if (result != null) {
			List<WordImportRecordError> errors = wordImportRecordErrorDao
					.findWordImportRecordErrors(recordId);
			result.setErrors(errors);
		}
		return result;
	}

	@Override
	public void addWordImportRecordError(WordImportRecordError error) {
		if (error == null || StringUtils.isEmpty(error.getRecordId())) {
			throw new ValidateException("que.word.recordId.missing");
		}
		String recordId = error.getRecordId();
		wordImportRecordErrorDao.addWordImportRecordError(error);
		wordImportRecordDao.incFailedCount(recordId);
	}

	@Override
	public void deleteWordImportRecordError(WordImportRecordError error) {
		if (error == null || StringUtils.isEmpty(error.getRecordId())
				|| error.getErrorId() == null) {
			throw new ValidateException("que.word.error.info.incomplete");
		}
		wordImportRecordErrorDao
				.deleteWordImportRecordError(error.getErrorId());

		String recordId = error.getRecordId();
		int remainFailed = wordImportRecordErrorDao
				.countErrorsByRecordId(recordId);
		WordImportRecord record = new WordImportRecord();
		record.setRemainFailed(remainFailed);
		record.setRecordId(recordId);
		wordImportRecordDao.updateRemainFailedCount(record);
	}

	@Override
	public List<WordImportRecordDTO> queryMyWordImportRecord(Long userId,
			Page page) {
		List<WordImportRecordDTO> result = wordImportRecordDao
				.queryMyWordImportRecord(userId, page);
		if (CollectionUtils.isNotEmpty(result)) {
			for (WordImportRecordDTO record : result) {
				List<WordImportRecordError> errors = wordImportRecordErrorDao
						.findWordImportRecordErrors(record.getRecordId());
				record.setErrors(errors);
			}
		}
		return result;
	}

}
