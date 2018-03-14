define(function(require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	var Utils = require('utils');
	var WdatePicker = require('date');
	var Handlebars = require("common/handlebars");
	var PageSort = require("scs/common/pagesort");

	var Pages = {
		page : null,
		init : function() {
			this.initPage();
			this.bindEvent();
		},
		initPage : function() {
			this.page = Page.create({
				id : 'jPageFoot',
				url : 'commlist.htm',
				pageSize : 20,
				buttonId : 'jPageSearch',
				formId : 'jPageForm',
				callback : function(datas) {
					datas.page.month = $('#jMonth').val();
					Handlebars.render('#jPageTpl', datas.page, '#jPageBody');
					$('#jPageTotal').text(Utils.Number.format(datas.total, '#,##0.00'));
				}
			});
			PageSort.init();
		},
		bindEvent : function() {
			$('#jMonth').click(function() {
				WdatePicker({
					dateFmt : 'yyyy-MM',
					isShowClear : false,
					readOnly : true
				});
			});
		}
	};

	Pages.init();

});
