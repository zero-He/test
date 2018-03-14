package cn.strong.leke.diag.model.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrendModel {

	private String label;
	private List<String> names;
	private List<Double> selfs;
	private List<Double> klass;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<Double> getSelfs() {
		return selfs;
	}

	public void setSelfs(List<Double> selfs) {
		this.selfs = selfs;
	}

	public List<Double> getKlass() {
		return klass;
	}

	public void setKlass(List<Double> klass) {
		this.klass = klass;
	}

	public static List<TrendModel> build() {
		List<TrendModel> trends = new ArrayList<>();
		TrendModel trend;

		trend = new TrendModel();
		trend.setLabel("周走势");
		trend.setNames(Arrays.asList("周一", "周二", "周三", "周四", "周五", "周六", "周日"));
		trend.setKlass(Arrays.asList(49D, 92D, 94D, 93D, 88D, null, 90D));
		trend.setSelfs(Arrays.asList(82D, 81D, 76D, null, 83D, null, 89D));
		trends.add(trend);

		trend = new TrendModel();
		trend.setLabel("月走势");
		trend.setNames(Arrays.asList("2月", "3月", "4月", "5月", "6月"));
		trend.setKlass(Arrays.asList(60D, 60D, 60D, 60D, 60D));
		trend.setSelfs(Arrays.asList(80D, 80D, 80D, 80D, 80D));
		trends.add(trend);

		return trends;
	}
}
