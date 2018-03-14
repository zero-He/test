define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require('utils');
	var JSON = require('json');
	require('./sheet.accord');
	var SheetComment = require('./sheet.comment');

	// 试卷和大题得分的重算
	function markPaperScore() {
		var paperScore = 0;
		$('.q-paper-group').each(function() {
			var totalScore = 0;
			$(this).find('.que-result').each(function() {
				var score = $(this).data('score');
				if (score) {
					totalScore += parseFloat(score);
				}
			});
			totalScore = Utils.Number.toFixed(totalScore, 1);
			paperScore += totalScore;
			$(this).find('.group-score>.text-score').text(totalScore);
		});
		paperScore = Utils.Number.toFixed(paperScore, 1);
		$('.q-paper-head .scoreval').text(paperScore);
	}

	function markCorrectResult(que, score, isRight) {
		var qs = $(que).find('.que-result');
		qs.data('score', score).data('isright', isRight);
		var html = '<span class="goal-score">' + Utils.Number.toFixed(score, 1) + '</span>';
		if (isRight) {
			html += '<span class="goal-right"></span>';
		} else if (score > 0) {
			html += '<span class="goal-halfs"></span>';
		} else {
			html += '<span class="goal-wrong"></span>';
		}
		qs.html(html);
		// 重算试卷和大题得分
		markPaperScore();
	}
	
	function markFillResult(que, isRight) {
		$(que).find('.q-fbresult').data('isright', isRight);
		if (isRight) {
			$(que).find('.q-fbresult>.judge-right').addClass('selected');
			$(que).find('.q-fbresult>.judge-wrong').removeClass('selected');
		} else {
			$(que).find('.q-fbresult>.judge-wrong').addClass('selected');
			$(que).find('.q-fbresult>.judge-right').removeClass('selected');
		}
	}

	var Question = {
		render : function(que) {
			$(que).find('.btn-correction').show().click(function(e) {
				e.stopPropagation();
				var qs = $(que).find('.que-result');
				var total = qs.data('total');
				$(this).accord({
					total : total,
					onResult : function(score, isRight) {
						markCorrectResult(que, score, isRight);
					}
				})
			});
			SheetComment.render(que);
			if (Leke.user.currentRoleId == 101) {
				$(que).find('.btn-comment').show().click(function() {
					SheetComment.doComment(que);
				});
			}
		},
		parse : function(que) {
			var questionId = $(que).data('qid');
			var score = $(que).find('.que-result').data('score');
			var isRight = $(que).find('.que-result').data('isright');
			isRight = (isRight == undefined ? null : isRight);

			var corrected = (score != null && score != undefined);
			if (!corrected) {
				score = 0;
				isRight = false;
			}
			var commentJson = $(que).find('.que-comment-json').html();
			var comments = commentJson ? JSON.parse(commentJson) : null;
			return {
				corrected : corrected,
				questionId : questionId,
				totalIsRight : isRight,
				totalResultScore : score,
				answerResults : [{
					isRight : isRight,
					resultScore : score
				}],
				correctComments : comments
			};
		}
	}

	var FillBlank = {
		render : function(que) {
			$(que).find('.btn-correction').show().click(function(e) {
				e.stopPropagation();
				var inputs = $(que).find('.q-fbresult');
				var fillSize = inputs.length;
				var showZero = true;
				var showFull = true;
				var total = $(que).find('.que-result').data('total');
				if(fillSize > 0) {
					var trueSize = inputs.filter(function() {
						return $(this).data('isright') === true;
					}).length;
					var falseSize = inputs.filter(function() {
						return $(this).data('isright') === false;
					}).length;
					showZero = trueSize != fillSize;
					showFull = falseSize != fillSize;
				}
				$(this).accord({
					total : total,
					showZero : showZero,
					showFull : showFull,
					onResult : function(score, isRight) {
						markCorrectResult(que, score, isRight);
						if (isRight == true) {
							markFillResult(que, true);
						} else if (score == 0) {
							markFillResult(que, false);
						}
					}
				})
			});
			SheetComment.render(que);
			if (Leke.user.currentRoleId == 101) {
				$(que).find('.btn-comment').show().click(function() {
					SheetComment.doComment(que);
				});
			}
			$(que).find('.q-fbresult').each(function() {
				$(this).find('i').click(function() {
					$(this).addClass('selected');
					$(this).siblings().removeClass('selected');
					var fillIsRight = $(this).hasClass('judge-right');
					$(this).parent().data('isright', fillIsRight);
					// 同步批改状态到整题
					var fillSize = $(que).find('.q-fbresult').length;
					var trueSize = $(que).find('.q-fbresult').filter(function() {
						return $(this).data('isright') === true;
					}).length;

					var total = $(que).find('.que-result').data('total');
					var totalIsRight = (fillSize == trueSize);
					var score = Utils.Number.toFixed(total * trueSize / fillSize, 1);
					markCorrectResult(que, score, totalIsRight);
				});
			});
		},
		parse : function(que) {
			var questionId = $(que).data('qid');
			var score = $(que).find('.que-result').data('score');
			var isRight = $(que).find('.que-result').data('isright');
			isRight = (isRight == undefined ? null : isRight);

			var corrected = (score != null && score != undefined);
			if (!corrected) {
				score = 0;
				isRight = false;
			}

			var answerResults = $.map($(que).find('.q-fbresult'), function(v) {
				return {
					resultScore : 0,
					isRight : $(v).data('isright') || false
				};
			});

			var commentJson = $(que).find('.que-comment-json').html();
			var comments = commentJson ? JSON.parse(commentJson) : null;
			return {
				corrected : corrected,
				questionId : questionId,
				totalIsRight : isRight,
				totalResultScore : score,
				answerResults : answerResults,
				correctComments : comments
			};
		}
	}

	var BigQue = {
		render : function(que) {
			$(que).find('.q-que').filter(function() {
				return $(this).data('corrable');
			}).each(function() {
				PaperCorrect.render(this);
			});
		},
		parse : function(que) {
			var ques = $(que).find('.q-que').filter(function() {
				return $(this).data('corrable');
			})
			var subs = $.map(ques, function(v) {
				return PaperCorrect.parse(v);
			});
			var questionId = $(que).data('qid');
			var corrected = subs.filter(function(v) {
				return v.corrected == false;
			}).length == 0;
			return {
				corrected : corrected,
				questionId : questionId,
				subs : subs
			};
		}
	}

	var PaperCorrect = {
		render : function(que) {
			var hasSub = $(que).data('hassub');
			var template = $(que).data('template');
			if (hasSub) {
				BigQue.render(que);
			} else if (template == 'FillBlank') {
				FillBlank.render(que);
			} else {
				Question.render(que);
			}
		},
		parse : function(que) {
			var hasSub = $(que).data('hassub');
			var template = $(que).data('template');
			if (hasSub) {
				return BigQue.parse(que);
			} else if (template == 'FillBlank') {
				return FillBlank.parse(que);
			} else {
				return Question.parse(que);
			}
		},
		renderSheet : function() {
			// 题目渲染（根据编辑状态渲染组件）
			$('.q-paper-que>.q-que').filter(function() {
				return $(this).data('corrable');
			}).each(function() {
				PaperCorrect.render(this);
			});
		},
		parseSheet : function() {
			var ques = $('.q-paper-que>.q-que').filter(function() {
				return $(this).data('corrable');
			});
			var corrects = $.map(ques, function(v) {
				return PaperCorrect.parse(v);
			});
			var unqids = corrects.filter(function(v) {
				return v.corrected == false;
			}).map(function(v) {
				return v.questionId;
			});
			return {
				unqids : unqids,
				unDoneNum : unqids.length,
				correctJson : JSON.stringify(corrects)
			};
		}
	}

	module.exports = PaperCorrect;
});
