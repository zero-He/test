define(function(require, exports, module) {
	var ko = require("knockout");
	var RepoCst = require('repository/service/RepoCst');
	
	var I18n = require('i18n');
	require('common/knockout/bindings/i18n');
	require('repository/question-list');
	I18n.init('queJS');
	
	var leagueId = LeagueCtx.leagueId;
	
	ko.applyBindings({
		config: {
			tabs: RepoCst.leagueTabs(leagueId),
			loadUrl: Leke.ctx + '/auth/provost/question/league/details.htm?leagueId=' + leagueId,
			scope: 'league',
			leagueId: leagueId,
			shareScope: 5
		}
	});
});