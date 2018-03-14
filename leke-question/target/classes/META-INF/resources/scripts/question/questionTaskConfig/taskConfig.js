define(function(require, exports, module) {
	var $ = require('jquery');
	var utils = require('utils');
	
	var TaskConfig = {
			fInit : function() {
				this.fBindEvent();
			},
			
			fBindEvent:function() {
				var _this = this;
				
				$('#bAdd').on('click', $.proxy(_this, 'doAdd'));
			},
			
			doAdd:function(evt) {
				var _this = this;
				var taskCount = $('#taskCount').val();
				var reg =/^[0-9]*$/;
				if (taskCount==null || taskCount =='' || !reg.test(taskCount)) {
					utils.Notice.alert('请填写数字！');
					return;
				}
				$.ajax({
					type : 'post',
					url : ctx + '/auth/admin/questionTaskConfig/ajax/setTaskConfig.htm',
					data : {
						taskCount:taskCount
					},
					dataType : 'json',
					success : function(json) {
						if (json.datas.result == 1) {
							utils.Notice.alert('设置成功！');
							$('#taskCount').val('');
						} else {
							utils.Notice.alert('设置失败！');
						}
					}
				});
			}
	};
	
	TaskConfig.fInit();
});

