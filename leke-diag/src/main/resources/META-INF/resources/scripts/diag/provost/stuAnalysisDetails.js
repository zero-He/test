define(function(require, exports, module) {
	var $ = require('jquery');
	var Handlebars = require("common/handlebars");
	var Page = require('page');
	var utils = require('utils');
	
	var TeaDiligent = {
		init : function() {
			this.bindEvent();
			this.loadData();
		},
		bindEvent : function() {
			$(document).on('click','.jDetails',function(){
				var href = $(this).data('href');
				var subjectName = encodeURIComponent(encodeURIComponent($(this).data('subjectname')));
				var gradeName = encodeURIComponent(encodeURIComponent($('#jGradeName').text()));
				href += '&subjectName='+subjectName + '&gradeName='+ gradeName;
				window.open(href);
			});
		},
		loadData : function() {
			var url = ctx + '/auth/provost/swot/findStuAnalysisDetails.htm';
			var data = { gradeId : $('#jGradeId').val() };
			$.post(url, data, function(json){
				var dataList = json.datas.dataList;
				if(dataList != null) {
					Handlebars.render("#jTemplate",dataList,"#jBodyData");
				}else{
					$('#jEmptyData').show();
				}
			});
		}
	};

	TeaDiligent.init();

});
