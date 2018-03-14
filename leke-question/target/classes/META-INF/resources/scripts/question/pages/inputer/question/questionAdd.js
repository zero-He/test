define(function(require, exports, module) {
	var ko = require('knockout');
	var utils = require('utils');
	require('repository/head/scope-title');
	require('question/qed/qed');
	
	ko.applyBindings({
		mode: 'add',
		afterSave: function(qid, vm) {
			vm.reset();
			utils.Notice.alert('习题保存成功！');
		}
	});
});