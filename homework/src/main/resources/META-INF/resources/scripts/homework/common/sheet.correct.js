
/**
 * 批改
 */
define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require('utils');
	var Dialog = require('dialog');
	var Tools = require("./sheet.tools");

	var Handles = {
		Openend : function(question) {
			var $el = question.find('.j-grading');
			var comments = question.find('.j-comment').data('comments');
			if (typeof comments === 'string' && comments == '') {
				comments = null;
			}
			if ($el.data('correct')) {
				var item = {
					resultScore : $el.data('score'),
					isRight : $el.data('right')
				};
				return {
					hasNull : false,
					totalIsRight : item.isRight,
					totalResultScore : item.resultScore,
					correctComments : comments,
					answerResults : [item]
				}
			} else {
				return {
					hasNull : true,
					totalIsRight : false,
					totalResultScore : 0,
					answerResults : [{
						isRight : false,
						resultScore : 0
					}],
					correctComments : comments
				};
			}
		},
		FillBlank : function(question) {
			var $el = question.find('.j-grading');
			var comments = question.find('.j-comment').data('comments');
			if (typeof comments === 'string' && comments == '') {
				comments = null;
			}
			if ($el.data('correct')) {
				var answerResults = []
				var $decision = question.find('.j-decision');
				$decision.each(function() {
					answerResults.push({
						resultScore : 0,
						isRight : $(this).data('right')
					});
				});
				if(answerResults.length == 0) {
					answerResults.push({
						resultScore : 0,
						isRight : $el.data('right')
					});
				}
				return {
					hasNull : false,
					totalIsRight : $el.data('right'),
					totalResultScore : $el.data('score'),
					correctComments : comments,
					answerResults : answerResults
				};
			} else {
				var answerResults = []
				question.find('.j-decision').each(function() {
					answerResults.push({
						isRight : false,
						resultScore : 0
					});
				});
				return {
					hasNull : true,
					totalIsRight : false,
					totalResultScore : 0,
					answerResults : answerResults,
					correctComments : null
				};
			}
		}
	};
	Handles.Oral = Handles.Handwrite = Handles.Openend;

	var Correct = {
		initBindEvent : function() {
			var _this = this;
			$('.j-grading').each(function() {
				$(this).click(function() {
					var result = _this.fillblankIsCorrect(this);
					if (result.allCorrect) {
						Tools.grade(this);
						if(result.allRight != null){
							$emList = $('.p-grading-body').find('em');
							if(result.allRight){
								$emList.eq(0).remove();
							}else{
								var fullScore = $emList.eq($emList.length -1).data('score');
								$emList.eq($emList.length -1).remove();
								if(fullScore == $emList.eq($emList.length -2).data('score')){
									$emList.eq($emList.length -2).remove();
								}
							}
						}
					} else {
						Utils.Notice.alert('请先给每个空判断对错。')
					}
					return false;
				});
				Tools.renderScore($(this));
			});
			$('.j-decision').each(function() {
				var $this = $(this);
				$this.find('i').click(function() {
					$this.data('correct', true).data('right', $(this).data('right'));
					$(this).addClass('selected').siblings().removeClass('selected');
					//填空题自动给分
					var $target =  $(this).offsetParent('.j-question').find('.j-grading');
					var total =$target.data('total');
					var $els = $(this).offsetParent('.j-question').find('.j-decision');
					var count = $els.length;
					var rightCount = $.grep($els,function(n,i) {
					   return $(n).data('right');
					}).length;
					var avg = Utils.Number.toFixed(total / count * rightCount,2);
					Tools.renderScore2($target,avg);
				});
				if ($this.data('correct') && $this.data('right') == true) {
					$this.find('i.p-right-all').addClass('selected');
				} else if ($this.data('correct') && $this.data('right') == false) {
					$this.find('i.p-right-error').addClass('selected');
				}
			});
			$('.j-comment>span>span').each(function() {
				$(this).click(function() {
					var type = $(this).data('type');
					Tools.comment(type, $(this).closest('.j-comment'));
				});
			});
			$('.j-comment').each(function() {
				Tools.renderComment($(this));
			});
			$('.j-review').each(function() {
				Tools.review($(this));
			});
		},
		fillblankIsCorrect : function(target) {
			var decisionList = $(target).closest('.j-question').find('.j-decision');
			var result = true;
			decisionList.each(function() {
				if ($(this).data('correct') != true) {
					result = false;
				}
			});
			//true : 全部正确，false:全部错误，null 无法判定或对错都有
			var allRight = true;
			if(decisionList.length == 0){
				allRight = null;
			} else {
				var rightTotal = 0;
				var errorTotal = 0;
				decisionList.each(function() {
					if ($(this).find('.selected').data('right')) {
						rightTotal++;
					}else{
						errorTotal++;
					}
				});
				if(decisionList.length == rightTotal){
					allRight = true;
				}else if(decisionList.length == errorTotal){
					allRight = false;
				}else{
					allRight = null;
				}
			}
			console.log(allRight);
			return {allCorrect: result , allRight: allRight };
		},
		fParseQuestion : function(parent) {
			var hasNull = false, questionResultList = [];
			$.each($(parent).children('.j-question'), function() {
				var question = $(this);
				if (question.data('subjective') == false) {
					return;
				}
				var questionId = question.data('question-id');
				var hasSub = question.data('has-sub');
				if (hasSub) {
					var subs = [];
					$.each(question.find('.j-question'), function() {
						var cquestion = $(this);
						if (cquestion.data('subjective') == false) {
							return;
						}
						var result = Correct.getQuestionResult(cquestion);
						if (result != null) {
							if (result.hasNull) {
								hasNull = true;
							}
							result.questionId = cquestion.data('question-id')
							subs.push(result);
						}
					});
					questionResultList.push({
						questionId : questionId,
						subs : subs
					});
				} else {
					var result = Correct.getQuestionResult(question);
					hasNull = result.hasNull ? true : hasNull;
					result.questionId = questionId;
					questionResultList.push(result);
				}
			});
			return {
				hasNull : hasNull,
				questionResultList : questionResultList
			};
		},
		parseSheet : function() {
			var hasNull = false, questionResultList = [];
			var firstQueId = null;
			$.each($('.p-group-detail>ul>li>.j-question'), function() {
				var question = $(this);
				if (question.data('subjective') == false) {
					return;
				}
				var questionId = question.data('question-id');
				var hasSub = question.data('has-sub');
				if (hasSub) {
					var subs = [];
					$.each(question.find('.j-question'), function() {
						var cquestion = $(this);
						if (cquestion.data('subjective') == false) {
							return;
						}
						var result = Correct.getQuestionResult(cquestion);
						if (result.hasNull) {
							hasNull = true;
						}
						result.questionId = cquestion.data('question-id')
						subs.push(result);
					});
					questionResultList.push({
						questionId : questionId,
						subs : subs
					});
				} else {
					var result = Correct.getQuestionResult(question);
					hasNull = result.hasNull ? true : hasNull;
					result.questionId = questionId;
					questionResultList.push(result);
				}
				if(firstQueId == null && hasNull){
					firstQueId = questionId;
				}
			});
			return {
				hasNull : hasNull,
				firstQueId : firstQueId,
				questionResultList : questionResultList
			};
		},
		parseComplete : function() {
			var hasNulls = [];
			$.each($('.p-group-detail>ul>li>.j-question'), function() {
				var question = $(this);
				var questionId = question.data('question-id');
				if (question.data('subjective') == false) {
					hasNulls.push({isDone:false,qid:questionId});
					return;
				}
				var hasSub = question.data('has-sub');
				if (hasSub) {
					var hasNull = true;
					$.each(question.find('.j-question'), function() {
						var cquestion = $(this);
						if (cquestion.data('subjective') == false) {
							hasNull = hasNull && true;
							return;
						}
						var result = Correct.getQuestionResult(cquestion);
						hasNull = hasNull && result.hasNull;
					});
					hasNulls.push({isDone:hasNull,qid:questionId});
				} else {
					var result = Correct.getQuestionResult(question);
					hasNulls.push({isDone:result.hasNull,qid:questionId})
				}
			});
			return hasNulls;
		},
		getQuestionResult : function(question) {
			var template = question.data('template');
			if (Handles[template]) {
				return Handles[template](question);
			} else {
				return null;
			}
		}
	}

	module.exports = Correct;
});
