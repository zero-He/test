define(function(require, exports, module) {
	var $ = require('jquery');

	// 题目结构的处理
	var QueStruct = {
		init : function() {
			this.initExtendArrow();
			this.initRecordArrow();
			this.initPaperAttach();
		},
		// 试卷附件显示
		initPaperAttach : function() {
			var attach = $('.q-attach-preview');
			if (attach.length > 0) {
				require.async('common/ui/ui-fileplayer/ui-fileplayer.js', function() {
					var fileId = attach.data('id');
					attach.paper({
						id : fileId
					});
				});
			}
		},
		// 题目答题记录的显示/隐藏
		initRecordArrow : function() {
			$('.que-replies .que-replies-arrow').show().click(function() {
				$(this).toggleClass('que-replies-show').parent().next().toggle();
			});
		},
		// 题目扩展信息的展开/收起
		initExtendArrow : function() {
			var allToggleAnswer = $('#panel_btnToggleAnswer');
			if (allToggleAnswer.length = 0) {
				$('.que-tool-btn .btn-extend').click(function() {
					var $this = $(this).toggleClass('btn-extend-show');
					var $extend = $this.closest('.q-que').find('.que-extend');
					if ($this.hasClass('btn-extend-show')) {
						// 现在是收起
						$extend.show();
					} else {
						// 现在是展开
						$extend.hide();
					}
				});
			}
		}
	};

	// 试卷结构的处理
	var PapStruct = {
		init : function() {
			this.initPaperPage();
			this.initGroupArrow();
		},
		// 大题的展开/收起
		initGroupArrow : function() {
			$('.group-arrow').click(function() {
				var $this = $(this);
				var $body = $this.toggleClass('group-arrow-hide').closest('.q-paper-group-title').next();
				if ($this.hasClass('group-arrow-hide')) {
					// 现在是展开
					$body.css({
						height : 0,
						visibility : 'hidden',
						overflow : 'hidden'
					});
				} else {
					// 现在是收起
					$body.css({
						height : 'auto',
						visibility : 'visible',
						overflow : 'visible'
					});
				}
			});
		},
		// 试卷附件页码
		initPaperPage : function() {
			var attend = $('.q-attach-preview');
			if (attend.length > 0) {
				var total = attend.find('img').length;
				attend.find('.attach-body').scroll(function() {
					var top = attend.offset().top;
					var half = attend.height() / 3;
					var img = attend.find('img').filter(function() {
						return $(this).offset().top - top - half > 0;
					})[0];
					var index = img ? $(img).data('idx') - 1 : total;
					attend.find('.attach-page-cur').text(index);
					console.log(half)
				});
			}
		}
	};

	var Struct = {
		QueStruct : QueStruct,
		PapStruct : PapStruct,
		init : function() {
			QueStruct.init();
			PapStruct.init();
		}
	}

	module.exports = Struct;
});