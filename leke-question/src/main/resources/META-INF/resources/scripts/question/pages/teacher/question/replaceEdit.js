define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var utils = require('utils');
	var dialog = require('dialog');
	var QueService = require('question/service/userQueService');
	require('question/qed/qed');
	
	ko.applyBindings({
		mode: 'edit',
		question: EdCtx.initJson,
		doSave: function(que, vm) {
			var q = $.extend({}, que, {
				enabled: true,
				category: 2
			});
			QueService.replaceEdit(q).then(function(datas) {
				dialog.close('questionEditDialog', 'success', datas.newId);
			}, function(msg) {
				utils.Notice.alert(msg || '题目保存失败！');
			});
		},
		doCancel: function() {
			dialog.close('questionEditDialog');
		}
	});

});