var Exercise = {
	init : function() {
		this.bindEvent();
		var scrollTopLoc = localStorage.exerciseReportTop;
        $('body').animate({scrollTop:scrollTopLoc});
	},
	bindEvent : function() {
		var _this = this;
		$(document).on('click', '.j-knowledge-exercise', function() {
			var kid = $(this).parent('li').data('kid');
			_this.tagScroll();
			//传给平板端
			//long
			//window.auto_practice.strengthenByKnowledge(kid);
			LeKeBridge.sendMessage2Native('strengthenByKnowledge',JSON.stringify({'knowledgeId':kid}))
		})
		$(document).on('click', '.j-knowledge-detail', function() {
			var kid = $(this).parent('li').data('kid');
			var exerciseId = $('#j_exerciseId').val();
			_this.tagScroll();
			//传给平板端
			//long ,string
			//window.auto_practice.knowledgeDetail(kid,exerciseId);
			LeKeBridge.sendMessage2Native('knowledgeDetail',JSON.stringify({'kid':kid,'exerciseId':exerciseId}))
		})

	},
	tagScroll:function(){
		var scrollTopVal = $('body').scrollTop();
        localStorage.exerciseReportTop = scrollTopVal;
	},
};
Exercise.init();
