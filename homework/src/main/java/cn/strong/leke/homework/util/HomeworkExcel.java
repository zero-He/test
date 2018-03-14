package cn.strong.leke.homework.util;

import java.math.BigDecimal;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import cn.strong.leke.homework.model.activity.SimpleExercise;

public class HomeworkExcel {

	public static void append(HSSFWorkbook wb, List<SimpleExercise> records) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setBorderBottom(BorderStyle.DASH_DOT);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setAlignment(HorizontalAlignment.CENTER);
		HSSFSheet sheet = wb.createSheet("自主学习排行");
		sheet.setDefaultRowHeightInPoints(25);
		sheet.setDefaultColumnWidth(20);
		appendTitle(wb, sheet);
		int index = 2;
		for (SimpleExercise record : records) {
			appendRow(sheet, record, index++);
		}
	}

	private static void appendTitle(HSSFWorkbook wb, HSSFSheet sheet) {
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell = row1.createCell(0);
		cell.setCellValue("练习排行");
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
		HSSFFont font = wb.createFont();
		font.setBold(true);
		style.setFont(font);
		cell.setCellStyle(style);

		HSSFRow row2 = sheet.createRow(1);
		// 创建单元格并设置单元格内容
		row2.createCell(0).setCellValue("乐号");
		row2.createCell(1).setCellValue("学生姓名");
		row2.createCell(2).setCellValue("答题数量");
		row2.createCell(3).setCellValue("答题正确数");
		row2.createCell(4).setCellValue("答题正确率");
	}

	private static void appendRow(HSSFSheet sheet, SimpleExercise record, int index) {
		HSSFRow row = sheet.createRow(index);
		row.createCell(0).setCellValue(record.getLoginName());
		row.createCell(1).setCellValue(record.getUserName());
		row.createCell(2).setCellValue(record.getTotalNum());
		row.createCell(3).setCellValue(record.getRightNum());
		if (record.getTotalNum() != null && record.getTotalNum() != 0) {
			long a = record.getTotalNum();
			long b = record.getRightNum();
			double f1 = new BigDecimal((float) b / a).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			row.createCell(4).setCellValue(f1);
		} else {
			row.createCell(4).setCellValue(0);
		}

	}
}
