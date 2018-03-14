define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require('utils');
	var LekeDate = require('common/base/date');
	var WdatePicker = require('date');
	var pattern = /^[1-9][0-9]*$/; //正整数  
	require('common/ui/ui-mask/jquery.mask');

	var Pages = {
		init : function() {
			$('#jValidTime').click(function() {
				WdatePicker({
					minDate : LekeDate.of(Leke.Clock.date()).add(1, 'd').toDate(),
					dateFmt : 'yyyy-MM-dd',
					readOnly : true
				});
			});
			$('#jCommRuleForm').submit(function() {
				for (var i = 1; i <= 5; i++) {
					var rate = $('#jRate' + i).val();
					if (rate == '') {
						Utils.Notice.alert('第' + i + '年分成比例不能为空');
						return false;
					}
					var rate = rate * 1;
					if (isNaN(rate)) {
						Utils.Notice.alert('第' + i + '年分成比例输入无效');
						return false;
					} else if (!pattern.test(rate) || rate <= 0) {
						Utils.Notice.alert('第' + i + '年分成比例必须为正整数');
						return false;
					} else if (rate > 100) {
						Utils.Notice.alert('第' + i + '年分成比例不能大于100');
						return false;
					}
				}
				var validTime = $('#jValidTime').val();
				if (validTime == '') {
					Utils.Notice.alert('生效时间不能为空');
						return false;
				}
				$('#jBtnSave').lock();
				return true;
			});
		}
	};

	Pages.init();

});
