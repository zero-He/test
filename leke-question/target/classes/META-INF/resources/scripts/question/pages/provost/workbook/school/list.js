define(function(require, exports, module) {
	var ko = require("knockout");
	var RepoCst = require('repository/service/RepoCst');
	
	var I18n = require('i18n');
	I18n.init('queJS');
	require('common/knockout/bindings/i18n');
	require('repository/workbook-list');
	
	ko.applyBindings({
		config: {
			tabs: RepoCst.schoolTabs,
			loadUrl: Leke.ctx + '/auth/provost/workbook/school/details.htm',
			scope: 'school',
			shareScope: 2
		}
	});
});