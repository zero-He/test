var NumberUtils = require('../../../utils/number');

var Utils = {
	/**
	 * 将秒转分钟。
	 * @param time 秒
	 */
	toSecond : function(time) {
		if (time < 60) {
			return 1;
		} else {
			return NumberUtils.toFixed(time / 60, 1);
		}
	},
	/**
	 * 返回分数级别
	 * @param score 分数
	 */
	toLevel : function(score) {
		if (score == undefined || score == null) {
			return 6;
		}
		if (score >= 85) {
			return 1;
		} else if (score >= 70) {
			return 2;
		} else if (score >= 60) {
			return 3;
		} else if (score >= 45) {
			return 4;
		} else {
			return 5;
		}
	},
	toLevelName : function(score) {
		var idx = Utils.toLevel(score);
		return Utils.levelNames[idx - 1];
	},
	levelNames : ["优秀", "良好", "及格", "较差", "危险", null]
};

module.exports = Utils;