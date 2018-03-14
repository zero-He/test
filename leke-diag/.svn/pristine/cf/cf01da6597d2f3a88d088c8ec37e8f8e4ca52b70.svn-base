define(function(require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	var Handlebars = require('common/handlebars');

	Page.create({
		id : 'jTablePage',
		url : 'person-behavior-interact-data.htm',
		formId : 'jTableForm',
		callback : function(datas) {
			var dataList = datas.page.dataList;
			dataList.forEach(function(v) {
				v.student = v.students[0];
				v.rank = 2;
			});
			Handlebars.render("#jTemplate", dataList, "#jTableBody");
		}
	});

});
