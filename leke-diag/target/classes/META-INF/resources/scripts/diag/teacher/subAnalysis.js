define(function(require, exports, module) {
	var $ = require('jquery');
	var TeacherClassList = require('diag/common/TeacherClassList');
	var SubAnalysisPie = require('diag/common/SubAnalysisPie');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	var SubAnalysis = {
		init : function() {
			this.bindEvent();
			TeacherClassList.onClassTypeChange = function() {
				SubAnalysisPie.load();
			}
			TeacherClassList.init();
			SubAnalysisPie.init();
		},
		bindEvent : function() {
			$('#jSearch').click(function() {
				SubAnalysisPie.load();
			});
			$('#jStageSubject').stgGrdSbjSelect({
				type : 'sbj',
				onChange : function(selection) {
					$('#jSubjectId').val(selection.subjectId);
				}
			});
		}
	};

	SubAnalysis.init();

});
