define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var utils = require('utils');
	var dialog = require('dialog');
	var QueService = require('question/service/userQueService');
	require('question/qed/qed');
	
	var QueryString = require('common/base/querystring');
	var WBPAP_QUE = require('question/wbpap-que');
	
	function doDisable(disable, questionId) {
		var dfd = $.Deferred();
		if(disable) {
			QueService.disable({
				questionId: questionId
			}).then(function() {
				dfd.resolve();
			}, function(msg) {
				utils.Notice.alert('原题删除失败！');
				dfd.resolve();
			});
		} else {
			dfd.resolve();
		}
		return dfd.promise();
	}
	
	function doSaveQue(que, disable) {
		var q = $.extend({}, que, {
			enabled: true,
			category: 2
		});
		q.questionId = null;
		QueService.add(q).then(function(datas) {
			doDisable(disable, que.questionId).then(function() {
				WBPAP_QUE.setLastQue(q);
				dialog.close('remoteAddDialog', 'success', datas.questionId);
			});
		}, function(msg) {
			utils.Notice.alert(msg || '题目保存失败！');
		});
	}
	
	function init() {
		var qs = QueryString.parse();
		var initQue = WBPAP_QUE.wrap(EdCtx.initJson);
		if(qs.curWorkbookNodeId) {
			initQue.wbnodes = [EdCtx.wbnode];
		}
		
		var owned = false;
		if(initQue && initQue.questionId) {
			owned = initQue.createdBy == Leke.user.userId;
		}
		var shareSchool = false;
		if(qs.curShareScope == '2') {
			shareSchool = true;
		}
		initQue.shareSchool = shareSchool;
		initQue.sharePlatform = false;

		ko.applyBindings({
			mode: 'add',
			question: initQue,
			doSave: function(que, vm) {
				if(owned) {
					dialog.confirm('是否要同时删除旧题？').done(function(disable) {
						doSaveQue(que, disable);
					});
				} else {
					doSaveQue(que);
				}
			},
			doCancel: function() {
				dialog.close('remoteAddDialog');
			}
		});
	}
	
	init();
});