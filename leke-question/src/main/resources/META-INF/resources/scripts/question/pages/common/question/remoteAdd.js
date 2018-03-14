define(function(require, exports, module) {
	var ko = require('knockout');
	var dialog = require('dialog');
	require('repository/head/scope-title');
	require('question/qed/qed');
	var initJson = EdCtx.initJson || {};
	var copyAdd = EdCtx.copyAdd || false;
	if(copyAdd){
		initJson.questionId = null;
	}
	ko.applyBindings({
		mode: initJson.questionId ? 'edit' :'add',
		question: EdCtx.initJson,
		afterSave: function(qid, vm) {
			dialog.close('remoteAddDialog', 'success', qid);
		},
		doCancel: function() {
			dialog.close('remoteAddDialog');
		}
	});
});