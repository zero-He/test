define(function(require, exports, module) {
	var $ = require('jquery');
	var PeriodDate = require('diag/common/PeriodDate');
	var AchievementLine = require('diag/common/AchievementLine');
	
	var win = {
		init : function() {
			this.loadData();
		},
		loadData : function() {
			AchievementLine.init();
			PeriodDate.init('jStartDate', 'jFinishDate');
		}
	};

	win.init();

});
