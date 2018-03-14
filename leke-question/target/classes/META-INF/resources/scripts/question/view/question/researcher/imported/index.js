define(function(require, exports, module) {
	var ko = require('knockout');
	var I18n = require('i18n');
	
	require('./component/que-imported-list');
	
	I18n.init('queJS');
	ko.applyBindings();
});