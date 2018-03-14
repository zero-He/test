define(function(require, exports, module) {
	var $ = require('jquery');

	var Analying = {
		init : function() {
			var timeId = setInterval(function() {
				$.post('genIsEnd.htm?homeworkId=' + homeworkId, function(json) {
					if (json.success && json.datas.statsStatus == 2) {
						clearInterval(timeId);
						location.reload(true);
					}
				});
			}, 5000);
		}
	};

	Analying.init();
});
