define(function(require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	var Handlebars = require('common/handlebars');

	var page = Page.create({
		id : 'jTablePage',
		url : 'person-behavior-homework-data.htm',
		formId : 'jTableForm',
		tips : '暂无数据，有作业就要及时完成哦！',
		callback : function(datas) {
			var dataList = datas.page.dataList;
			Handlebars.render("#jTemplate", dataList, "#jTableBody");
		}
	});

	$('#jStatusTab').on('click', 'li', function() {
		var status = $(this).data('status');
		$(this).addClass('active').siblings().removeClass('active');
		$('#jSubmitStatus').val(status);
		page.options.tips = $(this).data('tips');
		page.options.curPage = 1;
		page.loadData();
	});
});
