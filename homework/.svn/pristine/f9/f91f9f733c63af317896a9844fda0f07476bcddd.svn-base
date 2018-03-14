define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require("utils");
	var Dialog = require('dialog');
	var Sound = require('homework/common/Sound');

	var Microcourse = {
		init : function() {
			Sound.init();
			$('#fBtnSave').on('click', function() {
				var audioUrl = $('#audioUrl').val();
				if (audioUrl == '') {
					Utils.Notice.alert('你先录音后再提交');
					return;
				}
				Dialog.close('recorder', {
					type : 'audio',
					text : '',
					link : Leke.domain.fileServerName + '/' + audioUrl
				})
			});
			$('#fBtnCancel').on('click', function() {
				Dialog.close();
			});
		}
	};

	Microcourse.init();
});
