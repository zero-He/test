
var viewVM = {
		init: function(){
			var self = this;
			self.questionId = window.viewCtx.questionId;
			self.$el = $('#j-question-content');
			self.buildQuestionHtml();
			self.bindEvent();
		},
		bindEvent: function(){
			var self = this;
			$('.j-favorite').on('click',function(){
				var $this = $(this);
				var req = questionService.doFavorite(self.questionId);
				req.then(function(datas){
					Utils.Notice.print('收藏成功！');
					$this.addClass('collectionscur');
					$this.removeClass('collections');
				},function(msg){
					Utils.Notice.print( msg || '收藏失败！');
				});
			});
			$('.j-praise').on('click',function(){
				var req = questionService.doPraise(self.questionId);
				req.then(function(datas){
					Utils.Notice.print('点赞成功！');
				},function(msg){
					Utils.Notice.print( msg || '点赞失败！');
				});
			});
			
			$('.j-question-feedback').on('click',function(){
				Leke.control.openQuestionFeedback(self.questionId);
			});
			
			//加入习题篮
			$('#j-to-paper-add').on('click',function(){
				var $this = $(this);
				Leke.questionCart.add(self.questionId);
				$this.hide();
				$('#j-to-paper-del').show();
			});
			
			$('#j-to-paper-del').on('click',function(){
				var $this = $(this);
				Leke.questionCart.del(self.questionId);
				$this.hide();
				$('#j-to-paper-add').show();
			});
			
			//初始化习题篮
			Leke.questionCart.init({callBack: function(ids){
				if(ids.length && ids.indexOf(self.questionId) > -1){
					$('#j-to-paper-del').show();
					$('#j-to-paper-add').hide();
				}
				
			}});
		},
		buildQuestionHtml: function(){
			var self = this;
			var req = $.ajax({
				type: 'post',
				url: Leke.ctx + '/auth/common/question/pad/detail.htm?questionId=' + self.questionId,
				dataType: 'html'
			});
			req.then(function(resp, status, ajax) {
				self.$el.html(resp);
			});
		}
};

viewVM.init();