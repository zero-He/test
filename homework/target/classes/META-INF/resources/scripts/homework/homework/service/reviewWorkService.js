define(function(require, exports, module) {
	var $ = require('jquery');
	var JSON = require('json');
	var Utils = require('utils');
	var Dialog = require('dialog');
	require('homework/sheet.struct');
	require('homework/sheet.render');
	var Tools = require("homework/common/sheet.tools");
	var Review = require("homework/common/sheet.review");
	var Helper = require('homework/common/sheet.helper');

	var Work = {
		// 初始化
		fInit : function() {
			this.fBindSaveSubmit();
		},
		fBindSaveSubmit : function() {
			var _this = this;
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
			$('.j-review').on('click','i',function(){
				if($(this).data('pass') != 1){
					$(this).offsetParent('.q-container').addClass('rectify-ok').removeClass('rectify-no');
				}else{
					$(this).offsetParent('.q-container').addClass('rectify-no').removeClass('rectify-ok');
				}
			})
			
			$('#reviewSubmit').click(function() {
				var sheet = Review.parseSheet();
				var msg ="作业已全部批改，确定提交吗？";
				var btnName = {ok:"确定",cancel:"取消"};
				var unDoneNum = $('#count1').html();
				if(unDoneNum > 0){
					msg ="剩余【" + unDoneNum + "】题未完成，是否确定提交？";
					btnName.cancel = "去批改";
				}
				Helper.confirm2(msg,btnName).then(function(sure){
					var dfd = $.Deferred();
					if(sure == undefined){
						return dfd.reject();
					}
					if (!sure){
						var firstUnDoQueId = $('#j_question_panel').find('a');
						Helper.jumpQuestion(sheet.firstUnDoQueId);
						return dfd.reject();
					}else{
						return dfd.resolve();
					}
				}).then(function(){
					return Helper.commentary();
				}).then(function(commentary) {
					return $.post('saveReviewWork.htm', {
						homeworkId : $('#homeworkId').val(),
						homeworkDtlId : $('#homeworkDtlId').val(),
						commentary : commentary,
						isAgain : false,
						correctJson : JSON.stringify(sheet.questionResultList)
					});
				}).done(function(json) {
					if (json.success) {
						var tips = json.datas.tips || {};
						var message;
						if (json.datas.nextId != null) {
							message = "复批成功";
							setTimeout(function() {
								location.href = "correctWork.htm?homeworkDtlId=" + json.datas.nextId;
							}, 2000);
						} else {
							message = "恭喜你，作业已经全部复批完成啦";
							setTimeout(function() {
								window.close();
							}, 2000);
						}
						if (tips.leke) {
							message += "，乐豆+" + tips.leke;
						}
						if (tips.exp) {
							message += "，经验+" + tips.exp;
						}
						Utils.Notice.alert(message);
					}
				});
			});
		}
	};
	module.exports = Work;
});
