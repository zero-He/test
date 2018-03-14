
var viewVM = {
		init: function(){
			var self = this;
			self.showAnalysis = false;
			self.questionId = window.viewCtx.questionId;
			self.$el = $('#j-question-content');
			self.buildQuestionHtml();
			self.bindEvent();
		},
		bindEvent: function(){
			var self = this;
			
			$(self.$el).on('click','#j-operation',function(){
				var $this = $(this);
				$('#j-question-analysis').toggle();
				self.showAnalysis = !self.showAnalysis;
				if(self.showAnalysis){
					$this.html('隐藏答案解析');
				}else{
					$this.html('显示答案解析');
				}
			});
		},
		buildQuestionHtml: function(){
			var self = this;
			var req = $.ajax({
				type: 'post',
				url: Leke.ctx + '/auth/common/question/pad/detailPart.htm?questionId=' + self.questionId,
				dataType: 'html'
			});
			req.then(function(resp, status, ajax) {
				self.$el.html(resp);
			});
		}
};
viewVM.init();