define(function(require, exports, module) {
	var ko = require("knockout");
	var RepoCst = require('repository/service/RepoCst');
	
	var I18n = require('i18n');
	require('common/knockout/bindings/i18n');
	require('repository/question-list');
	I18n.init('queJS');
	
	ko.applyBindings({
		config: {
			tabs: RepoCst.schoolTabs,
			loadUrl: Leke.ctx + '/auth/provost/question/school/details.htm',
			scope: 'school',
			shareScope: 2
		}
	});
});