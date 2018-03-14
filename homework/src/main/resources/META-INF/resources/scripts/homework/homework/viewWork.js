define(function(require, exports, module) {
	var $ = require('jquery');
	require('homework/sheet.struct');
	require('homework/sheet.render');
	require('homework/common/completionHelper');

	var ViewWork = {
		init : function() {
			$('.j-jump').click(function() {
				var url = $(this).data('url');
				var homeworkId = $(this).data('homeworkid');
				var data = {
					homeworkId : homeworkId,
					homeworkDtlId : $(this).data('homeworkdtlid')
				};
				$.post(url, data, function(json) {
					if (json.success) {
						location.href = 'viewWork.htm?homeworkId=' + homeworkId + '&homeworkDtlId='
								+ json.datas.homeworkDtlId;
					}
				});
			});
		}
	};

	ViewWork.init();

});
