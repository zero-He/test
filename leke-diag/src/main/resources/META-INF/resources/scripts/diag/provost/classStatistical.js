define(function(require, exports, module) {
	var $ = require('jquery');
	var PeriodDate = require('diag/common/PeriodDate');
	var ClassStatisticalLine = require('diag/common/ClassStatisticalLine');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');

	var ClassStatistical = {
		init : function() {
			this.bindEvent();
			PeriodDate.init('jStartTime', 'jEndTime');
			ClassStatisticalLine.init();
		},
		bindEvent : function() {
			$('#jSearch').click(function() {
				ClassStatisticalLine.load();
			});
			$('#jGradeSubject').stgGrdSbjSelect({
				type : 'grd_sbj',
				caption : false,
				onChange : function(selection) {
					$('#jGradeId').val(selection.gradeId);
					$('#jSubjectId').val(selection.subjectId);
					ClassStatisticalLine.load();
				}
			});
		}
	};

	ClassStatistical.init();

});
