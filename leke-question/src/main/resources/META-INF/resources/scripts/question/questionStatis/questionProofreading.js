define(function(require, exports, module) {
	var $ = require('jquery');
	var Mustache = require('mustache');
	
	var QuestionProofreading = {
			fInit : function() {
				this.fLoad();
			},
			
			fLoad : function() {
				$.ajax({
					type : 'post',
					url : ctx + '/auth/researcher/questionStatis/ajax/findQuestionProofreading.htm',
					data : {},
					dataType : 'json',
					success : function(json) {
						var listjson = json.datas;
						var output = Mustache.render($('#listContext_tpl').val(),listjson);
						$('#listContext').html(output);
					}
				});
			}
	};
	
	QuestionProofreading.fInit();
});