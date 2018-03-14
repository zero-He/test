define(function(require, exports, module) {
	var ko = require('knockout');
	require('homework/homework/component/question-panel-right');
	require('homework/homework/correctWorkScore.js');
	var questionFilter = require('homework/common/questionFilter');
	questionFilter.init('','correct');
	ko.applyBindings();
});
