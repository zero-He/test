package cn.strong.leke.homework.controller;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.homework.manage.SheetQueryService;
import cn.strong.leke.homework.model.SheetBook;
import cn.strong.leke.homework.model.SheetPage;
import cn.strong.leke.homework.util.SheetCst;

@Controller
@RequestMapping("/auth/teacher/sheet/*")
public class SheetMatchController {

	@Resource
	private SheetQueryService sheetQueryService;

	@RequestMapping(value = "/match/index", method = RequestMethod.GET)
	public String matchIndex(String bookId, Model model) {
		SheetBook sheetBook = this.sheetQueryService.getSheetBookById(bookId);
		List<SheetPage> sheetPages = this.sheetQueryService.findSheetPageByIds(sheetBook.getPageIds());

		List<String> imgs = sheetPages.stream().flatMap(v -> v.getPages().stream().sorted(SheetCst.COMP_BLOCK))
				.map(v -> v.getImg()).collect(toList());

		model.addAttribute("sheetBook", sheetBook);
		model.addAttribute("sheetPages", sheetPages);
		model.addAttribute("imgs", JsonUtils.toJSON(imgs));
		return "/auth/common/sheet/match/index";
	}
}