define(function(require, exports, module) {
	var $ = require('jquery');
	var PeriodDate = require('diag/common/PeriodDate');
	var AttendancePie = require('diag/common/AttendancePie');
	var Clazz = require("tutor/clazz/find");

	var Attendance = {
		init : function() {
			this.bindEvent();
			PeriodDate.init('startTime', 'endTime');
			AttendancePie.init();
			Clazz.fInit({
				'userId' : $('#jStudentId').val()
			});
		},
		bindEvent : function() {
			$('#jSearch').click(function() {
				AttendancePie.load();
			});
			$('#jStudentList li').click(function() {
				var $this = $(this);
				var userId = $this.data('stuid');
				Clazz.fLoad({
					'userId' : userId
				});
				if (!$this.hasClass('active')) {
					$this.addClass("active").siblings().removeClass('active');
					$('#jStudentId').val($this.data('stuid'));
					AttendancePie.load();
				}
				
			});
		}
	};

	Attendance.init();

});
