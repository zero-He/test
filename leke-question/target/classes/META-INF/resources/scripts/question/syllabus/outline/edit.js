define(function(require, exports, module) {
	var ko = require('knockout');
	var I18n = require('i18n');
	I18n.init('paperJS');
	require('common/knockout/bindings/i18n');
	require('./wbpaper/wbpap-edit');
	
	ko.applyBindings({
		outline: ko.observable(OLCtx.outline)
	});
});