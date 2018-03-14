
/**
 * 页面Flash加载
 */
define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require('utils');
	var JSON = require('json');
	var swfobject = require('common/swfobject');
	var Flash = require('core-business/flashloader');
	var OL_SVR = window.Leke && Leke.domain.onlineServerName;
	if (!OL_SVR) {
		OL_SVR = (window.location.protocol == 'http:' ? 'http:' : 'https:') + '//onlineclass.leke.cn'
	}

	window.onDeleteAudioComplete = function(id) {
		$('#' + id).val('');
	};
	window.onAddAudioComplete = function(url, id) {
		var fileUrl = Leke.domain.fileServerName;
		$('#' + id).val(Utils.HtmlComments.wrap(fileUrl + '/' + url, 'recorder'));
	};
	window.onAsCanvasSaveComplete = function(content, url, id) {
		$('#' + id).val(content);
	}
	window.getDataByElementId = function(id) {
		var src = $('#' + id).parents('.que-body').find('.que-stem').html();
		return {
			src : src,
			content : $('#' + id).val()
		};
	}

	var Handlers = {
		// 短小语音播放，无进度条
		// <dfn class="j-dfn" data-type="play" data-url="https://file.leke.cn/aa/bb/cccccc.mp3"></dfn>
		play : function(target) {
			var audioUrl = $(target).data('url');
			var flashUrl = ctx + '/flashs/common/ui/ui-videoclass/sound/SoundPlayBar.swf?audioUrl=' + audioUrl;
			this.embed(target, flashUrl, '120px', '25px', null, null, {
				styleclass : 'f-vam'
			});
		},
		// 长语音播放，有进度条
		// <dfn class="j-dfn" data-type="audio" data-url="https://file.leke.cn/aa/bb/cccccc.mp3"></dfn>
		audio : function(target) {
			var audioUrl = $(target).data('url');
			var flashUrl = ctx + '/flashs/common/ui/ui-videoclass/sound/SoundPlayer.swf?audioUrl=' + audioUrl;
			this.embed(target, flashUrl, '295px', '30px', null, null, {
				styleclass : 'f-vam'
			});
		},
		// 录制音频
		// <dfn class="j-dfn" data-type="recorder" data-vid="que_1231" data-url="https://file.leke.cn/aa/bb/cccccc.mp3"></dfn>
		recorder : function(target) {
			var vid = $(target).data('vid') || '';
			var url = $(target).data('url') || '';
			var flashUrl = ctx + '/flashs/common/ui/ui-videoclass/sound/SoundRecorder.swf';
			var flashvars = {
				duration : 420,
				isCanDelete : true,
				audioUrl : url,
				elementId : vid,
				ticket : Leke.ticket,
				uploadUrl : ctx + '/auth/common/upload/audioUpload.htm'
			};
			this.embed(target, flashUrl, '350px', '150px', flashvars, null, {
				styleclass : 'f-vam'
			});
		},
		// 手写题
		// <dfn class="j-dfn" data-type="handwrite" data-elementid="123"></dfn>
		handwrite : function(target) {
			var vid = $(target).data('vid');
			var flashvars = {
				elementId : vid,
				funcs : 'text,pen,shape,color,eraser,clear,cancel,submit',
				ticket : Leke.ticket,
				uploadUrl : '/auth/common/upload/imageUpload.htm'
			};
			var flashUrl = ctx + '/flashs/common/ui/ui-videoclass/canvas/Canvas.swf';
			this.embed(target, flashUrl, '100%', '350px', flashvars, {
				wmode : "transparent"
			}, {
				styleclass : 'f-vam'
			});
		},
		// 根据参数生成Flash
		embed : function(target, url, width, height, flashvars, params, attrs) {
			var id = 'dfnid_' + Math.random();
			$(target).attr('id', id);
			params = $.extend({
				wmode : "transparent",
				menu : "false"
			}, params || {});
			swfobject.embedSWF(url, id, width, height, "11.1.0", "expressInstall.swf", flashvars, params, attrs);
		}
	};

	var doc = null;
	var $d = $('.j-doc');
	if ($d.length > 0) {
		var initparams = $d.data('initparams');
		doc = Flash.embedDoc($d, {
			width : 712,
			height : 1000,
			onInit : function() {
				return $.extend({}, initparams, {
					serverIp : OL_SVR
				});
			}
		});
	}

	var Flash = {
		// 扫描
		scan : function(el) {
			$('.j-dfn').each(function() {
				var type = $(this).data('type');
				if (Handlers[type]) {
					Handlers[type](this);
				}
			});
		},
		doc : doc
	}

	module.exports = Flash;
});