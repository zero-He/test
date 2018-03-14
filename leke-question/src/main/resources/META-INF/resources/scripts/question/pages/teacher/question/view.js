define(function(require, exports, module) {
	var ko = require('knockout');
	require('repository/head/scope-title');
	require('question/component/question/preview-btns');
	var DFN = require('homework/sheet.dfn');
	
	ko.applyBindings();
	DFN.init();
});