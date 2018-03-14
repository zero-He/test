define(function(require, exports, module) {
	var ko = require('knockout');
	var dialog = require('dialog');
	require('repository/head/scope-title');
	require('question/qed/qed');
	
	ko.applyBindings({
		mode: 'edit',
		question: EdCtx.initJson,
		afterSave: function(qid, vm) {
			dialog.close('questionEditDialog', 'success', qid);
		},
		doCancel: function() {
			dialog.close('questionEditDialog');
		}
	});

});