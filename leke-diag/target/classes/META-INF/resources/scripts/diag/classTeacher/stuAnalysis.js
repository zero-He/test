define(function(require, exports, module) {
	var $ = require('jquery');
	var dialog = require('dialog');
	var Page = require('page');
	var Mustache = require('mustache');

	var win = {
		// 初始化
		init : function() {
			var $firstClass = $('.jTabClass li').first();
			if($firstClass.length > 0){
				$('#jClassId').val($firstClass.data('id'));
				this.loadList();
				this.bindEvent();
			}
		},
		// 加载数据
		loadList : function() {
			var _this = this;
			var classId = $('#jClassId').val();
			var data = {classId : classId };
			var url = ctx + '/auth/classTeacher/swot/stuAnalysisData.htm';
			$.post(url,data,function(json){
				var dataList = json.datas.dataList;
				if(dataList != null && dataList.length > 0) {
					var output = Mustache.render($("#jstuAnalysisList_tpl").val(),dataList);
					$('#jstuAnalysisListBody').html(output);
					$('#jEmptyData').hide();
				}else{
					$('#jstuAnalysisListBody').html("");
					$('#jEmptyData').show();
				}
			});
		},
		// 绑定查询事件和查看事件
		bindEvent : function() {
			//班级tab 切换
			$(document).on('click','.jTabClass li',function(){
				$('.jTabClass li').removeClass('active');
				$(this).addClass('active');
				$('#jClassId').val($(this).data('id'));
			    win.loadList();
			});
			//详情页
			$(document).on('click','.jDetails',function(){
				var href = $(this).data('href');
				href +='&className='+ encodeURIComponent(encodeURIComponent($.trim($('.jTabClass li.active a').text())));
				window.open(href);
			});
		}
	};
	win.init();

});
