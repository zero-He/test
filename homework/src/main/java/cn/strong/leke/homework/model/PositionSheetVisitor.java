package cn.strong.leke.homework.model;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.function.Function;

import cn.strong.leke.paper.sheet.model.position.PositionRange;
import cn.strong.leke.paper.sheet.model.position.PositionSheet;

public class PositionSheetVisitor {

	private PositionSheet sheet;
	private String rangeIds;
	private Map<String, PositionRange> rangeMap;

	private static final Function<PositionRange, String> MAPPER = PositionRange::getRangeId;

	public static PositionSheetVisitor build(PositionSheet sheet) {
		return new PositionSheetVisitor(sheet);
	}

	public PositionSheetVisitor(PositionSheet sheet) {
		this.sheet = sheet;
		this.rangeIds = sheet.getPages().stream().flatMap(v -> v.getRanges().stream()).map(MAPPER).sorted()
				.collect(joining(","));
		this.rangeMap = sheet.getPages().stream().flatMap(v -> v.getRanges().stream())
				.collect(toMap(MAPPER, Function.identity()));
	}
	
	public PositionSheet getSheet() {
		return this.sheet;
	}

	public Long getPaperId() {
		return this.sheet.getPaperId();
	}

	public Integer getPkgCount() {
		return this.sheet.pkgCount();
	}

	public Integer getPageCount() {
		return this.sheet.getPages().size();
	}

	public Integer getRangeSize() {
		return this.rangeMap.size();
	}

	public String getRangeIds() {
		return this.rangeIds;
	}

	public PositionRange getRange(String rangeId) {
		return this.rangeMap.get(rangeId);
	}
}
