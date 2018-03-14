define(function(require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	var Utils = require('utils');
	var WdatePicker = require('date');
	var Handlebars = require("common/handlebars");

	var Pages = {
		page : null,
		init : function() {
			$('#jStartDate').click(function() {
				WdatePicker({
					dateFmt : 'yyyy-MM-dd',
					maxDate : '#F{$dp.$D(\'jEndDate\');}',
					readOnly : true
				});
			});
			$('#jEndDate').click(function() {
				WdatePicker({
					dateFmt : 'yyyy-MM-dd',
					minDate : '#F{$dp.$D(\'jStartDate\');}',
					readOnly : true
				});
			});
			this.page = Page.create({
				id : 'jPageFoot',
				url : 'taskList.htm',
				pageSize : 10,
				buttonId : 'jPageSearch',
				formId : 'jPageForm',
				callback : function(datas) {
					Handlebars.render('#jPageTpl', datas.page, '#jPageBody');
				}
			});
		}
	};

	Pages.init();
});
