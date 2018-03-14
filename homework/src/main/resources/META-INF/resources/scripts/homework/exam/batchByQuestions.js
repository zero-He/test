define(function (require, exports, module) {
	var $ = require('jquery');
	var Render = require('homework/sheet.render');
	require('common/ui/ui-scrollbar/jquery.scrollbar.min');
	var win = {
		init: function () {
			this.bindEvent();
			this.initShowView();
			$('.j-card-scrollbar').scrollbar();
		},
		bindEvent: function () {
			var _this = this;
			/*点击答题卡触发*/
			$('.m-correct-question-right .btn').click(function () {
				$('.m-correct-question-right').toggleClass('full');
				_this.initShowView();
			});
			/*点击返回按题批改*/
			$('.j-exitBatch').click(function () {
				var href = Leke.domain.homeworkServerName + '/auth/teacher/exam/teaOnlineExamDetail.htm?homeworkId=' + $(this).data('hwid');
				location.href = href;
			});
			var showQuestionItems = {
				/*大类题型展开收缩*/
				toggle: function () {
					$('.j-arrow').on('click', function (event) {
						event.preventDefault();
						var $this = $(this);
						if ($this.hasClass('p-arrow-hide')) {
							$this.removeClass('p-arrow-hide').parent('.p-group-title').siblings('.p-group-detail').slideDown();
						} else {
							$this.addClass('p-arrow-hide').parent('.p-group-title').siblings('.p-group-detail').slideUp();
						}
					});
				}

			}
			showQuestionItems.toggle();

		},
		initShowView: function () {
			if ($('.m-correct-question-right').hasClass('full')) {
				$('.m-correct-question-left').hide();
			} else {
				$('.m-correct-question-left').show();
			}
		}
	};

	win.init();
});