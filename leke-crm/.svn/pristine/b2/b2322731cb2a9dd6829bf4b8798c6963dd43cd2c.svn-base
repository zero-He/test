package cn.strong.leke.scs.model.rule;

import java.math.BigDecimal;
import java.util.List;

/**
 * 点充值佣金规则。
 * @author  andy
 * @created 2015年6月15日 下午7:00:13
 * @since   v1.0.0
 */
public class PointCommRule {

	private List<BigDecimal> rates;

	public List<BigDecimal> getRates() {
		return rates;
	}

	public void setRates(List<BigDecimal> rates) {
		this.rates = rates;
	}

	public BigDecimal rate(Integer year) {
		if (year >= 0 && year < this.rates.size() - 1) {
			return this.rates.get(year);
		}
		return null;
	}
}
