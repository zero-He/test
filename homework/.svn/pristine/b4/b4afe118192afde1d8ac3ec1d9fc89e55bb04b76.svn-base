
/**
 * 答案解析
 */
define(function(require, exports, module) {
	var $ = require('jquery');
	var JSON = require('json');
	var Editor = require('homework/sheet.editor');
	var RichText = require('common/ui/ui-htmleditor/ui-richtext');

	var Handles = {
		SingleChoice : function(question) {
			var paperType = question.data('paper-type');
			var values;
			if (paperType == 2) {
				values = $.map(question.find('.p-answer-opts>em.p-answer-opts-cur'), function(obj) {
					return $(obj).data('answer-id');
				});
			} else {
				values = $.map(question.find('input:radio:checked'), function(obj) {
					return $(obj).val();
				});
			}
			return {
				hasNull : values.length == 0,
				values : values
			};
		},
		MultiChoice : function(question) {
			var paperType = question.data('paper-type');
			var values;
			if (paperType == 2) {
				values = $.map(question.find('.p-answer-opts>em.p-answer-opts-cur'), function(obj) {
					return $(obj).data('answer-id');
				});
			} else {
				var values = $.map(question.find('input:checkbox:checked'), function(obj) {
					return $(obj).val();
				});
			}
			return {
				hasNull : values.length == 0,
				values : values
			};
		},
		FillBlank : function(question) {
			var hasNull = false;
			var values = '';
			if(question.data('editabled')){
				values = $.map(question.find('input:text'), function(obj) {
					var value = $.trim($(obj).val());
					hasNull = value == "" ? true : hasNull;
					return value;
				});
			}
			return {
				hasNull : hasNull,
				values : values
			};
		},
		Openend : function(question) {
			var hasNull = true;
			var values = $.map(question.find('textarea.j-essay-answer'), function(obj) {
				var value = $.trim($(obj).val());
				if( value.indexOf('<img') > -1 ||  $.trim(removeHTMLTag(value)).length > 0){
					hasNull =false;
				}
				if(hasNull){ value = ''; }
				return value;
			});
			if(question.find('textarea.j-essay-answer').length == 0){
				hasNull = false;
			}
			return {
				hasNull : hasNull,
				values : values
			};
			function removeHTMLTag(str) {
	            str = str.replace(/<\/?[^>]*>/g,''); //去除HTML tag
	            str = str.replace(/[ | ]*n/g,'n'); //去除行尾空白
	            str = str.replace(/n[s| | ]*r/g,'n'); //去除多余空行
	            str=str.replace(/&nbsp;/ig,'');//去掉&nbsp;
	            return str;
			}
		},
		Punctuate : function(question) {
			var hasNull = true;
			var values = $.map(question.find('input:text'), function(obj) {
				var value = $.trim($(obj).val());
				hasNull = value != "" ? false : hasNull;
				return value;
			});
			if(question.find('input:text').length == 0){
				hasNull = false;
			}
			return {
				hasNull : hasNull,
				values : values
			};
		},
		Oral : function(question) {
			var hasNull = false;
			var values = $.map(question.find('input:hidden'), function(obj) {
				var value = $.trim($(obj).val());
				hasNull = value == "" ? true : hasNull;
				return value;
			});
			return {
				hasNull : hasNull,
				values : values
			};
		},
		Handwrite : function(question) {
			var hasNull = false;
			var values = $.map(question.find('input:hidden:first'), function(obj) {
				var value = $.trim($(obj).val());
				hasNull = value == "" ? true : hasNull;
				return value;
			});
			return {
				hasNull : hasNull,
				values : values
			};
		}
	};
	Handles.Judgement = Handles.SingleChoice;

	var Answer = {
		parseSheet : function() {
			Editor.sync();
			RichText.sync();
			var hasNull = false;
			var firstQueId = null;
			var answerInfoList = $.map($('.j-question').filter(function() {
				return $(this).data('issub') === false;
			}).filter(function() {
				return $(this).data('editabled');
			}), function(obj) {
				var question = $(obj);
				if (question.data('has-sub')) {// 有子题
					var isUnDone = false;
					var subs = $.map($('.j-question', question).filter(function() {
						return $(this).data('editabled');
					}), function(obj) {
						var cquestion = $(obj);
						var answer = Answer.getAnswer(cquestion);
						hasNull = answer.hasNull ? true : hasNull;
						if (answer.hasNull) {
							isUnDone = true;
						}
						return {
							questionId : cquestion.data('question-id'),
							answerContent : JSON.stringify(answer.values)
						};
					});
					if(firstQueId==null && isUnDone ){
						firstQueId = question.data('question-id');
					}
					return {
						questionId : question.data('question-id'),
						subs : subs
					};
				} else {// 无子题
					var answer = Answer.getAnswer(question);
					hasNull = answer.hasNull ? true : hasNull;
					if(firstQueId==null && answer.hasNull ){
						firstQueId = question.data('question-id');
					}
					return {
						questionId : question.data('question-id'),
						answerContent : JSON.stringify(answer.values)
					};
				}
			});
			answerInfoList = $.grep(answerInfoList, function(answer, i) {
				return answer.answerContent != null || (answer.subs && answer.subs.length > 0);
			});
			return {
				hasNull : hasNull,
				firstQueId : firstQueId,
				answerJson : JSON.stringify(answerInfoList)
			};
		},
		parseComplete : function() {
			Editor.sync();
			RichText.sync();
			var questions = [];
			$.each($('.p-group-detail>ul>li>.j-question'), function() {
				var question = $(this);
				var questionId = question.data('question-id');
				if (question.data('has-sub')) {// 有子题
					var hasNull = false;
					$.each(question.find('.j-question'), function() {
						var cquestion = $(this);
						var answer = Answer.getAnswer(cquestion);
						if (answer.hasNull) {
							hasNull = true;
							return false;
						}
					});
					questions.push({isDone:hasNull,qid:questionId});
				} else {// 无子题
					var answer = Answer.getAnswer(question);
					questions.push({isDone:answer.hasNull,qid:questionId});
				}
			});
			return questions;
		},
		getAnswer : function(question) {
			var template = question.data('template');
			var t = Handles[template](question);
			return t;
		}
	};

	module.exports = Answer;
});
