define(function(require, exports, module) {
	var $ = require('jquery');
	var AllAnalysisBar = require('diag/common/AllAnalysisBar');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	var AllAnalysis = {
		init : function() {
			this.bindEvent();
			AllAnalysisBar.init();
		},
		bindEvent : function() {
			$('#jSearch').click(function() {
				AllAnalysisBar.load();
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

	AllAnalysis.init();

});
