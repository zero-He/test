define(function(require, exports, module) {
	var $ = require('jquery');

	var QueMap = {};

	function markReviewResult(que, isPassed) {
		var box = $(que).find('.que-bugfix').data('passed', isPassed);
		box.html(isPassed ? '<span class="bugfix-pass"></span>' : '<span class="bugfix-again"></span>');
		var questionId = $(que).data('qid');
		$.extend(QueMap[questionId], {
			isPassedFix : isPassed
		});
	}

	var Question = {
		render : function(que) {
			$(que).find('.btn-pass,.btn-again').show();
			$(que).find('.btn-pass').click(function() {
				markReviewResult(que, true);
			});
			$(que).find('.btn-again').click(function() {
				markReviewResult(que, false);
			});
			var questionId = $(que).data('qid');
			QueMap[questionId] = {
				questionId : questionId,
				isPassedFix : null,
				correctComments : []
			};
		},
		parse : function(que) {
			var questionId = $(que).data('qid');
			return QueMap[questionId];
		}
	}

	var BigQue = {
		render : function(que) {
			$(que).find('.q-que').filter(function() {
				return $(this).data('corrable');
			}).each(function() {
				Question.render(this);
			});
		},
		parse : function(que) {
			var ques = $(que).find('.q-que').filter(function() {
				return $(this).data('corrable');
			})
			var subs = $.map(ques, function(v) {
				return Question.parse(v);
			});
			var questionId = $(que).data('qid');
			var conNull = subs.filter(function(v) {
				return v.isPassedFix == null;
			}).length > 0;
			var conFalse = subs.filter(function(v) {
				return v.isPassedFix == false;
			}).length > 0;
			var isPassedFix = conNull ? null : !conFalse;
			return {
				questionId : questionId,
				isPassedFix : isPassedFix,
				subs : subs
			};
		}
	}

	var Review = {
		render : function(que) {
			var hasSub = $(que).data('hassub');
			if (hasSub) {
				BigQue.render(que);
			} else {
				Question.render(que);
			}
		},
		parse : function(que) {
			var hasSub = $(que).data('hassub');
			if (hasSub) {
				return BigQue.parse(que);
			} else {
				return Question.parse(que);
			}
		},
		renderSheet : function() {
			// 题目渲染（根据编辑状态渲染组件）
			$('.q-paper-que>.q-que').filter(function() {
				return $(this).data('corrable');
			}).each(function() {
				Review.render(this);
			});
		},
		parseSheet : function() {
			var ques = $('.q-paper-que>.q-que').filter(function() {
				return $(this).data('corrable');
			});
			var reviews = $.map(ques, function(v) {
				return Review.parse(v);
			});
			var unqids = reviews.filter(function(v) {
				return v.isPassedFix == null;
			}).map(function(v) {
				return v.questionId;
			});
			return {
				unqids : unqids,
				unDoneNum : unqids.length,
				correctJson : JSON.stringify(reviews)
			};
		}
	}

	module.exports = Review;
});
