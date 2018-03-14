/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.monitor.core.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.strong.leke.framework.exceptions.LekeRuntimeException;

/**
 *
 * 描述:
 *
 * @author Deo
 * @created 2016-11-21 下午3:05:29
 * @since v1.0.0
 */
public class ExportExcelForTable<T>
{

	private static final Logger logger = LoggerFactory.getLogger(ExportExcelForTable.class);
	private HSSFWorkbook workbook;

	/**
	 * 描述:导出Excel文档 ,双标题（含图片）
	 * 
	 * @param title sheet名
	 * @param headers标题
	 * @param fieldNames字段名
	 * @param dataset数据
	 * @param out 流
	 * @param pattern
	 */
	public void exportExcel(String title, String[] headers, String[] fieldNames, Collection<T> dataset, OutputStream out, String pattern)
	{
		workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBold(true);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style2.setBorderBottom(BorderStyle.THIN);
		style2.setBorderLeft(BorderStyle.THIN);
		style2.setBorderRight(BorderStyle.THIN);
		style2.setBorderTop(BorderStyle.THIN);
		style2.setAlignment(HorizontalAlignment.CENTER);
		style2.setVerticalAlignment(VerticalAlignment.CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBold(false);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 生成字体
		HSSFFont font3 = workbook.createFont();
		// font3.setColor(HSSFColor.BLUE.index);

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("leno");

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		HSSFRow row2 = sheet.createRow(1);
		for (short i = 0, n = 0; i < headers.length; i++)
		{// i是headers的索引，n是Excel的索引
			HSSFCell cell1 = row.createCell(n);
			cell1.setCellStyle(style);
			HSSFRichTextString text = null;
			if (headers[i].contains(":"))
			{// 双标题
				String[] temp = headers[i].split(":");
				text = new HSSFRichTextString(temp[0]);
				String[] tempSec = temp[1].split(",");
				sheet.addMergedRegion(new CellRangeAddress(0, 0, n, (short) (n + tempSec.length - 1)));
				short tempI = n;
				for (int j = 0; j < tempSec.length - 1; j++)
				{
					HSSFCell cellT = row.createCell(++tempI);
					cellT.setCellStyle(style);
				}
				for (int j = 0; j < tempSec.length; j++)
				{
					HSSFCell cell2 = row2.createCell(n++);
					cell2.setCellStyle(style);
					cell2.setCellValue(new HSSFRichTextString(tempSec[j]));
				}
			} else
			{// 单标题
				HSSFCell cell2 = row2.createCell(i);
				cell2.setCellStyle(style);
				text = new HSSFRichTextString(headers[i]);
				sheet.addMergedRegion(new CellRangeAddress(0, 1, n, n));
				n++;
			}
			cell1.setCellValue(text);
		}

		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 1;
		while (it.hasNext())
		{
			index++;
			row = sheet.createRow(index);
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			for (int i = 0; i < fieldNames.length; i++)
			{
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
				try
				{
					Object value = readValue(t, fieldNames[i]);
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					if (value instanceof Boolean)
					{
						boolean bValue = (Boolean) value;
						textValue = "是";
						if (!bValue)
						{
							textValue = "否";
						}
					} else if (value instanceof Date)
					{
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value instanceof byte[])
					{
						// 有图片时，设置行高为60px;
						row.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(i, (short) (35.7 * 80));
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6, index);
						anchor.setAnchorType(AnchorType.MOVE_DONT_RESIZE);
						patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					} else
					{
						// 其它数据类型都当作字符串简单处理
						textValue = null != value ? value.toString() : null;
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null)
					{
						Pattern p = Pattern.compile("^//d+(//.//d+)?{1}");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches())
						{
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else
						{
							HSSFRichTextString richString = new HSSFRichTextString(textValue);
							richString.applyFont(font3);
							cell.setCellValue(richString);
						}
					}
				} catch (Exception e)
				{
					logger.error(e.getMessage(), e);
					throw new LekeRuntimeException(e);
				}
			}

		}
		try
		{
			workbook.write(out);
		} catch (IOException e)
		{
			logger.error("IO error when write workbook", e);
			throw new LekeRuntimeException("IO error when write workbook", e);
		}

	}

	private static <T> Object readValue(T obj, String fieldName) throws Exception
	{
		String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		if (obj instanceof Map)
		{
			Map<?, ?> map = (Map<?, ?>) obj;
			return map.get(fieldName);
		} else
		{
			Class<? extends Object> tCls = obj.getClass();
			Method getMethod = tCls.getMethod(getMethodName, new Class[]
			{});
			return getMethod.invoke(obj, new Object[]
			{});
		}
	}
}