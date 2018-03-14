define(function(require, exports, module) {
	var ko = require("knockout");
	var RepoCst = require('repository/service/RepoCst');
	
	var I18n = require('i18n');
	I18n.init('queJS');
	require('common/knockout/bindings/i18n');
	require('repository/workbook-list');
	
	var leagueId = LeagueCtx.leagueId;
	
	ko.applyBindings({
		config: {
			tabs: RepoCst.leagueTabs(leagueId),
			loadUrl: Leke.ctx + '/auth/provost/workbook/league/details.htm',
			scope: 'league',
			leagueId: leagueId,
			shareScope: 5
		}
	});
});