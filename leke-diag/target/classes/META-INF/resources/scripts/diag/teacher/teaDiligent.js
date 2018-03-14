define(function(require, exports, module) {
	var $ = require('jquery');
	var Handlebars = require("common/handlebars");
	var Page = require('page');
	var utils = require('utils');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	var TeaDiligent = {
		init : function() {
			this.bindEvent();
			this.loadCourseSubjectInfo();
		},
		bindEvent : function() {
			$('#jStageSubject').stgGrdSbjSelect({
				type : 'sbj',
				onChange : function(selection) {
					$('#jSubjectId').val(selection.subjectId);
				}
			});
		},
		loadCourseSubjectInfo:function(){
			Page.create({
				id: 'jPageFoot',
				url:ctx + '/auth/teacher/diligent/findCourseSubjectInfo.htm',
				curPage:1,
				pageSize:10,
				pageCount:10,//分页栏上显示的分页数
				buttonId: 'jSearch',
				formId:'searchForm',
				callback:function(datas){
					var page = datas.page;
					if(page != null) {
						var output = Handlebars.render("#jtemplateData",page,"#jtbodyData");
					}
				}
			});
		}
	};

	TeaDiligent.init();

});
