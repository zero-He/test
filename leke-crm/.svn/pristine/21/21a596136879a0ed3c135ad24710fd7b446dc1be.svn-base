define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require('utils');
	var pattern = /^[1-9][0-9]*$/;

	function isInteger(value) {
		if (!pattern.test(value)) {
			return false;
		}
		value = value * 1;
		if (isNaN(value) || value <= 0) {
			return false;
		}
		return true;
	}

	$('#jParametersForm').submit(function() {
		var value = $('#jInformation').val();
		if (value == '') {
			Utils.Notice.alert("完善信息赠送点不能为空");
			return false;
		} else if (!isInteger(value)) {
			Utils.Notice.alert("完善信息赠送点只能输入正整数");
			return false;
		}

		value = $('#jRelationSale').val();
		if (value == '') {
			Utils.Notice.alert("绑定客户经理赠送点不能为空");
			return false;
		} else if (!isInteger(value)) {
			Utils.Notice.alert("绑定客户经理赠送点只能输入正整数");
			return false;
		}

		return true;
	});
});
