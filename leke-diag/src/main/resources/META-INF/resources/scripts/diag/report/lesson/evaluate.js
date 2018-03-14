define(function(require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	var Utils = require('utils');
	var Handlebars = require('common/handlebars');

	var toFixed = Utils.Number.toFixed;
	function toRate(value, total, digit) {
		if (total == 0) {
			return 0;
		}
		return Utils.Number.toFixed(value * 100 / total, digit || 0);
	}

	var page = Page.create({
		id : 'jTablePage',
		url : 'list.htm',
		formId : 'jTableForm',
		callback : function(datas) {
			var dataList = datas.page.dataList;
			dataList.forEach(function(v) {
				v.overallLevel = toFixed((v.professionalLevel + v.rhythmLevel + v.interactionLevel) / 3, 1);
				v.overallRate = toRate(v.overallLevel, 5);
				v.professionalRate = toRate(v.professionalLevel, 5);
				v.rhythmRate = toRate(v.rhythmLevel, 5);
				v.interactionRate = toRate(v.interactionLevel, 5);
			});
			Handlebars.render("#jTemplate", dataList, "#jTableBody");
		}
	});

	$('#jHasText, #jTableForm input:radio').change(function() {
		page.options.curPage = 1;
		page.loadData();
	});
});
