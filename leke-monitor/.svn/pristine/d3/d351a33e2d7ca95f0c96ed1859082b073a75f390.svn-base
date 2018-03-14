define(function(require, exports, module) {
	var $ = require('jquery');

	var PageSort = {
		init : function(opts) {
			var opts = $.extend(opts || {}, {
				headId : '#jPageHead',
				buttonId : '#jPageSearch',
				orderId : '#jPageOrder',
				sortId : '#jPageSort',
				cls_sort : 'm-sort-leke',
				cls_def : 'm-sort-default',
				cls_asc : 'm-sort-asc',
				cls_desc : 'm-sort-desc'
			});
			$(opts.headId + ' .' + opts.cls_sort).append('<i class="' + opts.cls_def + '"></i>').click(function() {
				var icon = $(this).find('i');
				$(opts.headId + ' .' + opts.cls_sort + ' i').addClass(opts.cls_def);
				$(opts.orderId).val($(this).data('column'));
				if (icon.hasClass(opts.cls_asc)) {
					$(opts.sortId).val('desc');
					icon.addClass(opts.cls_desc).removeClass(opts.cls_asc).removeClass(opts.cls_def);
				} else {
					$(opts.sortId).val('asc');
					icon.addClass(opts.cls_asc).removeClass(opts.cls_desc).removeClass(opts.cls_def);
				}
				$(opts.buttonId).trigger('click');
			});
		}
	}

	module.exports = PageSort;
});