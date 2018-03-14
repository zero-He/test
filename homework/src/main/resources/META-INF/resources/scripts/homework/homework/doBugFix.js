define(function(require, exports, module) {
	var ko = require('knockout');
	require('homework/homework/component/question-panel-right');
	var questionFilter = require('homework/common/questionFilter');
	questionFilter.init('','bugfix');
	ko.applyBindings();
});
