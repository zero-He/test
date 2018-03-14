define(function(require, exports, module) {
	var $ = require('jquery');
	var PeriodDate = require('diag/common/PeriodDate');
	var StuSubmitStatePie = require('diag/common/SubmitStatePie');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	var StuSubmitState = {
		init : function() {
			this.bindEvent();
			PeriodDate.init('startTime', 'endTime');
			StuSubmitStatePie.init();
		},
		bindEvent : function() {
			$('#s-search').click(function() {
				StuSubmitStatePie.load();
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

	StuSubmitState.init();

});
