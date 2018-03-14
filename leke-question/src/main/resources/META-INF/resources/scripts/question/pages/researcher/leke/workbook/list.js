define(function(require, exports, module) {
	var ko = require("knockout");
	var DM = Leke.domain;
	var RP_SVR = DM.repositoryServerName;
	var QU_SVR = DM.questionServerName;
	var I18n = require('i18n');
	require('repository/workbook-list');
	I18n.init('repositoryJs');
	var RepoCst = require('repository/service/RepoCst');
	ko.applyBindings({
		config: {
			tabs: RepoCst.lekeTabs,
			scope: 'leke_boutique',
			loadUrl: Leke.ctx + '/auth/researcher/leke/workbook/details.htm',
			shareScope: 7
		}
	});
});