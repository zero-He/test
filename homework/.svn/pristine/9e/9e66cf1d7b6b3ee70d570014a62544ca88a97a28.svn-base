define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require('utils');
	var PaperAnswer = require('../sheet/paper.answer');
	var BaseService = require('../BaseService');

	var Http = {
		saveWork : function(exerciseId, answerJson) {
			$.post("/auth/student/exercise/saveSnapshot.htm", {
				exerciseId : exerciseId,
				answerJson : answerJson
			}).done(function(json) {
				if (json.success) {
					Utils.Notice.tips('已为您自动保存草稿。')
				}
			});
		},
		submitWork : function(exerciseId, answerJson) {
			$.post("/auth/student/exercise/submitWork.htm", {
				exerciseId : exerciseId,
				answerJson : answerJson
			}).done(function(json) {
				if (json.success) {
					window.location.href = 'doWorkSuccess.htm?'+json.datas.urlParams;
				}
			});
		}
	}

	function startAutoSaveSnapshot() {
		setInterval(function() {
			Service.saveWork();
		}, 30 * 1000);
	}

	var Service = {
		bind : function() {
			startAutoSaveSnapshot();
			$('#panel_btnSaveWork').click(Service.saveWork);
			$('#panel_btnSubmitWork').click(Service.submitWork);
		},
		saveWork : function(sheet) {
			var sheet = PaperAnswer.parseSheet();
			Http.saveWork(Csts.exerciseId, sheet.answerJson);
		},
		submitWork : function(sheet) {
			var sheet = PaperAnswer.parseSheet();
			var message = '练习已全部完成，确定提交吗？', btnOk = '确定', btnCancel = '取消';
			if (sheet.unDoneNum > 0) {
				message = "剩余【" + sheet.unDoneNum + "】题未完成，是否确定提交？";
				btnCancel = "去完成";
			}
			BaseService.doConfirm(message, btnOk, btnCancel).done(function(sure) {
				if (sure) {
					Http.submitWork(Csts.exerciseId, sheet.answerJson);
				} else {
					BaseService.goQueId(sheet.unqids[0]);
				}
			});
		}
	}

	module.exports = Service;
});
