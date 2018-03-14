define(function(require, exports, module) {
	var $ = require('jquery');
	var RichEditor = require('./sheet.richeditor');
	var RichText = require('common/ui/ui-htmleditor/sheet.richtext');
	var ImgPreview = require('common/ui/ui-imgpreview/ui-imgpreview.js');

	function emptyFun(que) {
	}

	var Question = {
		renderEdit : emptyFun,
		renderView : emptyFun,
		parse : emptyFun
	}

	function buildAnswer(que, hasNull, values) {
		return {
			questionId : $(que).data('qid'),
			answerContent : JSON.stringify(values),
			hasNull : hasNull
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
			return buildAnswer(que, values.length == 0, values);
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
			var hasNull = false;
			var values = $.map($(que).find('.que-stem').find('input'), function(v) {
				var value = $.trim($(v).val());
				hasNull = value === '' ? true : hasNull;
				return value;
			});
			return buildAnswer(que, hasNull, values);
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
			var hasNull = true, values = [''];
			if (hasText(value)) {
				hasNull = false;
				values = [value];
			}
			return buildAnswer(que, hasNull, values);
		}
	});

	// 口语题
	Ques.Oral = $.extend({}, Question, {});

	// 其它题
	Ques.Handwrite = $.extend({}, Question, {});

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
			var subques = $(que).find('.q-que').filter(function() {
				return $(this).data('editable');
			});
			var answers = $.map(subques, function(v) {
				return Answer.parse(v);
			});
			var hasNull = answers.filter(function() {
				return this.hasNull == true;
			}).length > 0;
			return {
				questionId : $(que).data('qid'),
				hasNull : hasNull,
				subs : answers
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
			return Ques[template].parse(que);
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
			});
			answers = $.grep(answers, function(answer, i) {
				return answer.answerContent != null || (answer.subs && answer.subs.length > 0);
			});
			var unqids = answers.filter(function(v) {
				return v.hasNull == true;
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
