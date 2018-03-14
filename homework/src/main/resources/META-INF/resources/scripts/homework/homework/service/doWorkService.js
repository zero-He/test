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
				if ($('#btnSave').isLock() || $('#btnSubmit').isLock()) {
					return;
				}
				Work.fSaveSnapshotHttp();
			}, 3 * 60 * 1000);
		},
		fBindSaveSnapshot : function() {
			$('#btnSave').click(function() {
				if ($('#btnSubmit').isLock()) {
					return;
				}
				$(this).lock();
				Work.fSaveSnapshotHttp();
			});
		},
		fSaveSnapshotHttp : function() {
			var sheet = Answer.parseSheet();
			var homeworkDtlId = $('#homeworkDtlId').val();
			$.post("saveWork.htm", {
				homeworkDtlId : homeworkDtlId,
				answerJson : sheet.answerJson
			}, function(json) {
				if (json.success) {
					$('#btnSave').unlock();
					Utils.Notice.tips('已为您自动保存草稿。')
				}
			});
		},
		fBindSaveSubmit : function() {
			$('#btnSubmit').click(function() {
				if ($('#btnSave').isLock()) {
					return;
				}
				var that = this;
				$(that).lock();
				var sheet = Answer.parseSheet();
				var msg ="作业已全部完成，确定提交吗？";
				var btnName = {ok:"确定",cancel:"取消"};
				var unDoneNum = $('#count1').html();
				if(unDoneNum > 0){
					msg ="剩余【" + unDoneNum + "】题未完成，是否确定提交？";
					btnName.cancel = "去完成";
				}
				Helper.confirm2(msg,btnName).done(function(sure){
					if(sure == undefined){
						return ;
					}
					if(sure){
						Work.fSaveSubmitHttp(sheet.answerJson);
					}else{
						$(that).unlock();
						if(sheet.firstQueId != null){
							Helper.jumpQuestion(sheet.firstQueId);
						}
					}
				})
				
			});
		},
		// 提交HTTP请求
		fSaveSubmitHttp : function(answerJson) {
			var homeworkDtlId = $('#homeworkDtlId').val();
			$.post("submitWork.htm", {
				homeworkDtlId : homeworkDtlId,
				answerJson : answerJson
			}, function(json) {
				if (json.success) {
					window.location.href = "doWorkSuccess.htm?homeworkDtlId=" + homeworkDtlId;
				}
			});
		}
	};
	module.exports = Work;
});
