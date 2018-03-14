define(function(require, exports, module) {
	var $ = require('jquery');
	var Handlebars = require("common/handlebars");
	var Page = require('page');
	var Utils = require("utils");
	var LekeDate = require('common/base/date');
	var HomeworkType = require("homework/common/homeworkType");

	var homeworkEditTpl = $('#jHomeworkEditTpl').html();
	Handlebars.registerHelper({
		fmtTime : function(time){
			return LekeDate.format(time,'yyyy-MM-dd HH:mm:ss');
		}
	});
	var HomeworkList = {
		page : null,
		init : function() {
			var _this = this;
			HomeworkType.render($('#jHomeworkType'));
			this.initLoadPage();
			this.bindEvent();
		},
		initLoadPage : function() {
			this.page = Page.create({
				id : 'divPage',
				url : ctx + '/auth/teacher/homework/queryReCorrectHomeworkList.htm',
				curPage : 1,
				pageSize : 10,
				pageCount : 10,
				buttonId : 'bHomeworkList',
				formId : 'formPage',
				tipsId:'f_emptyDataContainer',
				callback : function(datas) {
					Handlebars.render("#homeworkContext_tpl", datas.page,'#homeworkListContext');
				}
			});
			var _this = this;
		},
		bindEvent : function() {
			//作业类型change
			$('#jHomeworkType').change(function() {
				$('#bHomeworkList').click();//选择作业类型后，刷新数据
			});
		}
	};

	HomeworkList.init();

});
