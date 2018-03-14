define(function(require, exports, module) {
	var $ = require('jquery');
	var fileRender = require('homework/common/website-file-render');

	var datas = {
		homeworkId : Csts.homeworkId,
		homeworkDtlId : Csts.homeworkDtlId,
		position : Csts.position,
		duration : -1,
		isPlayEnd : false
	}

	var type = Csts.file.type;
	if (type == 'mp3' || type == 'mp4') {
		// 微课定时保存
		fileRender('jPreviewDiv', Csts.file, Csts.position, function(position, duration) {
			console.log('position: ' + position + ', duration: ' + duration);
			if (duration == 0) {
				return;
			}
			if (position + 3 <= duration) {
				position = 0;
			}
			datas = $.extend(datas, {
				position : position,
				duration : duration
			});
		}, function() {
			console.log('paly end.');
		});

		setInterval(function() {
			$.post('saveWork2.htm', datas);
		}, 30000);
	} else {
		// 课件翻页保存
		fileRender('jPreviewDiv', Csts.file, Csts.position, function(position, duration) {
			console.log('position: ' + position + ', duration: ' + duration);
			datas = $.extend(datas, {
				position : position,
				duration : duration
			});
			$.post('saveWork2.htm', datas);
		});
	}
});
