define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require('utils');
	var Dialog = require('dialog');

	var Pages = {
		init : function() {
			$('.m-tab li').click(function() {
				$(this).addClass('active').siblings().each(function() {
					$($(this).removeClass('active').data('tabid')).hide();
				});
				$($(this).data('tabid')).show();
			});
			$('#btnReWrite').click(function() {
				var taskId = $(this).data('id');
				Dialog.confirm('你确定要重新写入吗？').done(function(sure) {
					if (sure) {
						$.get('/auth/teacher/sheet/rewrite.htm', {
							taskId : taskId
						}).done(function(json) {
							if (json.success) {
								Utils.Notice.alert('重写已经提交，请稍后查看作业');
							}
						})
					}
				});
			});
		}
	};

	Pages.init();
});
