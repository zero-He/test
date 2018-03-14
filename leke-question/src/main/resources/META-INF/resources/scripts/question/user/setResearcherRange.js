define(function(require, exports, module) {
	var $ = require('jquery');
	var Mustache = require('mustache');
	var utils = require('utils');
	
	var SetCheckRange = {
			fInit : function() {
				this.fBindEvent();
				this.loadList();
			},
			
			fBindEvent:function() {
				var _this = this;
				$('#bSearch').on('click', function(){
					_this.loadList();
				});
			},
			
			
			
			loadList:function() {
				var userName = $('#userName').val();
				var schoolStageId = $('#schoolStageId').val();
				var subjectId = $('#subjectId').val();
				if((schoolStageId > 0 && subjectId == 0) || (schoolStageId == 0 && subjectId > 0)) {
					utils.Notice.alert('请同时选择学段科目！');
					return;
				}
				$.ajax({
					type : 'post',
					url : ctx + '/auth/admin/user/ajax/findQuestionUserList.htm',
					data : {
						userName : userName,
						schoolStageId : schoolStageId,
						subjectId : subjectId
					},
					dataType : 'json',
					success : function(json) {
						var listjson = json.datas;
						
						var output = Mustache.render($('#listContext_tpl').val(),listjson);
						$('#listContext').html(output);
						
					}
				});
			}
	};
	
	SetCheckRange.fInit();
});
