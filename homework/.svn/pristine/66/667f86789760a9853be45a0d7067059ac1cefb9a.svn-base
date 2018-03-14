define(function(require, exports, module) {
	var $ = require('jquery');
	var JSON = require('json');
	var Utils = require('utils');

	var GRADE_TPLS = {
		HTML : '<div class="p-grading"><div class="p-grading-body"></div><div class="p-grading-input">得分：<input type="text">&nbsp;分&nbsp;<button>确定</button></div></div>',
		ITEM : '<em data-score="{{ITEM_VALUE}}">{{ITEM_LABEL}}</em>'
	};

	var Grading = {
		opts : null,
		$root : null,
		$body : null,
		$input : null,
		$button : null,
		$target : null,
		init : function() {
			var _this = this;
			_this.$root = $(GRADE_TPLS.HTML).appendTo(document.body);
			_this.$body = _this.$root.find('.p-grading-body');
			_this.$input = _this.$root.find('input');
			_this.$button = _this.$root.find('button');
			_this.$body.on('click', 'em', function() {
				var score = $(this).data('score');
				_this.callback(score);
				_this.close();
				return false;
			});
			_this.$root.click(function() {
				return false;
			});
			$(document.body).click(function() {
				_this.close();
			});
			$('.p-card-scrollbar').scroll(function() {
				_this.close();
			});
			_this.$button.on('click', function() {
				if (_this.valid()) {
					_this.callback(parseFloat(_this.$input.val()));
					_this.close();
				}
			});
			_this.$input.on({
				keypress : function() {
					if ((event.keyCode < 48 || event.keyCode > 57) && event.keyCode != 46
							|| /\.\d$/.test($(this).val()) || $(this).val().length >= 5) {
						event.returnValue = false;
					}
				},
				keydown : function() {
					$(this).val($(this).val().replace(/[^\d\.]/g, ''));
				},
				keyup : function() {
					$(this).val($(this).val().replace(/[^\d\.]/g, ''));
				},
				blur : function() {
					return $(this).val() != '';
				}
			});
		},
		// 增加快捷选项
		initItems : function() {
			var total = this.opts.total;
			this.$body.empty();
			var interval = total > 10 ? parseInt(total / 10) : 1;
			for (var i = 0; i <= total; i += interval) {
				if (i == 0 && this.opts.showZero != true) {
					continue;
				}
				if (i == total && this.opts.showFull != true) {
					continue;
				}
				this.$body.append(GRADE_TPLS.ITEM.replace('{{ITEM_VALUE}}', i).replace('{{ITEM_LABEL}}', i + '分'));
			}
			if (this.opts.showFull) {
				this.$body.append(GRADE_TPLS.ITEM.replace('{{ITEM_VALUE}}', total).replace('{{ITEM_LABEL}}', '满分'));
			}
			// 输入框和错误消息重置
			this.$input.val('');
			this.$root.find('.p-grading-error').remove();
		},
		// 定位
		position : function() {
			var offsets = this.$target.offset();
			var top = offsets.top + this.$target.height() + 2;
			var left = offsets.left + this.$target.width() - this.$root.width();
			this.$root.css({
				top : top + 'px',
				left : left + 'px'
			});
		},
		// 验证输入值
		valid : function() {
			var val = this.$input.val();
			if (!$.isNumeric(val)) {
				this.appendError('请输入正确的分数');
				return false;
			}
			var total = this.opts.total;
			if (val > total) {
				this.appendError('你输入的分数超过最大值');
				return false;
			}
			return true;
		},
		// 
		appendError : function(text) {
			$('.p-grading-error').remove();
			var error = $('<div class=\"p-grading-error\">' + text + '</div>').appendTo(this.$root);
			setTimeout(function() {
				error.remove();
			}, 2000);
		},
		// 关闭
		close : function() {
			if (this.$root) {
				this.$root.hide();
			}
		},
		callback : function(score) {
			var isRight = score == this.opts.total;
			console.log(isRight)
			this.opts.onResult(score, isRight);
		},
		// 打开
		open : function(target, opts) {
			if (this.$root == null) {
				this.init();
			}
			this.opts = opts;
			this.$target = $(target);
			this.initItems();
			this.position();
			this.$root.show();
		}
	};

	$.fn.accord = function(opts) {
		opts = $.extend({
			total : 10,
			showFull : true,
			showZero : true,
			onResult : function(v) {
			}
		}, opts);
		Grading.open(this, opts);
	}
});
