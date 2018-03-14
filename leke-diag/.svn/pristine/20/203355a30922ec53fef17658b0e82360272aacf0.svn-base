package cn.strong.leke.diag.util;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.common.utils.function.Function;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.framework.exceptions.LekeRuntimeException;

public class ExcelUtils {
	
	/**行号*/
	public static final String ROW_NUM = "rowNum";
	/**相同的行号钱缀*/
	public static final String IDENTICAL = "identical_";
	
	public static List<Map<String,Object>> getWorkbook(String fileUrl,Function<Row, Map<String, Object>> function,String ...strings){
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Workbook workbook = ExcelUtils.getWorkbook(fileUrl);
		Sheet sheet = workbook.getSheetAt(0);
		sheet.removeRow(sheet.getRow(0));
		for (Row row : sheet) {
			Map<String, Object> map  = function.apply(row);
			if(map!=null){
				for(String str:strings){
					for(Map<String, Object> mapList:listMap){
						String o = (String)mapList.get(str);
						if(o!=null&&o.equals(map.get(str))){
							map.put(IDENTICAL+str, mapList.get(ROW_NUM));
							break;
						}
					}
				}
				listMap.add(map);
			}
		}
		return listMap;
	}
	
	/***
	 * 获得excle中的行列数据
	 * @param fileUrl 文件路径
	 * @param strings 放入map中的key值
	 * @return
	 */
	public static List<Map<String,Object>> getWorkbook(String fileUrl,final String...strings){
		Function<Row, Map<String, Object>> function = new Function<Row, Map<String, Object>>() {
			public Map<String, Object> apply(Row row) {
				Map<String, Object> map = new HashMap<String, Object>();
				boolean isEmpty = true;
				for(int i=0;i<strings.length;i++){
					String rowString = ExcelUtils.getStringValue(row.getCell(i));
					isEmpty &= StringUtils.isEmpty(rowString);
					map.put(strings[i], rowString);
				}
				map.put(ROW_NUM, row.getRowNum()+1+"");
				if(isEmpty){
					return null;
				}
				return map;
			}
		};
		return getWorkbook(fileUrl, function,strings);
	}
	
	public static Workbook getWorkbook(String fileUrl) {
		try {
			String fileId = getUploadFileId(fileUrl);
			byte[] bts = FileUtils.download(fileId);
			String suffix = fileId.split("\\.").length > 1 ? fileId.split("\\.")[1] : "";
			if ("xls".equalsIgnoreCase(suffix)) {
				return new HSSFWorkbook(new ByteArrayInputStream(bts));
			} else {
				return new XSSFWorkbook(new ByteArrayInputStream(bts));
			}
		} catch (Exception e) {
			throw new LekeRuntimeException("文件解析出错", e);
		}
	}

	public static String getStringValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		cell.setCellType(Cell.CELL_TYPE_STRING);
		return cell.getStringCellValue().trim();
	}

	public static String getUploadFileId(String fileUrl) {
		if (fileUrl.contains("http")) {
			String[] str = fileUrl.split("/");
			StringBuffer sb = new StringBuffer();
			for (int i = 3; i < str.length; i++) {
				if (i < str.length - 1) {
					sb.append(str[i]).append("/");
				} else {
					sb.append(str[i]);
				}
			}
			return sb.toString();
		} else {
			return fileUrl;
		}
	}

}
