define(function(require, exports, module) {
	var ko = require("knockout");
	var RepoCst = require('repository/service/RepoCst');
	
	var I18n = require('i18n');
	require('common/knockout/bindings/i18n');
	require('question/component/question/agency-question-list');
	I18n.init('queJS');
	
	ko.applyBindings({
		config: {
			scopeTabs: RepoCst.agencyScopes402('question'),
			tabs: RepoCst.fmstchAgencyTabs(),
			loadUrl: Leke.ctx + '/auth/researcher/question/fmstch/myAgencyDetails.htm',
			scope: 'famous_teacher',
			shareScope: 8
		}
	});
});