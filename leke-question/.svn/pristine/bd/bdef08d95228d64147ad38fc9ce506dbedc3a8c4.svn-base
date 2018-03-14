define(function(require, exports, module) {
	var $ = require('jquery');
	var Mustache = require('mustache');
	var utils = require('utils');
	
	var RecycleQuestionTask = {
			fInit : function() {
				this.fBindEvent();
				this.loadList();
			},
			
			fBindEvent:function() {
				var _this = this;
				$('#bSearch').on('click',  $.proxy(_this, 'loadList'));
				$('#listContext').on('click', '.link', $.proxy(_this, 'doRecycle'));
			},
			
			doRecycle:function(evt) {
				var _this = this;
				var userId = $(evt.target).data('id');
				$.ajax({
					type : 'post',
					url : ctx + '/auth/admin/questionTask/ajax/recycleQuestionTask.htm',
					data : {
						userId:userId
					},
					dataType : 'json',
					success : function(json) {
						if (json.datas.result == 1) {
							utils.Notice.alert('回收成功！');
							_this.loadList();
						} else {
							utils.Notice.alert('回收失败！');
						}
					}
				});
			},
			
			loadList:function() {
				var userName = $('#userName').val();
				
				$.ajax({
					type : 'post',
					url : ctx + '/auth/admin/questionTask/ajax/findQuestionTaskSurplusGroupByUserId.htm',
					data : {
						userName:userName
					},
					dataType : 'json',
					success : function(json) {
						var listjson = json.datas;
						var output = Mustache.render($('#listContext_tpl').val(),listjson);
						$('#listContext').html(output);
						
						var taskTotalCount = 0;
						for(var i=0; i<listjson.qtList.length; i++){
							taskTotalCount += listjson.qtList[i].taskCount;
						}
						$('#taskTotalCount').html(taskTotalCount);
					}
				});
			}
	};
	
	RecycleQuestionTask.fInit();
});

