define(function(require, exports, module) {
	var $ = require('jquery');
	var stuAnalysisBar = require('diag/classTeacher/stuSubjectAnalysisBar');

	var analysis = {
		init : function() {
			stuAnalysisBar.init();
		}
	};

	analysis.init();

});
