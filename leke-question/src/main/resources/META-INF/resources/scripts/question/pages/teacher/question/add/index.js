define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var utils = require('utils');
	var QueService = require('question/service/userQueService');
	require('question/qed/qed');
	
	ko.applyBindings({
		mode: 'add',
		doSave: function(que, vm) {
			var q = $.extend({}, que, {
				enabled: true,
				category: 2
			});
			QueService.add(q).then(function() {
				vm.reset();
				utils.Notice.alert('题目保存成功！');
			}, function(msg) {
				utils.Notice.alert(msg || '题目保存失败！');
			});
		}
	});
});