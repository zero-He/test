define(function(require, exports, module) {
	var ko = require("knockout");
	var RepoCst = require('repository/service/RepoCst');
	
	var I18n = require('i18n');
	require('common/knockout/bindings/i18n');
	require('question/component/question/agency-question-list');
	I18n.init('queJS');
	
	ko.applyBindings({
		config: {
			tabs: RepoCst.schoolAgencyTabs(),
			loadUrl: Leke.ctx + '/auth/teacher/question/school/myAgencyDetails.htm',
			scope: 'school',
			shareScope: 2
		}
	});
});