define(function(require, exports, module) {
	var $ = require('jquery');
	var JSON = require('json');
	var Utils = require('utils');
	var Dialog = require('dialog');

	require('homework/sheet.struct');
	require('homework/sheet.render');
	var Correct = require("homework/common/sheet.correct");
	var Helper = require('homework/common/sheet.helper');
	 require("homework/common/completionHelper");

	var Work = {
		// 初始化
		fInit : function() {
			Correct.initBindEvent();
			$('#correctSubmit').click(function() {
				var sheet = Correct.parseSheet();
				var msg = '已经全部批改，确认提交吗？';
				var btnName = {ok:"确定",cancel:"取消"};
				var unDoneNum = $('#count1').html();
				if(unDoneNum > 0){
					msg ="剩余【" + unDoneNum + "】题未批完，未批完的题目将按0分处理，确定要提交批改吗？";
					btnName.cancel = "去批改";
				}
				Helper.confirm2(msg,btnName).then(function(sure) {
					var dfd = $.Deferred();
					if(sure == undefined){
						return dfd.reject();
					}
					if (!sure){
						Helper.jumpQuestion(sheet.firstQueId);
						return dfd.reject();
					}else{
						return dfd.resolve();
					}
				}).then(function(){
						return Helper.commentary();
				}).then(function(commentary) {
					return $.post('saveCorrectWork.htm', {
						homeworkId : $('#homeworkId').val(),
						homeworkDtlId : $('#homeworkDtlId').val(),
						commentary : commentary,
						isAgain : $('#isAgain').val(),
						correctJson : JSON.stringify(sheet.questionResultList)
					});
				}).done(function(json) {
					if (json.success) {
						var tips = json.datas.tips || {};
						var message;
						if (json.datas.nextId != null) {
							message = "批改成功";
							setTimeout(function() {
								location.href = "correctWork.htm?homeworkDtlId=" + json.datas.nextId;
							}, 2000);
						} else {
							message = "恭喜你，作业已经全部批改完成啦";
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
				})
			});
		}
	};
	module.exports = Work;
});
