define(function(require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	var Handlebars = require("common/handlebars");
	var PageSort = require("scs/common/pagesort")

	var Pages = {
		init : function() {
			this.initPage();
		},
		initPage : function() {
			Page.create({
				id : 'jPageFoot',
				url : 'pointlist.htm',
				pageSize : 20,
				buttonId : 'jPageSearch',
				formId : 'jPageForm',
				callback : function(datas) {
					Handlebars.render('#jPageTpl', datas.page, '#jPageBody');
				}
			});
			PageSort.init();
		}
	};

	Pages.init();

});
