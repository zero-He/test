package cn.strong.leke.homework.controller;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.manage.SheetQueryService;
import cn.strong.leke.homework.manage.SheetTaskService;
import cn.strong.leke.homework.model.RepeatErrDto;
import cn.strong.leke.homework.model.SheetBook;
import cn.strong.leke.homework.model.SheetPage;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.homework.service.HomeworkService;
import cn.strong.leke.homework.util.SheetCst;
import cn.strong.leke.remote.service.paper.ISheetRemoteService;

@Controller
@RequestMapping("/auth/teacher/sheet/*")
public class SheetPageController {

	@Resource
	private SheetTaskService sheetTaskService;
	@Resource
	private SheetQueryService sheetQueryService;
	@Resource
	private HomeworkService homeworkService;
	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private ISheetRemoteService sheetRemoteService;

	@RequestMapping(value = "/repeat/index", method = RequestMethod.GET)
	public String repeatIndex(String bookId, Model model) {
		model.addAttribute("bookId", bookId);
		return "/auth/common/sheet/repeat/index";
	}

	@ResponseBody
	@RequestMapping("/repeat/get")
	public Object repeatGet(String bookId) {
		SheetBook sheetBook = this.sheetQueryService.getSheetBookById(bookId);
		List<SheetPage> sheetPages = this.sheetQueryService.findSheetPageByIds(sheetBook.getPageIds());
		sheetPages.sort((a, b) -> Integer.compare(a.getIndex(), b.getIndex()));

		List<String> allImgs = new ArrayList<>();
		List<Map<String, Object>> pages = sheetPages.stream().map(page -> {
			List<String> imgs = page.getPages().stream().sorted(SheetCst.COMP_BLOCK).map(v -> v.getImg())
					.collect(toList());
			Map<String, Object> item = new HashMap<>();
			item.put("pageId", page.getId());
			item.put("index", page.getIndex());
			item.put("imgs", imgs);
			allImgs.addAll(imgs);
			return item;
		}).collect(toList());

		return new JsonResult().addDatas("userName", sheetBook.getUserName()).addDatas("bookId", bookId)
				.addDatas("pages", pages).addDatas("imgs", allImgs);
	}

	@ResponseBody
	@RequestMapping("/repeat/next")
	public Object repeatNext(String bookId) {
		SheetBook sheetBook = this.sheetQueryService.getNextRepeatSheetBookById(bookId);
		return new JsonResult().addDatas("nextBookId", sheetBook != null ? sheetBook.getId() : null);
	}

	@ResponseBody
	@RequestMapping("/repeat/save")
	public Object repeatSave(String dataJson) {
		RepeatErrDto repeatErrDto = JsonUtils.fromJSON(dataJson, RepeatErrDto.class);
		this.sheetTaskService.executeRepeatPageError(repeatErrDto);
		SheetBook sheetBook = this.sheetQueryService.getNextRepeatSheetBookById(repeatErrDto.getBookId());
		return new JsonResult().addDatas("nextBookId", sheetBook != null ? sheetBook.getId() : null);
	}

	/**
	 * 缺页异常查看
	 */
	@RequestMapping(value = "/misspg/index", method = RequestMethod.GET)
	public String misspgIndex(String bookId, Model model) {
		SheetBook sheetBook = this.sheetQueryService.getSheetBookById(bookId);
		List<SheetPage> sheetPages = this.sheetQueryService.findSheetPageByIds(sheetBook.getPageIds());
		List<String> imgs = sheetPages.stream().flatMap(v -> v.getPages().stream().sorted(SheetCst.COMP_BLOCK))
				.map(v -> v.getImg()).collect(toList());
		model.addAttribute("sheetBook", sheetBook);
		model.addAttribute("imgs", JsonUtils.toJSON(imgs));
		return "/auth/common/sheet/misspg/index";
	}

	/**
	 * 缺页异常查看
	 */
	@RequestMapping(value = "/write/index", method = RequestMethod.GET)
	public String writeIndex(String bookId, Model model) {
		SheetBook sheetBook = this.sheetQueryService.getSheetBookById(bookId);
		List<SheetPage> sheetPages = this.sheetQueryService.findSheetPageByIds(sheetBook.getPageIds());
		List<String> imgs = sheetPages.stream().flatMap(v -> v.getPages().stream().sorted(SheetCst.COMP_BLOCK))
				.map(v -> v.getImg()).collect(toList());
		model.addAttribute("sheetBook", sheetBook);
		model.addAttribute("imgs", JsonUtils.toJSON(imgs));
		return "/auth/common/sheet/misspg/index";
	}
}