define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require('utils');
	var PaperReview = require('./sheet/paper.review');
	var BaseService = require('./BaseService');

	var Service = {
		bind : function() {
			$('#panel_btnSubmitWork').click(Service.submitWork);
		},
		submitWork : function() {
			var sheet = PaperReview.parseSheet();
			console.log(sheet.correctJson)
			var message = '作业已全部批改，确定提交吗？', btnOk = '确定', btnCancel = '取消';
			if (sheet.unDoneNum > 0) {
				message = "剩余【" + sheet.unDoneNum + "】题未复批，是否确定提交？";
				btnCancel = "去批改";
			}
			BaseService.doConfirm(message, btnOk, btnCancel).then(function(sure) {
				var dfd = $.Deferred();
				if (sure) {
					dfd.resolve();
				} else {
					BaseService.goQueId(sheet.unqids[0]);
					dfd.reject();
				}
				return dfd.promise();
		//	}).then(function() {
		//		return BaseService.doRemark();
			}).then(function(commentary) {
				return $.post('saveReviewWork.htm', {
					homeworkId : Csts.homeworkId,
					homeworkDtlId : Csts.homeworkDtlId,
					commentary : commentary || '',
					isAgain : false,
					correctJson : sheet.correctJson
				});
			}).done(function(json) {
				if (json.success) {
					var message;
					if (json.datas.nextId != null) {
						message = "复批成功";
						setTimeout(function() {
							location.href = "reviewWork.htm?homeworkDtlId=" + json.datas.nextId;
						}, 2000);
					} else {
						message = "恭喜你，作业已经全部复批完成啦";
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
