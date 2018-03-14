define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require('utils');
	var Dialog = require('dialog');

	var globalDoubtUrl = '/auth/student/myDoubt/doubtList/doubtForQuestion.htm?crossDomain=false';

	var Service = {
		doAsk : function(questionId) {
			var subjectId = Csts.subjectId, homeworkDtlId = Csts.homeworkDtlId, teacherId = Csts.teacherId;
			var url = globalDoubtUrl + "&questionId=" + questionId + "&subjectId=" + subjectId;
			if (Csts.teacherId != null && Csts.homeworkType != 7) {
				url += "&teacherId=" + teacherId;
			}
			if (Csts.isCorrect && Csts.homeworkType != 6 && Csts.homeworkType != 7) {
				url += "&homeworkDtlId=" + homeworkDtlId;
			}
			Dialog.open({
				title : '我要提问',
				size : 'lg',
				url : url
			});
		},
		doFavorite : function(questionId, callbackFn) {
			var url = Leke.domain.wrongtopicServerName + "/auth/student/wrong/favorite.htm?jsoncallback=?";
			var params = {
				questionId : questionId,
				subjectId : Csts.subjectId,
				questionSrc : Csts.homeworkName,
				homeworkName: Csts.homeworkName,
				homeworkDtlId : Csts.homeworkDtlId,
				paperId : Csts.paperId
			}
			$.getJSON(url, params, function(json) {
				if (json.success) {
					if (json.datas.favourite) {
						Utils.Notice.alert('收藏成功');
					} else {
						Utils.Notice.alert('取消收藏成功');
					}
					if (typeof callbackFn == 'function') {
						callbackFn(json.datas.favourite);
					}
				}
			});
		}
	};

	var PaperExtend = {
		bind : function() {
			var type = $('.q-paper').data('type');
			var attach = $('.q-paper').data('attach');
			var canFavo = Csts.workMode != 'answer' && Csts.workMode != 'correct' && (type == 1 || attach == true);
			var canAsk = Csts.workMode != 'correct' && type == 1;
			$('.q-paper-que>.q-que').each(function() {
				if (Leke.user.currentRoleId == 100) {
					if (canFavo) {
						PaperExtend.bindFavorite(this);
					}
					if (canAsk) {
						PaperExtend.bindAsk(this);
					}
				} else if (Leke.user.currentRoleId == 101) {
					PaperExtend.bindFinder(this);
				} else {
				}
			});
			if (Leke.user.currentRoleId == 100 && canFavo) {
				this.initFavorite();
			}
		},
		bindAsk : function(que) {
			$(que).find('.btn-ask').show().click(function() {
				var questionId = $(que).data('qid');
				Service.doAsk(questionId)
			});
		},
		initFavorite : function() {
			var _this = this;
			var url = Leke.domain.wrongtopicServerName + "/auth/common/findFavoriteQuestion.htm?jsoncallback=?";
			var qids = $('.q-paper-que>.q-que').map(function() {
				return $(this).data('qid');
			});
			$.getJSON(url, {
				questionIds : $.makeArray(qids)
			}).done(function(json) {
				if (json.success) {
					var fqids = json.datas.list;
					$('.q-paper-que>.q-que').each(function() {
						var qid = $(this).data('qid');
						var btn = $(this).find('.btn-favorite');
						if (fqids.indexOf(qid) >= 0) {
							btn.data('already', true).addClass('btn-favorite-already').text('取消收藏');
						} else {
							btn.data('already', false);
						}
					});
				}
			});
		},
		bindFavorite : function(que) {
			$(que).find('.btn-favorite').show().click(function() {
				var questionId = $(que).data('qid');
				var btn = $(this);
				Service.doFavorite(questionId, function(favorite) {
					if (favorite) {
						btn.data('already', true).addClass('btn-favorite-already').text('取消收藏');
					} else {
						btn.data('already', false).removeClass('btn-favorite-already').text('收藏');
					}
				});
			});
		},
		bindFinder : function(que) {
			$(que).find('.btn-finder').show().click(function() {
				var qid = $(this).parents('.q-que').data('qid');
				require.async(['question/web/leke.question.feedback'], function(vm) {
					vm.openQuestionFeedback(qid);
				});
			});
		}
	}

	module.exports = PaperExtend;
});
