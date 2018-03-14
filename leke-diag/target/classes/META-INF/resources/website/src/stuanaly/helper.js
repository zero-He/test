var Utils = require('utils');

var helper = {
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
	 * 将Number四舍五入为指定小数位数的数字。<br>
	 * 注意：Number.toFixed为五舍六入。
	 * @param num 数值
	 * @param digit 位数
	 */
	toFixed : Utils.Number.toFixed,

	/**
	 * 返回两个数的百分比。
	 * @param value 数值
	 * @param total 总数
	 * @param digit 舍入位数
	 */
	toRate : function(value, total, digit) {
		if (total == 0) {
			return 0;
		}
		return Utils.Number.toFixed(value * 100 / total, digit || 0);
	},
	/**
	 * 将秒转分钟。
	 * @param time 秒
	 */
	toSecond : function(time) {
		if (time < 60) {
			return 1;
		} else {
			return Utils.Number.toFixed(time / 60, 1);
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

module.exports = helper;
