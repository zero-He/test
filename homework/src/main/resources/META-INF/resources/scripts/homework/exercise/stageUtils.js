define(function(require, exports, module) {
	var $ = require('jquery');
	var utils = {
			doAboveStageData : function (id,callback) {
				var _this = this;
				$.getJSON('/auth/student/getStudentStageInfo.htm?jsonpcallback=?',function(json){
					var currStage = json.datas.stage;
					if(currStage != null) {
						var index = 0;
						$('#'+id).find('optgroup').each(function(i,n){
							if($(n).attr('label') == currStage.schoolStageName){
								index = i;
								return false;
							}
						});
						$('#'+id).find('optgroup').each(function(i,n){
							if(i < index){
								$(n).remove();
							}
						});
					}
					if(typeof callback == 'function'){
						callback();
					}
				})
			}
		
	};
	module.exports =  utils;

});
