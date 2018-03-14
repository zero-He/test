define(function(require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	var Handlebars = require("common/handlebars");

	var Pages = {
		init : function() {
			Page.create({
				id : 'jPageFoot',
				url : 'student.htm',
				pageSize : 20,
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
