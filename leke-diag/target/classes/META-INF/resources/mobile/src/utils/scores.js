var NumberUtils = require('./number');

var ReportBase = {
	Rates : {
		rateA : 85,
		rateB : 70,
		rateC : 60,
		rateD : 45
	},
	Colors : {
		submit : ['#619eed', '#ffd270', '#ff6e6e'],
		score : ['#31c46c', '#74d64b', '#5cc0ff', '#ffcd61', '#ff5656']
	},
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
		var idx = helper.toLevel(score);
		return helper.levelNames[idx - 1];
	},
	levelNames : ["优秀", "良好", "及格", "较差", "危险", null]
};

module.exports = ReportBase;