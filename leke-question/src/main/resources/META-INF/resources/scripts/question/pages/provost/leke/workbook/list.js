define(function(require, exports, module) {
	var ko = require("knockout");
	var DM = Leke.domain;
	var RP_SVR = DM.repositoryServerName;
	var QU_SVR = DM.questionServerName;
	var I18n = require('i18n');
	var RepoCst = require('repository/service/RepoCst');
	require('repository/workbook/workbook-list');
	ko.applyBindings({
		config: {
			tabs: RepoCst.lekeTabs,
			scope: 'leke',
			loadUrl: Leke.ctx + '/auth/provost/leke/workbook/details.htm',
			shareScope: 3
		}
	});
});