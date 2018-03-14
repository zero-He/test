define(function(require, exports, module) {
	require('common/forbidpaste');
	var $ = require('jquery');
	require('common/ui/ui-mask/jquery.mask');
	var Utils = require('utils');
	var Dialog = require('dialog');

	require('homework/sheet.struct');
	require('homework/sheet.render');
	require('homework/common/sheet.extend');
	var Answer = require('homework/common/sheet.answer');
	var Helper = require('homework/common/sheet.helper');

	var Work = {
		fInit : function() {
			this.fBindSaveSubmit();
			this.fAutoSaveSnapshot();
			this.fBindSaveSnapshot();
		},
		fAutoSaveSnapshot : function() {
			setInterval(function() {
				if ($('#savejob').isLock() || $('#submitjob').isLock()) {
					return;
				}
				Work.fSaveSnapshotHttp();
			}, 3 * 60 * 1000);
		},
		fBindSaveSnapshot : function() {
			$('#savejob').click(function() {
				if ($('#submitjob').isLock()) {
					return;
				}
				$(this).lock();
				Work.fSaveSnapshotHttp();
			});
		},
		fSaveSnapshotHttp : function() {
			var sheet = Answer.parseSheet();
			var homeworkDtlId = $('#homeworkDtlId').val();
			$.post("saveBugFix.htm", {
				homeworkDtlId : homeworkDtlId,
				answerJson : sheet.answerJson
			}, function(json) {
				if (json.success) {
					$('#savejob').unlock();
					Utils.Notice.tips('已为您自动保存草稿。')
				}
			});
		},
		fBindSaveSubmit : function() {
			$('#submitjob').click(function() {
				if ($('#savejob').isLock()) {
					return;
				}
				var that = this;
				var sheet = Answer.parseSheet();
				var msg ="作业已全部订正，确定提交吗？";
				var btnName = {ok:"确定",cancel:"取消"};
				var unDoneNum = $('#count1').html();
				if(unDoneNum > 0) {
					btnName.cancel = '去订正';
					msg = "剩余【" + unDoneNum + "】题未订正，是否确定提交作业？";
				}
				Helper.confirm2(msg,btnName).then(function(sure) {
					if(sure == undefined){
						return;
					}
					if(sure){
						Work.fSaveSubmitHttp(sheet.answerJson);
					}else {
						Helper.jumpQuestion(sheet.firstQueId);
					}
				});
			});
		},
		// 提交HTTP请求
		fSaveSubmitHttp : function(answerJson) {
			var homeworkDtlId = $('#homeworkDtlId').val();
			$.post("submitBugFix.htm", {
				homeworkDtlId : homeworkDtlId,
				answerJson : answerJson
			}, function(json) {
				if (json.success) {
					var url = "doBugFixSuccess.htm?homeworkDtlId=" + homeworkDtlId;
					var tips = json.datas.tips || {};
					var message = "订正提交成功";
					if (tips.leke) {
						url += "&lekeVal=" + tips.leke;
					}
					if (tips.exp) {
						url += "&expVal=" + tips.exp;
					}
					window.location.href = url;
				}
			});
		}
	};
	module.exports = Work;
});
