/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.base.SchoolStageContext;
import cn.strong.leke.context.repository.RepoRoles;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.framework.exceptions.LekeRuntimeException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.model.WordImportRecord;
import cn.strong.leke.question.model.WordImportRecordDTO;
import cn.strong.leke.question.model.WordImportRecordError;
import cn.strong.leke.question.service.QuestionManager;
import cn.strong.leke.question.service.WordImportRecordService;
import cn.strong.leke.question.word.DocCommandParser;
import cn.strong.leke.question.word.DocTextExtracter;
import cn.strong.leke.question.word.ParseCsts.CommandNames;
import cn.strong.leke.question.word.QuestionPartParser;
import cn.strong.leke.question.word.Word2DocConverter;
import cn.strong.leke.question.word.model.Command;
import cn.strong.leke.question.word.model.Doc;
import cn.strong.leke.question.word.model.QuestionPart;
import cn.strong.leke.question.word.model.WordQuestionInfo;
import cn.strong.leke.remote.model.tukor.GradeRemote;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;

/**
 * Word 习题导入控制器
 * 
 * @author liulongbiao
 * @created 2014年12月12日 上午11:27:00
 * @since v3.2
 */
@Controller
@RequestMapping("/auth/common/word")
public class QuestionWordImportController {

	private static final Logger logger = LoggerFactory
			.getLogger(QuestionWordImportController.class);

	@Autowired
	private Word2DocConverter word2DocConverter;
	@Autowired
	private DocCommandParser docCommandParser;
	@Autowired
	private QuestionPartParser questionPartParser;
	@Autowired
	private WordImportRecordService wordImportRecordService;
	@Autowired
	private QuestionManager questionManager;

	@RequestMapping("index")
	public void index() {
		// WORD 导入页面
	}

	@RequestMapping("getRecord")
	@ResponseBody
	public JsonResult getRecord(String recordId) {
		JsonResult json = new JsonResult();
		WordImportRecord record = wordImportRecordService
				.getWordImportRecord(recordId);
		json.addDatas("record", record);
		return json;
	}

	@RequestMapping("wordImport")
	@ResponseBody
	public JsonResult wordImport(String dataJson) {
		JsonResult result = new JsonResult();
		List<WordQuestionInfo> infos = JsonUtils.readList(dataJson, WordQuestionInfo.class);
		List<String> errors = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(infos)) {
			for (WordQuestionInfo info : infos) {
				try {
					processOneRecord(info);
				} catch (Exception e) {
					logger.warn(e.getMessage(), e);
					errors.add(e.getMessage());
				}
			}
		}
		if (CollectionUtils.isNotEmpty(errors)) {
			result.setErr(StringUtils.join(errors, '\n'));
		}
		return result;
	}

	private void processOneRecord(WordQuestionInfo info) {
		try {
			preprcessInfo(info);

			InputStream input = readWordFile(info.getFileId());
			Doc doc = word2DocConverter.convert(input);
			if (doc == null) {
				throw new RuntimeException("que.word.file.read.error");
			}
			List<QuestionPart> parts = docCommandParser.parse(doc);
			processQuestionParts(parts, info);
		} catch (Exception e) {
			throw new LekeRuntimeException("que.word.file.error.composite", e,
					info.getFileName(), e.getMessage());
		} finally {
			doAfterImported(info);
		}
	}

	private void doAfterImported(WordQuestionInfo info) {
		String fileId = info.getFileId();
		if (StringUtils.isNotEmpty(info.getFileId())) {
			FileUtils.delete(fileId);
		}
	}

	/*
	 * 预处理 info 信息
	 */
	private void preprcessInfo(WordQuestionInfo info) {
		if (StringUtils.isEmpty(info.getRecordId())) {
			throw new LekeRuntimeException("que.word.recordId.missing");
		}
		if (StringUtils.isEmpty(info.getFileId())) {
			throw new LekeRuntimeException("que.word.fileId.missing");
		}
		User user = UserContext.user.get();
		info.setUserId(user.getId());
		info.setUserName(user.getUserName());
		info.setRoleId(user.getCurrentRole().getId());
		School school = user.getCurrentSchool();
		if (school != null) {
			info.setSchoolId(school.getId());
			info.setSchoolName(school.getName());
		}

		SortedSet<Long> gradeIds = getGradeIds(info.getSchoolStageId());
		info.setDefaultGradeId(gradeIds.last());
	}

	private SortedSet<Long> getGradeIds(Long schoolStageId) {
		SortedSet<Long> result = new TreeSet<Long>();
		SchoolStageRemote stage = SchoolStageContext
				.getSchoolStage(schoolStageId);
		List<GradeRemote> grades = stage.getGrades();
		if (CollectionUtils.isNotEmpty(grades)) {
			for (GradeRemote grade : grades) {
				result.add(grade.getGradeId());
			}
		}
		return result;
	}

	/*
	 * 处理习题部分
	 */
	private void processQuestionParts(List<QuestionPart> parts,
			WordQuestionInfo info) {
		if (CollectionUtils.isEmpty(parts)) {
			throw new LekeRuntimeException("que.word.parts.empty");
		}
		String recordId = info.getRecordId();
		WordImportRecord record = newWordImportRecord(info, parts.size());
		wordImportRecordService.addWordImportRecord(record);
		try {
			int idx = 0;
			for (QuestionPart part : parts) {
				idx++;
				try {
					processPart(part, info);
					wordImportRecordService.updateIncSuccessedCount(recordId);
				} catch (Exception e) {
					WordImportRecordError error = new WordImportRecordError();
					error.setOrd(idx);
					error.setError(e.getMessage());
					error.setRecordId(record.getRecordId());
					error.setSummary(getStemSummary(part));
					wordImportRecordService.addWordImportRecordError(error);
				}
			}
			wordImportRecordService.updateProcessStatus(recordId,
					WordImportRecord.STATUS_FINISH);
		} catch (Exception ex) {
			wordImportRecordService.updateProcessStatus(recordId,
					WordImportRecord.STATUS_ERROR);
		}
	}

	/*
	 * 处理单个习题部分
	 */
	private void processPart(QuestionPart part, WordQuestionInfo info) {
		QuestionDTO dto = questionPartParser.parse(part, info);
		if (dto == null) {
			throw new LekeRuntimeException("que.word.part.parsed.null");
		}
		Long gradeId = dto.getGradeId();
		if (gradeId == null) {
			dto.setGradeId(info.getDefaultGradeId());
		}
		initUserInfo(dto, info);

		questionManager.addImportedQuestion(dto, info);
	}

	private void initUserInfo(QuestionDTO dto, WordQuestionInfo info) {
		Long userId = info.getUserId();
		String userName = info.getUserName();
		dto.setCreatedBy(userId);
		dto.setModifiedBy(userId);
		dto.setCreatorName(userName);
		dto.setOperatorName(userName);
		dto.setSchoolId(info.getSchoolId());
		dto.setSchoolName(info.getSchoolName());

		Long roleId = info.getRoleId();
		boolean sharePlatform = RepoRoles.isDefaultSharePlatform(roleId);
		boolean shareSchool = RepoRoles.isDefaultShareSchool(roleId);
		boolean sharePersonal = sharePlatform == false && shareSchool == false;

		dto.setSharePersonal(sharePersonal);
		dto.setShareSchool(shareSchool);
		dto.setSharePlatform(sharePlatform);
	}

	private WordImportRecord newWordImportRecord(WordQuestionInfo info,
			int total) {
		WordImportRecord record = new WordImportRecord();
		record.setRecordId(info.getRecordId());
		record.setSchoolStageId(info.getSchoolStageId());
		record.setSchoolStageName(info.getSchoolStageName());
		record.setSubjectId(info.getSubjectId());
		record.setSubjectName(info.getSubjectName());
		record.setFileId(info.getFileId());
		record.setFileName(info.getFileName());
		record.setUserId(info.getUserId());
		record.setUserName(info.getUserName());
		record.setTotal(total);
		record.setSuccessed(0);
		record.setFailed(0);
		record.setStatus(WordImportRecord.STATUS_PROCESSING);
		return record;
	}

	/*
	 * 抽取题文概要内容
	 */
	private String getStemSummary(QuestionPart part) {
		String result = "";
		Command cmd = part.getCommand(CommandNames.STEM);
		if (cmd != null) {
			result = "【题文】" + DocTextExtracter.extract(cmd.getContent());
		}
		if (result.length() > 35) {
			result = result.substring(0, 32) + "...";
		}
		return result;
	}

	/*
	 * 读取文档内容
	 */
	private InputStream readWordFile(String fileId) {
		byte[] bytes = FileUtils.download(fileId);
		if (bytes == null || bytes.length <= 0) {
			throw new LekeRuntimeException("que.word.file.not.found");
		}
		return new ByteArrayInputStream(bytes);
	}

	@RequestMapping("delError")
	@ResponseBody
	public JsonResult delError(WordImportRecordError error) {
		JsonResult result = new JsonResult();
		wordImportRecordService.deleteWordImportRecordError(error);
		return result;
	}

	@RequestMapping("myRecords")
	@ResponseBody
	public JsonResult myRecords(Page page) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		List<WordImportRecordDTO> records = wordImportRecordService
				.queryMyWordImportRecord(userId, page);
		json.addDatas("records", records);
		json.addDatas("page", page);
		return json;
	}

}
