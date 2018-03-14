define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require("utils");
	var Handlebars = require('common/handlebars');
	var LekeDate = require("common/base/date");
	Handlebars.registerHelper({
		fmtTime : function(time){
			return LekeDate.format(time,'yyyy-MM-dd HH:mm:ss');
		}
	});
	
	var Homework = {
		loadUrl : '/auth/teacher/homework/getReCorrectHomeworkDetail.htm',
		init : function() {
			this.loadHomeworkDtlList();
			this.bindEvent();
		},
		bindEvent : function() {
			var _this = this;
			$('.j-batchCorrect').click(function(){
				var _btn = this;
				$.post('/auth/teacher/homework/reviewBatchSumitHomeworks.htm',
						{homeworkId:$('#homeworkId').val()},function(data){
							Utils.Notice.alert('操作成功');
							$(_btn).addClass('f-hide');
							$('.j_diabledBtn').removeClass('f-hide');
							_this.loadHomeworkDtlList();
						});
			});
			$('#formPage').submit(function() {
				_this.loadHomeworkDtlList();
				return false;
			});
		},
		loadHomeworkDtlList : function() {
			var data = {
				homeworkId : $('#homeworkId').val(),
				studentName : $('#studentName').val()
			};
			$.post(this.loadUrl, data, function(json) {
				var page = {
					dataList : json.datas.list
				}
				Handlebars.render("#homeworkDtlContext_tpl", page,'#homeworkDtlListContext');
				if(page.dataList.length == 0){
					$('.j-no-data').show();
				}else{
					$('.j-no-data').hide();
				}
			});
		}
	};

	Homework.init();

});
