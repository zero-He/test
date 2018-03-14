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
			if(EdCtx.copy){
				q.shareSchool = false;
				q.sharePlatform = false;
				QueService.add(q).then(function() {
					dialog.close('questionCopyEditDialog', 'success');
				}, function(msg) {
					utils.Notice.alert(msg || '题目保存失败！');
				});
			}else{
				QueService.modify(q).then(function() {
					dialog.close('questionEditDialog', 'success');
				}, function(msg) {
					utils.Notice.alert(msg || '题目保存失败！');
				});
			}
		},
		doCancel: function() {
			dialog.close('questionEditDialog');
		}
	});

});