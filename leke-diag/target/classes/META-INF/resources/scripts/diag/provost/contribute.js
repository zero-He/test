define(function(require, exports, module) {
	var $ = require('jquery');
	var PeriodDate = require('diag/common/PeriodDate');
	var ContributeBar = require('diag/common/ContributeBar');

	var Contribute = {
		init : function() {
			this.bindEvent();
			PeriodDate.init('jStartTime', 'jEndTime');
			ContributeBar.init();
		},
		bindEvent : function() {
			$('#jSearch').click(function() {
				ContributeBar.load();
			});
		}
	};

	Contribute.init();

});
