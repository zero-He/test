/**
 * 
 */
package cn.strong.leke.question.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.ImageUtils;
import cn.strong.leke.common.utils.ImageUtils.Dimension;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.model.paper.ScoredQuestion;
import cn.strong.leke.model.question.Analysis;
import cn.strong.leke.model.question.Answer;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionStem;
import cn.strong.leke.question.service.IQueImageProcessor;
import cn.strong.leke.question.word.model.Img;
import cn.strong.leke.question.word.model.ImgDim;

/**
 * @author liulongbiao
 *
 */
@Service
public class QueImageProcessor implements IQueImageProcessor {

	private static Logger LOG = LoggerFactory.getLogger(QueImageProcessor.class);
	private static final Pattern PAT_B64 = Pattern
			.compile("<img\\s[^>]*?src\\s*=\\s*['\"]data:image/(\\w+);base64,([^'\"]*?)['\"][^>]*?>");

	@Override
	public void processBase64Images(QuestionDTO dto) {
		process(dto);
	}

	private void process(QuestionDTO dto) {
		if (dto == null) {
			return;
		}
		QuestionStem stem = dto.getStem();
		if (stem != null) {
			stem.setStemContent(processBase64Images(stem.getStemContent()));
		}

		List<Answer> answers = dto.getAnswers();
		if (CollectionUtils.isNotEmpty(answers)) {
			for (Answer answer : answers) {
				answer.setAnswerContent(processBase64Images(answer.getAnswerContent()));
			}
		}

		Analysis analysis = dto.getAnalysis();
		if (analysis != null) {
			analysis.setAnalysisContent(processBase64Images(analysis.getAnalysisContent()));
		}

		List<QuestionDTO> subs = dto.getSubs();
		if (CollectionUtils.isNotEmpty(subs)) {
			for (QuestionDTO sub : subs) {
				processBase64Images(sub);
			}
		}
	}

	@Override
	public void processBase64Images(ScoredQuestion dto) {
		process(dto);
	}

	private void process(ScoredQuestion dto) {
		if (dto == null) {
			return;
		}

		QuestionStem stem = dto.getStem();
		if (stem != null) {
			stem.setStemContent(processBase64Images(stem.getStemContent()));
		}

		List<Answer> answers = dto.getAnswers();
		if (CollectionUtils.isNotEmpty(answers)) {
			for (Answer answer : answers) {
				answer.setAnswerContent(processBase64Images(answer.getAnswerContent()));
			}
		}

		Analysis analysis = dto.getAnalysis();
		if (analysis != null) {
			analysis.setAnalysisContent(processBase64Images(analysis.getAnalysisContent()));
		}

		List<ScoredQuestion> subs = dto.getSubs();
		if (CollectionUtils.isNotEmpty(subs)) {
			for (ScoredQuestion sub : subs) {
				processBase64Images(sub);
			}
		}
	}

	private String processBase64Images(String content) {
		if (content == null || content.isEmpty()) {
			return content;
		}
		Matcher mat = PAT_B64.matcher(content);
		if (!mat.find()) {
			return content;
		}
		StringBuilder sb = new StringBuilder();
		int idx = 0;
		do {
			sb.append(content.substring(idx, mat.start()));
			String img = saveBase64Image(mat.group(2), mat.group(1));
			sb.append(img);
			
			idx = mat.end();
		} while(mat.find());
		if(idx < content.length() - 1) {
			sb.append(content.substring(idx));
		}
		return sb.toString();
	}

	@Override
	public String saveBase64Image(String b64Str, String ext) {
		byte[] bytes = Base64.getMimeDecoder().decode(b64Str);
		String[] urls = FileUtils.upload(bytes, ext);
		Img img = new Img(urls[1]);
		setImgDim(img, bytes, ext);
		return img.html();
	}

	private void setImgDim(Img img, byte[] bytes, String suffix) {
		try {
			InputStream imgStream = new ByteArrayInputStream(bytes);
			Dimension d = ImageUtils.getImageDimension(imgStream, suffix);
			img.setDim(new ImgDim(d.width, d.height));
		} catch (Exception ex) {
			LOG.info("unable to read image dimension.", ex);
		}
	}
}
