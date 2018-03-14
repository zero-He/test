define(function(require, exports, module) {
	var ko = require('knockout');
	var _ = require('underscore');
	var utils = require('utils');
	var dialog = require('dialog');
	var $ = require('jquery');
	var http = require('common/base/http');
	var RepoQS = require('repository/service/RepoQueryString');
	var RepoService = require('repository/service/RepoService');
	var BasicInfoService = require('core-business/service/basicInfoService');
	var KO_QUE_CART = require('question/quecart');
	
	var URL_EDIT = Leke.ctx + '/auth/common/question/questionEdit.htm?';
	var URL_VIEW = Leke.ctx + '/auth/common/question/view.htm?';
	var qs = RepoQS.all;
	function urlEdit() {
		var params = _.extend({
			questionId: qs.questionId
		}, RepoQS.scope);
		if(qs.action == 'checkview') {
			params.action = 'checkedit';
		}
		return URL_EDIT + RepoQS.stringify(params);
	}
	function urlView(qid) {
		var params = _.extend({
			questionId: qid
		}, RepoQS.scope);
		return URL_VIEW + RepoQS.stringify(params);
	}
	
	function BtnsVM(params) {
		var self = this;
		var data = self.data = params.data;
		self.qs = qs;
		self.questionId = qs.questionId;
		self.roleId = Leke.user.currentRoleId;
		self.fold = ko.observable(false);
		self.level = ko.observable(data.level || []);
	}
	
	BtnsVM.prototype.doFavorite = function(evt) {
		var self = this;
		RepoService.doFavoriteQuestion(qs.questionId).then(function() {
			utils.Notice.alert('习题收藏成功');
		}, function(msg) {
			utils.Notice.alert(msg || '习题收藏操作失败');
		});
	};
	
	BtnsVM.prototype.doAddQueCart = function(evt) {
		var self = this;
		//var r = KO_QUE_CART.add(qs.questionId);
		
		var que = {};
		que.qid = self.questionId;
		var queString = 'div[data-question-id="' + que.qid + '"]';
		que.qtypeid = $('.queWrapper').find(queString).data('question-type-id');
		
		BasicInfoService.questionTypes().done(function(types){
			var gid = que.qtypeid ;
			var type = _.find(types || [] ,function(t){
				return t.questionTypeId == gid;
			});
			que.qtypename = type.questionTypeName;
			var r = KO_QUE_CART.addType(que);
			if(r !== false) {
				utils.Notice.alert('添加习题篮成功');
			}
		})
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
					utils.Notice.alert('保存成功');
					window.location = urlView(qid);
				}
			}
		});
	};
	
	BtnsVM.prototype.openCopyAdd = function(evt) {
		var self = this;
		var req = http.post(Leke.ctx + '/auth/common/userQue/canEdit.htm',{questionId: qs.questionId});
		req.then(function(datas){
			if(datas && datas.canEdit){
				window.open(Leke.domain.questionServerName + '/auth/common/question/add/index.htm?action=create&curScope=1&questionId=' + qs.questionId);	
			}else{
				utils.Notice.alert('您无该学段学科权限，不能操作！');
			}
		},function(msg){
			utils.Notice.alert(msg || '获取习题信息错误！');
		});
	};
	

	BtnsVM.prototype.doFeedback = function(evt) {
		var self = this;
		var datas = {};
		var $q = $(evt.target);
		require.async(['question/web/leke.question.feedback'], function(vm) {
			vm.openQuestionFeedback(self.questionId);
		});
	};
	
	BtnsVM.prototype.doSetPrime = function() {
		var self = this;
		var qid = self.questionId;
		var url = Leke.ctx + '/auth/global/question/common/question/setQuestionPrime.htm';
		var req = http.post(url,{questionId: qid});
		req.then(function(datas){
			utils.Notice.alert('标记精品成功！');
			self.level(1);
		},function(msg){
			utils.Notice.alert(msg || '标记精品错误！');
		});
	};
	
	BtnsVM.prototype.doSetGeneral = function() {
		var self = this;
		var qid = self.questionId;
		var url = Leke.ctx + '/auth/global/question/common/question/setQuestionGeneral.htm';
		var req = http.post(url,{questionId: qid});
		req.then(function(datas){
			utils.Notice.alert('标记普通成功！');
			self.level(2);
		},function(msg){
			utils.Notice.alert(msg || '标记普通错误！');
		});
	};
	
	ko.components.register('preview-btns', {
	    template: require('./preview-btns.html'),
	    viewModel: BtnsVM
	});
});