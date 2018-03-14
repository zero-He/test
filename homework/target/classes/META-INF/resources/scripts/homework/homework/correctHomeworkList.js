define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require("utils");
	var Handlebars = require("common/handlebars");
	var I18n = require('homework/common/i18n');
	var LekeDate = require('common/base/date');
	var Page = require('page');
	var Homework = {
		init : function() {
			this.loadHomeworkDtlList();
		},
		loadHomeworkDtlList : function(sort) {
			Page.create({
				id : 'divPage',
				url : ctx + '/auth/student/loadCorrectHomework.htm',
				curPage : 1,
				pageSize : 10,
				pageCount : 10,
				tipsId: 'j_emptyData',
				callback : function(json) {
					var page = json.page;
					$.each(page.dataList,function(i,n){
						if(n.resType == 1){
							n.resTypeName = '课件';
						} else if( n.resType == 2) {
							n.resTypeName = '微课';
						} else {
							n.resTypeName = '试卷';
						}
						if(n.submitTime != null) {
							n.fmtSubmitTime = LekeDate.format(n.submitTime,'yyyy-MM-dd HH:mm:ss');
						}
						else {
							n.fmtSubmitTime ='未提交';
						}
					});
					Handlebars.render("#j-tmpl-homework", page, '#j-body-homework');
				}
			});
		}
	};

	Homework.init();

});
