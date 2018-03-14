define(function(require, exports, module) {
	var $ = require('jquery');
		
	var Handlebars = require("common/handlebars");
	var Page = require('page');
	var win = {
		init : function() {
			this.loadData();
			this.bindEvent();
		},
		loadData : function() {
			var url = ctx + '/auth/student/homeworkDtl/getMyHomework.htm';
			$.post(url,function(datas){
				if(datas.datas.dataList != null && datas.datas.dataList.length > 0){
					Handlebars.render("#myHomeworkContext_tpl", datas.datas.dataList,"#myHomeworkContext");
					$('#j_emptyData').hide();
				}else{
					$('#j_emptyData').show();
					$('.j_allHomework').data('type','_self');
				}
			});
		},
		bindEvent : function(){
			$('.j_allHomework').click(function(){
				var href = '/auth/student/exercise/homework/myHomework.htm';
				if($(this).data('type') == '_self'){
					location.href = href;
				} else {
					window.open(href);
				}
			});
		}
	};

	win.init();

});
