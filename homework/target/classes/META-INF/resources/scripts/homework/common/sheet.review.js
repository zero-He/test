
/**
 * 复批
 */
define(function(require, exports, module) {
	var $ = require('jquery');
	var Editor = require('homework/sheet.editor');
	var RichText = require('common/ui/ui-htmleditor/ui-richtext');

	
	function parseReviewResult(question) {
		var isPass = 0;
		if($(question).attr('class').indexOf('rectify-ok') > -1){
			isPass = 1;
		}
		var comments = question.find('.j-comment').data('comments');
		if (typeof comments === 'string' && comments == '') {
			comments = null;
		}
		return {
			questionId : question.data('question-id'),
			isPassedFix : isPass,
			correctComments : comments
		}
	}

	var Review = {
		parseSheet : function() {
			var questionResultList = [];
			var firstUnDoQueId = null;
			$.each($('.p-group-detail>ul>li>.j-question'), function() {
				var question = $(this);
				var questionId = question.data('question-id');
				var hasSub = question.data('has-sub');
				if (hasSub) {
					var subs = [];
					$.each(question.find('.j-question'), function() {
						var cquestion = $(this);
						var result = Review.getQuestionResult(cquestion);
						if (result != null) {
							subs.push(result);
						}
						if(firstUnDoQueId == null && !Review.isFinishReview(cquestion)) {
							firstUnDoQueId = questionId;
						}
					});
					if (subs.length > 0) {
						questionResultList.push({
							questionId : questionId,
							subs : subs
						});
					}
				} else {
					var result = Review.getQuestionResult(question);
					if (result) {
						questionResultList.push(result);
					}
					if(firstUnDoQueId == null && !Review.isFinishReview(question)) {
						firstUnDoQueId = questionId;
					}
				}
			});
			return {
				questionResultList : questionResultList,
				firstUnDoQueId : firstUnDoQueId
			};
		},
		parseComplete : function() {
			Editor.sync();
			RichText.sync();
			var questions = [];
			$.each($('.p-group-detail>ul>li>.j-question'), function() {
				var question = $(this);
				var questionId = question.data('question-id');
				var queObj = {isDone:false,qid:questionId};
				if(question.data('subjective') ){
					if (question.data('has-sub')) {// 有子题
						var hasNull = true;
						var reviewCount = 0;
						var subQuestion = question.find('.j-question');
						$.each(subQuestion, function() {
							var cquestion = $(this);
							var isReview = Review.isFinishReview(cquestion);
							if (isReview) {
								reviewCount++;
							}
						});
						if(reviewCount == subQuestion.length){
							hasNull = false;
						}
						queObj.isDone = hasNull;
						
					} else {// 无子题
						var isReview = Review.isFinishReview(question);
						queObj.isDone = !isReview;
					}
				}
				questions.push(queObj);
			});
			return questions;
		},
		isFinishReview : function(question) {
			return question.hasClass('rectify-no') || question.hasClass('rectify-ok') || question.find('.j-review').length == 0;
		},
		getQuestionResult : function(question) {
			return parseReviewResult(question);
		}
	}

	module.exports = Review;
});
