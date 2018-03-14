define(function(require, exports, module) {
	var $ = require('jquery');

	var Viewer = {
		init : function() {
			$('.j-essay-answer').each(function() {
				$(this).replaceWith(function() {
					return '<div style="width:99%; border: 1px solid rgb(169, 169, 169);"><div style="min-height: 100px; margin: 10px; word-break:break-all;">'
							+ $(this).val() + '</div></div>';
				})
			});
		}
	}

	Viewer.init();
});