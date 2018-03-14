define(function(require, exports, module) {
	var $ = require('jquery');
	var Handlebars = require("common/handlebars");
	var Page = require('page');
	var utils = require('utils');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	var win = {
		init : function() {
			this.bindEvent();
			this.loadData();
		},
		bindEvent : function() {
			//查询
			$('#jSearch').click(function() {
				win.loadData();
			});
			//学科
			$('#jStageSubject').stgGrdSbjSelect({
				type : 'sbj',
				onChange : function(selection) {
					$('#jsubjectId').val(selection.subjectId);
				}
			});
		},
		loadData : function() {
			Page.create({
				id: 'jPageFoot',
				url:ctx + '/auth/teacher/achievement/findTeacherAchievement.htm',
				curPage:1,
				pageSize:10,
				pageCount:10,//分页栏上显示的分页数
				buttonId: '',
				formId:'jSearchForm',
				callback:function(datas){
					var page = datas.page;
					if(page != null) {
						var output = Handlebars.render("#jTemplate",page,"#jBody");
					}
				}
			});
		}
	};

	win.init();

});
