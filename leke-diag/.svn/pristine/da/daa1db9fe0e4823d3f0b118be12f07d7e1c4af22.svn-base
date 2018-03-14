define(function(require, exports, module) {
	var $ = require('jquery');
	var PeriodDate = require('diag/common/PeriodDate');
	var StatisticalLine = require('diag/common/StatisticalLine');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	var Statistical = {
		init : function() {
			this.bindEvent();
			PeriodDate.init('startTime', 'endTime');
			StatisticalLine.init();
		},
		bindEvent : function() {
			$('#s-search').click(function() {
				StatisticalLine.load();
			});
			$('#jStudentList li').click(function() {
				var $this = $(this);
				if (!$this.hasClass('active')) {
					$this.addClass("active").siblings().removeClass('active');
					$('#studentId').val($this.data('stuid'));
					StatisticalLine.load();
				}
			});
			$('#jStageSubject').stgGrdSbjSelect({
				type : 'sbj',
				caption : false,
				onChange : function(selection) {
					$('#jSubjectId').val(selection.subjectId);
				}
			});
		}
	};

	Statistical.init();

});
