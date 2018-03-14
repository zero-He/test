define(function(require, exports, module) {
	var $ = require('jquery');
	var PeriodDate = require('diag/common/PeriodDate');
	var AttendancePie = require('diag/common/AttendancePie');
	var Clazz = require("tutor/clazz/find");

	var Attendance = {
		init : function() {
			this.bindEvent();
			var $firstStu = $('#jStudentList li').first();
			if($firstStu.length > 0 && Leke.user.currentRoleId == 102){
				$('#jStudentId').val($firstStu.data('stuid'));
			}
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
				if (!$this.hasClass('active')) {
					$this.addClass("active").siblings().removeClass('active');
					$('#jStudentId').val($this.data('stuid'));
					Clazz.fLoad({
						'userId' : $this.data('stuid')
					});
					AttendancePie.load();
				}
			});
		}
	};

	Attendance.init();

});
