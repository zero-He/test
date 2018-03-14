define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('underscore');
	var utils = require('utils');
	var dialog = require('dialog');
	var http = require('common/base/http');
	var RepoQS = require('repository/service/RepoQueryString');
	var RepoService = require('repository/service/RepoService');
	var shareService = require('core-business/service/shareService');
	
	var URL_EDIT = Leke.ctx + '/auth/common/question/questionEdit.htm?';
	var URL_VIEW = Leke.ctx + '/auth/common/question/view.htm?';
	var URL_LIST = Leke.ctx + '/auth/schoolResearcher/question/school/checkList.htm?';
	var qs = RepoQS.all;
	function urlEdit() {
		var params = _.extend({}, RepoQS.scope, {
			questionId: qs.questionId,
			curScope: 2,
			action: 'checkedit'
		});
		return URL_EDIT + RepoQS.stringify(params);
	}
	function confirmToList(msg) {
		dialog.alert(msg).then(function() {
			window.location = URL_LIST;
		});
	}
	
	function BtnsVM(params) {
		var self = this;
		self.qs = qs;
		self.questionId = qs.questionId;
		self.roleId = Leke.user.currentRoleId;
		self.fold = ko.observable(false);
	}
	
	var API_DIR = Leke.ctx + '/auth/schoolResearcher/question/school/';
	
	BtnsVM.prototype.doPass = function(evt) {
		var self = this;
		var qids = [self.questionId];
		http.postJSON(API_DIR + 'doCheckPass.htm', qids).then(function() {
			confirmToList('习题审核通过操作成功');
		}, function(msg) {
			utils.Notice.alert(msg || '习题审核通过操作失败');
		});
	};
	
	BtnsVM.prototype.doReject = function(evt) {
		var self = this;
		var qids = [self.questionId];
		dialog.prompt('退回原因').then(function(reason) {
			http.postJSON(API_DIR + 'doCheckReject.htm', {
				resIds: qids,
				checkNote: reason
			}).then(function() {
				confirmToList('习题审核不通过操作成功');
			}, function(msg) {
				utils.Notice.alert(msg || '习题审核不通过操作失败');
			});
		});
	};
	
	BtnsVM.prototype.openEditDialog = function(evt) {
		var self = this;
		
		dialog.open({
			id: 'questionEditDialog',
			title: '编辑',
			url: urlEdit(),
			size: 'full',
			onClose: function(type, qid) {
				if(type === 'success') {
					confirmToList('习题内容编辑成功');
				}
			}
		});
	};
	
	BtnsVM.prototype.openEditOutlineNode = function(evt) {
		var self = this;
		require.async(['repository/common/edit-olnode'], function(openEd) {
			openEd(CheckCtx.olnode, {
				idInfo: {
					questionId: self.questionId
				},
				url: Leke.ctx + '/auth/schoolResearcher/question/school/updateOlnode.htm'
			});
		});
	};
	
	ko.components.register('checkview-btns', {
	    template: require('./checkview-btns.html'),
	    viewModel: BtnsVM
	});
});