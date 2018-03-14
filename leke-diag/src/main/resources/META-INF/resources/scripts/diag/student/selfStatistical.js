define(function(require, exports, module) {
	var $ = require('jquery');
	var PeriodDate = require('diag/common/PeriodDate');
	var StatisticalLine = require('diag/common/StatisticalLine');
	
	var Statistical = {
		init : function() {
			PeriodDate.init('startTime', 'endTime');
			StatisticalLine.init();
			this.bindEvent();
		},
		bindEvent : function(){
			$('#jSubjectId').change(function(){
				StatisticalLine.init();
			});
		}
	};

	Statistical.init();

});
