define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require('utils');
	var PaperCorrect = require('./sheet/paper.correct');
	var BaseService = require('./BaseService');

	var Service = {
		bind : function() {
			$('#panel_btnSubmitWork').click(Service.submitWork);
		},
		submitWork : function() {
			var sheet = PaperCorrect.parseSheet();
			console.log(sheet.correctJson)
			var message = '作业已全部批改，确定提交吗？', btnOk = '确定', btnCancel = '取消';
			if (sheet.unDoneNum > 0) {
				message = "剩余【" + sheet.unDoneNum + "】题未批完，未批完的题目将按0分处理，确定要提交批改吗？";
				btnCancel = "去批改";
			}
			var promise = BaseService.doConfirm(message, btnOk, btnCancel).then(function(sure) {
				var dfd = $.Deferred();
				if (sure) {
					dfd.resolve();
				} else {
					BaseService.goQueId(sheet.unqids[0]);
					dfd.reject();
				}
				return dfd.promise();
			});
			if (Leke.user.currentRoleId == 101) {
				promise = promise.then(function() {
					return BaseService.doRemark();
				});
			}
			promise.then(function(commentary) {
				return $.post('saveCorrectWork.htm', {
					homeworkId : Csts.homeworkId,
					homeworkDtlId : Csts.homeworkDtlId,
					isAgain : Csts.isAgain,
					commentary : commentary || '',
					correctJson : sheet.correctJson
				});
			}).done(function(json) {
				if (json.success) {
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
					var tips = json.datas.tips || {};
					if (tips.leke) {
						message += "，乐豆+" + tips.leke;
					}
					if (tips.exp) {
						message += "，经验+" + tips.exp;
					}
					Utils.Notice.alert(message);
				}
			});
		}
	}

	module.exports = Service;
});
