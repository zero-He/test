define(function(require, exports, module) {
	var ko = require("knockout");
	var RepoCst = require('repository/service/RepoCst');
	
	var I18n = require('i18n');
	require('common/knockout/bindings/i18n');
	require('repository/question-list');
	I18n.init('queJS');
	
	ko.applyBindings({
		config: {
			tabs: RepoCst.fmstchTabs(),
			loadUrl: Leke.ctx + '/auth/researcher/question/fmstch/details.htm',
			scope: 'famous_teacher',
			shareScope: 8
		}
	});
});