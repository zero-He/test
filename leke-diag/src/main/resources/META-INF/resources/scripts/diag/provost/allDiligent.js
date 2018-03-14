define(function(require, exports, module) {
	var $ = require('jquery');
	var PeriodDate = require('diag/common/PeriodDate');
	var AllDiligentBar = require('diag/common/AllDiligentBar');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	var AllDiligent = {
		init : function() {
			AllDiligentBar.init2();
			this.bindEvent();
			PeriodDate.init('jStartTime', 'jEndTime');
		},
		bindEvent : function() {
			$('#jSearch').click(function() {
				AllDiligentBar.load2();
			});
			$('#jGradeSubject').stgGrdSbjSelect({
				type : 'grd_sbj',
				caption : false,
				onChange : function(selection) {
					$('#jGradeId').val(selection.gradeId);
					$('#jSubjectId').val(selection.subjectId);
				}
			});
		}
	};

	AllDiligent.init();

});
