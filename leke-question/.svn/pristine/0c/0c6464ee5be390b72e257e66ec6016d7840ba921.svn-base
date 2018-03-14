/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.doc;

import static cn.strong.leke.question.word.ParseCsts.CHAR_CODE_SPACE;
import static cn.strong.leke.question.word.ParseCsts.CHAR_CODE_TILE;
import static cn.strong.leke.question.word.ParseCsts.UNICODE_ASCII_OFFSET;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.POIXMLException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFSDT;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlString;
import org.apache.xmlbeans.impl.values.XmlAnyTypeImpl;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTEmpty;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPTab;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPicture;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.schemas.vml.CTShape;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.framework.exceptions.LekeRuntimeException;
import cn.strong.leke.question.word.Word2DocConverter;
import cn.strong.leke.question.word.model.Br;
import cn.strong.leke.question.word.model.Doc;
import cn.strong.leke.question.word.model.DocCtx;
import cn.strong.leke.question.word.model.Img;
import cn.strong.leke.question.word.model.ImgDim;
import cn.strong.leke.question.word.model.Text;

/**
 * 习题 Word 文档解析器
 * 
 * @author liulongbiao
 * @created 2014年12月8日 上午9:29:10
 * @since v3.2
 */
public class DocParser implements Word2DocConverter {

	private static Logger log = LoggerFactory.getLogger(DocParser.class);
	private static final Pattern PATTERN_WS = Pattern.compile("\\s+");
	private static final char UNDERSCORE = '_';

	private WordImgConverter wordImgConverter;

	public void setWordImgConverter(WordImgConverter wordImgConverter) {
		this.wordImgConverter = wordImgConverter;
	}

	@Override
	public Doc convert(InputStream is) {
		try (XWPFDocument doc = new XWPFDocument(OPCPackage.open(is))) {
			DocCtx ctx = new DocCtx();
			for (IBodyElement e : doc.getBodyElements()) {
				parseBodyElement(e, ctx);
			}
			return ctx.getDoc();
		} catch (InvalidFormatException e) {
			throw new LekeRuntimeException("que.word.file.format.invalid");
		} catch (IOException ioe) {
			throw new LekeRuntimeException("que.word.file.read.error");
		}
	}

	private void parseBodyElement(IBodyElement e, DocCtx ctx) {
		if (e instanceof XWPFParagraph) {
			parseParagraph((XWPFParagraph) e, ctx);
		} else if (e instanceof XWPFTable) {
			log.info("XWPFTable unsupport!");
			throw new LekeRuntimeException("que.word.file.table.unsupport");
		} else if (e instanceof XWPFSDT) {
			log.info("XWPFSDT unsupport!");
		}
	}

	private void parseParagraph(XWPFParagraph paragraph, DocCtx ctx) {
		ctx.paragraphStarted();
		List<XWPFRun> runs = paragraph.getRuns();
		for (XWPFRun run : runs) {
			parseRun(run, ctx);
		}
	}

	private void parseRun(XWPFRun run, DocCtx ctx) {
		XmlCursor c = run.getCTR().newCursor();
		c.selectPath("./*");
		while (c.toNextSelection()) {
			XmlObject o = c.getObject();
			if (o instanceof CTText) {
				String tagName = o.getDomNode().getNodeName();
				if (!"w:instrText".equals(tagName)) {
					parseText((CTText) o, run, ctx);
				}
			}
			if (o instanceof CTPTab) {
				log.debug("ignore tab");
			}
			if (o instanceof CTBr) {
				ctx.addInline(new Br());
			}
			if (o instanceof CTEmpty) {
				String tagName = o.getDomNode().getNodeName();
				if ("w:tab".equals(tagName)) {
					log.debug("ignore tab");
				}
				if ("w:br".equals(tagName) || "w:cr".equals(tagName)) {
					ctx.addInline(new Br());
				}
			}
			if (o instanceof CTPicture || o instanceof CTDrawing
					|| o instanceof CTObject) {
				parsePict(o, run, ctx);
			}
		}
		c.dispose();
	}

	private void parseText(CTText node, XWPFRun run, DocCtx ctx) {
		Text text = buildText(node, run);
		String content = text.getContent();
		if (StringUtils.isEmpty(content)) {
			return;
		}
		if (text.isUnderline()) {
			Matcher mat = PATTERN_WS.matcher(content);
			int idx = 0;
			while (mat.find()) {
				int start = mat.start();
				int end = mat.end();
				if (start != idx) {
					Text pre = text.copy();
					pre.setContent(content.substring(idx, start));
					ctx.addInline(pre);
				}

				String underscores = repeat(UNDERSCORE, end - start);
				Text blank = text.copy();
				blank.setContent(underscores);
				blank.setUnderline(false);
				ctx.addInline(blank);

				idx = end;
			}
			String post = content.substring(idx);
			if (StringUtils.isNotEmpty(post)) {
				Text pre = text.copy();
				pre.setContent(post);
				ctx.addInline(pre);
			}
		} else {
			ctx.addInline(text);
		}
	}

	private static String repeat(char ch, int count) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append(ch);
		}
		return sb.toString();
	}

	private Text buildText(CTText node, XWPFRun run) {
		String text = unifyAsciiChar(node.getStringValue());
		Text result = new Text(text);
		result.setBold(run.isBold());
		result.setItalic(run.isItalic());
		UnderlinePatterns underline = run.getUnderline();
		boolean hasUnderline = underline != null
				&& !underline.equals(UnderlinePatterns.NONE);
		result.setUnderline(hasUnderline);
		VerticalAlign valign = run.getSubscript();
		if (valign != null) {
			switch (valign) {
			case SUBSCRIPT:
				result.setSub(true);
				break;
			case SUPERSCRIPT:
				result.setSup(true);
				break;
			default:
				result.setSub(false);
				result.setSup(false);
				break;
			}
		}
		return result;
	}

	private static String unifyAsciiChar(String content) {
		if (content == null) {
			return null;
		}
		int len = content.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char ch = content.charAt(i);
			int code = (int) ch;
			if (code >= UNICODE_ASCII_OFFSET + CHAR_CODE_SPACE
					&& code <= UNICODE_ASCII_OFFSET + CHAR_CODE_TILE) {
				sb.append((char) (code - UNICODE_ASCII_OFFSET));
			} else if (Character.isSpaceChar(ch)) {
				sb.append((char) CHAR_CODE_SPACE);
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	private void parsePict(XmlObject o, XWPFRun run, DocCtx ctx) {
		if (wordImgConverter == null) {
			log.info("no converter exist to convert image to html!");
			return;
		}
		XWPFPictureData data = null;
		XmlObject[] picts = o
				.selectPath("declare namespace r='http://schemas.openxmlformats.org/officeDocument/2006/relationships'; .//@r:embed | .//@r:id");
		for (XmlObject pict : picts) {
			try {
				XmlString str = XmlString.Factory.parse(pict.toString());
				String refId = str.getStringValue();
				POIXMLDocumentPart part = run.getParent().getPart();
				if (part != null) {
					POIXMLDocumentPart relatedPart = part
							.getRelationById(refId);
					if (relatedPart instanceof XWPFPictureData) {
						data = (XWPFPictureData) relatedPart;
						break;
					}
				}
			} catch (XmlException e) {
				log.info("parse image refId error!");
			}
		}
		if (data != null) {
			ImgDim dim = parsePrefImgDim(o);
			Img img = wordImgConverter.convertImage(data, dim);
			ctx.addInline(img);
		}
	}

	/**
	 * 解析图片维度偏好
	 */
	private ImgDim parsePrefImgDim(XmlObject o) {
		ImgDim result = parseImgDimFromExtent(o);
		if (result == null) {
			result = parseImgDimFromShapeStyle(o);
		}
		return result;
	}

	private ImgDim parseImgDimFromShapeStyle(XmlObject o) {
		ImgDim result = null;
		XmlObject[] extents = o
				.selectPath("declare namespace v='urn:schemas-microsoft-com:vml'; .//v:shape");
		for (XmlObject shape : extents) {
			if (shape instanceof XmlAnyTypeImpl) {
				try {
					shape = CTShape.Factory.parse(shape.toString());
				} catch (XmlException e) {
					throw new POIXMLException(e);
				}
			}
			if (shape instanceof CTShape) {
				result = extractDimFromStyle(((CTShape) shape).getStyle());
			}
			break;
		}
		return result;
	}

	private ImgDim extractDimFromStyle(String style) {
		if (StringUtils.isEmpty(style)) {
			return null;
		}
		Map<String, String> styles = parseStyles(style);
		int width = toPx(styles.get("width"));
		int height = toPx(styles.get("height"));
		return new ImgDim(width, height);
	}

	private static final Pattern SIZE_PATTERN = Pattern
			.compile("\\s*([\\d\\.]+)\\s*(\\w+)\\s*");
	private static final double PX_PER_IN = 96.0;
	private static final double PT_PER_IN = 72.0;

	private static final Map<String, Double> UNIT_PXS;
	
	static {
		Map<String, Double> pxs = new HashMap<String, Double>();
		pxs.put("px", 1.0);
		pxs.put("in", PX_PER_IN);
		pxs.put("pt", PX_PER_IN / PT_PER_IN);
		UNIT_PXS = pxs;
	}

	private static double getUnitPx(String unit) {
		Double d = UNIT_PXS.get(unit.toLowerCase());
		return d == null ? 1.0 : d.doubleValue();
	}

	private static int toPx(String string) {
		Matcher mat = SIZE_PATTERN.matcher(string);
		if (mat.find()) {
			double num = Double.parseDouble(mat.group(1));
			double unitPx = getUnitPx(mat.group(2));
			return (int) (num * unitPx);
		}
		return 0;
	}

	private static Map<String, String> parseStyles(String style) {
		Map<String, String> result = new HashMap<String, String>();
		String[] styles = StringUtils.split(style, ';');
		for (String s : styles) {
			String[] pair = StringUtils.split(s, ':');
			if (pair.length == 2) {
				result.put(pair[0].trim(), pair[1].trim());
			}
		}
		return result;
	}

	private ImgDim parseImgDimFromExtent(XmlObject o) {
		ImgDim result = null;
		XmlObject[] extents = o
				.selectPath("declare namespace wp='http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing'; .//wp:extent");
		for (XmlObject ext : extents) {
			if (ext instanceof XmlAnyTypeImpl) {
				try {
					ext = CTPositiveSize2D.Factory.parse(ext.toString());
				} catch (XmlException e) {
					throw new POIXMLException(e);
				}
			}
			if (ext instanceof CTPositiveSize2D) {
				result = convertToDim((CTPositiveSize2D) ext);
			}
			break;
		}
		return result;
	}

	public static final int EMUS_PER_PX = 9525;

	private static ImgDim convertToDim(CTPositiveSize2D ext) {
		if (ext == null) {
			return null;
		}
		long cx = ext.getCx();
		int width = (int) (cx / EMUS_PER_PX);
		long cy = ext.getCy();
		int height = (int) (cy / EMUS_PER_PX);
		return new ImgDim(width, height);
	}
}
