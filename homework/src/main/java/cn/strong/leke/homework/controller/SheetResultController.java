package cn.strong.leke.homework.controller;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.manage.SheetQueryService;
import cn.strong.leke.homework.manage.SheetTaskService;
import cn.strong.leke.homework.model.PaperVisitor;
import cn.strong.leke.homework.model.PositionSheetVisitor;
import cn.strong.leke.homework.model.SheetBook;
import cn.strong.leke.homework.model.SheetChoiceRule;
import cn.strong.leke.homework.model.SheetErr;
import cn.strong.leke.homework.model.SheetOpenedRule;
import cn.strong.leke.homework.model.SheetPage;
import cn.strong.leke.homework.model.SheetRangeRule;
import cn.strong.leke.homework.model.SheetResult;
import cn.strong.leke.homework.model.SheetTitleRule;
import cn.strong.leke.homework.model.XPosition;
import cn.strong.leke.homework.util.SheetCst;
import cn.strong.leke.model.paper.ScoredQuestion;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionTemplates;
import cn.strong.leke.paper.sheet.model.position.ChoiceRange;
import cn.strong.leke.paper.sheet.model.position.GroupRange;
import cn.strong.leke.paper.sheet.model.position.PositionSheet;
import cn.strong.leke.remote.service.paper.ISheetRemoteService;

@Controller
@RequestMapping("/auth/teacher/sheet/*")
public class SheetResultController {

	@Resource
	private SheetTaskService sheetTaskService;
	@Resource
	private SheetQueryService sheetQueryService;
	@Resource
	private ISheetRemoteService sheetRemoteService;

	/**
	 * 填涂异常处理页页面
	 * @return
	 */
	@RequestMapping(value = "/filling/index", method = RequestMethod.GET)
	public String fillingIndex(String bookId, Model model) {
		model.addAttribute("bookId", bookId);
		return "/auth/common/sheet/filling/index";
	}

	/**
	 * 填涂异常数据获取
	 * @param bookId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/filling/get")
	public Object fillingGet(String bookId) {
		SheetBook sheetBook = this.sheetQueryService.getSheetBookById(bookId);
		List<SheetPage> pages = this.sheetQueryService.findSheetPageByIds(sheetBook.getPageIds());
		List<String> imgs = pages.stream().flatMap(v -> v.getPages().stream().sorted(SheetCst.COMP_BLOCK))
				.map(v -> v.getImg()).collect(toList());
		List<SheetResult> results = sheetBook.getResults().stream().filter(v -> SheetErr.errfill(v.getErrorNo()))
				.collect(toList());
		List<SheetRangeRule> rules = this.parseFillingRules(sheetBook, results);
		return new JsonResult().addDatas("bookId", bookId).addDatas("userName", sheetBook.getUserName())
				.addDatas("errorNo", "20302").addDatas("imgs", imgs).addDatas("results", results)
				.addDatas("rules", rules);
	}

	/**
	 * 填涂异常跳过
	 * @param bookId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/filling/next")
	public Object fillingNext(String bookId) {
		SheetBook sheetBook = this.sheetQueryService.getNextFillingSheetBookById(bookId);
		return new JsonResult().addDatas("nextBookId", sheetBook != null ? sheetBook.getId() : null);
	}

	/**
	 * 疑似异常保存
	 * @param dataJson
	 * @param bookId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/filling/save")
	public Object fillingSave(String dataJson, String bookId) {
		SheetBook sheetBook = this.sheetQueryService.getSheetBookById(bookId);
		Validation.isTrue(SheetErr.UNSHARP_FILLING.code.equals(sheetBook.getErrorNo()), "该数据已经处理");
		List<SheetResult> results = JsonUtils.readList(dataJson, SheetResult.class);
		this.mergeSheetResult(sheetBook, results);
		this.sheetTaskService.executeFillingError(sheetBook);

		sheetBook = this.sheetQueryService.getNextFillingSheetBookById(bookId);
		return new JsonResult().addDatas("nextBookId", sheetBook != null ? sheetBook.getId() : null);
	}

	/**
	 * 疑似异常处理页页面
	 * @return
	 */
	@RequestMapping(value = "/suspect/index", method = RequestMethod.GET)
	public String suspectIndex(String bookId, Model model) {
		model.addAttribute("bookId", bookId);
		return "/auth/common/sheet/suspect/index";
	}

	/**
	 * 疑似异常数据获取
	 * @param bookId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/suspect/get")
	public Object suspectGet(String bookId) {
		SheetBook sheetBook = this.sheetQueryService.getSheetBookById(bookId);
		List<SheetPage> pages = this.sheetQueryService.findSheetPageByIds(sheetBook.getPageIds());
		List<String> imgs = pages.stream().flatMap(v -> v.getPages().stream().sorted(SheetCst.COMP_BLOCK))
				.map(v -> v.getImg()).collect(toList());
		List<SheetResult> results = sheetBook.getResults().stream()
				.filter(v -> SheetErr.UNSHARP_SUSPECT.is(v.getErrorNo())).collect(toList());
		List<SheetRangeRule> rules = this.parseFillingRules(sheetBook, results);
		return new JsonResult().addDatas("bookId", bookId).addDatas("userName", sheetBook.getUserName())
				.addDatas("errorNo", "20401").addDatas("imgs", imgs).addDatas("results", results)
				.addDatas("rules", rules);
	}

	/**
	 * 疑似异常跳过
	 * @param bookId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/suspect/next")
	public Object suspectNext(String bookId) {
		SheetBook sheetBook = this.sheetQueryService.getNextSuspectSheetBookById(bookId);
		return new JsonResult().addDatas("nextBookId", sheetBook != null ? sheetBook.getId() : null);
	}

	/**
	 * 疑似异常保存
	 * @param dataJson
	 * @param bookId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/suspect/save")
	public Object suspectSave(String dataJson, String bookId) {
		SheetBook sheetBook = this.sheetQueryService.getSheetBookById(bookId);
		Validation.isTrue(SheetErr.UNSHARP_SUSPECT.code.equals(sheetBook.getErrorNo()), "该数据已经处理");
		List<SheetResult> results = JsonUtils.readList(dataJson, SheetResult.class);
		this.mergeSheetResult(sheetBook, results);
		this.sheetTaskService.executeSuspectError(sheetBook, results);

		sheetBook = this.sheetQueryService.getNextSuspectSheetBookById(bookId);
		return new JsonResult().addDatas("nextBookId", sheetBook != null ? sheetBook.getId() : null);
	}

	private void mergeSheetResult(SheetBook sheetBook, List<SheetResult> results) {
		Map<Long, SheetResult> resultMap = results.stream().peek(result -> result.setErrorNo(null))
				.collect(toMap(SheetResult::getQuestionId, Function.identity()));
		results = sheetBook.getResults().stream().map(result -> {
			if (result.getIdx() == 0 && resultMap.containsKey(result.getQuestionId())) {
				SheetResult newSheetResult = resultMap.get(result.getQuestionId());
				result.setScore(newSheetResult.getScore());
			}
			return result;
		}).collect(toList());
		sheetBook.setResults(results);
	}

	private List<SheetRangeRule> parseFillingRules(SheetBook sheetBook, List<SheetResult> results) {
		Set<Long> qidSet = results.stream().map(SheetResult::getQuestionId).collect(toSet());
		PaperVisitor paper = PaperVisitor.build(sheetBook.getPaperId());
		PositionSheet psheet = sheetRemoteService.getPositionSheet(Long.parseLong(sheetBook.getSheetId()));
		PositionSheetVisitor sheet = new PositionSheetVisitor(psheet);
		Map<Long, XPosition> zoomMap = this.parseQuestionPosition(psheet);

		List<SheetRangeRule> allrules = new ArrayList<>();
		paper.getPaperDetail().getGroups().forEach(sg -> {
			List<SheetRangeRule> rules = new ArrayList<>();
			sg.getQuestions().forEach(sq -> {
				if (!sq.getHasSub()) {
					if (qidSet.contains(sq.getQuestionId())) {
						String questionNo = String.valueOf(sq.getOrd()) + ".";
						rules.add(this.parseSheetQuestionRule(questionNo, sq, paper, sheet, zoomMap));
					}
					return;
				}
				for (int i = 0; i < sq.getSubs().size(); i++) {
					ScoredQuestion subsq = sq.getSubs().get(i);
					if (qidSet.contains(subsq.getQuestionId())) {
						String questionNo = String.valueOf(sq.getOrd()) + ".(" + (i + 1) + ")";
						rules.add(this.parseSheetQuestionRule(questionNo, subsq, paper, sheet, zoomMap));
					}
				}
			});
			if (!rules.isEmpty()) {
				SheetTitleRule rule = new SheetTitleRule();
				rule.setTitle(sg.getGroupTitle());
				allrules.add(rule);
				allrules.addAll(rules);
			}
		});
		return allrules;
	}

	private SheetRangeRule parseSheetQuestionRule(String questionNo, ScoredQuestion sq, PaperVisitor paper,
			PositionSheetVisitor sheet, Map<Long, XPosition> zoomMap) {
		QuestionDTO question = paper.getQuestion(sq.getQuestionId());
		if (QuestionTemplates.JUDGEMENT.equals(question.getTemplate())
				|| QuestionTemplates.SINGLE_CHOICE.equals(question.getTemplate())
				|| QuestionTemplates.MULTI_CHOICE.equals(question.getTemplate())) {
			SheetChoiceRule choiceRule = new SheetChoiceRule();
			choiceRule.setQid(sq.getQuestionId());
			choiceRule.setQno(questionNo);
			choiceRule
					.setAnswers(question.getAnswers().stream().map(v -> v.getAnswerId().toString()).collect(toList()));
			choiceRule.setJudge(QuestionTemplates.JUDGEMENT.equals(question.getTemplate()));
			choiceRule.setMulti(QuestionTemplates.MULTI_CHOICE.equals(question.getTemplate()));
			choiceRule.setZone(zoomMap.get(sq.getQuestionId()));
			return choiceRule;
		} else {
			SheetOpenedRule openedRule = new SheetOpenedRule();
			openedRule.setQid(sq.getQuestionId());
			openedRule.setQno(questionNo);
			openedRule.setMaxScore(sq.getScore());
			openedRule.setZone(zoomMap.get(sq.getQuestionId()));
			return openedRule;
		}
	}

	private Map<Long, XPosition> parseQuestionPosition(PositionSheet sheet) {
		Map<Long, XPosition> result = new HashMap<>();
		sheet.getPages().forEach(page -> {
			int idx = Integer.parseInt(page.getPage());
			page.getRanges().forEach(range -> {
				if (range instanceof ChoiceRange) {
					ChoiceRange crange = (ChoiceRange) range;
					//	int x = crange.getFirst().getX();
					int y = crange.getZone().getY() - 35;
					int h = crange.getZone().getH() + 35;
					int w = crange.getFirst().getW();
					int dsplit = crange.getDsplit();
					for (int i = 0; i < crange.getCount(); i++) {
						Long qid = crange.getQids().get(i);
						int x = crange.getFirst().getX() + crange.getFirst().getW() * i + crange.getDiff().getX() * i
								+ (i / 5) * (dsplit - crange.getDiff().getX());
						result.put(qid, new XPosition(idx, x, y, w, h));
					}
				} else if (range instanceof GroupRange) {
					GroupRange grange = (GroupRange) range;
					if (grange.getUiIdx() == 0) {
						Long qid = ((GroupRange) range).getQids().get(0);
						result.put(qid, new XPosition(idx, range.getZone()));
					}
				}
			});
		});
		result.values().stream().forEach(v -> {
			if (v.getX() > 1240) {
				v.setX(v.getX() - 1240);
			}
			v.max(12);
		});
		return result;
	}
}
