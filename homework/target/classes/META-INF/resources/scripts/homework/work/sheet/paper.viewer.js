define(function(require, exports, module) {
	var $ = require('jquery');
	var SheetFlash = require('./sheet.flash');
	var RichText = require('common/ui/ui-htmleditor/sheet.richtext');
	var ImgPreview = require('common/ui/ui-imgpreview/ui-imgpreview.js');

	function parseOralUrl(reply) {
		if (reply && reply.length) {
			var matches = reply.match(/<!--\s+(audio|recorder)\s*:\s*(\S+)\s*-->/);
			if (matches && matches.length >= 3) {
				return matches[2];
			}
		}
		return "";
	}

	var Question = {
		renderView : function(que) {
		}
	}

	var Ques = {};

	// 单选题
	Ques.SingleChoice = $.extend({}, Question, {});

	// 判断题
	Ques.Judgement = $.extend({}, Ques.SingleChoice, {});

	// 多选题
	Ques.MultiChoice = $.extend({}, Ques.SingleChoice, {});

	// 填空题
	Ques.FillBlank = $.extend({}, Question, {
		renderView : function(que) {
			$(que).find('.q-fbinput').each(function() {
				RichText.create($(this), {
					readonly : true,
					width : $(this).data('width')
				});
			});
		}
	});

	Ques.Punctuate = $.extend({}, Question, {});

	// 问答题
	Ques.Openend = $.extend({}, Question, {
		renderView : function(que) {
			$(que).find('textarea.que-reply-value').replaceWith(function() {
				var el = $('<div class="que-reply-openend">' + $(this).val() + '</div>');
				new ImgPreview(el);
				return el;
			});
		}
	});

	// 口语题
	Ques.Oral = $.extend({}, Question, {
		renderView : function(que) {
			var reply = $.trim($(que).find('textarea.que-reply-value').text());
			var url = parseOralUrl(reply);
			if (url) {
				$(que).find('.que-reply').append('<dfn class="j-dfn" data-type="audio" data-url="' + url + '"></dfn>')
				SheetFlash.scan();
			} else {
				if (reply.length > 0) {
					Ques.Openend.renderView(que);
				}
			}
		}
	});

	// 其它题
	Ques.Handwrite = $.extend({}, Question, {
		renderView : function(que) {
			var replyEl = $(que).find('textarea.que-reply-value');
			if (replyEl.text().length > 0) {
				$(que).find('.que-stem').hide();
				replyEl.replaceWith(function() {
					var el = $('<div class="que-reply-openend">' + $(this).val() + '</div>');
					new ImgPreview(el);
					return el;
				});
			}
		}
	});

	var BigQue = {
		render : function(que) {
			$(que).find('.q-que').each(function() {
				Viewer.render(this);
			});
		}
	}

	Ques.Cloze = Ques.Reading = Ques.Listening = BigQue;

	var Viewer = {
		render : function(que) {
			var hasSub = $(que).data('hassub');
			var template = $(que).data('template');
			if (hasSub) {
				BigQue.render(que);
				return;
			}
			Ques[template].renderView(que);
		},
		renderSheet : function() {
			// 题目渲染（根据编辑状态渲染组件）
			$('.q-paper-que>.q-que').each(function() {
				Viewer.render(this);
			});
		}
	}

	module.exports = Viewer;
});
