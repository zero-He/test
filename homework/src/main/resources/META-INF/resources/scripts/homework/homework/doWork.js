define(function(require, exports, module) {
	var $ = require('jquery');
	var ko = require('knockout');
	require('homework/homework/component/question-panel-right');
	require('homework/homework/component/do-work-right');
	require('homework/common/homework-heartbeat');
	ko.applyBindings();
});
