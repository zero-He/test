define(function(require, exports, module) {
	var $ = require('jquery');
	var swfobject = require('common/swfobject');

	window.onDeleteAudioComplete = function(elementId) {
		$('#' + elementId).val('');
	};
	window.onAddAudioComplete = function(url, elementId) {
		$('#' + elementId).val(url);
	};

	var Sound = {
		init : function() {
			$('.j-dfn').each(function() {
				var type = $(this).data('type');
				if (Sound[type]) {
					Sound[type](this);
				}
			});
		},
		recorder : function(target) {
			var flashUrl = ctx + '/flashs/common/ui/ui-videoclass/sound/SoundRecorder.swf';
			var flashvars = {
				elementId : $(target).data('elementid'),
				isCanDelete : true,
				ticket : Leke.ticket,
				uploadUrl : '/auth/common/upload/audioUpload.htm',
				audioUrl : '',
				duration : 420
			};
			this.embed(target, flashUrl, '220px', '145px', flashvars, null, {
				styleclass : 'f-vam'
			});
		},
		audio : function(target) {
			var audioUrl = $(target).data('url');
			var flashUrl = assets + '/flashs/common/ui/ui-videoclass/sound/SoundPlayer.swf?audioUrl=' + audioUrl;
			this.embed(target, flashUrl, '200px', '30px', null, null, {
				styleclass : 'f-vam'
			});
		},
		embed : function(target, url, width, height, flashvars, params, attrs) {
			var id = 'dfnid_' + Math.random();
			$(target).attr('id', id);
			params = $.extend({
				wmode : "transparent",
				menu : "false"
			}, params || {});
			swfobject.embedSWF(url, id, width, height, "11.1.0", "expressInstall.swf", flashvars, params, attrs);
		}
	}

	module.exports = Sound;
})