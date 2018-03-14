define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require("utils");
	var Dialog = require('dialog');

	$('#fBtnSave').on('click', function() {
		var text = $.trim($('.j-comment-text').val());
		if (text == null || text == '') {
			Utils.Notice.alert("请输入批注内容");
			return;
		}
		Dialog.close('comment', 'text', [{
			type : 'text',
			text : text,
			link : null
		}])
	});
	$('#fBtnCancel').on('click', function() {
		Dialog.close();
	});
});
