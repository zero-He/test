define(function(require, exports, module) {
	var $ = require('jquery');
	var completionHelper = {
		init : function () {
			$('.m-question-operation').each(function(i,n){
				var $el = $(n).parents('.j-question').find('.j-decision,.p-fillblank');
				if($el.length  ==1){
					$el.each(function(i,m){
						$(m).remove();
					});
				}
			});
		}	
	}
	completionHelper.init();
//	module.exports = completionHelper;
});
