/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.controller;

import static cn.strong.leke.model.BaseCache.PREFIX_QUESTION;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.core.nosql.CacheUtils;
import cn.strong.leke.framework.exceptions.LekeRuntimeException;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.question.duplication.InverseDocumentFrequency;
import cn.strong.leke.question.duplication.QuestionContentExtracter;
import cn.strong.leke.question.duplication.TermFrequency;

/**
 * 
 * 描述:
 * 
 * @author liulb
 * @created 2014年8月8日 上午11:28:04
 * @since v1.0.0
 */
@Controller
@RequestMapping("/auth/common/cache")
public class CacheController {

	private static final Logger logger = LoggerFactory
			.getLogger(CacheController.class);

	@RequestMapping("index")
	public void index() {

	}

	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(String prefix, String key) {
		JsonResult json = new JsonResult();
		Object result = null;
		if (StringUtils.isNotBlank(key)) {
			result = CacheUtils.hdel(prefix, key);
		} else {
			result = CacheUtils.delete(prefix);
		}
		json.addDatas("result", result);
		return json;
	}

	@RequestMapping("get")
	@ResponseBody
	public JsonResult get(String prefix, String key) {
		JsonResult json = new JsonResult();
		Object result = null;
		if (StringUtils.isNotBlank(key)) {
			result = CacheUtils.hget(prefix, key);
		} else {
			result = CacheUtils.get(prefix);
		}
		json.addDatas("result", result);
		return json;
	}

	@RequestMapping("idf")
	public void idf(HttpServletResponse response) {
		try {
			IDFWriter idfWriter = new IDFWriter();

			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=\"idf.txt\"");
			response.setContentType("application/octet-stream");
			PrintWriter writer = response.getWriter();
			idfWriter.write(writer);
		} catch (Exception e) {
			logger.error("idf error", e);
			throw new LekeRuntimeException("idf error", e);
		}
	}

	private static class IDFWriter {
		private static final int PAGE_SIZE = 20;
		private static final long SUBJECT_ENGLISH = 3;

		private InverseDocumentFrequency chinese = new InverseDocumentFrequency();
		private InverseDocumentFrequency english = new InverseDocumentFrequency();

		public void write(PrintWriter writer) {
			build();
			write(writer, chinese);
			write(writer, english);
		}

		private void write(PrintWriter writer, InverseDocumentFrequency idf) {
			if (idf.getDocs() > 0) {
				writer.print("== docs: ");
				writer.print(idf.getDocs());
				writer.print(" ==");
				writer.println();
			}
			for (Map.Entry<String, Double> entry : idf.idfs().entrySet()) {
				writer.print(entry.getKey());
				writer.print('\t');
				writer.print(entry.getValue());
				writer.println();
			}
			writer.flush();
		}

		public void build() {
			Set<String> qids = CacheUtils.hkeys(PREFIX_QUESTION);
			List<String> page = new ArrayList<String>();
			for (String qid : qids) {
				page.add(qid);
				if (page.size() == PAGE_SIZE) {
					buildPage(page);
					page.clear();
				}
			}
			if (!page.isEmpty()) {
				buildPage(page);
			}
		}

		private void buildPage(List<String> page) {
			if (page.isEmpty()) {
				return;
			}
			String[] ids = new String[page.size()];
			ids = page.toArray(ids);
			List<QuestionDTO> questions = CacheUtils
					.hmget(PREFIX_QUESTION, ids);
			for (QuestionDTO question : questions) {
				add(question);
			}
		}

		private void add(QuestionDTO question) {
			if (question == null) {
				return;
			}
			Long subjectId = question.getSubjectId();
			boolean isEnglish = subjectId != null
					&& subjectId.equals(SUBJECT_ENGLISH);
			InverseDocumentFrequency idf = isEnglish ? english : chinese;
			TermFrequency tf = QuestionContentExtracter
					.getTermFrequency(question);
			idf.add(tf);
		}

	}

}
