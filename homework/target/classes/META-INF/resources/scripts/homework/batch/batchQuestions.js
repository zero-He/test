define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require('utils');
	var Render = require('homework/sheet.render');
	var Dialog = require('dialog');
	require('common/ui/ui-scrollbar/jquery.scrollbar.min');
	var win = {
		init : function() {
			this.bindEvent();
			this.initShowView();
			$('.j-card-scrollbar').scrollbar();
		},
		bindEvent : function() {
			var _this = this;
			$('.m-correct-question-right .btn').click(function(){
				$('.m-correct-question-right').toggleClass('full');
				_this.initShowView();
			});
			$('.j-exitBatch').click(function(){
				var href =Leke.domain.homeworkServerName+'/auth/teacher/homework/homeworkDetail.htm?homeworkId='+$(this).data('hwid');
				location.href=href;
			});
			var showQuestionItems={
					/*大类题型展开收缩*/
					toggle:function(){
						$('.j-arrow').on('click',  function(event) {
							event.preventDefault();
							var $this=$(this);
							if($this.hasClass('p-arrow-hide')){
								$this.removeClass('p-arrow-hide').parent('.p-group-title').siblings('.p-group-detail').slideDown();
							}else{
								$this.addClass('p-arrow-hide').parent('.p-group-title').siblings('.p-group-detail').slideUp();
							}
						});
					}

				}
				showQuestionItems.toggle();
			
			
			$(document).on('click','.j_selfCheck',function(){
				var hwId= $(this).data('homeworkid');
				var _this = this;
				Dialog.confirm('不作批改后，本次作业中未提交和未批改的学生作业将默认不再批改，不影响已批改的学生作业，确定进行此操作吗？').done(function(sure) {
					if (sure) {
						var url ="/auth/teacher/homework/selfCheck.htm";
						$.post(url,{'homeworkId':hwId},function(data){
							if(data.success){
								Utils.Notice.alert('操作成功');
								setTimeout(function(){
									location.reload();
								},1500)
							}else{
								Utils.Notice.alert('操作失败');
							}
						});
					}
				});
			});

		},initShowView : function () {
			if ($('.m-correct-question-right').hasClass('full')) {
				$('.m-correct-question-left').hide();
			} else {
				$('.m-correct-question-left').show();
			}
		}
	};

	win.init();
});