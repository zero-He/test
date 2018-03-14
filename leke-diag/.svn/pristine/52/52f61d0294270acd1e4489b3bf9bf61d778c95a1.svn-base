package cn.strong.leke.diag.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import cn.strong.leke.common.utils.DateUtils;

public class ExcelWriter {

	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private int rowIndex;
	private HSSFCellStyle headStyle;
	private HSSFCellStyle dataStyle;

	public ExcelWriter() {
		this.workbook = new HSSFWorkbook();
		this.headStyle = this.buildHeadStyle();
		this.dataStyle = this.buildDataStyle();
	}

	public void createSheet(String title) {
		this.sheet = workbook.createSheet(title);
		this.sheet.setDefaultColumnWidth(20);
		this.rowIndex = 0;
	}

	public void appendHead(List<String> headers) {
		HSSFRow row = sheet.createRow(this.rowIndex++);
		for (int i = 0; i < headers.size(); i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(this.headStyle);
			cell.setCellValue(new HSSFRichTextString(headers.get(i)));
		}
	}

	public <T> void appendData(List<T> rowDatas, List<String> keys) {
		for (T rowData : rowDatas) {
			HSSFRow row = this.sheet.createRow(this.rowIndex++);
			for (int i = 0; i < keys.size(); i++) {
				String key = keys.get(i);
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(this.dataStyle);
				Object value = this.resolveValue(rowData, key);
				this.writeCell(row, cell, value);
			}
		}
	}

	private void writeCell(HSSFRow row, HSSFCell cell, Object value) {
		if (value == null) {
			cell.setCellValue("--");
		} else if (value instanceof Boolean) {
			boolean bool = (Boolean) value;
			cell.setCellValue(bool ? "是" : "否");
		} else if (value instanceof Date) {
			Date date = (Date) value;
			cell.setCellValue(DateUtils.formatTime(date));
		} else if (value instanceof Number) {
			cell.setCellValue(value.toString());
		} else {
			cell.setCellValue(value.toString());
		}
	}

	private <T> Object resolveValue(T rowData, String key) {
		if (rowData instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) rowData;
			return map.get(key);
		} else {
			try {
				return PropertyUtils.getProperty(rowData, key);
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}
	}

	public void writeEnd(OutputStream out) {
		try {
			workbook.write(out);
		} catch (IOException e) {
			throw new RuntimeException("IO error when write workbook", e);
		}
	}

	public void setHeadStyle(HSSFCellStyle headStyle) {
		this.headStyle = headStyle;
	}

	public void setDataStyle(HSSFCellStyle headStyle) {
		this.headStyle = headStyle;
	}

	private HSSFCellStyle buildHeadStyle() {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBold(true);
		style.setFont(font);
		return style;
	}

	private HSSFCellStyle buildDataStyle() {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.WHITE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont font = workbook.createFont();
		font.setBold(true);
		style.setFont(font);
		return style;
	}
}
