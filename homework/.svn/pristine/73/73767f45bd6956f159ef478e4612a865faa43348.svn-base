package cn.strong.leke.homework.manage;

import static cn.strong.leke.homework.model.SheetErr.NOMATCH_HOMEWORK;
import static cn.strong.leke.homework.model.SheetErr.PAGE_MISSING;
import static cn.strong.leke.homework.model.SheetErr.PAGE_REPEAT;
import static cn.strong.leke.homework.model.SheetErr.UNKNOWN_ADJUST;
import static cn.strong.leke.homework.model.SheetErr.UNKNOWN_LEKENO;
import static cn.strong.leke.homework.model.SheetErr.UNKNOWN_QRCODE;
import static cn.strong.leke.homework.model.SheetErr.UNSHARP_FILLING;
import static cn.strong.leke.homework.model.SheetErr.UNSHARP_POSITION;
import static cn.strong.leke.homework.model.SheetErr.UNSHARP_SUSPECT;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.homework.dao.mongo.SheetBookDao;
import cn.strong.leke.homework.dao.mongo.SheetPageDao;
import cn.strong.leke.homework.dao.mongo.SheetTaskDao;
import cn.strong.leke.homework.dao.mybatis.HomeworkDao;
import cn.strong.leke.homework.dao.mybatis.HomeworkDtlDao;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.HomeworkDtlInfo;
import cn.strong.leke.homework.model.LekeNoErrDto;
import cn.strong.leke.homework.model.PaperVisitor;
import cn.strong.leke.homework.model.PositionSheetVisitor;
import cn.strong.leke.homework.model.RepeatErrDto;
import cn.strong.leke.homework.model.SheetBook;
import cn.strong.leke.homework.model.SheetErr;
import cn.strong.leke.homework.model.SheetPage;
import cn.strong.leke.homework.model.SheetPage.Range;
import cn.strong.leke.homework.model.SheetResult;
import cn.strong.leke.homework.model.SheetTask;
import cn.strong.leke.homework.model.SheetTask.History;
import cn.strong.leke.homework.model.SheetWriteDto;
import cn.strong.leke.homework.util.HomeworkCst;
import cn.strong.leke.homework.util.SheetCst;
import cn.strong.leke.model.paper.ScoredQuestion;
import cn.strong.leke.model.question.AnswerResult;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionResult;
import cn.strong.leke.model.question.QuestionTemplates;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.paper.sheet.model.position.ChoiceRange;
import cn.strong.leke.paper.sheet.model.position.GroupRange;
import cn.strong.leke.paper.sheet.model.position.IdTypes;
import cn.strong.leke.paper.sheet.model.position.PositionRange;
import cn.strong.leke.paper.sheet.model.position.PositionSheet;
import cn.strong.leke.remote.service.paper.ISheetRemoteService;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;

@Component
public class SheetTaskService {

	private static final Logger logger = LoggerFactory.getLogger(SheetTaskService.class);

	@Resource
	private SheetTaskDao sheetTaskDao;
	@Resource
	private SheetPageDao sheetPageDao;
	@Resource
	private SheetBookDao sheetBookDao;
	@Resource
	private HomeworkDao homeworkDao;
	@Resource
	private HomeworkDtlDao homeworkDtlDao;
	@Resource
	private ISheetRemoteService sheetRemoteService;
	@Resource
	private IUserRemoteService userRemoteService;
	@Resource
	private MessageSender sheetWriteSender;

	private void changeTaskStep(SheetTask sheetTask, Integer step, Integer status) {
		sheetTask.setStep(step);
		sheetTask.getHistorys().add(new History(step));
		if (status != null) {
			sheetTask.setStatus(status);
		}
		this.sheetTaskDao.updateSheetTask(sheetTask);
	}

	/**
	 * 答案卡上传任务处理
	 * @param taskId
	 */
	public void executeSheetTask(String taskId, boolean force) {
		SheetTask sheetTask = this.sheetTaskDao.getSheetTaskById(taskId);
		if (sheetTask.getStep() >= SheetCst.STEP_CHECK) {
			if (force) {
				logger.info("Task({}): Task be enforced, reset the task progress.", taskId);
				// 重置任务进度
				sheetTask.setHistorys(new ArrayList<>(sheetTask.getHistorys().subList(0, 3)));
			} else {
				logger.info("Task({}): Quit because of the repetitive task execution.", taskId);
				return;
			}
		}
		List<SheetPage> sheetPages = this.sheetPageDao.findSheetPageByTaskId(taskId);
		if (sheetPages.isEmpty()) {
			logger.info("Task({}): task abort, not found page.", taskId);
			this.changeTaskStep(sheetTask, SheetCst.STEP_FINISH, SheetCst.STATUS_FINISH_NODATA);
			return;
		}

		// 数据检查
		logger.info("Task({}): start task, total page: {}.", taskId, sheetPages.size());
		this.changeTaskStep(sheetTask, SheetCst.STEP_CHECK, SheetCst.STATUS_PROCESSING);
		sheetTask.setTotalPageNum(sheetPages.size());
		this.pickSheetPageError(sheetPages, sheetTask.getSchoolId());
		long qrcode = sheetPages.stream().filter(v -> UNKNOWN_QRCODE.is(v.getErrorNo())).count();
		long lekeno = sheetPages.stream().filter(v -> UNKNOWN_LEKENO.is(v.getErrorNo())).count();
		long adjust = sheetPages.stream().filter(v -> UNKNOWN_ADJUST.is(v.getErrorNo())).count();
		sheetPages = sheetPages.stream().filter(v -> v.getErrorNo() == 0).collect(toList());
		sheetTask.setValidPageNum(sheetPages.size());
		logger.info("Task({}): check finish, valid page: {}, adjust err: {}, qrcode err: {}, lekeno err: {}.", taskId,
				sheetPages.size(), adjust, qrcode, lekeno);
		if (sheetPages.isEmpty()) {
			logger.info("Task({}): task abort, all pages are error.", taskId);
			this.changeTaskStep(sheetTask, SheetCst.STEP_FINISH, SheetCst.STATUS_FINISH_HASERROR);
			return;
		}

		// 数据分组
		this.changeTaskStep(sheetTask, SheetCst.STEP_MERGE, null);
		List<SheetBook> sheetBooks = this.mergeToSheetBook(sheetPages, taskId);
		logger.info("Task({}): merge finish, book size is: {}.", taskId, sheetBooks.size());

		// 数据匹配
		this.changeTaskStep(sheetTask, SheetCst.STEP_MATCH, null);
		this.matchHomework(sheetBooks);
		long match = sheetBooks.stream().filter(v -> NOMATCH_HOMEWORK.is(v.getErrorNo())).count();
		logger.info("Task({}): match finish, match error: {}.", taskId, match);

		// 数据解析（附页异常检查）
		this.changeTaskStep(sheetTask, SheetCst.STEP_PARSE, null);
		this.pickMarkErrorSheetBook(sheetBooks);
		long repeat = sheetBooks.stream().filter(v -> PAGE_REPEAT.is(v.getErrorNo())).count();
		long missing = sheetBooks.stream().filter(v -> PAGE_MISSING.is(v.getErrorNo())).count();
		long position = sheetBooks.stream().filter(v -> UNSHARP_POSITION.is(v.getErrorNo())).count();
		long filling = sheetBooks.stream().filter(v -> UNSHARP_FILLING.is(v.getErrorNo())).count();
		long suspect = sheetBooks.stream().filter(v -> UNSHARP_SUSPECT.is(v.getErrorNo())).count();
		logger.info(
				"Task({}): parse finish, repeat page err: {}, missing page err: {}, position err: {}, filling err: {}, suspect err: {}.",
				taskId, repeat, missing, position, filling, suspect);

		this.sheetBookDao.deleteSheetBooks(taskId);
		this.sheetBookDao.insertSheetBooks(sheetBooks);
		sheetTask.setTotalBookNum(sheetBooks.size());
		List<SheetBook> validBooks = sheetBooks.stream().filter(v -> SheetErr.noerr(v.getErrorNo())).collect(toList());
		sheetTask.setValidBookNum(validBooks.size());
		if (validBooks.isEmpty()) {
			logger.info("Task({}): task abort, all books are error.", taskId);
			this.changeTaskStep(sheetTask, SheetCst.STEP_FINISH, SheetCst.STATUS_FINISH_HASERROR);
			return;
		}

		// 开始作业写入
		this.changeTaskStep(sheetTask, SheetCst.STEP_WRITE, null);
		validBooks.forEach(book -> sheetWriteSender.send(new SheetWriteDto(book.getId(), false)));
		logger.info("Task({}): write finish, total book: {}.", taskId, validBooks.size());
		this.changeTaskStep(sheetTask, SheetCst.STEP_FINISH, SheetCst.STATUS_FINISH_NOERROR);
	}

	private void pickSheetPageError(List<SheetPage> sheetPages, Long schoolId) {
		// 挑选二维码和定位块识别异常的页
		sheetPages.forEach(page -> {
			if (StringUtils.isBlank(page.getSheetId())) {
				page.setSheetId("-1");
			}
			if (StringUtils.isBlank(page.getLekeNo())) {
				page.setLekeNo("-1");
			}
			if (StringUtils.isBlank(page.getStuno())) {
				page.setStuno("-1");
			}
			if (StringUtils.isNotBlank(page.getClterrorNo()) && "1".equals(page.getClterrorNo())) {
				// 明确标记的识别异常
				page.setErrorNo(UNKNOWN_ADJUST.code);
			} else if (CollectionUtils.isEmpty(page.getPages())) {
				// 没有pages就判定为识别异常
				page.setErrorNo(UNKNOWN_ADJUST.code);
			} else if ("-1".equals(page.getSheetId())) {
				// 没有sheetId就判定为二维码异常
				page.setErrorNo(UNKNOWN_QRCODE.code);
			} else if ("-1".equals(page.getLekeNo()) && "-1".equals(page.getStuno())) {
				// 没有lekeNo就判定为乐号异常
				page.setErrorNo(UNKNOWN_LEKENO.code);
			} else {
				// 标记无异常
				page.setErrorNo(0);
			}
		});

		// 写入页序号
		Predicate<SheetPage> filter2 = v -> !UNKNOWN_ADJUST.code.equals(v.getErrorNo()) && !UNKNOWN_QRCODE.code.equals(v.getErrorNo());
		sheetPages.stream().filter(filter2).collect(groupingBy(SheetPage::getSheetId)).forEach((sheetId, list1) -> {
			PositionSheet sheet = this.sheetRemoteService.getPositionSheet(Long.parseLong(sheetId));
			list1.forEach(sheetPage -> {
				if (CollectionUtils.isNotEmpty(sheetPage.getPages())) {
					Integer pageId = Integer.parseInt(sheetPage.getPages().get(0).getPageId());
					int index = sheet.pkgIndex(pageId);
					sheetPage.setIndex(index + 1);
				}
			});
		});

		// 写入学生信息
		Predicate<SheetPage> filter1 = v -> v.getErrorNo() == 0;

		// 乐号处理为用户ID
		Map<String, List<SheetPage>> typeMap1 = sheetPages
				.stream().filter(filter1).filter(v -> !IdTypes.STUNO.equals(v.getIdtype()))
				.collect(groupingBy(SheetPage::getLekeNo));
		typeMap1.forEach((lekeNo, list) -> {
			Long userId = this.userRemoteService.findUserIdByLoginName(lekeNo);
			if (userId != null) {
				UserBase userBase = UserBaseContext.getUserBaseByUserId(userId);
				list.forEach(page -> {
					page.setUserId(userBase.getId());
					page.setUserName(userBase.getUserName());
				});
			} else {
				list.stream().filter(filter2).forEach(page -> page.setErrorNo(UNKNOWN_LEKENO.code));
			}
		});
		
		// 学号处理为用户ID
		Map<String, List<SheetPage>> typeMap2 = sheetPages.stream().filter(filter1)
				.filter(v -> IdTypes.STUNO.equals(v.getIdtype())).collect(groupingBy(SheetPage::getStuno));
		typeMap2.forEach((stuno, list) -> {
			Long userId = this.userRemoteService.queryUserIdBySnoAndSchoolId(stuno, schoolId);
			if (userId != null) {
				UserBase userBase = UserBaseContext.getUserBaseByUserId(userId);
				list.forEach(page -> {
					page.setUserId(userBase.getId());
					page.setUserName(userBase.getUserName());
				});
			} else {
				list.stream().filter(filter2).forEach(page -> page.setErrorNo(UNKNOWN_LEKENO.code));
			}
		});
		this.sheetPageDao.updateSheetPages(sheetPages);
	}

	// 按SheetId,userId分组形成SheetBook
	private List<SheetBook> mergeToSheetBook(List<SheetPage> sheetPages, String taskId) {
		List<SheetBook> books = new ArrayList<>();
		sheetPages.stream().collect(groupingBy(SheetPage::getSheetId)).forEach((sheetId, list1) -> {
			list1.stream().collect(groupingBy(SheetPage::getUserId)).forEach((userId, list2) -> {
				SheetPage page = list2.get(0);
				SheetBook book = new SheetBook();
				book.setTaskId(taskId);
				book.setUserId(userId);
				book.setUserName(page.getUserName());
				book.setLekeNo(page.getLekeNo());
				book.setStuno(page.getStuno());
				book.setSheetId(sheetId);
				book.setPageIds(list2.stream().map(SheetPage::getId).collect(toList()));
				book.setPages(list2);
				book.setErrorNo(0);
				books.add(book);
			});
		});
		return books;
	}

	// 匹配作业
	private void matchHomework(List<SheetBook> sheetBooks) {
		Predicate<SheetBook> filter = v -> SheetErr.noerr(v.getErrorNo());
		sheetBooks.stream().filter(filter).collect(groupingBy(SheetBook::getSheetId)).forEach((sheetId, list) -> {
			PositionSheet sheet = this.sheetRemoteService.getPositionSheet(Long.parseLong(sheetId));
			Long paperId = sheet.getPaperId();
			list.forEach(sheetBook -> {
				SheetBook matchBook = this.homeworkDtlDao.getMatchSheetHomeworkDtls(paperId, sheetBook.getUserId());
				if (matchBook != null) {
					sheetBook.setHomeworkId(matchBook.getHomeworkId());
					sheetBook.setHomeworkName(matchBook.getHomeworkName());
					sheetBook.setHomeworkDtlId(matchBook.getHomeworkDtlId());
					sheetBook.setClassId(matchBook.getClassId());
					sheetBook.setClassType(matchBook.getClassType());
					sheetBook.setClassName(matchBook.getClassName());
				} else {
					sheetBook.setErrorNo(NOMATCH_HOMEWORK.code);
					sheetBook.setHomeworkName(sheet.getPaperTitle());
				}
			});
		});
	}

	// 区分页数异常，标记异常的试卷，解析正常的试卷并写入结果和填涂异常信息
	private void pickMarkErrorSheetBook(List<SheetBook> sheetBooks) {
		Predicate<SheetBook> filter = v -> v.getErrorNo() == 0;
		sheetBooks.stream().filter(filter).collect(groupingBy(SheetBook::getSheetId)).forEach((sheetId, books) -> {
			PositionSheet positionSheet = this.sheetRemoteService.getPositionSheet(Long.parseLong(sheetId));
			PositionSheetVisitor sheet = PositionSheetVisitor.build(positionSheet);
			PaperVisitor paper = PaperVisitor.build(positionSheet.getPaperId());
			books.forEach(sheetBook -> {
				boolean hasError = this.checkPageError(sheetBook, sheet);
				if (hasError) {
					// 合并其它任务数据
					this.mergeOtherTaskPage(sheetBook);
					// 再次检查页异常
					hasError = this.checkPageError(sheetBook, sheet);
					if (hasError) {
						// 如果还有页异常，直接退出检查
						return;
					}
				}

				// 检查填涂异常
				this.checkFillError(sheetBook, sheet, paper);
			});
		});
	}

	private boolean checkPageError(SheetBook sheetBook, PositionSheetVisitor sheet) {
		sheetBook.setPaperId(sheet.getPaperId());
		// 验证结构页数和上传页数是否匹配
		long distinctPkgCount = sheetBook.getPages().stream().map(SheetPage::getIndex).distinct().count();
		if (distinctPkgCount < sheetBook.getPages().size()) {
			// 重页异常
			sheetBook.setErrorNo(PAGE_REPEAT.code);
			return true;
		}
		if (distinctPkgCount < sheet.getPkgCount().intValue()) {
			// 缺页异常
			sheetBook.setErrorNo(PAGE_MISSING.code);
			return true;
		}
		return false;
	}

	private boolean checkFillError(SheetBook sheetBook, PositionSheetVisitor sheet, PaperVisitor paper) {
		Stream<Range> rangeStream = sheetBook.getPages().stream().flatMap(v -> v.getPages().stream())
				.flatMap(v -> v.getRanges().stream());
		// 对照结构数据和上传数据，生成题目答案及批改数据
		List<SheetResult> results = rangeStream.flatMap(range -> {
			PositionRange prange = sheet.getRange(range.getRangeId());
			if (prange instanceof ChoiceRange) {
				// 选择类题目，由系统批改，不考虑得分
				return this.parseChoiceRange(range, (ChoiceRange) prange, paper);
			} else if (prange instanceof GroupRange) {
				return this.parseOpenedRange(range, (GroupRange) prange, paper);
			} else {
				return Stream.empty();
			}
		}).collect(toList());
		Integer errorNo = 0;
		if (hasErrorNo(results, UNSHARP_SUSPECT)) {
			errorNo = UNSHARP_SUSPECT.code;
		}
		if (hasErrorNo(results, UNSHARP_FILLING)) {
			errorNo = UNSHARP_FILLING.code;
		}
		if (hasErrorNo(results, UNSHARP_POSITION)) {
			errorNo = UNSHARP_POSITION.code;
		}
		sheetBook.setErrorNo(errorNo);
		sheetBook.setResults(results);
		return errorNo == UNSHARP_FILLING.code || errorNo == UNSHARP_POSITION.code;
	}

	// 解析选择型的题目
	private Stream<SheetResult> parseChoiceRange(Range range, ChoiceRange crange, PaperVisitor paper) {
		return IntStream.range(0, crange.getQids().size()).mapToObj(i -> {
			Long questionId = crange.getQids().get(i);
			String result = range.getResults().get(i);
			QuestionDTO questionDTO = paper.getQuestion(questionId);
			SheetResult sheetResult = new SheetResult();
			sheetResult.setQuestionId(questionId);
			if ("-1".equals(result)) {
				// 定位点异常
				sheetResult.setAnswers(new ArrayList<>());
				sheetResult.setErrorNo(UNSHARP_POSITION.code);
			} else if (result.length() > 1 && !QuestionTemplates.MULTI_CHOICE.equals(questionDTO.getTemplate())) {
				// 填涂异常
				sheetResult.setAnswers(new ArrayList<>());
				sheetResult.setErrorNo(UNSHARP_FILLING.code);
			} else if ("".equals(result)) {
				// 疑似异常
				sheetResult.setAnswers(new ArrayList<>());
				sheetResult.setErrorNo(UNSHARP_SUSPECT.code);
			} else if (result.toUpperCase().chars().allMatch(v -> (v - 'A') >= questionDTO.getAnswers().size())) {
				// 超出最大选项
				sheetResult.setAnswers(new ArrayList<>());
				sheetResult.setErrorNo(UNSHARP_FILLING.code);
			} else {
				// 无异常
				IntFunction<String> mapper = v -> questionDTO.getAnswers().get(v - 'A').getAnswerId() + "";
				List<String> answers = result.toUpperCase().chars().mapToObj(mapper).collect(toList());
				sheetResult.setAnswers(answers);
			}
			return sheetResult;
		});
	}

	// 解析问答型的题目
	private Stream<SheetResult> parseOpenedRange(Range range, GroupRange grange, PaperVisitor paper) {
		Long questionId = grange.getQids().get(0);
		ScoredQuestion scoredQuestion = paper.getQuestionScored(questionId);
		SheetResult sheetResult = new SheetResult();
		sheetResult.setIdx(grange.getUiIdx());
		sheetResult.setQuestionId(questionId);
		sheetResult.setAnswers(Arrays.asList("<img src=\"" + range.getImg() + "\" />"));
		
		// GroupRange.uiIdx == 0 才有打分框
		if (grange.getUiIdx() == 0) {
			if ("-1".equals(range.getScore())) {
				// 定位点异常
				sheetResult.setErrorNo(UNSHARP_POSITION.code);
			} else if ("".equals(range.getScore())) {
				// 填涂异常
				sheetResult.setErrorNo(UNSHARP_FILLING.code);
			} else {
				try {
					BigDecimal score = new BigDecimal(range.getScore());
					BigDecimal maxScore = scoredQuestion.getScore().setScale(1, RoundingMode.HALF_UP);
					if (score.compareTo(maxScore) > 0) {
						// 超出最大值
						sheetResult.setErrorNo(UNSHARP_FILLING.code);
					} else if (score.compareTo(maxScore) == 0) {
						// 等于最大值
						sheetResult.setScore(scoredQuestion.getScore());
					} else {
						// 无异常
						sheetResult.setScore(score);
					}
				} catch (NumberFormatException e) {
					// 如果分数不合法，纠正为填涂异常
					range.setScore("");
					sheetResult.setErrorNo(UNSHARP_FILLING.code);
				}
			}
		}
		return Stream.of(sheetResult);
	}

	public List<QuestionResult> parseQuestionResults(PaperVisitor paper, List<SheetResult> sheetResults) {
		// 按题目ID分组
		Map<Long, List<SheetResult>> queMap = sheetResults.stream()
				.collect(groupingBy(SheetResult::getQuestionId, toList()));
		// 数据超过1个的合并
		List<SheetResult> sheetResults2 = queMap.values().stream().map(list -> {
			if (list.size() == 1) {
				return list.get(0);
			}
			list.sort(Comparator.comparingInt(SheetResult::getIdx));
			String answerContent = list.stream().flatMap(v -> v.getAnswers().stream()).collect(joining());
			SheetResult result = list.get(0);
			result.setAnswers(Arrays.asList(answerContent));
			return result;
		}).collect(toList());
		// 转换数据结构
		List<QuestionResult> questions = sheetResults2.stream().map(sheetResult -> {
			Long questionId = sheetResult.getQuestionId();
			QuestionDTO questionDTO = paper.getQuestion(questionId);
			ScoredQuestion scoredQuestion = paper.getQuestionScored(questionId);

			QuestionResult questionResult = new QuestionResult();
			questionResult.setQuestionId(questionId);
			questionResult.setAnswerResults(this.toAnswerResult(sheetResult.getAnswers()));
			if (sheetResult.getScore() != null) {
				boolean isRight = false;
				BigDecimal resultScore = sheetResult.getScore();
				if (resultScore.compareTo(scoredQuestion.getScore()) >= 0) {
					resultScore = scoredQuestion.getScore();
					isRight = true;
				}
				questionResult.setTotalIsRight(isRight);
				questionResult.setTotalResultScore(resultScore);

				if (isLikeFillBlank(questionDTO)) {
					int fillSize = questionDTO.getAnswers().size() - 1;
					questionResult.getAnswerResults().addAll(this.fillBlankAnswerResult(fillSize));
				} else {
					AnswerResult answerResult = questionResult.getAnswerResults().get(0);
					answerResult.setResultScore(resultScore);
					answerResult.setIsRight(isRight);
				}
			}
			return questionResult;
		}).collect(toList());

		Map<Long, QuestionResult> questionMap = questions.stream()
				.collect(toMap(QuestionResult::getQuestionId, Function.identity()));
		Function<ScoredQuestion, QuestionResult> mapper = v -> questionMap.get(v.getQuestionId());
		return paper.getPaperDetail().getGroups().stream().flatMap(v -> v.getQuestions().stream()).map(sq -> {
			if (!sq.getHasSub()) {
				return questionMap.get(sq.getQuestionId());
			}
			List<QuestionResult> subs = sq.getSubs().stream().map(mapper).filter(v -> v != null).collect(toList());
			if (subs.isEmpty()) {
				return null;
			}
			QuestionResult questionResult = new QuestionResult();
			questionResult.setQuestionId(sq.getQuestionId());
			questionResult.setSubs(subs);
			return questionResult;
		}).filter(v -> v != null).collect(toList());
	}

	private List<AnswerResult> toAnswerResult(List<String> answers) {
		return answers.stream().map(v -> {
			AnswerResult answerResult = new AnswerResult();
			answerResult.setMyAnswer(v);
			answerResult.setAnswerSource(HomeworkCst.HOMEWORK_DATA_SOURCE_SHEET);
			return answerResult;
		}).collect(toList());
	}

	private List<AnswerResult> fillBlankAnswerResult(int fillSize) {
		return IntStream.range(0, fillSize).mapToObj(i -> {
			AnswerResult answerResult = new AnswerResult();
			answerResult.setMyAnswer("");
			answerResult.setAnswerSource(HomeworkCst.HOMEWORK_DATA_SOURCE_SHEET);
			return answerResult;
		}).collect(toList());
	}

	// 合并当前任务数据
	private SheetBook mergeCurrentTaskPage(SheetPage sheetPage, LekeNoErrDto lekeNoErrDto) {
		SheetBook sheetBook = this.sheetBookDao.findSheetBookByCurrentTask(sheetPage.getTaskId(),
				sheetPage.getSheetId(), lekeNoErrDto.getUserId());
		if (sheetBook != null) {
			// 合并数据
			List<String> pageIds = new ArrayList<>(sheetBook.getPageIds());
			pageIds.add(lekeNoErrDto.getPageId());
			List<SheetPage> pages = this.sheetPageDao.findSheetPageByIds(pageIds);
			sheetBook.setPages(pages);
			sheetBook.setPageIds(pageIds);
		} else {
			// 创建卷信息
			Homework homework = this.homeworkDao.getHomeworkById(lekeNoErrDto.getHomeworkId());
			sheetBook = new SheetBook();
			sheetBook.setTaskId(sheetPage.getTaskId());
			sheetBook.setLekeNo(lekeNoErrDto.getLekeNo());
			sheetBook.setStuno(lekeNoErrDto.getStuno());
			sheetBook.setSheetId(sheetPage.getSheetId());
			List<String> pageIds = new ArrayList<>();
			pageIds.add(lekeNoErrDto.getPageId());
			sheetBook.setPageIds(pageIds);
			List<SheetPage> pages = new ArrayList<>();
			pages.add(sheetPage);
			sheetBook.setPages(pages);
			sheetBook.setErrorNo(0);
			sheetBook.setUserId(lekeNoErrDto.getUserId());
			sheetBook.setUserName(lekeNoErrDto.getUserName());
			sheetBook.setHomeworkDtlId(lekeNoErrDto.getHomeworkDtlId());
			sheetBook.setClassId(homework.getClassId());
			sheetBook.setClassType(homework.getClassType());
			sheetBook.setClassName(homework.getClassName());
			sheetBook.setHomeworkId(homework.getHomeworkId());
			sheetBook.setHomeworkName(homework.getHomeworkName());
			this.sheetBookDao.insertSheetBooks(Arrays.asList(sheetBook));
		}
		return sheetBook;
	}

	// 合并其它任务数据
	private void mergeOtherTaskPage(SheetBook sheetBook) {
		List<SheetBook> otherBooks = this.sheetBookDao.findSheetBookByOtherTask(sheetBook.getTaskId(),
				sheetBook.getSheetId(), sheetBook.getLekeNo());
		if (otherBooks.isEmpty()) {
			return;
		}
		List<String> pageIds = otherBooks.stream().flatMap(v -> v.getPageIds().stream()).collect(toList());
		otherBooks.stream().peek(v -> v.setPageIds(new ArrayList<>())).forEach(sheetBookDao::updateSheetBook);
		List<SheetPage> pages = this.sheetPageDao.findSheetPageByIds(pageIds);
		sheetBook.getPageIds().addAll(pageIds);
		sheetBook.getPages().addAll(pages);
		if (sheetBook.getId() != null) {
			this.sheetBookDao.updateSheetBook(sheetBook);
		}
	}

	// 处理乐号异常
	public void executeLekeNoUnknowError(LekeNoErrDto lekeNoErrDto) {
		SheetPage sheetPage = this.sheetPageDao.getSheetPageById(lekeNoErrDto.getPageId());
		Validation.isTrue(UNKNOWN_LEKENO.code.equals(sheetPage.getErrorNo()), "该数据已经处理");
		sheetPage.setLekeNo(lekeNoErrDto.getLekeNo());
		sheetPage.setStuno(lekeNoErrDto.getStuno());
		sheetPage.setErrorNo(0);
		this.sheetPageDao.updateSheetPage(sheetPage);

		// 合并当前任务数据
		SheetBook sheetBook = this.mergeCurrentTaskPage(sheetPage, lekeNoErrDto);
		this.sheetBookDao.updateSheetBook(sheetBook);

		// 下面按页异常处理
		this.executeRepeatPageError0(sheetBook);
	}

	public void executeRepeatPageError(RepeatErrDto repeatErrDto) {
		SheetBook sheetBook = this.sheetBookDao.getSheetBookById(repeatErrDto.getBookId());
		Validation.isTrue(PAGE_REPEAT.code.equals(sheetBook.getErrorNo()), "该数据已经处理");
		List<SheetPage> pages = this.sheetPageDao.findSheetPageByIds(repeatErrDto.getPageIds());
		sheetBook.setPageIds(repeatErrDto.getPageIds());
		sheetBook.setPages(pages);
		this.executeRepeatPageError0(sheetBook);
	}

	// 处理页异常
	private void executeRepeatPageError0(SheetBook sheetBook) {
		Long sheetId = Long.parseLong(sheetBook.getSheetId());
		PositionSheet positionSheet = this.sheetRemoteService.getPositionSheet(sheetId);
		PositionSheetVisitor sheet = PositionSheetVisitor.build(positionSheet);
		// 检查页异常
		boolean hasError = this.checkPageError(sheetBook, sheet);
		if (hasError) {
			// 合并其它任务数据
			this.mergeOtherTaskPage(sheetBook);
			// 再次检查页异常
			hasError = this.checkPageError(sheetBook, sheet);
			if (hasError) {
				this.sheetBookDao.updateSheetBook(sheetBook);
				return;
			}
		}
		// 检查填涂异常
		PaperVisitor paper = PaperVisitor.build(positionSheet.getPaperId());
		hasError = this.checkFillError(sheetBook, sheet, paper);
		if (hasError) {
			this.sheetBookDao.updateSheetBook(sheetBook);
			return;
		}
		// 检查疑似异常并写入数据
		this.executeFillingError(sheetBook);
	}

	// 处理填涂异常的答题卡
	public void executeFillingError(SheetBook sheetBook) {
		if (hasErrorNo(sheetBook.getResults(), UNSHARP_SUSPECT)) {
			sheetBook.setErrorNo(UNSHARP_SUSPECT.code);
		} else {
			sheetBook.setErrorNo(0);
		}
		HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlDao.getHomeworkDtlInfoById(sheetBook.getHomeworkDtlId());
		if (homeworkDtlInfo.getSubmitTime() != null) {
			sheetBook.setErrorNo(NOMATCH_HOMEWORK.code);
			this.sheetBookDao.updateSheetBook(sheetBook);
			return;
		}
		// 发送初次写入消息
		this.sheetBookDao.updateSheetBook(sheetBook);
		this.sheetTaskDao.incrValidBookNum(sheetBook.getTaskId());
		this.sheetWriteSender.send(new SheetWriteDto(sheetBook.getId(), false));
	}

	// 处理疑似异常的答题卡
	public void executeSuspectError(SheetBook sheetBook, List<SheetResult> results) {
		sheetBook.setErrorNo(0);
		// 发送重复写入消息
		this.sheetBookDao.updateSheetBook(sheetBook);
		SheetWriteDto sheetWriteDto = new SheetWriteDto(sheetBook.getId(), true);
		sheetWriteDto.setResults(results);
		this.sheetWriteSender.send(sheetWriteDto);
	}

	/**
	 * 任务中正确数据重写入。
	 * @param taskId
	 */
	public void executeNormalReWrite(String taskId) {
		List<String> bookIds = this.sheetBookDao.findNormalBookIdsByTaskId(taskId);
		bookIds.forEach(bookId -> this.sheetWriteSender.send(new SheetWriteDto(bookId, true)));
	}

	// 判断是否包含某个类型的异常
	private static boolean hasErrorNo(List<SheetResult> results, SheetErr error) {
		return results.stream().anyMatch(v -> error.is(v.getErrorNo()));
	}

	private static boolean isLikeFillBlank(QuestionDTO questionDTO) {
		return QuestionTemplates.FILL_BLANK.equals(questionDTO.getTemplate())
				|| QuestionTemplates.PUNCTUATE.equals(questionDTO.getTemplate());
	}
}
