define(function(require, exports, module) {
	var $ = require('jquery');
	var Mustache = require('mustache');
	var fillBaseNamesService = require('question/questionStatis/fillBaseNamesService');
	
	var DraftAmountList = {
			fInit : function() {
				this.loadList();
			},
			
			loadList:function() {
				$.ajax({
					type : 'post',
					url : ctx + '/auth/admin/questionStatis/ajax/findDraftAmountList.htm',
					data : {},
					dataType : 'json',
					success : function(json) {
						var listjson = json.datas;
						fillBaseNamesService.inputStatis(listjson.inputStatisList).done(function(list) {
							listjson.inputStatisList = list;
							var output = Mustache.render($('#listContext_tpl').val(),listjson);
							$('#listContext').html(output);
						});
					}
				});
			}
	};
	
	DraftAmountList.fInit();
});