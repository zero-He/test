package cn.strong.leke.diag.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import cn.strong.leke.context.base.ParametersContext;

public class ScoreLevelUtils {

	private static final boolean fromCahced = false;
	private static final String KEY = "diag.level.config";
	private static ScoreLevelConfig defaultLevelConfig;
	private static final String[] names = { "优秀", "良好", "及格", "较差", "危险" };

	static {
		ScoreLevelConfig config = new ScoreLevelConfig();
		config.setRateA(85);
		config.setRateB(70);
		config.setRateC(60);
		config.setRateD(45);
		defaultLevelConfig = config;
	}

	public static int toLevel(Double score) {
		ScoreLevelConfig config = resloveLevelConfig();
		if (score == null) {
			return 5;
		} else if (score >= config.rateA) {
			return 1;
		} else if (score >= config.rateB) {
			return 2;
		} else if (score >= config.rateC) {
			return 3;
		} else if (score >= config.rateD) {
			return 4;
		} else {
			return 5;
		}
	}

	public static String toLevelName(Double score) {
		int level = toLevel(score);
		return names[level - 1];
	}

	public static List<ScoreLevel> resolveScoreLevels(BigDecimal totalScore) {
		ScoreLevelConfig config = resloveLevelConfig();
		List<ScoreLevel> levels = new ArrayList<>();
		int z = resolveScore(totalScore, 100);
		int a = resolveScore(totalScore, config.rateA);
		int b = resolveScore(totalScore, config.rateB);
		int c = resolveScore(totalScore, config.rateC);
		int d = resolveScore(totalScore, config.rateD);
		levels.add(new ScoreLevel("优秀", a, config.rateA, z, 100));
		levels.add(new ScoreLevel("良好", b, config.rateB, a, config.rateA));
		levels.add(new ScoreLevel("及格", c, config.rateC, b, config.rateB));
		levels.add(new ScoreLevel("较差", d, config.rateD, c, config.rateC));
		levels.add(new ScoreLevel("危险", 0, 0, d, config.rateD));
		return levels;
	}

	private static int resolveScore(BigDecimal totalScore, int rate) {
		return totalScore.multiply(new BigDecimal(rate)).divide(new BigDecimal(100), 0, RoundingMode.HALF_UP)
				.intValue();
	}

	private static ScoreLevelConfig resloveLevelConfig() {
		if (fromCahced) {
			return ParametersContext.getObject(KEY, ScoreLevelConfig.class, defaultLevelConfig);
		} else {
			return defaultLevelConfig;
		}
	}

	public static class ScoreLevel {
		private String name;
		private int min;
		private int max;
		private int minRate;
		private int maxRate;

		private ScoreLevel(String name, int min, int minRate, int max, int maxRate) {
			this.name = name;
			this.min = min;
			this.minRate = minRate;
			this.max = max;
			this.maxRate = maxRate;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getMin() {
			return min;
		}

		public void setMin(int min) {
			this.min = min;
		}

		public int getMax() {
			return max;
		}

		public void setMax(int max) {
			this.max = max;
		}

		public int getMinRate() {
			return minRate;
		}

		public void setMinRate(int minRate) {
			this.minRate = minRate;
		}

		public int getMaxRate() {
			return maxRate;
		}

		public void setMaxRate(int maxRate) {
			this.maxRate = maxRate;
		}
	}

	static class ScoreLevelConfig {

		private int rateA;
		private int rateB;
		private int rateC;
		private int rateD;

		public int getRateA() {
			return rateA;
		}

		public void setRateA(int rateA) {
			this.rateA = rateA;
		}

		public int getRateB() {
			return rateB;
		}

		public void setRateB(int rateB) {
			this.rateB = rateB;
		}

		public int getRateC() {
			return rateC;
		}

		public void setRateC(int rateC) {
			this.rateC = rateC;
		}

		public int getRateD() {
			return rateD;
		}

		public void setRateD(int rateD) {
			this.rateD = rateD;
		}
	}
}
