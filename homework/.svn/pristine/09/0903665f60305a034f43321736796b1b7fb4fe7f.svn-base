define(function(require, exports, module) {
	var $ = require('jquery');
	var _ = require('underscore');
	var Handlebars = require("common/handlebars");
	var win = {
		init : function() {
			this.loadData();
			this.bindEvent();
		},
		loadData : function() {
			var url = ctx + '/auth/teacher/homework/getClassHomeworksData.htm';
			$.post(url,function(datas){
				if(datas.datas.dataList != null && datas.datas.dataList.length > 0){
					var dataList = _.map(datas.datas.dataList, function(n){
						n.classTypeStr = '';
						if(n.classType == 3){
							n.classTypeStr = 'layout';
						}
						return n;
					});
					Handlebars.render("#myHomeworkContext_tpl", dataList,"#myHomeworkContext");
					$('#j_emptyData').hide();
				}else{
					$('#j_emptyData').show();
					$('.j_allHomework').data('type','_self');
				}
			});
		},
		bindEvent:function(){
			$('.j_allHomework').click(function(){
				var href = '/auth/teacher/homework/homeworkList.htm?ord=1';
				if($(this).data('type') == '_self') {
					location.href = href;
				} else {
					window.open(href);
				}
			});
			$(document).on('click','.j_detail',function(){
				var href = $(this).data('href')+'&className='+encodeURIComponent(encodeURIComponent($(this).data('clazz')));
				window.open(href);
			})
		}
	};

	win.init();

});
