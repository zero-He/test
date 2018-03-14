define(function(require, exports, module) {
	var ko = require("knockout");
	var I18n = require('i18n');
	require('common/knockout/bindings/i18n');
	require('./check-question-list');
	I18n.init('queJS');
	
	ko.applyBindings();
});