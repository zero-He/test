define(function(require, exports, module) {
	var $ = require('jquery');
	var Mustache = require('mustache');
	var Page = require('page');
	var Utils = require("utils");
	var I18n = require('homework/common/i18n');

	var HomeworkList = {
		page : null,
		init : function() {
			this.initLoadPage();
		},
		initLoadPage : function() {
			this.page = Page.create({
				id : 'divPage',
				url : 'loadHomeworkList.htm',
				curPage : 1,
				pageSize : 10,
				pageCount : 10,
				buttonId : 'bHomeworkList',
				formId : 'formPage',
				callback : function(datas) {
					var page = datas.page;
					$.each(page.dataList, function() {
						this.avgScore = Utils.Number.toFixed(this.avgScore, 1);
					});
					page.isInvalid = function() {
						return this.status === 2;
					}
					var output = Mustache.render($("#homeworkContext_tpl").val(), page);
					$('#homeworkListContext').html(output);
				}
			});
			$('#homeworkListContext').on('click', '.jStats', function() {
				var homeworkId = $(this).data('homeworkid');
				var resText = $.ajax({
					url : ctx + '/auth/teacher/homework/tryGenerate.htm?homeworkId=' + homeworkId,
					async : false
				}).responseText;

				var data = $.parseJSON(resText);

				if (data.success == false) {
					// 有错误时，不打开页面
					return false;
				}

				if (data.success && data.datas.genFlag) {
					// 已经生成的直接打开页面
					return true;
				}

				var message = $.i18n.prop('homework.diag.alert.message.success');
				if (data.datas.isAllCorrect == false) {
					message = $.i18n.prop('homework.diag.alert.message.correct') + message;
				}
				if (confirm(message)) {
					// 打开生成页面
					$(this).attr('href', $(this).data('href') + '&_t=' + Math.random());
					return true;
				} else {
					// 用户取消，不打开页面
					return false;
				}
			});
		}
	};
	HomeworkList.init();

});
