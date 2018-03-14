define(function(require, exports, module) {
	var $ = require('jquery');
	var PeriodDate = require('diag/common/TeacherClassList');
	var AchievementLine = require('diag/common/AchievementLineTeacher');
	
	var win = {
		init : function() {
			this.loadData();
		},
		loadData : function() {
			PeriodDate.init('jStartDate', 'jFinishDate');
			AchievementLine.init();
		}
	};

	win.init();

});
