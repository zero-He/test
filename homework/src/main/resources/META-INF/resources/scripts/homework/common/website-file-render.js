define(function(require, exports, module) {
	var $ = require('jquery');
	var swfobject = require('common/swfobject');

	var parvars = {
		quality : 'high',
		bgcolor : '#ffffff',
		allowscriptaccess : 'always',
		allowfullscreen : 'true',
		wmode : 'transparent'
	}, attvars = {
		id : 'CourseView',
		name : 'CourseView',
		align : 'middle'
	};

	var MediaOpts = {
		swfName : 'MediaApplication.swf',
		width : '100%',
		height : '473'
	};
	var DocumentOpts = {
		swfName : 'OnlineSchool.swf',
		width : '800',
		height : '640'
	}

	function embedSWF(id, flashvars, opts) {
		var swfUrl = assets + '/flashs/common/ui/ui-videoclass/preview/' + opts.swfName;
		swfobject.embedSWF(swfUrl, id, opts.width, opts.height, '10.2.0', 'playerProductInstall.swf', flashvars,
				parvars, attvars);
		swfobject.createCSS('#' + id, 'display:block; text-align:left;');
	}

	function render(id, file, position, onProgressCallback, onPlayEndCallback) {
		if (file.type == 'mp3' || file.type == 'mp4') {
			var suffix = file.type == 'mp3' ? 'mp3' : 'flv';
			var flashvars = {
				taskId : file.id,
				position : position || 0,
				rtmpIp : Leke.domain.rtmpServerName,
				rtmpPath : suffix + ':' + file.cwUrl
			};
			embedSWF(id, flashvars, MediaOpts);
			window.onProgress = onProgressCallback;
			window.onPlayEnd = onPlayEndCallback;
		} else {
			var flashvars = {
				taskId : file.id,
				index : position || 1,
				serverIp : Leke.domain.onlineServerName,
				transType : file.cwSuffix,
				count : file.pageCount,
				name : file.name,
				filePath : file.cwUrl
			};
			embedSWF(id, flashvars, DocumentOpts);
			window.onProgress = onProgressCallback
		}
	}

	module.exports = render;
});