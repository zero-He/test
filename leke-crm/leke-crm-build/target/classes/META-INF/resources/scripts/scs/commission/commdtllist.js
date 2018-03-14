define(function(require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	var Utils = require('utils');
	var Handlebars = require("common/handlebars");
	var PageSort = require("scs/common/pagesort")

	var Pages = {
		init : function() {
			Page.create({
				id : 'jPageFoot',
				url : 'commdtllist.htm',
				pageSize : 20,
				buttonId : 'jPageSearch',
				formId : 'jPageForm',
				callback : function(datas) {
					Handlebars.render('#jPageTpl', datas.page, '#jPageBody');
					$('#jPageTotal').text(Utils.Number.format(datas.total, '#,##0.00'));
				}
			});
			PageSort.init();
		}
	};

	Pages.init();

});
