define(function(require, exports, module) {
	var $ = require('jquery');
	var Mustache = require('mustache');
	var fillBaseNamesService = require('question/questionStatis/fillBaseNamesService');
	
	var DraftAmountList = {
			fInit : function() {
				this.loadList();
			},
			
			loadList:function() {
				var officialTagId = $('#officialTagId').val();
				
				$.ajax({
					type : 'post',
					url : '/auth/admin/questionStatis/ajax/findStatisByOfficialTagId.htm',
					data : {
						officialTagId:officialTagId
					},
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
