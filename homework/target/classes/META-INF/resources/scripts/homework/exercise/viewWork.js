define(function(require, exports, module) {
	var $ = require('jquery');
	var Render = require('homework/sheet.render');
	var Score = require('homework/common/sheet.score');
	var utils = require('utils');
	
	function GetQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}
	var str = "完成自主练习";
	var leke = GetQueryString("leke");
	var exp = GetQueryString("exp");
	if (leke && parseInt(leke)) {
		str = str + "，乐豆+" + leke;
	}
	if (exp && parseInt(leke)) {
		str = str + "，经验+" + exp;
	}
	if (leke && parseInt(leke) || (exp && parseInt(leke))) {
		utils.Notice.alert(str);
	}
	var ViewWork = {
		init : function() {
			var score = $('#score').val();
			Score.append('.p-exam-head', score);
		}
	};

	ViewWork.init();

});
