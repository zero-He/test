define(function(require, exports, module) {
	var ko = require("knockout");
	var RepoCst = require('repository/service/RepoCst');
	
	var I18n = require('i18n');
	require('common/knockout/bindings/i18n');
	require('repository/workbook-list');
	I18n.init('queJS');
	
	ko.applyBindings({
		config: {
			tabs: RepoCst.fmsschTabs(),
			loadUrl: Leke.ctx + '/auth/researcher/workbook/fmssch/details.htm',
			scope: 'famous_school',
			shareScope: 9
		}
	});
});