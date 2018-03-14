define(function(require, exports, module) {
	var $ = require('jquery');
	var SheetFlash = require('./sheet.flash');
	var RichEditor = require('./sheet.richeditor');
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

	function emptyFun(que) {
	}

	var Question = {
		renderEdit : emptyFun,
		renderView : emptyFun,
		parse : emptyFun
	}

	function buildAnswer(que, replied, values) {
		return {
			questionId : $(que).data('qid'),
			answerContent : JSON.stringify(values),
			replied : replied
		}
	}

	var Ques = {};

	// 单选题
	Ques.SingleChoice = $.extend({}, Question, {
		renderEdit : function(que) {
			$(que).find('.que-option').click(function() {
				$(que).find('.q-radio').removeClass('q-radio-selected');
				$(this).find('.q-radio').addClass('q-radio-selected');
			});
		},
		parse : function(que) {
			var values = $.map($(que).find('.q-radio-selected'), function(v) {
				return $(v).data('id');
			});
			return buildAnswer(que, values.length > 0, values);
		}
	});

	// 判断题
	Ques.Judgement = $.extend({}, Ques.SingleChoice, {
		renderEdit : function(que) {
			$(que).find('.q-radio').click(function() {
				$(que).find('.q-radio').removeClass('q-radio-selected');
				$(this).addClass('q-radio-selected');
			});
		}
	});

	// 多选题
	Ques.MultiChoice = $.extend({}, Ques.SingleChoice, {
		renderEdit : function(que) {
			$(que).find('.que-option').click(function() {
				$(this).find('.q-radio').toggleClass('q-radio-selected');
			});
		}
	});

	// 填空题
	Ques.FillBlank = $.extend({}, Question, {
		renderEdit : function(que) {
			var subjective = $(que).data('subjective');
			$(que).find('.q-fbinput').each(function() {
				var isRight = $(this).data('right');
				var readonly = isRight === true;
				RichText.create($(this), {
					readonly : readonly,
					subjective : subjective,
					width : $(this).data('width')
				});
			});
		},
		renderView : function(que) {
			$(que).find('.q-fbinput').each(function() {
				RichText.create($(this), {
					readonly : true,
					width : $(this).data('width')
				});
			});
		},
		parse : function(que) {
			var values = $.map($(que).find('.que-stem').find('input'), function(v) {
				var value = $.trim($(v).val());
				return value;
			});
			var replied = values.filter(function(v) {
				return v === '';
			}).length == 0;
			return buildAnswer(que, replied, values);
		}
	});

	Ques.Punctuate = $.extend({}, Question, {
		renderEdit : function(que) {
			$(que).find('.q-punbreak').click(function() {
				var val = $(this).text();
				$(this).text(val == '/' ? ' ' : '/');
			});
		},
		renderView : function(que) {
		},
		parse : function(que) {
			var values = $.map($(que).find('.q-punbreak'), function(v) {
				return $.trim($(v).text());
			});
			var replied = values.filter(function(v) {
				return v && v.length > 0;
			}).length > 0;
			return buildAnswer(que, replied, values);
		}
	});

	// 问答题
	function hasText(str) {
		if (str.indexOf('<img') >= 0) {
			return true;
		}
		str = str.replace(/<\/?[^>]*>/g, ''); //去除HTML tag
		str = str.replace(/[ | ]*n/g, 'n'); //去除行尾空白
		str = str.replace(/n[s| | ]*r/g, 'n'); //去除多余空行
		str = str.replace(/&nbsp;/ig, '');//去掉&nbsp;
		return $.trim(str).length > 0;
	}
	Ques.Openend = $.extend({}, Question, {
		renderEdit : function(que) {
			RichEditor.init($(que).find('textarea.que-reply-value'));
		},
		renderView : function(que) {
			$(que).find('textarea.que-reply-value').replaceWith(function() {
				var el = $('<div class="que-reply-openend">' + $(this).val() + '</div>');
				new ImgPreview(el);
				return el;
			});
		},
		parse : function(que) {
			var value = $.trim($(que).find('textarea.que-reply-value').val());
			if (hasText(value)) {
				return buildAnswer(que, true, [value]);
			} else {
				return buildAnswer(que, false, ['']);
			}
		}
	});

	// 口语题
	Ques.Oral = $.extend({}, Question, {
		renderEdit : function(que) {
			var qid = $(que).data('qid');
			var reply = $(que).find('textarea.que-reply-value').text();
			var url = parseOralUrl(reply);
			$(que).find('.que-reply').append('<dfn class="j-dfn" data-type="recorder" data-vid="reply_' + qid
					+ '" data-url="' + url + '"></dfn>')
			SheetFlash.scan();
		},
		renderView : function(que) {
			var reply = $(que).find('textarea.que-reply-value').text();
			var url = parseOralUrl(reply);
			if (url) {
				$(que).find('.que-reply').append('<dfn class="j-dfn" data-type="audio" data-url="' + url + '"></dfn>')
				SheetFlash.scan();
			}
		},
		parse : function(que) {
			var value = $.trim($(que).find('textarea.que-reply-value').val());
			if (value.length > 0) {
				return buildAnswer(que, true, [value]);
			} else {
				return buildAnswer(que, false, ['']);
			}
		}
	});

	// 其它题
	Ques.Handwrite = $.extend({}, Question, {
		renderEdit : function(que) {
			var qid = $(que).data('qid');
			$(que).find('.que-stem').hide();
			$(que).find('.que-reply').append('<dfn class="j-dfn" data-type="handwrite" data-vid="reply_' + qid
					+ '"></dfn>')
			SheetFlash.scan();
		},
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
		},
		parse : function(que) {
			var value = $.trim($(que).find('textarea.que-reply-value').val());
			if (value.length > 0) {
				return buildAnswer(que, true, [value]);
			} else {
				return buildAnswer(que, false, ['']);
			}
		}
	});

	function hasSub(template) {
		return template == 'BigQue' || template == 'Cloze' || template == 'Reading' || template == 'Listening';
	}

	var BigQue = {
		render : function(que) {
			$(que).find('.q-que').each(function() {
				Answer.render(this);
			});
		},
		parse : function(que) {
			var ques = $(que).find('.q-que').filter(function() {
				return $(this).data('editable');
			});
			var subs = $.map(ques, function(v) {
				return Answer.parse(v);
			});
			var replied = subs.filter(function(v) {
				return v.replied != true;
			}).length == 0;
			return {
				questionId : $(que).data('qid'),
				replied : replied,
				subs : subs
			};
		}
	}

	Ques.Cloze = Ques.Reading = Ques.Listening = BigQue;

	var Answer = {
		render : function(que) {
			var template = $(que).data('template');
			if (hasSub(template)) {
				BigQue.render(que);
				return;
			}
			var editable = $(que).data('editable');
			if (editable) {
				Ques[template].renderEdit(que);
			} else {
				Ques[template].renderView(que);
			}
		},
		parse : function(que) {
			var template = $(que).data('template');
			if (hasSub(template)) {
				return BigQue.parse(que);
			}
			var editable = $(que).data('editable');
			if (editable) {
				return Ques[template].parse(que);
			} else {
				return null;
			}
		},
		renderSheet : function() {
			// 题目渲染（根据编辑状态渲染组件）
			$('.q-paper-que>.q-que').each(function() {
				Answer.render(this);
			});
		},
		parseSheet : function() {
			RichText.sync();
			RichEditor.sync();
			var answers = $.map($('.q-paper-que>.q-que'), function(v) {
				return Answer.parse(v);
			}).filter(function(v) {
				return v != null;
			});
			var unqids = answers.filter(function(v) {
				return v.replied == false;
			}).map(function(v) {
				return v.questionId;
			});
			return {
				unqids : unqids,
				unDoneNum : unqids.length,
				answerJson : JSON.stringify(answers)
			};
		}
	}

	module.exports = Answer;
});
